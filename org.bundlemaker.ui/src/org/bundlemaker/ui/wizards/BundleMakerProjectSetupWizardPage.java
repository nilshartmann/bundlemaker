package org.bundlemaker.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BundleMakerProjectSetupWizardPage extends WizardPage {
	private Text _projectNameText;
	private Combo _combo;

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
		lblProjectName.setText("Project name");

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
		lblJre.setText("JRE");

		_combo = new Combo(composite, SWT.READ_ONLY);
		_combo.setItems(new String[] { "JDK1.5", "JDK1.6", "JDK1.7" });
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(_projectNameText, 7);
		fd_combo.right = new FormAttachment(lblJre, 193, SWT.RIGHT);
		fd_combo.left = new FormAttachment(lblJre, 74);
		_combo.setLayoutData(fd_combo);

	}
}
