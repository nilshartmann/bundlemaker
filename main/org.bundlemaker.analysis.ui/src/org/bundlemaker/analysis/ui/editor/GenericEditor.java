package org.bundlemaker.analysis.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class GenericEditor extends EditorPart {

  public static final String   ID    = "org.bundlemaker.analysis.ui.editor.GenericEditor";

  private final String         NAME  = "name";

  private final String         CLAZZ = "class";

  private List<DependencyPart> dependencyParts;

  private TabFolder            tabFolder;

  @Override
  public void doSave(IProgressMonitor monitor) {

  }

  @Override
  public void doSaveAs() {

  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    setInput(input);
    setSite(site);
  }

  @Override
  public boolean isDirty() {
    return false;
  }

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  // public static void showDependencies( Collection<IArtifact> artifacts) {
  // DependencyGraph graph = new DependencyGraph(artifacts);
  // graph.calculateDependencies();
  // dsmView.useDependencyGraph(graph);
  // dsmView.selectViewTab();
  // }

  @Override
  public void createPartControl(Composite parent) {
    dependencyParts = new ArrayList<DependencyPart>();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("org.bundlemaker.analysis.ui.dependencyView");

    tabFolder = new TabFolder(parent, SWT.NONE);

    DependencySelectionProvider selectionProvider = new DependencySelectionProvider();

    getSite().setSelectionProvider(selectionProvider);

    if (extensionPoint != null) {

      for (IExtension extension : extensionPoint.getExtensions()) {
        for (IConfigurationElement element : extension.getConfigurationElements()) {
          String title = element.getAttribute(NAME);
          TabItem tabItem = new TabItem(tabFolder, SWT.NONE);

          try {
            // Create new instance of contributed DependencyPart
            DependencyPart dependencyPart = (DependencyPart) element.createExecutableExtension(CLAZZ);

            // add it to internal list of parts
            dependencyParts.add(dependencyPart);

            // set the selection provider
            dependencyPart.setSelectionProvider(selectionProvider);

            // initialize(i.e. create gui components)
            dependencyPart.init(tabFolder);

            // connect it to tab folder
            dependencyPart.setTabItem(tabItem);
            dependencyPart.setTabFolder(tabFolder);
            tabItem.setText(title);
            tabItem.setControl(dependencyPart.getComposite());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

    registerListener();

  }

  private void registerListener() {
    tabFolder.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
        setTabItemFocus(tabFolder.getSelectionIndex());
      }
    });

  }

  @Override
  public void setFocus() {
    int selectedItemIndex = tabFolder.getSelectionIndex();
    System.out.printf("Setze focus aus index: %d%n", selectedItemIndex);
    this.setTabItemFocus(selectedItemIndex);
  }

  public void setTabItemFocus(int index) {
    dependencyParts.get(index).setFocus();
  }

  @Override
  public void dispose() {
    super.dispose();
    for (DependencyPart dependencyPart : dependencyParts) {
      dependencyPart.dispose();
    }

  }

  class DependencySelectionProvider implements ISelectionProvider {

    private ISelection _currentSelection;

    ListenerList       listeners = new ListenerList();

    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
      listeners.add(listener);
    }

    @Override
    public ISelection getSelection() {
      return _currentSelection;
    }

    @Override
    public void removeSelectionChangedListener(ISelectionChangedListener listener) {
      listeners.remove(listener);
    }

    @Override
    public void setSelection(ISelection select) {

      _currentSelection = select;

      Object[] list = listeners.getListeners();
      for (int i = 0; i < list.length; i++) {
        ((ISelectionChangedListener) list[i]).selectionChanged(new SelectionChangedEvent(this, _currentSelection));
      }
    }
  }

}
