package org.bundlemaker.ui.wizards;

import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.ui.internal.BundleMakerUiUtils;
import org.bundlemaker.ui.internal.UIImages;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

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

	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
		setWindowTitle("New Bundlemaker Project");
		setDefaultPageImageDescriptor(UIImages.BUNDLEMAKER_ICON
				.getImageDescriptor());

	}

	NewBundleMakerProjectWizardCreationPage mainPage;

	@Override
	public void addPages() {
		super.addPages();

		// add bundlemaker page
		mainPage = new NewBundleMakerProjectWizardCreationPage();
		addPage(mainPage);
	}

	@Override
	public boolean performFinish() {
		createNewProject();

		if (newProject == null) {
			return false;
		}

		return true;

	}

	IProject newProject;

	private IProject createNewProject() {
		if (newProject != null) {
			return newProject;
		}

		// get a project handle
		final IProject newProjectHandle = mainPage.getProjectHandle();

		// get a project descriptor
		URI location = null;
		if (!mainPage.useDefaults()) {
			location = mainPage.getLocationURI();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace
				.newProjectDescription(newProjectHandle.getName());
		description.setLocationURI(location);

		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				CreateProjectOperation op = new CreateProjectOperation(
						description, "Create new Bundlemaker project");
				try {
					// see bug
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved. Making this undoable resulted in too many
					// accidental file deletions.
					op.execute(monitor,
							WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				} catch (ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation operation
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof ExecutionException
					&& t.getCause() instanceof CoreException) {
				CoreException cause = (CoreException) t.getCause();
				IStatus status;
				if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
					status = BundleMakerUiUtils
							.newWarning(
									format("The underlying file system is case insensitive. There is an existing project or directory that conflicts with '%s'",
											newProjectHandle.getName()), cause);
				} else {
					status = BundleMakerUiUtils.newStatus(cause,
							"Problems while creating the project");
				}
				StatusAdapter statusAdapter = new StatusAdapter(status);
				statusAdapter.setProperty(
						IStatusAdapterConstants.TITLE_PROPERTY,
						"Project creation problems");
				StatusManager.getManager().handle(status, StatusManager.BLOCK);
			} else {
				StatusAdapter statusAdapter = new StatusAdapter(
						BundleMakerUiUtils.newWarning(
								format("Internal error: %s", t.getMessage()), t));
				statusAdapter.setProperty(
						IStatusAdapterConstants.TITLE_PROPERTY,
						"Project creation problems");
				StatusManager.getManager().handle(statusAdapter,
						StatusManager.LOG | StatusManager.BLOCK);
			}
			return null;
		}

		try {
			BundleMakerCore.addBundleMakerNature(newProjectHandle);
			// IBundleMakerProject bundleMakerProject = BundleMakerCore
			// .getBundleMakerProject(newProjectHandle,
			// new NullProgressMonitor());
			// BundleMakerProjectDescription bundleMakerProjectDescription =
			// bundleMakerProject
			// .getProjectDescription();
			// bundleMakerProjectDescription.setJre(mainPage.get);

		} catch (CoreException ex) {
			IStatus status = BundleMakerUiUtils.newStatus(ex,
					"Could not add Bundlemaker nature");
			StatusAdapter statusAdapter = new StatusAdapter(status);
			statusAdapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY,
					"Project creation problems");
			StatusManager.getManager().handle(status, StatusManager.BLOCK);
			return null;

		}

		newProject = newProjectHandle;

		return newProject;
	}
}
