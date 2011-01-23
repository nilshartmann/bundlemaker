package org.bundlemaker.ui.wizards;

import org.bundlemaker.ui.internal.UIImages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewBundleMakerProjectWizard extends Wizard implements INewWizard {

	public NewBundleMakerProjectWizard() {
		setWindowTitle("New Bundlemaker Project");
		setDefaultPageImageDescriptor(UIImages.BUNDLEMAKER_ICON
				.getImageDescriptor());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPages() {
		super.addPages();

		BundleMakerProjectSetupWizardPage page = new BundleMakerProjectSetupWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
