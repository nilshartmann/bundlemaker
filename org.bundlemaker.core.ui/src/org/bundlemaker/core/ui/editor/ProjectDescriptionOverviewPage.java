/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.IManagedForm;
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

  private TreeViewer                     _treeViewer;

  private IBundleMakerProjectDescription _projectDescription;

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

    // SectionPart sectionPart = new SectionPart(projectContentSection);
    // mform.addPart(sectionPart);

  }

  @Override
  protected void setInput(IEditorInput input) {
    super.setInput(input);
    if (input == null) {
      System.err.println("Input is null ?!?!?!");
      return;
    }
    IFileEditorInput adapter = (IFileEditorInput) input.getAdapter(IFileEditorInput.class);
    if (adapter == null) {
      System.err.println("Unsupported EditorInput " + input + " cannot be adapted to an "
          + IFileEditorInput.class.getName());
      return;
    }
    System.out.println("************ input: " + input);
    System.out.println("input class: " + input.getClass().getName());
    System.out.println("adapter: " + adapter);
    IProject project = adapter.getFile().getProject();
    try {
      IBundleMakerProject bundleMakerProject = BundleMakerCore
          .getBundleMakerProject(project, new NullProgressMonitor());
      List<? extends IFileBasedContent> fileBasedContent = bundleMakerProject.getProjectDescription()
          .getFileBasedContent();
      for (IFileBasedContent iFileBasedContent : fileBasedContent) {
        System.out.println("content: " + iFileBasedContent);
      }
      if (_treeViewer != null) {
        System.out.println("Setze treeviewer!!!!");
        _treeViewer.setInput(bundleMakerProject.getProjectDescription());
      } else {
        System.out.println("TreeViewer is null !!!!");
      }
      _projectDescription = bundleMakerProject.getProjectDescription();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private void createProjectContentSection(IManagedForm mform) {
    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    Section projectContentSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED);
    projectContentSection.setText("Project content");

    Composite sectionComposite = toolkit.createComposite(projectContentSection);
    sectionComposite.setLayout(new GridLayout());

    Tree projectContentTree = toolkit.createTree(sectionComposite, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);

    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    projectContentTree.setLayoutData(gd);

    _treeViewer = new TreeViewer(projectContentTree);
    _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    _treeViewer.setContentProvider(new BaseWorkbenchContentProvider());

    System.out.println("Init treeviewer mit projectdescription " + _projectDescription.getFileBasedContent());
    _treeViewer.setInput(_projectDescription);

    toolkit.paintBordersFor(sectionComposite);

    gd = new GridData(GridData.FILL_BOTH);
    projectContentSection.setLayoutData(gd);
    projectContentSection.setClient(sectionComposite);
    projectContentSection.setExpanded(true);

  }
}
