package org.bundlemaker.core.ui.view.dependencytree;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

public class CropableDependencyTreeComposite extends Composite {

  /** - */
  private DependencyTreeComposite      _dependencyTreeComposite;

  /** - */
  private ToolItem                     _cropButton;

  /** - */
  private ToolItem                     _backButton;

  /** - */
  private ToolItem                     _forwardButton;

  /** - */
  private ToolItem                     _clearButton;

  /** - */
  private int                          _currentPosition = -1;

  /** - */
  private List<List<IDependency>>      _dependencySelectionList;

  /** - */
  private IDependencySelectionListener _dependencySelectionListener;

  /** - */
  private String                       _detailDependencyProviderId;

  /** - */
  private ToolItem                     _autoExpandFrom;

  /** - */
  private ToolItem                     _autoExpandTo;

  /** - */
  private ExpandStrategy               _expandStrategy;

  /**
   * <p>
   * Creates a new instance of type {@link CropableDependencyTreeComposite}.
   * </p>
   * 
   * @param parent
   */
  public CropableDependencyTreeComposite(Composite parent, String detailDependencyProviderId) {
    super(parent, SWT.NONE);

    //
    Assert.isNotNull(detailDependencyProviderId);

    //
    _detailDependencyProviderId = detailDependencyProviderId;
    _dependencySelectionList = new LinkedList<List<IDependency>>();

    //
    _expandStrategy = new ExpandStrategy();

    //
    this.setLayout(new org.eclipse.swt.layout.GridLayout());

    //
    ToolBar toolbar = new ToolBar(this, SWT.FLAT);

    // the dependency tree composite
    _dependencyTreeComposite = new DependencyTreeComposite(this, _detailDependencyProviderId, _expandStrategy) {

      /**
       * {@inheritDoc}
       */
      @Override
      protected String getDependencySelectionId() {

        // the dependency selection id
        String dependencySelectionId = CropableDependencyTreeComposite.this.getDependencySelectionId();

        if (dependencySelectionId != null) {
          return dependencySelectionId;
        }

        return super.getDependencySelectionId();
      }

      // @Override
      // protected void toViewerSelected(TreeViewer fromTreeViewer, TreeViewer toTreeViewer) {
      // ISelection selection = fromTreeViewer.getSelection();
      // cropCurrentSelection();
      // fromTreeViewer.setSelection(selection);
      // }

      // @Override
      // protected void fromViewerSelected(TreeViewer fromTreeViewer, TreeViewer toTreeViewer) {
      // ISelection selection = toTreeViewer.getSelection();
      // cropCurrentSelection();
      // toTreeViewer.setSelection(selection);
      // }
    };

    createCropButtons(toolbar);
    new ToolItem(toolbar, SWT.SEPARATOR);
    createAutoExpandMenus(toolbar);

    _dependencySelectionListener = new IDependencySelectionListener() {
      @Override
      public void dependencySelectionChanged(IDependencySelectionChangedEvent event) {
        if (event.getProviderId().equals(DependencyTreeView.ID)) {
          enableButtons();
        }
      }
    };

    //
    Selection.instance().getDependencySelectionService()
        .addDependencySelectionListener(Selection.DETAIL_DEPENDENCY_SELECTION_ID, _dependencySelectionListener);
  }

  /**
   * <p>
   * </p>
   * 
   * @param toolbar
   */
  private void createAutoExpandMenus(ToolBar toolbar) {

    //
    Widget[] widgets = createAutoExpandMenu(toolbar);
    _autoExpandFrom = (ToolItem) widgets[1];
    _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_MODULES.getImage());

