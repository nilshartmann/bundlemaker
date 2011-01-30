/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.ui.internal.UIImages;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectDescriptionOverviewPage extends FormPage {
	private Section _projectContentMasterSection;
	private Section _projectContentDetailsSection;
	private TableTree _tableTree;
	private TableTreeViewer _tableTreeViewer;
	private Composite _composite;
	private Label _label;
	private Text _nameText;
	private Label _versionLabel;

	public ProjectDescriptionOverviewPage(FormEditor editor) {
		super(editor, "Project overview", "Project overview");
	}

	protected void createFormContent(IManagedForm mform) {
		super.createFormContent(mform);
		FormToolkit toolkit = mform.getToolkit();
		final ScrolledForm form = mform.getForm();
		toolkit.decorateFormHeading(form.getForm());
		form.setImage(UIImages.BUNDLEMAKER_ICON.getImage());
		form.setText("Bundlemaker project");
		ColumnLayout columnLayout = new ColumnLayout();
		columnLayout.minNumColumns = 2;
		columnLayout.maxNumColumns = 2;
		mform.getForm().getBody().setLayout(columnLayout);

		_projectContentMasterSection = mform.getToolkit().createSection(
				mform.getForm().getBody(), Section.TITLE_BAR);
		ColumnLayoutData cld_projectContentMasterSection = new ColumnLayoutData();
		cld_projectContentMasterSection.heightHint = 127;
		_projectContentMasterSection
				.setLayoutData(cld_projectContentMasterSection);
		mform.getToolkit().paintBordersFor(_projectContentMasterSection);
		_projectContentMasterSection.setText("Project content");

		_tableTreeViewer = new TableTreeViewer(_projectContentMasterSection,
				SWT.BORDER | SWT.FULL_SELECTION);
		_tableTree = _tableTreeViewer.getTableTree();
		mform.getToolkit().paintBordersFor(_tableTree);
		_projectContentMasterSection.setClient(_tableTree);

		_projectContentDetailsSection = mform.getToolkit().createSection(
				mform.getForm().getBody(), Section.TITLE_BAR);
		ColumnLayoutData cld_projectContentDetailsSection = new ColumnLayoutData();
		cld_projectContentDetailsSection.heightHint = 146;
		_projectContentDetailsSection
				.setLayoutData(cld_projectContentDetailsSection);
		mform.getToolkit().paintBordersFor(_projectContentDetailsSection);
		_projectContentDetailsSection.setText("Details");

		_composite = mform.getToolkit().createComposite(
				_projectContentDetailsSection, SWT.NONE);
		mform.getToolkit().paintBordersFor(_composite);
		_projectContentDetailsSection.setClient(_composite);
		// ColumnLayout cl_composite = new ColumnLayout();
		// cl_composite.minNumColumns = 2;
		// cl_composite.maxNumColumns = 2;
		_composite.setLayout(FormLayoutUtils.createFormGridLayout(false, 2));

		_label = mform.getToolkit().createLabel(_composite, "Name:", SWT.NONE);
		_nameText = mform.getToolkit().createText(_composite, "", SWT.NONE);
		_nameText.setText("");

		_versionLabel = mform.getToolkit().createLabel(_composite, "Version:",
				SWT.NONE);
		mform.getToolkit().createText(_composite, "", SWT.NONE);

		mform.getToolkit().createLabel(_composite, "Sources:", SWT.NONE);
		mform.getToolkit().createText(_composite, "", SWT.NONE);

		// mform.getToolkit().createLabel(_composite, "Binaries:", SWT.NONE);
		// mform.getToolkit().createText(_composite, "", SWT.NONE);
	}
}
