package org.bundlemaker.core.analysis.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MoveResourcesCommand extends AbstractArtifactTreeTransformationCommand {

  /** - */
  private IBundleMakerArtifact                         _targetArtifact;

  /** - */
  private List<IResourceArtifactSelector>              _resourceArtifactSelectors;

  /** - */
  private Map<IResourceArtifact, IBundleMakerArtifact> _movedResourcesWithFormerParents;

  /**
   * <p>
   * Creates a new instance of type {@link MoveResourcesCommand}.
   * </p>
   */
  public MoveResourcesCommand() {

    //
    _resourceArtifactSelectors = new ArrayList<IResourceArtifactSelector>();
    _movedResourcesWithFormerParents = new HashMap<IResourceArtifact, IBundleMakerArtifact>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param e
   */
  public void add(IResourceArtifactSelector e) {
    _resourceArtifactSelectors.add(e);
  }

  /**
   * <p>
   * </p>
   * 
   * @param c
   */
  public void addAll(Collection<? extends IResourceArtifactSelector> c) {
    _resourceArtifactSelectors.addAll(c);
  }

  public void setTargetArtifact(IBundleMakerArtifact targetArtifact) {
    _targetArtifact = targetArtifact;
  }

  @Override
  public boolean isReadyToExecute() {
    return _targetArtifact != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onUndo() throws Exception {

    //
    for (Entry<IResourceArtifact, IBundleMakerArtifact> entry : _movedResourcesWithFormerParents.entrySet()) {
      entry.getValue().addArtifact(entry.getKey());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onExecute() throws Exception {

    //
    for (IResourceArtifactSelector selector : _resourceArtifactSelectors) {

      //
      for (IResourceArtifact resourceArtifact : selector.getResourceArtifacts()) {

        //
        if (_targetArtifact.canAdd(resourceArtifact)) {
          _movedResourcesWithFormerParents.put(resourceArtifact, resourceArtifact.getParent());
          _targetArtifact.addArtifact(resourceArtifact);
        }
      }
    }
  }
}
