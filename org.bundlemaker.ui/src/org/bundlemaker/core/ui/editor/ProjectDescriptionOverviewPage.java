/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.ui.internal.UIImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectDescriptionOverviewPage extends FormPage {

	public ProjectDescriptionOverviewPage(FormEditor editor) {
		super(editor, "Project overview", "Project overview");
	}

	protected void createFormContent(IManagedForm mform) {
		super.createFormContent(mform);
		FormToolkit toolkit = mform.getToolkit();
		ScrolledForm form = mform.getForm();
		form.setImage(UIImages.BUNDLEMAKER_ICON.getImage());
		form.setText("Bundlemaker project");
		form.getBody().setLayout(new GridLayout());
		toolkit.createButton(form.getBody(), "Checkbox", SWT.CHECK);

	}

}
