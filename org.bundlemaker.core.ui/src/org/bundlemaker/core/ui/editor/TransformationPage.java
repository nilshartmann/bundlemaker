package org.bundlemaker.core.ui.editor;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.exporter.BinaryBundleExporter;
import org.bundlemaker.core.osgi.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.osgi.pde.exporter.TargetPlatformProjectExporter;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;
import org.bundlemaker.core.transformations.dsl.ui.utils.TransformationDslUtils;
import org.bundlemaker.core.transformations.resourceset.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.transformations.resourceset.ResourceSetBasedTransformation;
import org.bundlemaker.core.ui.editor.transformation.Evaluator;
import org.bundlemaker.core.ui.editor.transformation.NewModule;
import org.bundlemaker.core.ui.editor.transformation.TransformationExecutor;
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

    Composite dslComposite = toolkit.createComposite(form.getBody(), SWT.BORDER);
    dslComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    dslComposite.setLayout(new GridLayout(3, false));

    final Text uriText = toolkit.createText(dslComposite, "platform:/resource/spring/spring-transformation.bmt");
    uriText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    final Button parseButton = toolkit.createButton(dslComposite, "Apply", SWT.PUSH);
    parseButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        testParsing(uriText.getText().trim());
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    final Button exportButton = toolkit.createButton(dslComposite, "Export", SWT.PUSH);
    exportButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        try {

          IModularizedSystem modularizedSystem = getModularizedSystem("eins");
          Collection<IModule> allModules = modularizedSystem.getAllModules();
          System.out.println("Modules: ");
          for (IModule iModule : allModules) {
            System.out.println("Module: " + iModule.getModuleIdentifier());
          }
          System.out.println("ResourceModules:");
          Collection<IResourceModule> resourceModules = modularizedSystem.getResourceModules();
          for (IResourceModule iResourceModule : resourceModules) {
            System.out.println("ResourceModule: " + iResourceModule.getModuleIdentifier());
          }
          exportToSimpleReport(getBundleMakerProject(), modularizedSystem);
          exportAsPdeProjects(getBundleMakerProject(), modularizedSystem);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    refreshStateLabel();

  }

  private void testParsing(String uri) {

    try {
      TransformationModel model = TransformationDslUtils.parse(uri);
      TransformationExecutor executor = new TransformationExecutor(createModularizedSystem("eins"), model);
      executor.apply();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private IModularizedSystem createModularizedSystem(String name) throws Exception {
    IBundleMakerProject project = getBundleMakerProject();

    if (project.hasModularizedSystemWorkingCopy(name)) {
      project.deleteModularizedSystemWorkingCopy(name);
    }
    return getModularizedSystem(name);
  }

  private IModularizedSystem getModularizedSystem(String name) throws Exception {
    IBundleMakerProject project = getBundleMakerProject();
    if (project.hasModularizedSystemWorkingCopy(name)) {
      return project.getModularizedSystemWorkingCopy(name);
    }
    return project.createModularizedSystemWorkingCopy(name);
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
            IBundleMakerProject project = getBundleMakerProject();

            if (project.getState() == null || project.getState() == BundleMakerProjectState.CREATED) {
              project.initialize(monitor);
            }

            if (project.getState() == BundleMakerProjectState.INITIALIZED) {
              project.parse(monitor, true);
            }

            project.open(monitor);
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

      exportToSimpleReport(bundleMakerProject, system);
      exportAsPdeProjects(bundleMakerProject, system);

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
    File destination = new File(getExportDir(modularizedSystem), "/report");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);
    System.out.println("exportToSimpleReport...");
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    SimpleReportExporter exporter = new SimpleReportExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
    System.out.println("exportToSimpleReport done!");
  }

  private File getExportDir(IModularizedSystem modularizedSystem) {
    File exportDir = new File(System.getProperty("user.dir"), "export");
    File concreteDir = new File(exportDir, modularizedSystem.getName());
    concreteDir.mkdirs();
    System.out.println("Export dir: " + concreteDir);
    return concreteDir;
  }

  private void exportToBinaryBundle(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(getExportDir(modularizedSystem), "/bundles");

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

  private void exportAsPdeProjects(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(getExportDir(modularizedSystem), "/pde");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    System.out.println("exportAsProjects...");
    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter();
    pdeExporter.setUseClassifcationForExportDestination(true);
    // pdeExporter.setTemplateRootDirectory(templateDirectory);
    new ModularizedSystemExporterAdapter(pdeExporter).export(modularizedSystem, exporterContext);

    TargetPlatformProjectExporter targetPlatformProjectExporter = new TargetPlatformProjectExporter();
    // targetPlatformProjectExporter.setTemplateDirectory(templateDirectory);
    targetPlatformProjectExporter.export(modularizedSystem, exporterContext);

    System.out.println("exportAsProjects done!");
  }

  private String getTransformationScript() {
    return _scriptText.getText();
  }
}
