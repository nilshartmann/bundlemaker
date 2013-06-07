package org.bundlemaker.core.ui.projecteditor.filebased.edit;

import java.io.File;
import java.util.LinkedList;
import java.util.Set;

import org.bundlemaker.core.common.utils.IFileBasedProjectContentInfo;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.project.filecontent.FileBasedProjectContentProvider;
import org.bundlemaker.core.ui.FormLayoutUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
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
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class EditFileBasedContentProviderDialog extends TitleAreaDialog {
  private Text                                  _nameTextField;

  private Text                                  _versionTextField;

  private Button                                _analyzeButton;

  private Button                                _analyzeSourcesButton;

  /**
   * The original content or null if a new content is created with this dialog
   */
  private final FileBasedProjectContentProvider _originalContent;

  private ContentListBlock                      _binariesContentList;

  private ContentListBlock                      _sourcesContentList;

  private String                                _name;

  private String                                _version;

  private java.util.List<String>                _binaryRoots;

  private java.util.List<String>                _sourceRoots;

  boolean                                       _analyze        = true;

  boolean                                       _analyzeSources = false;

  public EditFileBasedContentProviderDialog(Shell shell, FileBasedProjectContentProvider existingContent) {
    super(shell);
    Assert.isNotNull(existingContent);
    _originalContent = existingContent;
    _name = _originalContent.getName();
    _version = _originalContent.getVersion();
    _binaryRoots = stringList(_originalContent.getBinaryPaths());
    _sourceRoots = stringList(_originalContent.getSourcePaths());
    _analyze = _originalContent.isAnalyze();
    _analyzeSources = _originalContent.getAnalyzeMode() == AnalyzeMode.BINARIES_AND_SOURCES;

  }

  public EditFileBasedContentProviderDialog(Shell shell) {
    super(shell);

    _originalContent = null;
  }

  private static java.util.List<String> stringList(Set<VariablePath> paths) {
    java.util.List<String> strings = new LinkedList<String>();
    if (paths != null) {
      for (VariablePath path : paths) {
        strings.add(String.valueOf(path.getUnresolvedPath()));
      }
    }
    return strings;
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

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    setMessage("Define project content from archives and folders");
    setTitle("Add files and folders");
    // setPageComplete(false);

    Composite dialogComposite = new Composite(parent, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout gridLayout = FormLayoutUtils.createFormGridLayout(false, 2);
    dialogComposite.setLayout(gridLayout);

    createHeadSection(dialogComposite);
    _binariesContentList = addContentList(dialogComposite, "Binaries");
    _sourcesContentList = addContentList(dialogComposite, "Sources");

    prepopulateForm();
    refresh();

    Dialog.applyDialogFont(parent);

    // setControl(dialogComposite);
    return parent;

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

    _analyzeButton.setSelection(_originalContent.isAnalyze());
    _analyzeSourcesButton.setSelection(_originalContent.getAnalyzeMode() == AnalyzeMode.BINARIES_AND_SOURCES);
  }

  private void createHeadSection(Composite dialogComposite) {

    Composite buttonRow = new Composite(dialogComposite, SWT.NONE);
    buttonRow.setLayout(new GridLayout(2, true));
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    buttonRow.setLayoutData(gridData);

    _analyzeButton = new Button(buttonRow, SWT.CHECK);
    _analyzeButton.setText("Analyze");

    _analyzeButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        refresh();
      }

    });

    _analyzeSourcesButton = new Button(buttonRow, SWT.CHECK);
    _analyzeSourcesButton.setText("Analyze sources");
    _analyzeSourcesButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        refresh();
      }

    });

    addLabel(dialogComposite, "Name:");
    _nameTextField = addText(dialogComposite);

    addLabel(dialogComposite, "Version:");
    _versionTextField = addText(dialogComposite);
  }

  private ContentListBlock addContentList(Composite dialogComposite, String title) {
    addLabel(dialogComposite, title);

    ContentListBlock contentListBlock = new ContentListBlock() {

      /*
       * (non-Javadoc)
       * 
       * @see org.bundlemaker.core.ui.editor.ContentListBlock#contentListChanged()
       */
      @Override
      protected void contentListChanged() {
        refresh();
      }

    };
    contentListBlock.createContent(dialogComposite);
    return contentListBlock;
  }

  private void refreshMessages() {
    if (_analyzeSourcesButton.isEnabled() && _analyzeSourcesButton.getSelection() == true
        && _sourcesContentList.isEmpty()) {
      setMessage("You have selected to analyze source but haven't added any sources", IMessageProvider.WARNING);
      return;
    }

    if (_sourcesContentList.hasItems()) {
      if (_analyzeButton.getSelection() == false) {
        setMessage("You have added source entries but have not enabled analyze content", IMessageProvider.WARNING);
        return;
      }
      if (_analyzeSourcesButton.getSelection() == false) {
        setMessage("You have added source entries but have not enabled to analyze them", IMessageProvider.WARNING);
        return;
      }
    }

    // no warnings
    setMessage("Enter the content description");

  }

  private void refresh() {
    boolean analyze = _analyzeButton.getSelection();
    _analyzeSourcesButton.setEnabled(analyze);
    _sourcesContentList.setEnabled(analyze);

    guessNameAndVersionIfNotSet();

    boolean canFinish = _binariesContentList.hasItems() && isNameAndVersionSet();
    // setPageComplete(canFinish);

    refreshMessages();
  }

  private boolean isNameAndVersionSet() {
    return !(_nameTextField.getText().isEmpty() && _versionTextField.getText().isEmpty());
  }

  /**
   * 
   */
  private void guessNameAndVersionIfNotSet() {

    if (_binariesContentList.getItems().size() != 1) {
      return; // nothing to guess
    }

    if (_nameTextField.getText().isEmpty() && _versionTextField.getText().isEmpty()) {
      IFileBasedProjectContentInfo jarInfo = getJarInfo(_binariesContentList.getItems().get(0));
      if (jarInfo != null) {
        _nameTextField.setText(jarInfo.getName());
        _versionTextField.setText(jarInfo.getVersion());
      }
    }

  }

  protected IFileBasedProjectContentInfo getJarInfo(String fileName) {
    try {

      VariablePath variablePath = new VariablePath(fileName);

      File file = variablePath.getAsFile();
      return IFileBasedProjectContentInfo.Factory.extractFileBasedProjectContentInfo(file);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    // either not an existing file or failure
    return null;
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

  // @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed() {
    finish();
    super.okPressed();
  }

  public void finish() {
    _name = _nameTextField.getText();
    _version = _versionTextField.getText();
    _binaryRoots = _binariesContentList.getItems();
    _sourceRoots = _sourcesContentList.getItems();

    _analyze = _analyzeButton.getSelection();
    _analyzeSources = _analyzeSourcesButton.getSelection();

  }

}
