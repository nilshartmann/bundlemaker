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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.IMatrixListener;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.Matrix;
import org.bundlemaker.core.ui.editor.dsm.figures.matrix.MatrixEvent;
import org.bundlemaker.core.ui.editor.dsm.figures.sidemarker.HorizontalSideMarker;
import org.bundlemaker.core.ui.editor.dsm.figures.sidemarker.VerticalSideMarker;
import org.bundlemaker.core.ui.editor.dsm.utils.DsmUtils;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.bundlemaker.core.ui.print.FigurePrinter;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
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
  public static String         DSM_EDITOR_ID   = DSMArtifactModelEditor.class.getName();

  /**
   * Dummy input used for this editor
   */
  @SuppressWarnings("restriction")
  private static IEditorInput  nullInputEditor = new NullEditorInput();

  /** - */
  private DsmViewWidget        _viewWidget;

  /** - */
  private DsmDetailComposite   _detailComposite;

  /** - */
  private int[]                _selectedCell;

  /** - */
  private IBundleMakerArtifact _fromArtifact;

  /** - */
  private IBundleMakerArtifact _toArtifact;

  /**
   * Opens the DSM View.
   * 
   * <p>
   * This method does nothing in case the DSM view could not be opened for any reason.
   * 
   */
  public static void openDsmView() {
    EditorHelper.openEditor(DSM_EDITOR_ID, nullInputEditor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void analysisModelModified() {
    Display.getDefault().syncExec(new Runnable() {
      @Override
	public void run() {
         initSelection(getCurrentArtifactSelection());
      }
   });
    
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
    _detailComposite.addPropertyChangeListener(new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			initSelection(getCurrentArtifactSelection());
		}
	});
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
              getNullSafeString(_viewWidget.getModel().getValues()[_selectedCell[0]][_selectedCell[1]], "0"));
          _detailComposite.getFromLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell[1]]);
          _detailComposite.getToLabel().setText(_viewWidget.getModel().getLabels()[_selectedCell[0]]);
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

          _selectedCell = new int[] { event.getX(), event.getY() };

          IDependency dependency = _viewWidget.getModel().isToggled() ? ((DsmViewModel) _viewWidget.getModel())
              .getDependency(event.getY(),
                  event.getX()) : ((DsmViewModel) _viewWidget.getModel()).getDependency(event.getX(), event.getY());

          Selection.instance().getDependencySelectionService()
              .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSMArtifactModelEditor.DSM_EDITOR_ID, dependency);

          _fromArtifact = (IBundleMakerArtifact) _viewWidget.getModel().getNodes()[event.getX()];
          _toArtifact = (IBundleMakerArtifact) _viewWidget.getModel().getNodes()[event.getY()];
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected boolean isPinSelection() {
    return _detailComposite.getPinSelectionButton().getSelection();
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
//    if (!EMPTY_ARTIFACT_SELECTION.equals(getCurrentArtifactSelection())) {
//      Selection
//          .instance()
//          .getArtifactSelectionService()
//          .setSelection(Selection.MAIN_ARTIFACT_SELECTION_ID, DSM_EDITOR_ID,
//              getCurrentArtifactSelection().getSelectedArtifacts(),
//              getCurrentArtifactSelection().useChildrenOfSelectedArtifacts());
//    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCurrentArtifactSelection(IArtifactSelection selection) {

    //
    if (isPinSelection()) {
      return;
    }

    //
    if (selection.getProviderId().equals(DSM_EDITOR_ID)) {
      return;
    }

    //
    if (selection.hasSelectedArtifacts() && selection.getSelectedArtifacts().size() == 1
        && isResourceOrResourceContent(selection)) {
      return;
    }

    super.setCurrentArtifactSelection(selection);

    initSelection(selection);
  }

  private boolean isResourceOrResourceContent(IArtifactSelection selection) {
    return selection.getSelectedArtifacts().get(0) instanceof IResourceArtifact
        || selection.getSelectedArtifacts().get(0).getParent(IResourceArtifact.class) != null;
  }

  private void initSelection(IArtifactSelection selection) {

    // keep last selected?
    if (!selection.hasSelectedArtifacts()) {
      return;
    }

    //
    if (_viewWidget != null && _detailComposite != null) {

      //
      _detailComposite.getVisualizeChildrenButton().setSelection(selection.useChildrenOfSelectedArtifacts());
      
      // set the model
      if (selection.useChildrenOfSelectedArtifacts()) {
        List<IBundleMakerArtifact> bundleMakerArtifacts = new LinkedList<IBundleMakerArtifact>();
        for (IBundleMakerArtifact artifact : selection.getSelectedArtifacts()) {
          bundleMakerArtifacts.addAll(artifact.getChildren());
        }
        DsmViewModel model = new DsmViewModel(bundleMakerArtifacts);
        model.setLabelPresentationMode(_detailComposite.getLabelPresentationMode());
		_viewWidget.setModel(model);

        // clear the dependency selection
        resetDependencySelection();
      } else {
        DsmViewModel model = new DsmViewModel(selection.getSelectedArtifacts());
        model.setLabelPresentationMode(_detailComposite.getLabelPresentationMode());
		_viewWidget.setModel(model);

        // clear the dependency selection
        resetDependencySelection();
      }

      setDefaultDependencyDescription();
    }
    
  }
  

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    clearDependencySelection();
    super.dispose();
  }

  /**
   * <p>
   * </p>
   */
  private void clearDependencySelection() {
    _selectedCell = null;
    List<IDependency> dependencies = Collections.emptyList();
    Selection.instance().getDependencySelectionService()
        .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSM_EDITOR_ID, dependencies);
  }

  /**
   * <p>
   * </p>
   * 
   * @param list
   */
  private void resetDependencySelection() {

    //
    DsmViewModel dsmViewModel = (DsmViewModel) _viewWidget.getModel();

    //
    List<?> artifacts = Arrays.asList(dsmViewModel.getNodes());

    //
    if (_fromArtifact == null || _toArtifact == null || !artifacts.contains(_fromArtifact)
        || !artifacts.contains(_toArtifact)) {

      //
      clearDependencySelection();
    }

    //
    else {

      //
      _selectedCell = new int[] { artifacts.indexOf(_fromArtifact), artifacts.indexOf(_toArtifact) };

      IDependency dependency = _viewWidget.getModel().isToggled() ? ((DsmViewModel) _viewWidget.getModel())
          .getDependency(_selectedCell[1],
              _selectedCell[0]) : ((DsmViewModel) _viewWidget.getModel()).getDependency(_selectedCell[0],
          _selectedCell[1]);

      Selection.instance().getDependencySelectionService()
          .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSMArtifactModelEditor.DSM_EDITOR_ID, dependency);

      // //
      // _selectedCell = null;
      //
      // List<IDependency> dependencies = Collections.emptyList();
      //
      // Selection.instance().getDependencySelectionService()
      // .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSM_EDITOR_ID, dependencies);
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
        manager.add(new Separator("edit"));
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        manager.appendToGroup("edit", new ContributionItem("Test") {

          /**
           * {@inheritDoc}
           */
          @Override
          public void fill(Menu menu, int index) {
            _menuItem = new MenuItem(menu, SWT.PUSH);
            _menuItem.setText("Export...");
            _menuItem.addSelectionListener(new SelectionListener() {

              @Override
              public void widgetSelected(SelectionEvent e) {

                final int sideMarkerOffset = FigureUtilities.getTextWidth(
                    DsmUtils.getLongestString(_viewWidget.getModel().getLabels()), Display.getCurrent().getSystemFont());

                //
                int matrixWidth = _viewWidget.getModel().getConfiguration().getHorizontalBoxSize()
                    * _viewWidget.getModel().getItemCount();
                int matrixHeight = _viewWidget.getModel().getConfiguration().getVerticalBoxSize()
                    * _viewWidget.getModel().getItemCount();
                final Matrix matrix = new Matrix(_viewWidget.getModel(), new DependencyLabelProvider(), _viewWidget
                    .getModel());
                matrix.setSize(matrixWidth, matrixHeight);
                matrix.setFont(Display.getCurrent().getSystemFont());

                //
                final VerticalSideMarker verticalSideMarker = new VerticalSideMarker(_viewWidget.getModel());
                verticalSideMarker.setSize(sideMarkerOffset, matrixHeight);
                verticalSideMarker.setFont(Display.getCurrent().getSystemFont());

                //
                final HorizontalSideMarker horizontalSideMarker = new HorizontalSideMarker(_viewWidget.getModel());
                horizontalSideMarker.setSize(matrixWidth, sideMarkerOffset);
                horizontalSideMarker.setFont(Display.getCurrent().getSystemFont());

                IFigure mainFigure = new Figure() {

                  /**
                   * {@inheritDoc}
                   */
                  @Override
                  public void paint(Graphics graphics) {
                    super.paint(graphics);

                    graphics.pushState();
                    graphics.translate(0, sideMarkerOffset);
                    verticalSideMarker.paint(graphics);
                    graphics.restoreState();

                    graphics.pushState();
                    graphics.translate(sideMarkerOffset, sideMarkerOffset);
                    matrix.paint(graphics);
                    graphics.restoreState();

                    graphics.pushState();
                    graphics.translate(sideMarkerOffset, 0);
                    horizontalSideMarker.paint(graphics);
                    graphics.restoreState();
                  }
                };
                mainFigure.setSize(matrix.getSize().width + sideMarkerOffset + 1, matrix.getSize().height
                    + sideMarkerOffset + 1);

                FigurePrinter.save(mainFigure);
              }

              @Override
              public void widgetDefaultSelected(SelectionEvent e) {
              }
            });
          }
        });
      }
    });

    Menu menu = menuManager.createContextMenu(dsmViewWidget);
    dsmViewWidget.setMenu(menu);
  }

  // /** Paints the figure onto the given graphics */
  // public static void paintDiagram(Graphics g, IFigure figure) {
  // // We want to ignore the first FreeformLayer (or we lose also all figure, as it draws the 'page boundaries'
  // // which is obviously not wanted in the exported images.
  // for (Object child : figure.getChildren()) {
  // // ConnectionLayer inherits from FreeformLayer, so rather checking for FreeformLayer we check whether child
  // // is not a ConnectionLayer!
  // if (child instanceof FreeformLayer && !(child instanceof ConnectionLayer)) {
  // paintDiagram(g, (IFigure) child);
  // } else {
  // ((IFigure) child).paint(g);
  // }
  // }
  // }

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
