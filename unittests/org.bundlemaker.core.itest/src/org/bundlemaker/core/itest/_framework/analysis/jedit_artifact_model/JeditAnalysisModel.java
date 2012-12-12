package org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.junit.Assert;

/**
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class JeditAnalysisModel {

  /** - */
  private IRootArtifact        _rootArtifact;

  /** - */
  private IBundleMakerArtifact _jeditModuleArtifact;

  /** - */
  private IBundleMakerArtifact _velocityModuleArtifact;

  /** - */
  private IBundleMakerArtifact _jreArtifact;

  /** - */
  private IBundleMakerArtifact _group1Artifact;

  /** - */
  private IBundleMakerArtifact _group2Artifact;

  /** - */
  private IBundleMakerArtifact _missingTypesArtifact;

  /** - */
  private IModularizedSystem   _modularizedSystem;

  /** - */
  private int                  _modifiedNotificationCount;

  /**
   * <p>
   * Creates a new instance of type {@link JeditAnalysisModel}.
   * </p>
   * 
   * @param modularizedSystem
   * @param configuration
   */
  public JeditAnalysisModel(IModularizedSystem modularizedSystem, IAnalysisModelConfiguration configuration) {

    //
    Assert.assertNotNull(modularizedSystem);
    Assert.assertNotNull(configuration);

    _modularizedSystem = modularizedSystem;
    _rootArtifact = createArtifactModel(modularizedSystem, configuration);

    _jreArtifact = assertJreArtifactModule(_rootArtifact);

    _group1Artifact = assertGroupArtifact(_rootArtifact, "group1");
    _group2Artifact = assertGroupArtifact(_rootArtifact, "group1/group2");

    _jeditModuleArtifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact,
        _modularizedSystem.getName(),
        "1.0.0");
    assertNode(_jeditModuleArtifact.getParent(), IGroupArtifact.class, "group2");
    assertNode(_jeditModuleArtifact.getParent().getParent(), IGroupArtifact.class, "group1");

    _velocityModuleArtifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact, "velocity",
        "1.5");

    // add an model modified listener
    _rootArtifact.addAnalysisModelModifiedListener(new IAnalysisModelModifiedListener() {
      @Override
      public void analysisModelModified() {
        _modifiedNotificationCount++;
      }
    });
  }

  public int getModifiedNotificationCount() {
    return _modifiedNotificationCount;
  }

  public IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  public IBundleMakerArtifact getJeditModuleArtifact() {
    return _jeditModuleArtifact;
  }

  public IBundleMakerArtifact getVelocityModuleArtifact() {
    return _velocityModuleArtifact;
  }

  public IBundleMakerArtifact getJreArtifact() {
    return _jreArtifact;
  }

  public IBundleMakerArtifact getGroup1Artifact() {
    return _group1Artifact;
  }

  public IBundleMakerArtifact getGroup2Artifact() {
    return _group2Artifact;
  }

  public IBundleMakerArtifact getMissingTypesArtifact() {
    return _missingTypesArtifact;
  }

  public IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IDependency> getDependenciesTo(IBundleMakerArtifact from, IBundleMakerArtifact... to) {

    // test dependencies
    List<IDependency> deps = new LinkedList<IDependency>(from.getDependenciesTo(
        to));

    //
    Collections.sort(deps, new Comparator<IDependency>() {
      public int compare(IDependency o1, IDependency o2) {
        return o1.toString().compareTo(o2.toString());
      }
    });

    //
    return deps;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param qualifiedName
   * @return
   */
  private IGroupArtifact assertGroupArtifact(IBundleMakerArtifact rootArtifact, String qualifiedName) {
    IGroupArtifact result = ArtifactVisitorUtils.findGroupArtifactByQualifiedName(rootArtifact, qualifiedName);
    Assert.assertNotNull(qualifiedName, result);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  private IModuleArtifact assertJreArtifactModule(final IRootArtifact rootArtifact) {

    //
    final IModuleArtifact[] result = new IModuleArtifact[1];

    //
    rootArtifact.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        if (rootArtifact.getModularizedSystem().getExecutionEnvironment().equals(moduleArtifact.getAssociatedModule())) {
          result[0] = moduleArtifact;
        }
        return false;
      }
    });

    //
    Assert.assertNotNull(result[0]);
    return result[0];
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param type
   * @param nodeName
   */
  private void assertNode(IBundleMakerArtifact node, Class<?> type, String nodeName) {
    Assert.assertTrue(String.format("Node '%s' has to be assignable from %s", node, type),
        type.isAssignableFrom(node.getClass()));
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private IRootArtifact createArtifactModel(IModularizedSystem modularizedSystem,
      IAnalysisModelConfiguration configuration) {

    IRootArtifact rootArtifact = modularizedSystem.getAnalysisModel(configuration).getRoot();

    Assert.assertNotNull(rootArtifact);
    // Assert.assertEquals(4, rootArtifact.getChildren().size());

    // assert JRE
    Assert.assertNotNull(ArtifactVisitorUtils.findJreModuleArtifact(rootArtifact));

    // return the result
    return rootArtifact;
  }
}
