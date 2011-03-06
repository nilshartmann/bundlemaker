/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectDescriptionEditor extends FormEditor {

	public ProjectDescriptionEditor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addPages() {
		try {
			addPage(new ProjectDescriptionOverviewPage(this));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		if (input == null) {
			System.err.println("Input is null ?!?!?!");
			return;
		}
		IFileEditorInput adapter = (IFileEditorInput) input
				.getAdapter(IFileEditorInput.class);
		if (adapter == null) {
			System.err.println("Unsupported EditorInput " + input
					+ " cannot be adapted to an "
					+ IFileEditorInput.class.getName());
			return;
		}
		System.out.println("input: " + input);
		System.out.println("input class: " + input.getClass().getName());
		System.out.println("adapter: " + adapter);
		IProject project = adapter.getFile().getProject();
		try {
			IBundleMakerProject bundleMakerProject = BundleMakerCore
					.getBundleMakerProject(project, new NullProgressMonitor());
			List<? extends IFileBasedContent> fileBasedContent = bundleMakerProject
					.getProjectDescription().getFileBasedContent();
			for (IFileBasedContent iFileBasedContent : fileBasedContent) {
				System.out.println("content: " + iFileBasedContent);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
