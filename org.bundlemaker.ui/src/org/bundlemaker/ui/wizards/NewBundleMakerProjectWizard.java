package org.bundlemaker.ui.wizards;

import org.bundlemaker.ui.internal.UIImages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * <p>
 * A project wizard that creates a new Bundlemaker project
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
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

		// add bundlemaker page
		NewBundleMakerProjectWizardCreationPage page = new NewBundleMakerProjectWizardCreationPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
