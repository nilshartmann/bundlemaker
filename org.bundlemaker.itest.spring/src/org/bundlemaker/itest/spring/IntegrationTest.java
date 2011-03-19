package org.bundlemaker.itest.spring;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.itest.AbstractIntegrationTest;
import org.bundlemaker.itest.spring.experimental.PatternBasedTypeSelector;
import org.bundlemaker.itest.spring.tests.ModularizedSystemTests;
import org.bundlemaker.itest.spring.tests.ModuleTest;
import org.bundlemaker.itest.spring.tests.ResourceModelTests;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IntegrationTest extends AbstractIntegrationTest {

  /**
   * <p>
   * Creates a new instance of type {@link IntegrationTest}.
   * </p>
   */
  public IntegrationTest() {
    // super("spring", true, false, false, true);
    super("spring", true, true, false, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doAddProjectDescription(IBundleMakerProject bundleMakerProject) throws Exception {

    // create the project description
    IntegrationTestUtils.createProjectDescription(bundleMakerProject.getProjectDescription());

    // save the project description
    bundleMakerProject.getProjectDescription().save();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doAddTransformations(IModularizedSystem modularizedSystem) {

    // add the transformations
    IntegrationTestUtils.addEmbedAntTransformation(modularizedSystem);
    IntegrationTestUtils.addModularizeSpringTransformation(modularizedSystem);

    //
    modularizedSystem.getTransformations().add(new DeleteModuleTransformation("Spring", "2.5.6"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doPostProcessModularizedSystem(IModularizedSystem modularizedSystem) {

    // add experimental
    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "bsh.**" }, null, "bsh", "2.0b4"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "com.sun.jmx.**", "com.sun.management.**", "com.sun.rowset.**",
            "com.sun.activation.**", "javax.activation.**", "javax.jws.**", "javax.management.**",
            "org.omg.stub.javax.management.**", "javax.xml.**", "javax.sql.rowset.**", "javax.annotation.**",
            "org.w3c.dom.**" }, null, "jdk16", "jdk16"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "com.thoughtworks.qdox.**" }, null, "qdox", "1.5"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "javax.persistence.**" }, null, "toplink-essentials", "1.0"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "javax.transaction.**" }, null, "jta", "0.0.0"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "junit.**" }, null, "junit", "4.4"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "org.apache.commons.collections.**" }, null, "commons-collections",
            "3.2"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "org.aspectj.**" }, null, "aspectjrt", "0.0.0"));

    modularizedSystem.getTypeSelectors().add(
        new PatternBasedTypeSelector(new String[] { "org.aopalliance.**" }, null, "aopalliance", "0.0.0"));

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doCheckBundleMakerProject(IBundleMakerProject bundleMakerProject) {

    // check the resource model
    ResourceModelTests.checkResourceModel(bundleMakerProject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doCheckModularizedSystem(IModularizedSystem modularizedSystem) {

    //
    ModularizedSystemTests.testGetModules(modularizedSystem);
    ModuleTest.testModules(modularizedSystem);
  }
}