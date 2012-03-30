package org.bundlemaker.analysis.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
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

  public static final String        ID    = "org.bundlemaker.analysis.ui.editor.GenericEditor";

  private final String              NAME  = "name";

  private final String              CLAZZ = "class";

  private List<DependencyTabHolder> dependencyParts;

  private TabFolder                 tabFolder;

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
    dependencyParts = new ArrayList<DependencyTabHolder>();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("org.bundlemaker.analysis.ui.dependencyView");

    tabFolder = new TabFolder(parent, SWT.NONE);

    if (extensionPoint != null) {

      for (IExtension extension : extensionPoint.getExtensions()) {
        for (IConfigurationElement element : extension.getConfigurationElements()) {
          String title = element.getAttribute(NAME);
          TabItem tabItem = new TabItem(tabFolder, SWT.NONE);

          try {
            // Create new instance of contributed DependencyPart
            DependencyPart dependencyPart = (DependencyPart) element.createExecutableExtension(CLAZZ);

            // add it to internal list of parts
            DependencyTabHolder holder = new DependencyTabHolder(dependencyPart, tabItem);
            dependencyParts.add(holder);

            // initialize(i.e. create gui components)
            dependencyPart.init(tabFolder);

            tabItem.setText(title);
            tabItem.setControl(dependencyPart.getComposite());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      System.out.printf(" * * * %d DependencyParts created%n", dependencyParts.size());
    }

    registerListener();

  }

  private void registerListener() {
    tabFolder.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
        System.out.println("TabFolder SelectionEdvent - index: " + tabFolder.getSelectionIndex());
        setTabItemFocus(tabFolder.getSelectionIndex());
      }
    });

  }

  @Override
  public void setFocus() {
    int selectedItemIndex = tabFolder.getSelectionIndex();
    this.setTabItemFocus(selectedItemIndex);
  }

  public void setTabItemFocus(int index) {
    DependencyTabHolder dependencyTabHolder = dependencyParts.get(index);
    if (dependencyTabHolder != null) {
      System.out.println("setTabItemFocus to " + dependencyTabHolder.getDependencyPart());
      dependencyTabHolder.getDependencyPart().setFocus();
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    for (DependencyTabHolder dependencyPart : dependencyParts) {
      dependencyPart.getDependencyPart().dispose();
    }

  }

  public void useArtifacts(List<IBundleMakerArtifact> artifacts) {
    // Notify dependency parts of new artifacts
    for (DependencyTabHolder dependencyPart : dependencyParts) {
      dependencyPart.getDependencyPart().useArtifacts(artifacts);
    }
  }

  /**
   * <p>
   * Opens the specified DependencyPart
   * </p>
   * <p>
   * If there is no DependencyPart with the specified Id nothing happens
   * 
   * @param dependencyPartId
   */
  public void openDependencyPart(String dependencyPartId) {
    for (DependencyTabHolder dependencyTabHolder : dependencyParts) {
      // TODO identify via some Id?
      if (dependencyPartId.equals(dependencyTabHolder.getDependencyPart().getClass().getName())) {
        tabFolder.setSelection(dependencyTabHolder.getTabItem());
        dependencyTabHolder.getDependencyPart().setFocus();
        break;
      }
    }

  }

  class DependencyTabHolder {
    private final DependencyPart _dependencyPart;

    private final TabItem        _tabItem;

    public DependencyTabHolder(DependencyPart dependencyPart, TabItem tabItem) {
      super();
      _dependencyPart = dependencyPart;
      _tabItem = tabItem;
    }

    public DependencyPart getDependencyPart() {
      return _dependencyPart;
    }

    public TabItem getTabItem() {
      return _tabItem;
    }

  }
}
