package org.bundlemaker.core.ui.editor;

import java.util.LinkedList;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Allows the creation and modification on IFilebasedContent
 * 
 * @author Nils Hartmann
 * 
 */
public class ModifyProjectContentDialog extends TitleAreaDialog {

  private Text                   _nameTextField;

  private Text                   _versionTextField;

  private Button                  _analyzeButton;

  private Button                  _analyzeSourcesButton;

  /**
   * The original content or null if a new content is created with this dialog
   */
  private final IFileBasedContent _originalContent;

  private ContentListBlock       _binariesContentList;

  private ContentListBlock       _sourcesContentList;

  private String                 _name;

  private String                 _version;

  private java.util.List<String> _binaryRoots;

  private java.util.List<String> _sourceRoots;

  boolean                         _analyze        = true;

  boolean                         _analyzeSources = false;

  public ModifyProjectContentDialog(Shell parentShell, IFileBasedContent existingContent) {
    super(parentShell);
    Assert.isNotNull(existingContent);
    _originalContent = existingContent;
    _name = existingContent.getName();
    _version = existingContent.getVersion();
    _binaryRoots = stringList(existingContent.getBinaryRootPaths());
    _sourceRoots = stringList(existingContent.getSourceRootPaths());
    _analyze = existingContent.isResourceContent();
    _analyzeSources = existingContent.isAnalyzeSourceResources();

    configureDialog();
  }

  public ModifyProjectContentDialog(Shell parentShell) {
    super(parentShell);
    _originalContent = null;
    configureDialog();
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  private static java.util.List<String> stringList(Set<IRootPath> paths) {
    java.util.List<String> strings = new LinkedList<String>();
    if (paths != null) {
      for (IRootPath path : paths) {
        strings.add(path.getUnresolvedPath().toString());
      }
    }
    return strings;
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    String text = (isNewContent() ? "Add" : "Edit");

    text += " content";

    newShell.setText(text);
  }

  /**
   * Returns true if this dialog instance edits <b>new</b> content (rather then modifying an already existing
   * IFileBasedContent)
   * 
   * @return
   */
  protected boolean isNewContent() {
    return (_originalContent == null);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Define content");
    setMessage("Enter the content description");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout gridLayout = FormLayoutUtils.createFormGridLayout(false, 2);
    dialogComposite.setLayout(gridLayout);

    addNameAndVersionRow(dialogComposite);
    _binariesContentList = addContentList(dialogComposite, "Binaries");
      _sourcesContentList = addContentList(dialogComposite, "Sources");

    prepopulateForm();
    refreshEnablement();

    Dialog.applyDialogFont(areaComposite);

    return areaComposite;

  }

  private void prepopulateForm() {
    if (isNewContent()) {

      // Set default values
      _analyzeButton.setSelection(_analyze);
      _analyzeSourcesButton.setSelection(_analyzeSources);

      // nothing else to pre-populate
      return;
    }

    _nameTextField.setText(_name);
    _versionTextField.setText(_version);

    _binariesContentList.setItems(_binaryRoots.toArray(new String[0]));
    if (_sourcesContentList != null) {
      _sourcesContentList.setItems(_sourceRoots.toArray(new String[0]));
    }

    _analyzeButton.setSelection(_originalContent.isResourceContent());
    _analyzeSourcesButton.setSelection(_originalContent.isAnalyzeSourceResources());
  }

  private void addNameAndVersionRow(Composite dialogComposite) {

    _analyzeButton = new Button(dialogComposite, SWT.CHECK);
    _analyzeButton.setText("Analyze");
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    _analyzeButton.setLayoutData(gridData);
    _analyzeButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        refreshEnablement();
      }

    });

    _analyzeSourcesButton = new Button(dialogComposite, SWT.CHECK);
    _analyzeSourcesButton.setText("Analyze sources");
    gridData = new GridData();
    gridData.horizontalSpan = 2;
    _analyzeSourcesButton.setLayoutData(gridData);
    _analyzeSourcesButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        refreshEnablement();
      }

    });

    addLabel(dialogComposite, "Name:");
    _nameTextField = addText(dialogComposite);

    addLabel(dialogComposite, "Version:");
    _versionTextField = addText(dialogComposite);
  }

  private ContentListBlock addContentList(Composite dialogComposite, String title) {
    addLabel(dialogComposite, title);

    ContentListBlock contentListBlock = new ContentListBlock();
    contentListBlock.createContent(dialogComposite);
    return contentListBlock;
  }

  private void refreshEnablement() {
    boolean analyze = _analyzeButton.getSelection();
    _analyzeSourcesButton.setEnabled(analyze);
    _sourcesContentList.setEnabled(analyze);
  }

  private Text addText(Composite parent) {
    Text textField = new Text(parent, SWT.BORDER);

    GridData textFieldData = new GridData(SWT.FILL, SWT.NONE, true, false);
    textFieldData.widthHint = new PixelConverter(textField).convertWidthInCharsToPixels(25);
    textField.setLayoutData(textFieldData);
    // textField.addModifyListener(_updateListener);
    return textField;
  }

  private Label addLabel(Composite parent, String text) {
    Label label = new Label(parent, SWT.NONE);
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    label.setLayoutData(gd);

    label.setText(text);
    return label;
  }

  public String getName() {
    return _name;
  }

  public String getVersion() {
    return _version;
  }

  public java.util.List<String> getBinaryPaths() {
    return _binaryRoots;
  }

  public java.util.List<String> getSourcePaths() {
    return _sourceRoots;
  }

  public boolean isAnalyze() {
    return _analyze;
  }

  public boolean isAnalyzeSources() {
    return _analyzeSources;
  }

  @Override
  protected void okPressed() {
    _name = _nameTextField.getText();
    _version = _versionTextField.getText();
    _binaryRoots = _binariesContentList.getItems();
      _sourceRoots = _sourcesContentList.getItems();

    _analyze = _analyzeButton.getSelection();
    _analyzeSources = _analyzeSourcesButton.getSelection();

    super.okPressed();
  }

}
