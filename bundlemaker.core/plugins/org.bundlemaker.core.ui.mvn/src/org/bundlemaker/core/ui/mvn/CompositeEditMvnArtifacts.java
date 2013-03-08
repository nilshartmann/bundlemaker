package org.bundlemaker.core.ui.mvn;

import java.util.LinkedList;

import org.bundlemaker.core.mvn.content.MvnArtifactType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CompositeEditMvnArtifacts extends Composite {

  /** the list displaying the artifact entries */
  private List                            _contentList;

  /** - */
  private java.util.List<MvnArtifactType> _artifactTypes;

  /** - */
  private Button                          _removeButton;

  /**
   * <p>
   * Creates a new instance of type {@link CompositeEditMvnArtifacts}.
   * </p>
   * 
   * @param parent
   * @param style
   */
  public CompositeEditMvnArtifacts(Composite parent, int style) {
    this(parent, style, null);
  }

  /**
   * <p>
   * Creates a new instance of type {@link CompositeEditMvnArtifacts}.
   * </p>
   * 
   * @param parent
   * @param style
   * @param artifactTypes
   */
  public CompositeEditMvnArtifacts(Composite parent, int style, java.util.List<MvnArtifactType> artifactTypes) {
    super(parent, style);

    //
    _artifactTypes = artifactTypes == null ? new LinkedList<MvnArtifactType>() : new LinkedList<MvnArtifactType>(
        artifactTypes);

    //
    init();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public java.util.List<MvnArtifactType> getArtifactTypes() {
    // return the result
    return _artifactTypes;
  }

  /**
   * <p>
   * </p>
   */
  protected void init() {

    //
    final Shell shell = getShell();
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    setLayoutData(layoutData);
    setLayout(new GridLayout(2, false));

    // Create the SWT List displaying the content
    _contentList = new List(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
    layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.verticalIndent = 0;
    _contentList.setLayoutData(layoutData);
    _contentList.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        refreshEnablement();
      }
    });

    // Create the button bar on the right side of the content list
    Composite buttonBar = new Composite(this, SWT.NONE);

    buttonBar.setLayout(new GridLayout(1, false));
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalIndent = 0;
    buttonBar.setLayoutData(gd);

    //
    newTextButton(buttonBar, "Add Artifact...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addArtifact(shell);
      }

    });

    //
    _removeButton = newTextButton(buttonBar, "Remove Entry", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        removeArtifact();
      }

    });

    //
    redrawList();
    refreshEnablement();
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected void onRefreshEnablement() {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @param parentShell
   */
  private void addArtifact(Shell parentShell) {

    // the maven input dialog
    DialogEditMvnCoordinates mvnInputDialog = new DialogEditMvnCoordinates(parentShell);

    // the input dialog
    if (DialogEditMvnCoordinates.OK == mvnInputDialog.open()) {

      //
      MvnArtifactType mvnArtifactType = new MvnArtifactType();
      mvnArtifactType.setGroupId(mvnInputDialog.getGroupId());
      mvnArtifactType.setArtifactId(mvnInputDialog.getArtifactId());
      mvnArtifactType.setVersion(mvnInputDialog.getVersion());

      //
      _artifactTypes.add(mvnArtifactType);

      //
      redrawList();
    }

    // refresh
    refreshEnablement();
  }

  protected void removeArtifact() {
    _contentList.remove(_contentList.getSelectionIndices());
    redrawList();
    refreshEnablement();
  }

  /**
   * Creates a default text button with the specified text and SelectionListener
   * 
   * @param composite
   * @param text
   * @param listener
   * @return
   */
  private Button newTextButton(Composite composite, String text, SelectionListener listener) {

    final Button button = new Button(composite, SWT.PUSH);
    button.setText(text);
    button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    if (listener != null) {
      button.addSelectionListener(listener);
    }
    return button;

  }

  /**
   * <p>
   * </p>
   */
  private void refreshEnablement() {

    int itemsSelected = _contentList.getSelectionCount();
    _removeButton.setEnabled(itemsSelected > 0);

    //
    onRefreshEnablement();
  }

  /**
   * <p>
   * </p>
   */
  private void redrawList() {
    _contentList.removeAll();
    for (MvnArtifactType artifactType : _artifactTypes) {
      _contentList.add(artifactType.getGroupId() + " : " + artifactType.getArtifactId() + " : "
          + artifactType.getVersion());
    }
  }
}
