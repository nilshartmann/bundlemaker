/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <p>
 * A Dialog that prompts for all informations needed for a
 * {@link org.bundlemaker.core.projectdescription.IFileBasedContent IFileBasedContent}
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ResourceLocationSelectorDialog extends TitleAreaDialog {

  private ResourceChooser      _sourcePathChooser;

  private ResourceChooser      _binaryPathChooser;

  private Text                 _nameTextField;

  private Text                 _versionTextField;

  private String               _resourceName       = "";

  private String               _resourceVersion    = "";

  private String               _resourceSourcePath = "";

  private String               _resourceBinaryPath = "";

  private final UpdateListener _updateListener     = new UpdateListener();

  /**
   * <p>
   * Creates a new instance of type {@link ResourceLocationSelectorDialog}.
   * </p>
   * 
   * @param parentShell
   */
  protected ResourceLocationSelectorDialog(Shell parentShell) {
    super(parentShell);

    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText("Add resource");
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Add resource");
    setMessage("Select a resource");

    Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite composite = new Composite(areaComposite, SWT.NONE);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    gridLayout.makeColumnsEqualWidth = false;
    composite.setLayout(gridLayout);

    addLabel(composite, "Name:");
    _nameTextField = addText(composite);

    addLabel(composite, "Version:");
    _versionTextField = addText(composite);

    addLabel(composite, "Binary:");
    _binaryPathChooser = addResourceChooser(composite);

    addLabel(composite, "Source:");
    _sourcePathChooser = addResourceChooser(composite);

    return composite;
  }

  @Override
  protected Control createContents(Composite parent) {
    Control control = super.createContents(parent);
    updateEnablement();
    return control;
  }

  private ResourceChooser addResourceChooser(Composite parent) {
    ResourceChooser resourceChooser = new ResourceChooser(parent);
    resourceChooser.addModifyListener(_updateListener);
    return resourceChooser;
  }

  private Text addText(Composite parent) {
    Text textField = new Text(parent, SWT.BORDER);

    GridData textFieldData = new GridData(SWT.FILL, SWT.NONE, true, false);
    textFieldData.widthHint = new PixelConverter(textField).convertWidthInCharsToPixels(25);
    textField.setLayoutData(textFieldData);
    textField.addModifyListener(_updateListener);
    return textField;
  }

  private Label addLabel(Composite parent, String text) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(text);
    return label;
  }

  private void updateEnablement() {
    Button okButton = getButton(IDialogConstants.OK_ID);
    okButton.setEnabled(isResourceNameSet() && isResourceVersionSet() && isResourceBinaryPathSet());
  }

  public String getResourceBinaryPath() {
    return _binaryPathChooser.getSelectedPath();
  }

  public boolean isResourceBinaryPathSet() {
    return _binaryPathChooser.isPathSelected();
  }

  public String getResourceSourcePath() {
    return _sourcePathChooser.getSelectedPath();
  }

  public boolean isSourcePathSet() {
    return _sourcePathChooser.isPathSelected();
  }

  /**
   * <p>
   * Returns true if a resource name has been entered into the name textfield
   * </p>
   * 
   * @return
   */
  public boolean isResourceNameSet() {
    return !getResourceName().isEmpty();
  }

  /**
   * <p>
   * Returns the name of the resource that is entered into the name textfield or an empty string, when no name has been
   * entered. The String that is returned is {@link String#trim() trimed} before it is returned
   * </p>
   * 
   * @return the resource name. Never null.
   */
  public String getResourceName() {
    return _resourceName;
  }

  public boolean isResourceVersionSet() {
    return (getResourceVersion() != null);
  }

  /**
   * <p>
   * Returns the version of the resource that is entered into the version textfield or an empty string, when no name has
   * been entered. The String that is returned is {@link String#trim() trimed} before it is returned
   * </p>
   * 
   * @return the resource version. Never null.
   */
  public String getResourceVersion() {
    return _resourceVersion;
  }

  /**
   * A {@link ModifyListener} that refreshs the enabled-state after a Text has been modified
   * <p>
   * </p>
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   * 
   */
  class UpdateListener implements ModifyListener {

    @Override
    public void modifyText(ModifyEvent e) {
      // TODO ...
      _resourceName = _nameTextField.getText().trim();
      _resourceVersion = _versionTextField.getText().trim();

      updateEnablement();
    }
  }

}
