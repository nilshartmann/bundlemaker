package org.bundlemaker.ui.wizards;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * <p>
 * 
 * </p>
 * 
 * TODO consider using org.eclipse.ui.dialogs.WizardNewProjectCreationPage
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerProjectSetupWizardPage extends WizardPage {
	private Text _projectNameText;
	private Combo _jreCombo;

	private String _projectName;
	private JreDescription _jre;

	public BundleMakerProjectSetupWizardPage() {
		super("Bundlemaker Project Wizard");

		setTitle("Create a Bundlemaker project");
		setDescription("Set project name and JRE.");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		setControl(composite);
		composite.setLayout(new FormLayout());

		Label lblProjectName = new Label(composite, SWT.NONE);
		FormData fd_lblProjectName = new FormData();
		fd_lblProjectName.top = new FormAttachment(0, 10);
		fd_lblProjectName.left = new FormAttachment(0, 10);
		lblProjectName.setLayoutData(fd_lblProjectName);
		lblProjectName.setText("&Project name");

		_projectNameText = new Text(composite, SWT.BORDER);
		FormData fd_projectNameText = new FormData();
		fd_projectNameText.bottom = new FormAttachment(lblProjectName, 18);
		fd_projectNameText.left = new FormAttachment(lblProjectName, 21);
		fd_projectNameText.top = new FormAttachment(lblProjectName, -3, SWT.TOP);
		fd_projectNameText.right = new FormAttachment(100, -21);
		_projectNameText.setLayoutData(fd_projectNameText);

		Label lblJre = new Label(composite, SWT.NONE);
		FormData fd_lblJre = new FormData();
		fd_lblJre.top = new FormAttachment(lblProjectName, 18);
		fd_lblJre.left = new FormAttachment(0, 10);
		lblJre.setLayoutData(fd_lblJre);
		lblJre.setText("&JRE");

		_jreCombo = new Combo(composite, SWT.READ_ONLY);

		ComboViewer comboViewer = populateJreComboBox();
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(_projectNameText, 7);
		fd_combo.right = new FormAttachment(lblJre, 193, SWT.RIGHT);
		fd_combo.left = new FormAttachment(lblJre, 74);
		_jreCombo.setLayoutData(fd_combo);

		DataBindingContext dbc = new DataBindingContext();
		WizardPageSupport.create(this, dbc);

		IObservableValue usernameObservable = WidgetProperties.text(SWT.Modify)
				.observe(_projectNameText);
		Binding projectNameBinding = dbc.bindValue(
				usernameObservable,
				BeanProperties.value(BundleMakerProjectSetupWizardPage.class,
						"projectName").observe(this), new UpdateValueStrategy()
						.setBeforeSetValidator(new IValidator() {

							@Override
							public IStatus validate(Object value) {
								if (!(value instanceof String)) {
									return ValidationStatus
											.error("Please enter a project name");
								}
								String string = (String) value;
								if (string.trim().isEmpty()) {
									return ValidationStatus
											.error("Please enter a project name");
								}
								return ValidationStatus.ok();
							}
						}), new UpdateValueStrategy(
						UpdateValueStrategy.POLICY_NEVER));

		IObservableValue jreObservable = ViewerProperties.singleSelection()
				.observe(comboViewer);
		Binding binding = dbc.bindValue(
				jreObservable,
				BeanProperties.value(BundleMakerProjectSetupWizardPage.class,
						"jre").observe(this), null, new UpdateValueStrategy(
						UpdateValueStrategy.POLICY_NEVER));
		ControlDecorationSupport.create(projectNameBinding, SWT.LEFT | SWT.TOP);

		binding.updateTargetToModel();

	}

	class JreDescription {
		private final String _id;
		private final String _name;
		private final boolean _defaultJre;

		public JreDescription(String id, String name, boolean defaultJre) {
			super();
			_id = id;
			_name = name;
			_defaultJre = defaultJre;
		}

		public String getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public boolean isDefaultJre() {
			return _defaultJre;
		}
	}

	private ComboViewer populateJreComboBox() {
		ComboViewer viewer = new ComboViewer(_jreCombo);
		List<JreDescription> availableJreDescriptions = getAvailableJreDescriptions();
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				JreDescription jreDescription = (JreDescription) element;
				return super.getText(jreDescription.getName());
			}
		});

		viewer.setInput(availableJreDescriptions);

		for (JreDescription jreDescription : availableJreDescriptions) {
			if (jreDescription.isDefaultJre()) {
				viewer.setSelection(new StructuredSelection(jreDescription));
				break;
			}
		}
		return viewer;
	}

	private List<JreDescription> getAvailableJreDescriptions() {
		List<JreDescription> availableJres = new LinkedList<BundleMakerProjectSetupWizardPage.JreDescription>();

		final String defaultJreId = JavaRuntime.getDefaultVMInstall().getId();
		IVMInstallType[] types = JavaRuntime.getVMInstallTypes();

		for (int i = 0; i < types.length; i++) {
			IVMInstallType type = types[i];
			IVMInstall[] jres = type.getVMInstalls();
			for (int j = 0; j < jres.length; j++) {
				JreDescription jreDescription = new JreDescription(
						jres[j].getId(), jres[j].getName(), jres[j].getId()
								.equals(defaultJreId));
				availableJres.add(jreDescription);
			}
		}

		return availableJres;
	}

	public String getProjectName() {
		return _projectName;
	}

	public void setProjectName(String projectName) {
		_projectName = projectName;
		System.out.println("projectName: " + projectName);
	}

	public JreDescription getJre() {
		return _jre;
	}

	public void setJre(JreDescription jre) {
		_jre = jre;
		System.out.println("jre: " + jre);
	}

}
