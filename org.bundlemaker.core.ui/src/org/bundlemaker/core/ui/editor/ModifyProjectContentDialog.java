package org.bundlemaker.core.ui.editor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ModifyProjectContentDialog extends TitleAreaDialog {

  private final boolean          _editResources;

  private final boolean          _newContent;

  private Text                   _nameTextField;

  private Text                   _versionTextField;

  private List                   _binariesList;

  private List                   _sourcesList;

  private String                 _name;

  private String                 _version;

  private java.util.List<String> _binaryRoots;

  private java.util.List<String> _sourceRoots;

  public ModifyProjectContentDialog(Shell parentShell, IFileBasedContent existingContent) {
    super(parentShell);
    Assert.isNotNull(existingContent);
    _newContent = false;
    _editResources = existingContent.isResourceContent();
    _name = existingContent.getName();
    _version = existingContent.getVersion();
    _binaryRoots = stringList(existingContent.getBinaryPaths());
    _sourceRoots = stringList(existingContent.getSourcePaths());

    configureDialog();
  }

  public ModifyProjectContentDialog(Shell parentShell, boolean editResources) {
    super(parentShell);

    _editResources = editResources;
    _newContent = true;
    configureDialog();
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  private static java.util.List<String> stringList(Set<IPath> paths) {
    java.util.List<String> strings = new LinkedList<String>();
    if (paths != null) {
      for (IPath path : paths) {
        strings.add(path.toString());
      }
    }
    return strings;
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    String text = (_newContent ? "Add" : "Edit");

    text += (_editResources ? " resource" : " type");

    newShell.setText(text);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Define " + (_editResources ? "Resource" : "Type") + " Content");
    setMessage("Enter the content description");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout gridLayout = FormLayoutUtils.createFormGridLayout(false, 2);
    dialogComposite.setLayout(gridLayout);

    addNameAndVersionRow(dialogComposite);
    _binariesList = addContentList(dialogComposite, "Binaries");
    if (_editResources) {
      _sourcesList = addContentList(dialogComposite, "Sources");
    }

    prepopulateForm();

    Dialog.applyDialogFont(areaComposite);

    return areaComposite;

  }

  private void prepopulateForm() {
    if (_newContent) {
      return;
    }

    _nameTextField.setText(_name);
    _versionTextField.setText(_version);

    _binariesList.setItems(_binaryRoots.toArray(new String[0]));
    if (_sourcesList != null) {
      _sourcesList.setItems(_sourceRoots.toArray(new String[0]));
    }
  }

  private void addNameAndVersionRow(Composite dialogComposite) {
    addLabel(dialogComposite, "Name:");
    _nameTextField = addText(dialogComposite);

    addLabel(dialogComposite, "Version:");
    _versionTextField = addText(dialogComposite);
  }

  private List addContentList(Composite dialogComposite, String title) {
    addLabel(dialogComposite, title);

    Composite contentListComposite = new Composite(dialogComposite, SWT.NONE);
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    contentListComposite.setLayoutData(layoutData);
    contentListComposite.setLayout(new GridLayout(2, false));

    final List list = new List(contentListComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
    layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.verticalIndent = 0;
    // layoutData.verticalAlignment = GridData.BEGINNING;
    // layoutData.grabExcessVerticalSpace = true;
    list.setLayoutData(layoutData);

    Composite buttonBar = new Composite(contentListComposite, SWT.NONE);

    buttonBar.setLayout(new GridLayout(1, false));
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalIndent = 0;
    buttonBar.setLayoutData(gd);

    final Button addArchivesButton = new Button(buttonBar, SWT.PUSH);
    addArchivesButton.setText("Add archives...");
    addArchivesButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addArchivesButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        String[] archives = selectArchives(addArchivesButton.getShell(), "Select archives to add");
        for (String archive : archives) {
          list.add(archive);
        }

      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    final Button addFolderButton = new Button(buttonBar, SWT.PUSH);
    addFolderButton.setText("Add folder...");
    addFolderButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addFolderButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        String folder = selectFolder(addFolderButton.getShell());
        if (folder != null) {
          list.add(folder);
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });
    Button removeButton = new Button(buttonBar, SWT.PUSH);
    removeButton.setText("Remove");
    removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    removeButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        list.remove(list.getSelectionIndices());
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    return list;

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

  /**
   * Opens a {@link DirectoryDialog} and returns the qualified selected folder or null if the dialog has been canceled.
   * 
   * @param parentShell
   * @return
   */
  private String selectFolder(Shell parentShell) {
    DirectoryDialog dialog = new DirectoryDialog(parentShell, SWT.MULTI);
    return dialog.open();
  }

  private String[] selectArchives(Shell parentShell, String title) {
    FileDialog fileDialog = new FileDialog(parentShell, SWT.MULTI);
    fileDialog.setText(title);
    fileDialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
    if (fileDialog.open() == null) {
      return new String[0];
    }
    String[] fileNames = fileDialog.getFileNames();
    String[] binaryRoots = new String[fileNames.length];
    if (fileNames.length > 0) {
      // Add all selected archives to the project description
      for (int i = 0; i < fileNames.length; i++) {
        IPath path = new Path(fileDialog.getFilterPath()).append(fileNames[i]);
        String binaryRoot = path.toOSString();
        binaryRoots[i] = binaryRoot;

      }

    }
    return binaryRoots;
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

  @Override
  protected void okPressed() {
    _name = _nameTextField.getText();
    _version = _versionTextField.getText();
    _binaryRoots = Arrays.asList(_binariesList.getItems());

    if (_sourcesList != null) {
      _sourceRoots = Arrays.asList(_sourcesList.getItems());
    }
    super.okPressed();
  }

}
