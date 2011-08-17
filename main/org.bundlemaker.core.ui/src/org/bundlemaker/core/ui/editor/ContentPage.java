/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.ui.editor.resources.ProjectResourcesBlock;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * <p>
 * Graphical editor for BundleMakers project contents
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ContentPage extends FormPage implements BundleMakerProjectProvider {

  private ScrolledForm          _form;

  private boolean               _needsReparsing = true;

  private ProjectResourcesBlock _projectResourcesBlock;

  public ContentPage(ProjectDescriptionEditor editor) {
    super(editor, "Content", "Content");
  }

  @Override
  protected void createFormContent(IManagedForm mform) {
    super.createFormContent(mform);

    BundleMakerAdapterFactory.register();

    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();
    _form = form;
    toolkit.decorateFormHeading(form.getForm());
    form.setImage(UIImages.BUNDLEMAKER_ICON_SMALL.getImage());
    form.setText("Content");
    form.getBody().setLayout(FormLayoutUtils.createFormGridLayout(true, 1));

    createResourcesSection(mform, "Resources:",
        "A <b>resource</b> is part of a project that can be parsed, transformed and exported by BundleMaker.", true);

    // Add actions to toolbar
    IToolBarManager toolBarManager = _form.getToolBarManager();
    toolBarManager.add(new ParseAction());
    _form.updateToolBar();

    getBundleMakerProject().addBundleMakerProjectChangedListener(new IBundleMakerProjectChangedListener() {

      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {

        if (event.getType() == Type.PROJECT_STATE_CHANGED) {
          _needsReparsing = getBundleMakerProject().getState() != BundleMakerProjectState.READY;
          Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
              refreshFormTitle();
            }
          });
        }

      }
    });

    refreshFormTitle();
  }

  /**
   * Creates a block for either the resource- or type content of the project
   * 
   * @param mform
   * @param title
   * @param description
   * @param resources
   */
  private void createResourcesSection(final IManagedForm mform, String title, String description,
      final boolean resources) {

    _projectResourcesBlock = new ProjectResourcesBlock(title, description, this);
    _projectResourcesBlock.addPropertyChangeListener(new IPropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent event) {
        _needsReparsing = true;
        refreshFormTitle();
      }
    });
    _projectResourcesBlock.createControl(mform);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.editor.BundleMakerProjectProvider#getBundleMakerProject()
   */
  @Override
  public IBundleMakerProject getBundleMakerProject() {
    ProjectDescriptionEditor descriptionEditor = (ProjectDescriptionEditor) getEditor();
    IBundleMakerProject bundleMakerProject = descriptionEditor.getBundleMakerProject();
    return bundleMakerProject;
  }

  /**
   * <p>
   * Returns the {@link IBundleMakerProjectDescription} that this editor is working on.
   * </p>
   * 
   * @return the project description. Never null.
   */
  protected IBundleMakerProjectDescription getBundleMakerProjectDescription() {
    return getBundleMakerProject().getProjectDescription();
  }

  private void refreshFormTitle() {

    _form.setText("Content");
    if (_needsReparsing) {
      _form.setMessage("Needs reparsing", IMessageProvider.INFORMATION);
    } else {
      _form.setMessage(null, IMessageProvider.NONE);
    }
  }

  @Override
  public void parseProject() {

    // Bug-Fix: Refresh the workspace to prevent eclipse from showing hidden projects
    try {
      ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    } catch (CoreException e) {
      // silently ignore...
    }

    // allow user to save the project if project is dirty
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (!page.saveEditor(getEditor(), true)) {
      // user canceled operation
      return;
    }

    // Parse the project
    ParseBundleMakerProjectRunnable.parseProject(getBundleMakerProject());
  }

  /**
   * An {@link Action} that (re-)parses the BundleMaker project
   */
  class ParseAction extends Action {
    public ParseAction() {
      super("Parse");
      setImageDescriptor(UIImages.REFRESH.getImageDescriptor());
    }

    @Override
    public void run() {
      parseProject();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.forms.editor.FormPage#dispose()
   */
  @Override
  public void dispose() {
    if (_projectResourcesBlock != null) {
      try {
        _projectResourcesBlock.dispose();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    super.dispose();

  }

}
