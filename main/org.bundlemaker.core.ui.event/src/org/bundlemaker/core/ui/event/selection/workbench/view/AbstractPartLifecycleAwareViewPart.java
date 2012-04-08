package org.bundlemaker.core.ui.event.selection.workbench.view;

import org.bundlemaker.core.ui.event.selection.internal.lifecycle.LifecycleAwarePartListener;
import org.bundlemaker.core.ui.event.selection.workbench.ILifecycleAwarePart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPartLifecycleAwareViewPart extends ViewPart implements ILifecycleAwarePart {

  /** - */
  private LifecycleAwarePartListener _partListener;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(IViewSite site) throws PartInitException {
    super.init(site);

    // register part listener
    _partListener = new LifecycleAwarePartListener(this);
    this.getSite().getPage().addPartListener(_partListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // register part listener
    this.getSite().getPage().removePartListener(_partListener);

    // call super
    super.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartActivated() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartBroughtToTop() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartClosed() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartDeactivated() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartOpened() {
    //
  }
}
