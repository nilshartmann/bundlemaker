package org.bundlemaker.core.ui.editor;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.exporter.BinaryBundleExporter;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedTransformation;
import org.bundlemaker.core.ui.editor.transformation.Evaluator;
import org.bundlemaker.core.ui.editor.transformation.NewModule;
import org.bundlemaker.core.ui.editor.transformation.Transformations;
import org.bundlemaker.core.ui.internal.UIImages;
import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class TransformationPage extends FormPage {

  public TransformationPage(ProjectDescriptionEditor editor) {
    super(editor, "Transformations", "Transformations");
  }

  private Label _stateLabel;

  private Text  _scriptText;

  private void refreshStateLabel() {
    _stateLabel.setText("Project state: " + getBundleMakerProject().getState());
  }

  protected void createFormContent(IManagedForm mform) {

    super.createFormContent(mform);

    BundleMakerAdapterFactory.register();

    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();
    toolkit.decorateFormHeading(form.getForm());
    form.setImage(UIImages.BUNDLEMAKER_ICON.getImage());
    form.setText("Transformations");
    form.getBody().setLayout(FormLayoutUtils.createFormGridLayout(true, 1));

    Composite openComposite = toolkit.createComposite(form.getBody(), SWT.NONE);
    openComposite.setLayout(new GridLayout(2, false));
    final Button openButton = toolkit.createButton(openComposite, "Open project", SWT.PUSH);
    openButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        openBundleMakerProject();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
    _stateLabel = toolkit.createLabel(openComposite, "Project state: ");
    _stateLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    toolkit.createLabel(form.getBody(), "Transformation script:");
    _scriptText = toolkit.createText(form.getBody(), "transformations.", SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
        | SWT.BORDER);
    _scriptText.setLayoutData(new GridData(GridData.FILL_BOTH));

    final Button applyButton = toolkit.createButton(form.getBody(), "Apply and export", SWT.PUSH);
    applyButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        applyTransformations();

      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    refreshStateLabel();

  }

  /**
   * <p>
   * Returns the {@link IBundleMakerProjectDescription} that this editor is working on.
   * </p>
   * 
   * @return the project description. Never null.
   */
  protected IBundleMakerProject getBundleMakerProject() {
    ProjectDescriptionEditor descriptionEditor = (ProjectDescriptionEditor) getEditor();
    IBundleMakerProject bundleMakerProject = descriptionEditor.getBundleMakerProject();
    return bundleMakerProject;
  }

  private void openBundleMakerProject() {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            getBundleMakerProject().open(monitor);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      refreshStateLabel();
    }
  }

  private void applyTransformations() {
    try {
      IBundleMakerProject bundleMakerProject = getBundleMakerProject();
      IModularizedSystem system = bundleMakerProject.getModularizedSystemWorkingCopy(bundleMakerProject.getProject()
          .getName());
      ResourceSetBasedTransformation transformation = new ResourceSetBasedTransformation();
      // system.getTransformations().clear();

      Transformations transformations = new Evaluator().evaluate(getTransformationScript());

      List<NewModule> newModules = transformations.getNewModules();
      for (NewModule newModule : newModules) {
        addModule(transformation, newModule);
      }

      system.getTransformations().add(transformation);
      system.applyTransformations();

      Collection<IResourceModule> resourceModules = system.getResourceModules();
      for (IResourceModule iResourceModule : resourceModules) {
        System.out.println("resource module: " + iResourceModule);
      }

      exportToSimpleReport(bundleMakerProject, system);
      exportToBinaryBundle(bundleMakerProject, system);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void addModule(ResourceSetBasedTransformation transformation, NewModule newModule) {
    ResourceSetBasedModuleDefinition moduleDefinition = transformation.addModuleDefinition(newModule.getName(),
        newModule.getVersion());
    moduleDefinition.addResourceSet(newModule.getFromName(), newModule.getFromVersion(), newModule.getIncludes(), null);
  }

  private void exportToSimpleReport(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File("D:/bm", "report");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    SimpleReportExporter exporter = new SimpleReportExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
  }

  private void exportToBinaryBundle(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File("D:/bm", "bundles");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    BinaryBundleExporter exporter = new BinaryBundleExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
  }

  private String getTransformationScript() {
    return _scriptText.getText();
  }
}
