package org.bundlemaker.core.ui.projecteditor.jdt.wizard;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.project.IModifiableProjectDescription;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class EditJdtContentProviderPage extends WizardPage {

  private IProjectDescription _projectDescription;

  private Text                _nameText;

  private CheckboxTableViewer _projectNames;

  /**
   * @param pageName
   */
  protected EditJdtContentProviderPage(IModifiableProjectDescription projectDescription) {
    super("EditJdtContentProviderPage");
    _projectDescription = projectDescription;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createControl(Composite parent) {
    setMessage("Select Java projects from your workspace");
    setTitle("Add Java projects");
    setPageComplete(false);

    Composite comp = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout(1, true);
    GridData gridData = new GridData(GridData.FILL_BOTH);
    comp.setLayout(layout);
    comp.setLayoutData(gridData);

    createProjectSelectionTable(comp);

    Composite c = new Composite(comp, SWT.NONE);
    GridLayout l = new GridLayout(2, false);
    gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = SWT.FILL;
    c.setLayout(l);
    c.setLayoutData(gridData);

    Label label = new Label(c, SWT.NONE);
    label.setText("Logical Name:");
    _nameText = new Text(c, SWT.BORDER);
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _nameText.setLayoutData(gd);
    _nameText.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        _textModifiedByUser = true;
        checkConsistency();
      }
    });
    
    label = new Label(c, SWT.NONE);
    label
        .setText("The logical name is used to group all selected JDT projects (comparable with a Maven parent project)");
    gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = SWT.FILL;
    gridData.horizontalSpan = 2;
    label.setLayoutData(gridData);
    


    setControl(comp);
  }

  private boolean _textModifiedByUser = false;

  private void createProjectSelectionTable(Composite radioGroup) {
    _projectNames = CheckboxTableViewer.newCheckList(radioGroup, SWT.BORDER);
    _projectNames.setContentProvider(new WorkbenchContentProvider());
    _projectNames.setLabelProvider(new WorkbenchLabelProvider());
    final List<IProject> projectsAlreadyAdded = getProjectsAlreadyAdded();
    // projectNames.setComparator(new ResourceComparator(ResourceComparator.NAME));
    _projectNames.addFilter(new ViewerFilter() {
      private final IProject[] projectHolder = new IProject[1];

      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (!(element instanceof IProject)) {
          return false;
        }
        IProject project = (IProject) element;

        // don't show projects that are already part of the bundlemaker project
        if (projectsAlreadyAdded.contains(project)) {
          return false;
        }

        if (!project.isAccessible()) {
          return false;
        }
        projectHolder[0] = project;
        return isJavaProject(project);
      }
    });
    _projectNames.setInput(ResourcesPlugin.getWorkspace().getRoot());
    GridData data = new GridData(GridData.FILL_BOTH);
    data.horizontalSpan = 2;
    data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    data.heightHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    _projectNames.getTable().setLayoutData(data);
    // table is disabled to start because all button is selected
    // projectNames.getTable().setEnabled(selectedButton.getSelection());
    _projectNames.addCheckStateListener(new ICheckStateListener() {
      @Override
      public void checkStateChanged(CheckStateChangedEvent event) {
        Object[] checkedElements = _projectNames.getCheckedElements();
        
        // override name only if not entered by the user
        if (!_textModifiedByUser) {
          IProject project = null;
          if ((checkedElements.length == 1) || (checkedElements.length>1 && _nameText.getText().isEmpty())) {
             project = (IProject) checkedElements[0];
             _nameText.setText(project.getName());
          }
          _textModifiedByUser = false;
        }



        checkConsistency();
      }
    });
  }

  protected void checkConsistency() {

    Object[] checkedElements = _projectNames.getCheckedElements();

    if (checkedElements.length < 1) {
      setErrorMessage("Please select at least one JDT project");
    } else if (_nameText.getText().isEmpty()) {
      setErrorMessage("Please enter a logical name");
    } else {
      setErrorMessage(null);
    }
    setPageComplete(checkedElements.length > 0 && _nameText.getText().length() > 0);
  }

  /**
   * @return
   */
  private List<IProject> getProjectsAlreadyAdded() {

    List<IProject> projects = new LinkedList<IProject>();
    List<? extends IProjectContentProvider> contentProviders = _projectDescription.getContentProviders();

    // Iterate over all Project content provider
    for (IProjectContentProvider iProjectContentProvider : contentProviders) {
      if (iProjectContentProvider instanceof JdtProjectContentProvider) {
        JdtProjectContentProvider jdtProjectContentProvider = (JdtProjectContentProvider) iProjectContentProvider;
        Collection<IJavaProject> javaProjects = jdtProjectContentProvider.getJavaProjects();

        for (IJavaProject iJavaProject : javaProjects) {
          if (!projects.contains(iJavaProject.getProject())) {
            projects.add(iJavaProject.getProject());
          }
        }
      }
    }

    return projects;
  }

  protected boolean isJavaProject(IProject project) {
    try {
      return project.hasNature("org.eclipse.jdt.core.javanature");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public IProject[] getSelectedProjects() {
    Object[] checkedElements = _projectNames.getCheckedElements();

    List<IProject> result = new LinkedList<IProject>();
    for (Object element : checkedElements) {
      result.add((IProject) element);
    }

    return result.toArray(new IProject[0]);
  }

  @Override
  public String getName() {
    return _nameText.getText();
  }

}
