/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Kai Lehmann - initial API and implementation
 *     Bundlemaker project team - integration with BundleMaker Analysis UI
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.dsm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixListener;
import org.bundlemaker.core.ui.editor.dsm.figures.MatrixEvent;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.core.ui.selection.Selection;
import org.bundlemaker.core.ui.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DSMArtifactModelEditor extends AbstractArtifactSelectionAwareEditorPart {

  /**
   * This is used as the DSMView's providerId for the xxxSelectionServices
   */
  public static String        DSM_EDITOR_ID   = DSMArtifactModelEditor.class.getName();

  /**
   * Dummy input used for this editor
   */
  @SuppressWarnings("restriction")
  private static IEditorInput nullInputEditor = new NullEditorInput();

  /** - */
  private DsmViewWidget       _viewWidget;

  /** - */
  private DsmDetailComposite  _detailComposite;

  /** - */
  private MatrixEvent         _selectedCell;

  /**
   * Opens the DSM View.
   * 
   * <p>This method does nothing in case the DSM view could not be opened for any reason.
   * 
   */
  public static void openDsmView() {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (workbenchWindow == null) {
      return;
    }

    IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
    if (workbenchPage == null) {
      return;
    }

    try {
      workbenchPage.openEditor(nullInputEditor, DSMArtifactModelEditor.DSM_EDITOR_ID);
    } catch (PartInitException e) {
      e.printStackTrace();
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    //
    GridLayout gridLayout = new GridLayout(1, true);
    parent.setLayout(gridLayout);

    //
    _viewWidget = new DsmViewWidget(new DsmViewModel(), parent);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(_viewWidget);
    _viewWidget.setZoom((50 + 10) * 0.02f);

    //
    _detailComposite = new DsmDetailComposite(parent, _viewWidget);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(_detailComposite);
    setDefaultDependencyDescription();

    //
    _viewWidget.addMatrixListener(new IMatrixListener.Adapter() {

      @Override
      public void marked(MatrixEvent event) {

        //
        if (isCellSelected(event)) {
          _detailComposite.getSelectionCountLabel().setText(
              getNullSafeString(_viewWidget.getModel().getValues()[event.getX()][event.getY()], "0"));
          _detailComposite.getFromLabel().setText(_viewWidget.getModel().getLabels()[event.getY()]);
          _detailComposite.getToLabel().setText(_viewWidget.getModel().getLabels()[event.getX()]);
        }

        //
        else if (_selectedCell != null) {
          _detailComposite.getSelectionCountLabel().setText(
              getNullSafeString(_viewWidget.getModel().getValues()[_selectedCell.getX()][_selectedCell.getY()], "0"));
          _detailComposite.getFromLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell.getY()]);
          _detailComposite.getToLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell.getX()]);
        }

        //
        else {
          setDefaultDependencyDescription();
        }
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void singleClick(MatrixEvent event) {
        if (isCellSelected(event)) {
          _selectedCell = event;
        }
      }
    });

    //
    _detailComposite.getVisualizeChildrenButton().addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Selection.instance().getArtifactSelectionService()
            .setUseChildrenOfSelectedArtifacts(_detailComposite.getVisualizeChildrenButton().getSelection());
      }
    });

    // create the context menu
    createContextMenu(_viewWidget);

    //
    setCurrentArtifactSelection(getCurrentArtifactSelection());
  }

  protected String getNullSafeString(String string, String defaultValue) {
    return string == null ? defaultValue : string;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartActivated() {

    //
    if (!EMPTY_ARTIFACT_SELECTION.equals(getCurrentArtifactSelection())) {
      Selection
          .instance()
          .getArtifactSelectionService()
          .setSelection(Selection.MAIN_ARTIFACT_SELECTION_ID, DSM_EDITOR_ID,
              getCurrentArtifactSelection().getSelectedArtifacts(),
              getCurrentArtifactSelection().useChildrenOfSelectedArtifacts());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onArtifactSelectionChanged(IArtifactSelection selection) {

    //
    if (selection.getProviderId().equals(DSM_EDITOR_ID)) {
      return;
    }

    setCurrentArtifactSelection(selection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    // setCurrentArtifactSelection(getCurrentArtifactSelection());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    clearDependencySelection();
    super.dispose();
  }

  private void clearDependencySelection() {
    List<IDependency> dependencies = Collections.emptyList();
    Selection.instance().getDependencySelectionService()
        .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSM_EDITOR_ID, dependencies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    //
    if (_viewWidget != null && _detailComposite != null) {

      //
      _detailComposite.getVisualizeChildrenButton().setSelection(artifactSelection.useChildrenOfSelectedArtifacts());

      // set the model
      if (artifactSelection.useChildrenOfSelectedArtifacts()) {
        List<IBundleMakerArtifact> bundleMakerArtifacts = new LinkedList<IBundleMakerArtifact>();
        for (IBundleMakerArtifact artifact : artifactSelection.getSelectedArtifacts()) {
          bundleMakerArtifacts.addAll(artifact.getChildren());
        }
        _viewWidget.setModel(new DsmViewModel(bundleMakerArtifacts));
      } else {
        _viewWidget.setModel(new DsmViewModel(artifactSelection.getSelectedArtifacts()));
      }

      // clear the dependency selection
      clearDependencySelection();
      setDefaultDependencyDescription();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getProviderId() {
    return DSM_EDITOR_ID;
  }

  /**
   * <p>
   * </p>
   * 
   * @param dsmViewWidget
   */
  private void createContextMenu(DsmViewWidget dsmViewWidget) {

    MenuManager menuManager = new MenuManager("#PopupMenu");
    menuManager.setRemoveAllWhenShown(true);
    menuManager.addMenuListener(new IMenuListener() {

      private MenuItem _menuItem;

      @Override
      public void menuAboutToShow(IMenuManager manager) {
        System.out.println("menuAboutToShow");
        manager.add(new Separator("edit"));
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        manager.appendToGroup("edit", new ContributionItem("Test") {

          /**
           * {@inheritDoc}
           */
          @Override
          public void fill(Menu menu, int index) {
            _menuItem = new MenuItem(menu, SWT.PUSH);
            _menuItem.setText("Jupp");
            _menuItem.addSelectionListener(new SelectionListener() {

              @Override
              public void widgetSelected(SelectionEvent e) {
                System.out.println(e);
              }

              @Override
              public void widgetDefaultSelected(SelectionEvent e) {
                System.out.println(e);
              }
            });
          }
        });
      }
    });

    Menu menu = menuManager.createContextMenu(dsmViewWidget);
    dsmViewWidget.setMenu(menu);
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @return
   */
  private boolean isCellSelected(MatrixEvent event) {
    return event.getX() <= _viewWidget.getModel().getLabels().length && event.getX() >= 0
        && event.getY() <= _viewWidget.getModel().getLabels().length && event.getY() >= 0;
  }

  /**
   * <p>
   * </p>
   * 
   */
  private void setDefaultDependencyDescription() {
    _detailComposite.getSelectionCountLabel().setText("0");
    _detailComposite.getFromLabel().setText("-");
    _detailComposite.getToLabel().setText("-");
  }
}