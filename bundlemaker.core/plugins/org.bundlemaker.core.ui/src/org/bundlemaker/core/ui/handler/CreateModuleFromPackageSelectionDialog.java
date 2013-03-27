package org.bundlemaker.core.ui.handler;

import java.util.Collection;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateModuleFromPackageSelectionDialog extends TitleAreaDialog {

  private final ModifyListener _validationModifyListener = new ValidationModifyListener();

  /** MSG_TITLE */
  private static final String  MSG_TITLE                 = "Create New Module";

  /** MSG_MESSAGE */
  private static final String  MSG_MESSAGE               = "Please choose target and name of new Module";

  /** MSG_CANNOT_ADD_ARTIFACTS */
  private static final String  MSG_CANNOT_ADD_ARTIFACTS  = "Can't create module in %s.";

  /** the tree viewer */
  private TreeViewer           _treeViewer;

  /** the root artifact */
  private IRootArtifact        _rootArtifact;

  /** the new parent */
  private IBundleMakerArtifact _newParent;

  private String               _moduleName;

  private String               _moduleVersion;

  private Text                 _nameTextField;

  private Text                 _versionTextField;

  /**
   * <p>
   * Creates a new instance of type {@link SelectArtifactParentDialog}.
   * </p>
   * 
   * @param shell
   * @param rootArtifact
   */
  public CreateModuleFromPackageSelectionDialog(Shell shell, IRootArtifact rootArtifact,
      String newModuleName, String newModuleVersion) {
    super(shell);

    // asserts
    Assert.isNotNull(rootArtifact);

    this._moduleName = newModuleName;
    this._moduleVersion = newModuleVersion;

    // set the parameters
    _rootArtifact = rootArtifact;
    _newParent = rootArtifact;

    //
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());

    setHelpAvailable(false);
  }

  /**
   * <p>
   * Returns the selected artifact.
   * </p>
   * 
   * @return the selected artifact.
   */
  public IGroupAndModuleContainer getParent() {
    return (IGroupAndModuleContainer) _newParent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setErrorMessage(String newErrorMessage) {
    super.setErrorMessage(newErrorMessage);

    Control button = getButton(IDialogConstants.OK_ID);
    if (button != null) {
      button.setEnabled(newErrorMessage == null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    // set text
    newShell.setText(MSG_TITLE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createDialogArea(Composite parent) {

    //
    setTitle(MSG_TITLE);
    setMessage(MSG_MESSAGE);

    Label label = new Label(parent, SWT.NONE);
    label.setText("Choose destination");

    //
    _treeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL
        | SWT.V_SCROLL | SWT.BORDER);
    _treeViewer.setInput(_rootArtifact);
    _treeViewer.expandToLevel(2);

    _treeViewer.setSelection(
        new StructuredSelection(((ArtifactTreeContentProvider) _treeViewer.getContentProvider()).getVirtualRoot()),
        true);
    _treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        validateInput();
      }
    });

    Composite dialogComposite = new Composite(parent, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    dialogComposite.setLayout(new GridLayout(2, false));

    label = new Label(dialogComposite, SWT.NONE);
    label.setText("Name:");

    _nameTextField = new Text(dialogComposite, SWT.BORDER);
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _nameTextField.setLayoutData(gd);
    _nameTextField.setText(_moduleName);

    label = new Label(dialogComposite, SWT.NONE);
    label.setText("Version:");
    _versionTextField = new Text(dialogComposite, SWT.BORDER);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _versionTextField.setLayoutData(gd);
    _versionTextField.setText(_moduleVersion);

    _nameTextField.addModifyListener(_validationModifyListener);
    _versionTextField.addModifyListener(_validationModifyListener);

    validateInput();

    //
    Dialog.applyDialogFont(parent);

    //
    return parent;
  }

  /**
   * <p>
   * </p>
   */
  protected void validateInput() {

    // get the new parent
    _newParent = (IBundleMakerArtifact) ((IStructuredSelection) _treeViewer.getSelection()).getFirstElement();

    String errorMessage = validateTarget();

    if (errorMessage == null) {
      errorMessage = validateModuleName();
    }

    if (errorMessage == null) {
      errorMessage = validateModuleVersion();
    }

    if (errorMessage == null) {
      String artifactName = _nameTextField.getText() + "_" + _versionTextField.getText();
      Collection<IBundleMakerArtifact> children = _newParent.getChildren();
      for (IBundleMakerArtifact child : children) {
        if (child.getName().equals(artifactName)) {
          errorMessage = "A Module with the specified Name and Version already exists on " + _newParent.getName();
          break;
        }
      }
    }

    // set error message
    setErrorMessage(errorMessage);
  }

  protected String validateTarget() {
    if (!(_newParent instanceof IGroupAndModuleContainer)) {
      return String.format(MSG_CANNOT_ADD_ARTIFACTS, _newParent.getName());
    }

    return null;
  }

  protected String validateModuleName() {
    String moduleName = _nameTextField.getText();
    if (moduleName == null || moduleName.trim().isEmpty()) {
      return "Enter a valid module name";
    }

    return null;
  }

  protected String validateModuleVersion() {
    String moduleVersion = _versionTextField.getText();
    if (moduleVersion == null || moduleVersion.trim().isEmpty()) {
      return "Enter a version, e.g. 1.0.0";
    }

    return null;
  }

  @Override
  protected void okPressed() {

    _moduleName = _nameTextField.getText();
    _moduleVersion = _versionTextField.getText();

    super.okPressed();
  }

  public String getModuleName() {
    return this._moduleName;
  }

  public String getModuleVersion() {
    return this._moduleVersion;
  }

  class ValidationModifyListener implements ModifyListener {

    @Override
    public void modifyText(ModifyEvent e) {
      validateInput();
    }

  }

}
