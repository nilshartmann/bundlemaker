/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.Iterator;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * <p>
 * Grafical editor for BundleMakers project contents
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ContentPage extends FormPage {

  public ContentPage(ProjectDescriptionEditor editor) {
    super(editor, "Content", "Content");
  }

  protected void createFormContent(IManagedForm mform) {
    super.createFormContent(mform);

    BundleMakerAdapterFactory.register();

    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();
    toolkit.decorateFormHeading(form.getForm());
    form.setImage(UIImages.BUNDLEMAKER_ICON.getImage());
    form.setText("Bundlemaker project");
    form.getBody().setLayout(FormLayoutUtils.createFormGridLayout(true, 1));

    createStateSection(mform);

    createResourcesSection(
        mform,
        "Resources:",
        "A <b>resource</b> is part of the project that will be parsed, transformed and exported by BundleMaker. Resources are comparable with sources in a Java project.",
        true);

    createResourcesSection(
        mform,
        "Types:",
        "With <b>types</b> you specify the dependencies that are needed by your resources. Types will <b>not</b> be parsed, transformed or exported. Types are comparable with binary dependencies that are specified on the classpath of a Java project.",
        false);

    // createProjectContentSection(mform);

  }

  private void createResourcesSection(final IManagedForm mform, String title, String description,
      final boolean resources) {
    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    Section resourcesSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED
        | Section.TWISTIE);
    FormText descriptionText = toolkit.createFormText(resourcesSection, false);
    descriptionText.setText("<form><p>" + description + "</p></form>", true, false);
    resourcesSection.setDescriptionControl(descriptionText);
    resourcesSection.setText(title);
    // resourcesSection.setDescription(description);
    resourcesSection.setLayoutData(new GridData(GridData.FILL_BOTH));

    Composite client = toolkit.createComposite(resourcesSection);
    client.setLayoutData(new GridData(GridData.FILL_BOTH));
    client.setLayout(new GridLayout(2, false));

    resourcesSection.setClient(client);

    final SectionPart resourcesSectionPart = new SectionPart(resourcesSection);
    mform.addPart(resourcesSectionPart);

    final Tree projectContentTree = toolkit.createTree(client, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    projectContentTree.setLayoutData(gd);

    final TreeViewer treeViewer = new TreeViewer(projectContentTree);
    treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    BaseWorkbenchContentProvider provider = new BaseWorkbenchContentProvider();
    treeViewer.setContentProvider(provider);

    IBundleMakerProjectDescription bundleMakerProjectDescription = getBundleMakerProjectDescription();
    System.out.println("Init treeviewer mit projectdescription " + bundleMakerProjectDescription);
    BundleMakerProjectDescriptionWrapper wrapper = (resources ? BundleMakerProjectDescriptionWrapper
        .forResources(bundleMakerProjectDescription) : BundleMakerProjectDescriptionWrapper
        .forTypes(bundleMakerProjectDescription));
    treeViewer.setInput(wrapper);

    Composite buttonBar = toolkit.createComposite(client);
    buttonBar.setLayout(new GridLayout(1, false));
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    buttonBar.setLayoutData(gd);

    final Button editButton = toolkit.createButton(buttonBar, "Edit...", SWT.PUSH);
    editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Button addButton = toolkit.createButton(buttonBar, "Add...", SWT.PUSH);
    addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        ModifyProjectContentDialog dialog = new ModifyProjectContentDialog(editButton.getShell(), resources, false);
        if (dialog.open() == Window.OK) {
          if (resources) {
            getBundleMakerProjectDescription().addResourceContent(dialog.getName(), dialog.getVersion(),
                dialog.getBinaryPaths(), dialog.getSourcePaths());
          } else {
            getBundleMakerProjectDescription().addTypeContent(dialog.getName(), dialog.getVersion(),
                dialog.getBinaryPaths());
          }
          // Refresh UI
          treeViewer.refresh();

          refreshProjectStateDisplay();

          // Mark editor dirty
          resourcesSectionPart.markDirty();

        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    final Button smartAddArchivesButton = toolkit.createButton(buttonBar, "Smart add archives...", SWT.PUSH);
    smartAddArchivesButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    smartAddArchivesButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        FileDialog fileDialog = new FileDialog(smartAddArchivesButton.getShell(), SWT.MULTI);
        fileDialog.setText("Add " + (resources ? " Resources" : "Types"));
        fileDialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
        // fileDialog.setFilterNames(new String[] { "Archives" });
        if (fileDialog.open() == null) {
          return;
        }
        String[] fileNames = fileDialog.getFileNames();
        if (fileNames.length > 0) {
          // Add all selected archives to the project description
          for (String string : fileNames) {
            IPath path = new Path(fileDialog.getFilterPath()).append(string);
            String binaryRoot = path.toOSString();
            if (resources) {
              getBundleMakerProjectDescription().addResourceContent(binaryRoot);
            } else {
              getBundleMakerProjectDescription().addTypeContent(binaryRoot);
            }
          }
          // Refresh view
          treeViewer.refresh();

          refreshProjectStateDisplay();

          // mark editor dirty
          resourcesSectionPart.markDirty();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
    Button removeButton = toolkit.createButton(buttonBar, "Remove", SWT.PUSH);
    removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    removeButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        ISelection selection = treeViewer.getSelection();
        if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
          // TODO enablement
          return;
        }

        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        Iterator iterator = structuredSelection.iterator();
        while (iterator.hasNext()) {
          Object element = iterator.next();
          System.out.println("element: " + element);
          if (element instanceof IFileBasedContent) {
            IFileBasedContent content = (IFileBasedContent) element;
            String id = content.getId();
            getBundleMakerProjectDescription().removeContent(id);
          }
          // Refresh view
          treeViewer.refresh();

          refreshProjectStateDisplay();

          // mark editor dirty
          resourcesSectionPart.markDirty();

        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    toolkit.paintBordersFor(client);
  }

  /**
   * <p>
   * Returns the {@link IBundleMakerProjectDescription} that this editor is working on.
   * </p>
   * 
   * @return the project description. Never null.
   */
  protected IBundleMakerProjectDescription getBundleMakerProjectDescription() {
    ProjectDescriptionEditor descriptionEditor = (ProjectDescriptionEditor) getEditor();
    IBundleMakerProject bundleMakerProject = descriptionEditor.getBundleMakerProject();
    return bundleMakerProject.getProjectDescription();
  }

  private FormText _stateFormText;

  private void refreshProjectStateDisplay() {

    BundleMakerProjectState projectState = getBundleMakerProjectDescription().getBundleMakerProject().getState();
    System.out.println("projectState: '" + projectState + "'");

    String state = String.valueOf(projectState);

    _stateFormText.setText(
        "<form><p><span font=\"header\">BundleMaker project state:</span> <span font=\"header\" color=\"statecolor\">"
            + state + ".</span></p></form>", true, false);

    _stateFormText.getParent().redraw();

  }

  private void createStateSection(final IManagedForm mform) {
    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();
    Composite client = toolkit.createComposite(form.getBody());
    client.setLayout(new GridLayout(2, false));
    client.setLayoutData(new GridData());
    _stateFormText = toolkit.createFormText(client, false);
    GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
    layoutData.horizontalAlignment = SWT.FILL;
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.widthHint = new PixelConverter(_stateFormText).convertWidthInCharsToPixels(48);
    _stateFormText.setLayoutData(layoutData);
    _stateFormText.setFont("header", JFaceResources.getBannerFont());
    _stateFormText.setColor("statecolor", toolkit.getColors().getColor(IFormColors.TITLE));

    refreshProjectStateDisplay();
    Button button = toolkit.createButton(client, "(Re-)Parse", SWT.PUSH);
    button.setLayoutData(new GridData());
    button.setImage(UIImages.REFRESH.getImage());
    button.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        parseProject();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
  }

  // private void createProjectContentSection(final IManagedForm mform) {
  // FormToolkit toolkit = mform.getToolkit();
  // final ScrolledForm form = mform.getForm();
  //
  // final Shell shell = form.getBody().getShell();
  //
  // Section projectContentSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED);
  // projectContentSection.setText("Project content");
  //
  // Composite sectionComposite = toolkit.createComposite(projectContentSection);
  // sectionComposite.setLayout(new GridLayout());
  //
  // final Tree projectContentTree = toolkit.createTree(sectionComposite, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
  // GridData gd = new GridData(GridData.FILL_BOTH);
  // gd.heightHint = 200;
  // gd.widthHint = 100;
  // projectContentTree.setLayoutData(gd);
  //
  // final TreeViewer treeViewer = new TreeViewer(projectContentTree);
  // treeViewer.setLabelProvider(new WorkbenchLabelProvider());
  // BaseWorkbenchContentProvider provider = new BaseWorkbenchContentProvider();
  // treeViewer.setContentProvider(provider);
  //
  // System.out.println("Init treeviewer mit projectdescription " + getBundleMakerProjectDescription());
  // treeViewer.setInput(getBundleMakerProjectDescription());
  //
  // Button addArchivesButton = toolkit.createButton(sectionComposite, "Add archive resources...", SWT.PUSH);
  //
  // final SectionPart projectContentSectionPart = new SectionPart(projectContentSection);
  // mform.addPart(projectContentSectionPart);
  //
  // addArchivesButton.addSelectionListener(new SelectionListener() {
  //
  // @Override
  // public void widgetSelected(SelectionEvent e) {
  // FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
  // if (fileDialog.open() == null) {
  // return;
  // }
  // String[] fileNames = fileDialog.getFileNames();
  // if (fileNames.length > 0) {
  // // Add all selected archives to the project description
  // for (String string : fileNames) {
  // IPath path = new Path(fileDialog.getFilterPath()).append(string);
  // String binaryRoot = path.toOSString();
  // getBundleMakerProjectDescription().addResourceContent(binaryRoot);
  // }
  // // Refresh view
  // treeViewer.refresh();
  //
  // // mark editor dirty
  // projectContentSectionPart.markDirty();
  // }
  // }
  //
  // @Override
  // public void widgetDefaultSelected(SelectionEvent e) {
  // }
  // });
  //
  // Button addResourceButton = toolkit.createButton(sectionComposite, "Add resource...", SWT.PUSH);
  // addResourceButton.addSelectionListener(new SelectionListener() {
  //
  // @Override
  // public void widgetSelected(SelectionEvent e) {
  // ResourceLocationSelectorDialog dlg = new ResourceLocationSelectorDialog(shell);
  // if (dlg.open() == Window.OK) {
  // // Add selected resource to projectdescription
  // getBundleMakerProjectDescription().addResourceContent(dlg.getResourceName(), dlg.getResourceVersion(),
  // dlg.getResourceBinaryPath(), dlg.getResourceSourcePath());
  //
  // // Refresh UI
  // treeViewer.refresh();
  //
  // // Mark editor dirty
  // projectContentSectionPart.markDirty();
  // }
  // }
  //
  // @Override
  // public void widgetDefaultSelected(SelectionEvent e) {
  // }
  // });
  //
  // toolkit.paintBordersFor(sectionComposite);
  //
  // gd = new GridData(GridData.FILL_BOTH);
  // projectContentSection.setLayoutData(gd);
  // projectContentSection.setClient(sectionComposite);
  // projectContentSection.setExpanded(true);
  //
  // }
  private void parseProject() {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            getBundleMakerProjectDescription().getBundleMakerProject().initialize(monitor);
            getBundleMakerProjectDescription().getBundleMakerProject().parse(monitor, true);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    refreshProjectStateDisplay();

  }
}