    // the selection listener
    SelectionListener selectionListener = new SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      public void widgetSelected(SelectionEvent e) {
        MenuItem item = (MenuItem) e.widget;
        if (item.getSelection()) {
          if ("Group".equals(item.getText())) {
            _expandStrategy.setFromTreeViewerAutoExpandType(IGroupArtifact.class);
            _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_GROUPS.getImage());
          } else if ("Module".equals(item.getText())) {
            _expandStrategy.setFromTreeViewerAutoExpandType(IModuleArtifact.class);
            _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_MODULES.getImage());
          } else if ("Package".equals(item.getText())) {
            _expandStrategy.setFromTreeViewerAutoExpandType(IPackageArtifact.class);
            _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_PACKAGES.getImage());
          } else if ("Resource".equals(item.getText())) {
            _expandStrategy.setFromTreeViewerAutoExpandType(IResourceArtifact.class);
            _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_RESOURCES.getImage());
          }
        }
      }
    };

    //
    for (MenuItem menuItem : ((Menu) widgets[0]).getItems()) {
      menuItem.addSelectionListener(selectionListener);
    }

    //
    widgets = createAutoExpandMenu(toolbar);
    _autoExpandTo = (ToolItem) widgets[1];
    _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_TO_MODULES.getImage());
    // _autoExpandTo.addSelectionListener(new SelectionAdapter() {
    //
    // @Override
    // public void widgetSelected(SelectionEvent e) {
    // System.out.println("widgetSelected" + e.getSource());
    // }
    //
    // @Override
    // public void widgetDefaultSelected(SelectionEvent e) {
    // System.out.println("widgetDefaultSelected" + e.getSource());
    // }
    // });

    //
    selectionListener = new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        MenuItem item = (MenuItem) e.widget;
        if (item.getSelection()) {
          if ("Group".equals(item.getText())) {
            _expandStrategy.setToTreeViewerAutoExpandType(IGroupArtifact.class);
            _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_TO_GROUPS.getImage());
          } else if ("Module".equals(item.getText())) {
            _expandStrategy.setToTreeViewerAutoExpandType(IModuleArtifact.class);
            _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_TO_MODULES.getImage());
          } else if ("Package".equals(item.getText())) {
            _expandStrategy.setToTreeViewerAutoExpandType(IPackageArtifact.class);
            _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_TO_PACKAGES.getImage());
          } else if ("Resource".equals(item.getText())) {
            _expandStrategy.setToTreeViewerAutoExpandType(IResourceArtifact.class);
            _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_TO_RESOURCES.getImage());
          }
        }
      }
    };

    //
    for (MenuItem menuItem : ((Menu) widgets[0]).getItems()) {
      menuItem.addSelectionListener(selectionListener);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param toolbar
   */
  public void createCropButtons(ToolBar toolbar) {

    // the back button
    _backButton = new ToolItem(toolbar, SWT.NONE);
    _backButton.setImage(UIDependencyTreeImages.ENABLED_BACKWARD_NAV.getImage());
    _backButton.setDisabledImage(UIDependencyTreeImages.DISABLED_BACKWARD_NAV.getImage());

    // the forward button
    _forwardButton = new ToolItem(toolbar, SWT.NONE);
    _forwardButton.setImage(UIDependencyTreeImages.ENABLED_FORWARD_NAV.getImage());
    _forwardButton.setDisabledImage(UIDependencyTreeImages.DISABLED_FORWARD_NAV.getImage());

    // the clear button
    _clearButton = new ToolItem(toolbar, SWT.NONE);
    _clearButton.setImage(UIDependencyTreeImages.ENABLED_PIN_SELECTION_CLEAR.getImage());
    _clearButton.setDisabledImage(UIDependencyTreeImages.DISABLED_PIN_SELECTION_CLEAR.getImage());

    // the crop button
    _cropButton = new ToolItem(toolbar, SWT.NONE);
    _cropButton.setImage(UIDependencyTreeImages.ENABLED_PIN_SELECTION_ADD.getImage());
    _cropButton.setDisabledImage(UIDependencyTreeImages.DISABLED_PIN_SELECTION_ADD.getImage());

    //
    GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
    _dependencyTreeComposite.setLayoutData(gridData);

    //
    _cropButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        cropCurrentSelection();
      }
    });

    _backButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        if (_currentPosition > 0) {
          _currentPosition = _currentPosition - 1;
          _dependencyTreeComposite.setDependencies(_dependencySelectionList.get(_currentPosition));

          enableButtons();
        }
      }
    });

    _forwardButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        if (_currentPosition < _dependencySelectionList.size() - 1) {
          _currentPosition = _currentPosition + 1;
          _dependencyTreeComposite.setDependencies(_dependencySelectionList.get(_currentPosition));

          enableButtons();
        }
      }
    });

    _clearButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

        _dependencyTreeComposite.setDependencies(_dependencySelectionList.get(0));

        // 'cut' the selection history
        for (int i = _dependencySelectionList.size() - 1; i > 0; i--) {
          _dependencySelectionList.remove(i);
        }

        _currentPosition = 0;

        enableButtons();
      }
    });

    enableButtons();
  }

  public Widget[] createAutoExpandMenu(final ToolBar toolbar) {

    // Rectangle clientArea = shell.getClientArea ();
    // toolBar.setLocation(clientArea.x, clientArea.y);
    final Menu menu = new Menu(toolbar.getShell(), SWT.POP_UP);

    final ToolItem toolItem = new ToolItem(toolbar, SWT.DROP_DOWN);

    MenuItem menuItemGroup = new MenuItem(menu, SWT.RADIO);
    menuItemGroup.setText("Group");

    MenuItem menuItemModule = new MenuItem(menu, SWT.RADIO);
    menuItemModule.setText("Module");

    MenuItem menuItemPackage = new MenuItem(menu, SWT.RADIO);
    menuItemPackage.setText("Package");

    MenuItem menuItemResource = new MenuItem(menu, SWT.RADIO);
    menuItemResource.setText("Resource");

    toolItem.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        if (event.detail == SWT.ARROW) {
          Rectangle rect = toolItem.getBounds();
          Point pt = new Point(rect.x, rect.y + rect.height);
          pt = toolbar.toDisplay(pt);
          menu.setLocation(pt.x, pt.y);
          menu.setVisible(true);
        }
      }
    });

    menuItemModule.setSelection(true);

    //
    return new Widget[] { menu, toolItem };
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   */
  public void setDependencies(List<IDependency> dependencies) {

    // check disposed
    if (checkDisposed()) {
      return;
    }

    _dependencyTreeComposite.setDependencies(dependencies);
    _currentPosition = 0;
    _dependencySelectionList.clear();
    _dependencySelectionList.add(dependencies);

    if (dependencies.size() > 0) {

      IDependency dependency = dependencies.get(0);

//      //
//      if (dependency.getFrom() instanceof IGroupArtifact) {
//        _expandStrategy.setFromTreeViewerAutoExpandType(IGroupArtifact.class);
//        _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_GROUPS.getImage());
//      } else if (dependency.getFrom() instanceof IModuleArtifact) {
//        _expandStrategy.setFromTreeViewerAutoExpandType(IModuleArtifact.class);
//        _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_MODULES.getImage());
//      } else if (dependency.getFrom() instanceof IPackageArtifact) {
//        _expandStrategy.setFromTreeViewerAutoExpandType(IPackageArtifact.class);
//        _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_PACKAGES.getImage());
//      } else if (dependency.getFrom() instanceof IResourceArtifact) {
//        _expandStrategy.setFromTreeViewerAutoExpandType(IResourceArtifact.class);
//        _autoExpandFrom.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_RESOURCES.getImage());
//      }
//      
//      if (dependency.getTo() instanceof IGroupArtifact) {
//        _expandStrategy.setToTreeViewerAutoExpandType(IGroupArtifact.class);
//        _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_GROUPS.getImage());
//      } else if (dependency.getTo() instanceof IModuleArtifact) {
//        _expandStrategy.setToTreeViewerAutoExpandType(IModuleArtifact.class);
//        _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_MODULES.getImage());
//      } else if (dependency.getTo() instanceof IPackageArtifact) {
//        _expandStrategy.setToTreeViewerAutoExpandType(IPackageArtifact.class);
//        _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_PACKAGES.getImage());
//      } else if (dependency.getTo() instanceof IResourceArtifact) {
//        _expandStrategy.setToTreeViewerAutoExpandType(IResourceArtifact.class);
//        _autoExpandTo.setImage(UIDependencyTreeImages.AUTO_EXPAND_FROM_RESOURCES.getImage());
//      }
    }

    enableButtons();
  }

  @Override
  public void dispose() {

    // dispose
    Selection.instance().getDependencySelectionService()
        .removeDependencySelectionListener(_dependencySelectionListener);

    //
    super.dispose();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getDependencySelectionId() {
    return null;
  }

  private void enableButtons() {

    //
    boolean dependenciesSelected = _dependencyTreeComposite.getSelectedDetailDependencies() != null
        && _dependencyTreeComposite.getSelectedDetailDependencies().size() > 0;

    if (checkDisposed()) {
      return;
    }

    try {
      //
      _backButton.setEnabled(dependenciesSelected && _currentPosition > 0);
      _forwardButton.setEnabled(dependenciesSelected && _currentPosition < _dependencySelectionList.size() - 1);
      _clearButton.setEnabled(dependenciesSelected && _dependencySelectionList.size() > 1);
      _cropButton.setEnabled(dependenciesSelected);
    } catch (Exception e) {
      checkDisposed();
    }
  }

  /**
   * <p>
   * </p>
   */
  private boolean checkDisposed() {

    //
    if (_backButton.isDisposed() || _forwardButton.isDisposed() || _clearButton.isDisposed()
        || _cropButton.isDisposed()) {

      //
      Selection.instance().getDependencySelectionService()
          .removeDependencySelectionListener(_dependencySelectionListener);

      //
      return true;
    }

    //
    return false;
  }

  private void cropCurrentSelection() {

    // 'cut' the selection history
    for (int i = _dependencySelectionList.size() - 1; i > _currentPosition; i--) {
      _dependencySelectionList.remove(i);
    }

    // System.out.println("AFTER CLEAN IS ");
    // for (List<IDependency> dependencies : _dependencySelectionList) {
    // System.out.println(" - " + dependencies);
    // }

    //
    List<IDependency> currentSelection = _dependencyTreeComposite.getSelectedDetailDependencies();
    _dependencySelectionList.add(currentSelection);
    _currentPosition = _currentPosition + 1;
    _dependencyTreeComposite.setDependencies(currentSelection);

    enableButtons();
  }
}
