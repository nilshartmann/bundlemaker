package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.eclipse.core.runtime.Assert;

public class DispatchingArtifactTreeVisitor implements IArtifactTreeVisitor {

  /** - */
  private IArtifactTreeVisitor[] _artifactTreeVisitors;

  /**
   * <p>
   * Creates a new instance of type {@link DispatchingArtifactTreeVisitor}.
   * </p>
   * 
   * @param artifactTreeVisitors
   */
  public DispatchingArtifactTreeVisitor(IArtifactTreeVisitor... artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
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
    for (IArtifactTreeVisitor artifactTreeVisitors : _artifactTreeVisitors) {
      visitChildren = artifactTreeVisitors.visit(packageArtifact) || visitChildren;
    }

    //
    return visitChildren;
  }
}
