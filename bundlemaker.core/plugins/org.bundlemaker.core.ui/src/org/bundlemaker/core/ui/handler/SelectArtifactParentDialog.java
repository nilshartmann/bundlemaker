/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * Allows the user to select a single artifact from a given artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SelectArtifactParentDialog extends TitleAreaDialog {

  /** MSG_TITLE */
  private static final String        MSG_TITLE                = "Choose parent artifact";

  /** MSG_MESSAGE */
  private static final String        MSG_MESSAGE              = "Please choose the new parent artifact.";

  /** MSG_CANNOT_ADD_ARTIFACTS */
  private static final String        MSG_CANNOT_ADD_ARTIFACTS = "Can't add selected artifacts to %s.";

  /** the tree viewer */
  private TreeViewer                 _treeViewer;

  /** the root artifact */
  private IRootArtifact              _rootArtifact;

  /** the selected artifacts */
  private List<IBundleMakerArtifact> _selectedArtifacts;

  /** the new parent */
  private IBundleMakerArtifact       _newParent;

  /**
   * <p>
   * Creates a new instance of type {@link SelectArtifactParentDialog}.
   * </p>
   * 
   * @param shell
   * @param rootArtifact
   */
  public SelectArtifactParentDialog(Shell shell, IRootArtifact rootArtifact,
      List<IBundleMakerArtifact> selectedArtifacts) {
    super(shell);

    // asserts
    Assert.isNotNull(rootArtifact);
    Assert.isNotNull(selectedArtifacts);

    // set the parameters
    _rootArtifact = rootArtifact;
    _selectedArtifacts = selectedArtifacts;

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
  public IBundleMakerArtifact getSelectedArtifact() {
    return _newParent;
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

    //
    _treeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL
        | SWT.V_SCROLL | SWT.BORDER);
    _treeViewer.setInput(_rootArtifact);
    _treeViewer.expandToLevel(2);
    _treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        validateInput();
      }
    });

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

    // check if all selected artifacts can be added
    for (IBundleMakerArtifact artifact : _selectedArtifacts) {
      if (!_newParent.canAdd(artifact)) {
        setErrorMessage(String.format(MSG_CANNOT_ADD_ARTIFACTS, _newParent.getName()));
        return;
      }
    }

    // reset error message
    setErrorMessage(null);
  }
}
