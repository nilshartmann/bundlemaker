package org.bundlemaker.core.ui.view.dependencytree;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class CropableDependencyTreeComposite extends Composite {

  /** - */
  private DependencyTreeComposite _dependencyTreeComposite;

  /** - */
  private ToolItem                _cropButton;

  /** - */
  private ToolItem                _backButton;

  /** - */
  private ToolItem                _forwardButton;

  /** - */
  private ToolItem                _clearButton;

  /** - */
  private int                     _currentPosition = -1;

  /** - */
  private List<List<IDependency>> _dependencySelectionList;

  private IDependencySelectionListener _dependencySelectionListener;

  /**
   * <p>
   * Creates a new instance of type {@link CropableDependencyTreeComposite}.
   * </p>
   * 
   * @param parent
   */
  public CropableDependencyTreeComposite(Composite parent, String detailDependencySelectionId) {
    super(parent, SWT.NONE);

    //
    _dependencySelectionList = new LinkedList<List<IDependency>>();

    //
    GridLayout gridLayout = new GridLayout();
    this.setLayout(gridLayout);

    final ToolBar toolbar = new ToolBar(this, SWT.FLAT);
    
    _dependencySelectionListener = new IDependencySelectionListener(){
      @Override
      public void dependencySelectionChanged(IDependencySelectionChangedEvent event) {
        if (event.getProviderId().equals(DependencyTreeView.ID)) {
          enableButtons();
        }
      }
    };
    
    Selection.instance().getDependencySelectionService().addDependencySelectionListener(Selection.DETAIL_DEPENDENCY_SELECTION_ID, _dependencySelectionListener);

    // ToolBarManager toolBarManager = new ToolBarManager(toolbar);
    //
    // //
    // IAction backAction = new Action() {
    // @Override
    // public void run() {
    // System.out.println("asdasdasdad");
    // }
    // };
    // backAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
    // "icons/full/elcl16/backward_nav.gif"));
    // backAction.setDisabledImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
    // "icons/full/dlcl16/backward_nav.gif"));
    // backAction.setEnabled(true);
    // toolBarManager.add(backAction);
    //
    // //
    // IAction forwardAction = new Action() {
    // @Override
    // public void run() {
    // System.out.println("asdasdasdad");
    // }
    // };
    // forwardAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
    // "icons/full/elcl16/forward_nav.gif"));
    // forwardAction.setDisabledImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
    // "icons/full/dlcl16/forward_nav.gif"));
    // forwardAction.setEnabled(true);
    // forwardAction.setId("honk");
    //
    // IAction action = new Action("aw", SWT.DROP_DOWN) {
    // @Override
    // public void run() {
    // System.out.println("asdasdasdad");
    // }
    // };
    // action.setId("honk");
    // toolBarManager.add(action);
    //
    // ActionContributionItem item = (ActionContributionItem) toolBarManager.find("honk");
    //
    // // ActionSetContributionItem actionSetContributionItem = new ActionSetContributionItem(new
    // ActionContributionItem(action), "actionSetId");
    // // ActionSetContributionItem actionSetContributionItem2 = new ActionSetContributionItem(new
    // ActionContributionItem(action), "actionSetId");
    // // toolBarManager.add(new ActionSetContributionItem(new ActionContributionItem(action), "actionSetId"));
    //
    // // toolBarManager.add(new GroupMarker("bloeck"));
    // // toolBarManager.appendToGroup("bloeck", new Action() {
    // // @Override
    // // public void run() {
    // // System.out.println("asdasdasdad");
    // // }
    // // });
    //
    // // call update
    // toolBarManager.update(true);
    //

    _backButton = new ToolItem(toolbar, SWT.NONE);
    _backButton.setImage(UIDependencyTreeImages.ENABLED_BACKWARD_NAV.getImage());
    _backButton.setDisabledImage(UIDependencyTreeImages.DISABLED_BACKWARD_NAV.getImage());

    _forwardButton = new ToolItem(toolbar, SWT.NONE);
    _forwardButton.setImage(UIDependencyTreeImages.ENABLED_FORWARD_NAV.getImage());
    _forwardButton.setDisabledImage(UIDependencyTreeImages.DISABLED_FORWARD_NAV.getImage());

    _clearButton = new ToolItem(toolbar, SWT.NONE);
    _clearButton.setImage(UIDependencyTreeImages.ENABLED_PIN_SELECTION_CLEAR.getImage());
    _clearButton.setDisabledImage(UIDependencyTreeImages.DISABLED_PIN_SELECTION_CLEAR.getImage());

    _cropButton = new ToolItem(toolbar, SWT.NONE);
    _cropButton.setImage(UIDependencyTreeImages.ENABLED_PIN_SELECTION_ADD.getImage());
    _cropButton.setDisabledImage(UIDependencyTreeImages.DISABLED_PIN_SELECTION_ADD.getImage());

    // final Menu menu = new Menu(getShell(), SWT.POP_UP);
    // menu.addMenuListener(new MenuListener() {
    //
    // @Override
    // public void menuShown(MenuEvent e) {
    // }
    //
    // @Override
    // public void menuHidden(MenuEvent e) {
    // }
    // });
    //
    // new MenuItem(menu, SWT.PUSH).setText("Menu item 1");
    // new MenuItem(menu, SWT.PUSH).setText("Menu item 2");
    // new MenuItem(menu, SWT.SEPARATOR);
    // new MenuItem(menu, SWT.PUSH).setText("Menu item 3");
    //
    // _cropButton.addListener(SWT.Selection, new Listener() {
    // public void handleEvent(Event event) {
    // if (event.detail == SWT.ARROW) {
    // Rectangle bounds = _cropButton.getBounds();
    // Point point = toolbar.toDisplay(bounds.x, bounds.y + bounds.height);
    // menu.setLocation(point);
    // menu.setVisible(true);
    // }
    // }
    // });

    // Listener selectionListener = new Listener() {
    // public void handleEvent(Event event) {
    // ToolItem item = (ToolItem)event.widget;
    // System.out.println(item.getText() + " is selected");
    // if( (item.getStyle() & SWT.RADIO) != 0 || (item.getStyle() & SWT.CHECK) != 0 )
    // System.out.println("Selection status: " + item.getSelection());
    // }
    // };
    //
    // itemPush.addListener(SWT.Selection, selectionListener);
    // itemCheck.addListener(SWT.Selection, selectionListener);
    // itemRadio1.addListener(SWT.Selection, selectionListener);
    // itemRadio2.addListener(SWT.Selection, selectionListener);
    // itemDropDown.addListener(SWT.Selection, selectionListener);
    //
    // // toolbar.pack();

    _dependencyTreeComposite = new DependencyTreeComposite(this, detailDependencySelectionId);
    GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
    _dependencyTreeComposite.setLayoutData(gridData);

    //
    _cropButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

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

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   */
  public void setDependencies(List<IDependency> dependencies) {
    _dependencyTreeComposite.setDependencies(dependencies);
    _currentPosition = 0;
    _dependencySelectionList.clear();
    _dependencySelectionList.add(dependencies);
    enableButtons();
  }

  @Override
  public void dispose() {
    Selection.instance().getDependencySelectionService().removeDependencySelectionListener(_dependencySelectionListener);
    super.dispose();
  }

  private void enableButtons() {

    //
    boolean dependenciesSelected = _dependencyTreeComposite.getSelectedDetailDependencies() != null
        && _dependencyTreeComposite.getSelectedDetailDependencies().size() > 0;

    //
    _backButton.setEnabled(dependenciesSelected && _currentPosition > 0);
    _forwardButton.setEnabled(dependenciesSelected && _currentPosition < _dependencySelectionList.size() - 1);
    _clearButton.setEnabled(dependenciesSelected && _dependencySelectionList.size() > 1);
    _cropButton.setEnabled(dependenciesSelected);
  }
}
