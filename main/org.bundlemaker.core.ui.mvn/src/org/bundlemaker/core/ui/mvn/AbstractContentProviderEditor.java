package org.bundlemaker.core.ui.mvn;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractContentProviderEditor implements IProjectContentProviderEditor {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canChangeAnalyzeMode(IProjectContentProvider projectContentProvider, Object element) {

    // can't edit analyze mode
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAnalyzeMode(IProjectContentProvider projectContentProvider, Object element, AnalyzeMode analyzeMode) {
    // default: do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canEdit(Object selectedObject) {

    // can't edit anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean edit(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object
      selectedObject) {

    // can't edit anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canRemove(Object selectedObject) {

    // can't remove anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object
      selectedObject) {

    // default: do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#getContextMenuActions(org.bundlemaker
   * .core.IBundleMakerProject, java.util.List)
   */
  @Override
  public List<IAction> getContextMenuActions(IBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {

    // default: no context menu contribution
    return null;
  }
}
