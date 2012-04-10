package org.bundlemaker.core.ui.view.dependencytable;

import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LazyDependencyProvider implements ILazyContentProvider {

  /** - */
  private TableViewer   _tableViewer;

  /** - */
  private IDependency[] _dependencies;

  /**
   * <p>
   * Creates a new instance of type {@link LazyDependencyProvider}.
   * </p>
   * 
   * @param tableViewer
   */
  public LazyDependencyProvider(TableViewer tableViewer) {
    Assert.isNotNull(tableViewer);

    _tableViewer = tableViewer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    this._dependencies = (IDependency[]) newInput;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateElement(int index) {
    if (_dependencies.length > index) {
      _tableViewer.replace(_dependencies[index], index);
    }
  }
}
