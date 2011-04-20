/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormText;
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

    ProjectResourcesBlock block = new ProjectResourcesBlock(resources, title, description, this);
    block.addPropertyChangeListener(new IPropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent event) {
        refreshProjectStateDisplay();
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

  private FormText _stateFormText;

  private void refreshProjectStateDisplay() {

    BundleMakerProjectState projectState = getBundleMakerProjectDescription().getBundleMakerProject().getState();
    System.out.println("projectState: '" + projectState + "'");

    String state = (projectState == null ? "unknown" : projectState.toString());

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

    Composite buttonBar = toolkit.createComposite(client);
    buttonBar.setLayoutData(new GridData());
    buttonBar.setLayout(new GridLayout(3, false));

    final Button initializeButton = toolkit.createButton(buttonBar, "Initialize", SWT.PUSH);
    initializeButton.setLayoutData(new GridData());
    // initializeButton.setImage(UIImages.REFRESH.getImage());
    initializeButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        initializeProject();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    Button _parseButton = toolkit.createButton(buttonBar, "(Re-)Parse", SWT.PUSH);
    _parseButton.setLayoutData(new GridData());
    _parseButton.setImage(UIImages.REFRESH.getImage());
    _parseButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        parseProject();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    final Button openButton = toolkit.createButton(buttonBar, "Open", SWT.PUSH);
    openButton.setLayoutData(new GridData());
    openButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        openProject();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });
  }

  private void parseProject() {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            IBundleMakerProject project = getBundleMakerProjectDescription().getBundleMakerProject();
            project.parse(monitor, true);
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

  private void initializeProject() {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            IBundleMakerProject project = getBundleMakerProjectDescription().getBundleMakerProject();
            project.initialize(monitor);
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

  private void openProject() {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            IBundleMakerProject project = getBundleMakerProjectDescription().getBundleMakerProject();
            project.open(monitor);
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
