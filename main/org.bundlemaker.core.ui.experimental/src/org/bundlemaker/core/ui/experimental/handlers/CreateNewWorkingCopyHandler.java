package org.bundlemaker.core.ui.experimental.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
public class CreateNewWorkingCopyHandler extends AbstractArtifactBasedHandler {
	@Override
	protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts)
		throws Exception
	{

		final Shell shell = HandlerUtil.getActiveShellChecked(event);
		if (selectedArtifacts.size() != 1) {
			
			// Invalid Selection. Should not happen. Anyway...

			MessageDialog.openError(
				shell,
				"New Working Copy",
				"Please select exactly one BundleMaker artifact"
			);
			return;
		}

		// Get BundleMaker Projekt
		IRootArtifact rootArtifact = selectedArtifacts.get(0).getRoot();
		final IBundleMakerProject bundleMakerProject =
			rootArtifact.getModularizedSystem().getBundleMakerProject();

		// Determine default name 
		Collection<IModularizedSystem> existingWorkingCopies =
			bundleMakerProject.getModularizedSystemWorkingCopies();

		Set<String> existingWorkingCopyNames = new HashSet<String>();
		for (IModularizedSystem system : existingWorkingCopies) {
			existingWorkingCopyNames.add(system.getName());
		}
		
		String defaultName = null;
		final String prefix = bundleMakerProject.getProject().getName() + "-";
		
		for (int i=1;i<=20;i++) {
			String candidate = prefix + i;
			if (!bundleMakerProject.hasModularizedSystemWorkingCopy(candidate)) {
				defaultName = candidate;
				break;
			}
		}
		
		// Create Validator to make sure user doesn't take a name already in use 
		WorkingCopyNameInputValidator validator =
			new WorkingCopyNameInputValidator(existingWorkingCopyNames);
		
		// Create the dialog
		InputDialog inputDialog =
			new InputDialog(
				shell,
				"New Working Copy",
				"Enter Name of new Working Copy",
				defaultName,
				validator
			);
		
		// Open
		if (inputDialog.open() != Window.OK) {
			// canceled
			return;
		}
		
		final String workingCopyName = inputDialog.getValue();
		
		// Create the working copy
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
		
		progressMonitorDialog.run(true,true,new IRunnableWithProgress() {
			
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
				
				// Create Working Copy
				try {
					bundleMakerProject.createModularizedSystemWorkingCopy(new NullProgressMonitor(), workingCopyName);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				}
				
				// Update Navigator
				shell.getDisplay().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						 CommonNavigatorUtils.update(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
						
					}
				});
				
				
			}
		});

	}

	/**
	 * An {@link IInputValidator} used to make sure the entered working copy name
	 * is unique
	 *
	*/
	class WorkingCopyNameInputValidator implements IInputValidator {

		private final Set<String> _existingWorkingCopyNames;

		public WorkingCopyNameInputValidator(Set<String> existingWorkingCopyNames) {
			_existingWorkingCopyNames = existingWorkingCopyNames;
		}

		@Override
		public String isValid(String newText) {

			if (newText == null || newText.trim().isEmpty()) {
				return "Name must not be empty";
			}

			if (_existingWorkingCopyNames.contains(newText)) {
				return "A Working Copy with that name already exists";
			}

			return null;
		}

	}

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
