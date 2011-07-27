/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.ui.editor.resources.ProjectResourcesBlock;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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

  private ScrolledForm _form;

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
    form.setText("Bundlemaker project");
    form.getBody().setLayout(FormLayoutUtils.createFormGridLayout(true, 1));

    createResourcesSection(mform, "Resources:",
        "A <b>resource</b> is part of a project that can be parsed, transformed and exported by BundleMaker.", true);

    IToolBarManager toolBarManager = form.getToolBarManager();
    toolBarManager.add(new ParseAction());
    form.updateToolBar();

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

    ProjectResourcesBlock block = new ProjectResourcesBlock(title, description, this);
    block.addPropertyChangeListener(new IPropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent event) {
        refreshFormTitle();
      }
    });
    block.createControl(mform);
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

    BundleMakerProjectState projectState = getBundleMakerProjectDescription().getBundleMakerProject().getState();

    String state = (projectState == null ? "unknown" : projectState.toString());

    _form.setText(String.format("Edit BundleMaker project [%s]", state));

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

      // Bug-Fix: Refresh the workspace to prevent eclipse from showing hidden projects
      try {
        ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
      } catch (CoreException e) {
        // silently ignore...
      }

      // Parse the project
      ParseBundleMakerProjectRunnable.parseProject(getBundleMakerProject());
      refreshFormTitle();
    }
  }
}
