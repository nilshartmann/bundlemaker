package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeArtifactLabelProvider extends ArtifactTreeLabelProvider {

  /** - */
  private Helper _helper;

  /** - */
  boolean        _showReferenceCount;

  /** - */
  boolean        _toArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyTreeArtifactLabelProvider}.
   * </p>
   * 
   * @param helper
   */
  public DependencyTreeArtifactLabelProvider(Helper helper) {

    //
    Assert.isNotNull(helper);

    //
    _helper = helper;
  }

  /**
   * <p>
   * </p>
   * 
   * @param showReferenceCount wether to show references count or not. Calculating references might be expensive.
   * @param toArtifacts
   */
  public void setShowReferenceCount(boolean showReferenceCount, boolean toArtifacts) {

    //
    _showReferenceCount = showReferenceCount;
    _toArtifacts = toArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getText(Object obj) {

    //
    if (_toArtifacts) {
      return getRightSideLabel((IBundleMakerArtifact) obj);

    }
    //
    else {
      return getLeftSideLabel((IBundleMakerArtifact) obj);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private String getLeftSideLabel(IBundleMakerArtifact artifact) {

    //
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(super.getText(artifact));

    if (_showReferenceCount) {

      // Warning: might be expensive to count....
      int dependencyCount = 0;
      Set<IBundleMakerArtifact> targetArtifacts = new HashSet<IBundleMakerArtifact>();

      //
      Collection<IDependency> dependencies = artifact.getDependenciesTo(_helper.getUnfilteredTargetArtifacts());
      if (dependencies != null) {
        for (IDependency dependency : dependencies) {
          for (IDependency coreDependency : dependency.getCoreDependencies()) {

            //
            Collection<IBundleMakerArtifact> unfilteredSourceDependencies = _helper.getUnfilteredSourceArtifacts();

            IBundleMakerArtifact fromArtifact = coreDependency.getFrom();

            //
            if (unfilteredSourceDependencies.contains(fromArtifact)) {
              targetArtifacts.add(coreDependency.getTo());
              dependencyCount = dependencyCount + coreDependency.getWeight();
            }
          }
        }
      }

      stringBuilder.append(" (");
      stringBuilder.append(dependencyCount);
      stringBuilder.append("/");
      // stringBuilder.append(dependencyCount == 1 ? " Reference to " : " References to ");
      stringBuilder.append(targetArtifacts.size());
      // stringBuilder.append(targetArtifacts.size() == 1 ? " Artifact)" : " Artifacts)");
      stringBuilder.append(")");
    }

    //
    return stringBuilder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private String getRightSideLabel(IBundleMakerArtifact artifact) {

    //
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(super.getText(artifact));

    if (_showReferenceCount) {

      int dependencyCount = 0;
      Set<IBundleMakerArtifact> targetArtifacts = new HashSet<IBundleMakerArtifact>();

      Collection<IDependency> dependencies = artifact.getDependenciesFrom(_helper.getUnfilteredSourceArtifacts());
      if (dependencies != null) {
        for (IDependency dependency : dependencies) {
          for (IDependency coreDependency : dependency.getCoreDependencies()) {

            //
            Collection<IBundleMakerArtifact> unfilteredTargetDependencies = _helper.getUnfilteredTargetArtifacts();

            IBundleMakerArtifact toArtifact = coreDependency.getTo();

            //
            if (unfilteredTargetDependencies.contains(toArtifact)) {
              targetArtifacts.add(coreDependency.getFrom());
              dependencyCount = dependencyCount + coreDependency.getWeight();
            }
          }
        }
      }
      stringBuilder.append(" (");
      stringBuilder.append(dependencyCount);
      stringBuilder.append("/");
      // stringBuilder.append(dependencyCount == 1 ? " Reference to " : " References to ");
      stringBuilder.append(targetArtifacts.size());
      // stringBuilder.append(targetArtifacts.size() == 1 ? " Artifact)" : " Artifacts)");
      stringBuilder.append(")");
    }

    //
    return stringBuilder.toString();
  }
}
