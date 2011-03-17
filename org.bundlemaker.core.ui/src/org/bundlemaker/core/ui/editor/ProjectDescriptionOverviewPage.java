/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
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
public class ProjectDescriptionOverviewPage extends FormPage {

  private TreeViewer _treeViewer;

  public ProjectDescriptionOverviewPage(ProjectDescriptionEditor editor) {
    super(editor, "Project overview", "Project overview");
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

    createProjectContentSection(mform);

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

  private void createProjectContentSection(final IManagedForm mform) {
    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    final Shell shell = form.getBody().getShell();

    Section projectContentSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED);
    projectContentSection.setText("Project content");

    Composite sectionComposite = toolkit.createComposite(projectContentSection);
    sectionComposite.setLayout(new GridLayout());

    final Tree projectContentTree = toolkit.createTree(sectionComposite, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    projectContentTree.setLayoutData(gd);

    _treeViewer = new TreeViewer(projectContentTree);
    _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    BaseWorkbenchContentProvider provider = new BaseWorkbenchContentProvider();
    _treeViewer.setContentProvider(provider);

    System.out.println("Init treeviewer mit projectdescription " + getBundleMakerProjectDescription());
    _treeViewer.setInput(getBundleMakerProjectDescription());

    Button addArchivesButton = toolkit.createButton(sectionComposite, "Add archive resources...", SWT.PUSH);

    final SectionPart projectContentSectionPart = new SectionPart(projectContentSection);
    mform.addPart(projectContentSectionPart);

    addArchivesButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
        if (fileDialog.open() == null) {
          return;
        }
        String[] fileNames = fileDialog.getFileNames();
        if (fileNames.length > 0) {
          // Add all selected archives to the project description
          for (String string : fileNames) {
            IPath path = new Path(fileDialog.getFilterPath()).append(string);
            String binaryRoot = path.toOSString();
            getBundleMakerProjectDescription().addResourceContent(binaryRoot);
          }
          // Refresh view
          _treeViewer.refresh();

          // mark editor dirty
          projectContentSectionPart.markDirty();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    Button addResourceButton = toolkit.createButton(sectionComposite, "Add resource...", SWT.PUSH);
    addResourceButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        ResourceLocationSelectorDialog dlg = new ResourceLocationSelectorDialog(shell);
        if (dlg.open() == Window.OK) {
          // Add selected resource to projectdescription
          getBundleMakerProjectDescription().addResourceContent(dlg.getResourceName(), dlg.getResourceVersion(),
              dlg.getResourceBinaryPath(), dlg.getResourceSourcePath());

          // Refresh UI
          _treeViewer.refresh();

          // Mark editor dirty
          projectContentSectionPart.markDirty();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    toolkit.paintBordersFor(sectionComposite);

    gd = new GridData(GridData.FILL_BOTH);
    projectContentSection.setLayoutData(gd);
    projectContentSection.setClient(sectionComposite);
    projectContentSection.setExpanded(true);

  }
}
