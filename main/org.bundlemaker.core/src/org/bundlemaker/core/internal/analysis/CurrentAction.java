package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.modules.ChangeAction;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@Deprecated
public class CurrentAction {

  /** - */
  private ChangeAction         _changeAction;

  /** - */
  private IBundleMakerArtifact _parent;

  /** - */
  private IBundleMakerArtifact _child;

  /**
   * <p>
   * Creates a new instance of type {@link CurrentAction}.
   * </p>
   * 
   * @param parent
   * @param child
   * @param changeAction
   */
  public CurrentAction(IBundleMakerArtifact parent, IBundleMakerArtifact child, ChangeAction changeAction) {

    Assert.isNotNull(parent);
    Assert.isNotNull(child);
    Assert.isNotNull(changeAction);

    _parent = parent;
    _child = child;
    _changeAction = changeAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ChangeAction getChangeAction() {
    return _changeAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IBundleMakerArtifact getParent() {
    return _parent;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IBundleMakerArtifact getChild() {
    return _child;
  }
}
