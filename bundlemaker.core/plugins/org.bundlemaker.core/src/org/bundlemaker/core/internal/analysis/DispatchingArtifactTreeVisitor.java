package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core._type.ITypeArtifact;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.runtime.Assert;

public class DispatchingArtifactTreeVisitor implements IAnalysisModelVisitor {

  /** - */
  private IAnalysisModelVisitor[] _artifactTreeVisitors;

  /**
   * <p>
   * Creates a new instance of type {@link DispatchingArtifactTreeVisitor}.
   * </p>
   * 
   * @param artifactTreeVisitors
   */
  public DispatchingArtifactTreeVisitor(IAnalysisModelVisitor... artifactTreeVisitors) {
    Assert.isNotNull(artifactTreeVisitors);

    //
    _artifactTreeVisitors = artifactTreeVisitors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IRootArtifact rootArtifact) {

    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(rootArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IGroupArtifact groupArtifact) {
    
    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(groupArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IModuleArtifact moduleArtifact) {
    
    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(moduleArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IResourceArtifact resourceArtifact) {
    
    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(resourceArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(ITypeArtifact typeArtifact) {
    
    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(typeArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IPackageArtifact packageArtifact) {
    
    //
    boolean visitChildren = false;

    //
    for (IAnalysisModelVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(packageArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }
}
