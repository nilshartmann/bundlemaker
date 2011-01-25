/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
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
		if (input != null) {
			System.out.println("input: " + input);
			System.out.println("input class: " + input.getClass().getName());
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
