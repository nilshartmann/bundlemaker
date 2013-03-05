package org.bundlemaker.core.ui.mvn;

import java.util.List;

import org.bundlemaker.core.mvn.content.MvnArtifactType;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DialogEditMvnArtifacts extends TitleAreaDialog {

  /** - */
  private CompositeEditMvnArtifacts _artifactListComposite;

  /** - */
  private List<MvnArtifactType>     _artifactTypes;

  /**
   * <p>
   * Creates a new instance of type {@link DialogEditMvnArtifacts}.
   * </p>
   * 
   * @param shell
   * @param mvnArtifacts
   */
  public DialogEditMvnArtifacts(Shell shell, List<MvnArtifactType> mvnArtifacts) {
    super(shell);

    //
    _artifactTypes = mvnArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createDialogArea(Composite parent) {

    setTitle("Maven repository artifacts");
    setMessage("Add artifacts from a maven repository to your project");

    //
    _artifactListComposite = new CompositeEditMvnArtifacts(parent, SWT.NONE, _artifactTypes);

    //
    return parent;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<MvnArtifactType> getResult() {
    return _artifactListComposite.getArtifactTypes();
  }
}
