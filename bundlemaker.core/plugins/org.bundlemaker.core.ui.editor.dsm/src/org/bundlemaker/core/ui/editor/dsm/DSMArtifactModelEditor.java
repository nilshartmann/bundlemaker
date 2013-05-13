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
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.editor.dsm.widget.DsmViewWidget;
import org.bundlemaker.core.ui.editor.dsm.widget.IDsmContentProvider;
import org.bundlemaker.core.ui.editor.dsm.widget.IMatrixListener;
import org.bundlemaker.core.ui.editor.dsm.widget.MatrixEvent;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.part.NullEditorInput;

/**
 * <p>
 * </p>
 *
 * @author  Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class DSMArtifactModelEditor extends AbstractArtifactSelectionAwareEditorPart {

	/**
	 * This is used as the DSMView's providerId for the xxxSelectionServices
	 */
	public static String DSM_EDITOR_ID = DSMArtifactModelEditor.class.getName();

	/**
	 * Dummy input used for this editor
	 */
	private static IEditorInput nullInputEditor = new NullEditorInput();

	/** - */
	private DsmViewWidget _viewWidget;

  /** - */
	private DsmDetailComposite _detailComposite;

  /** - */
	private int[] _selectedCell;

  /** - */
	private IBundleMakerArtifact _fromArtifact;

  /** - */
	private IBundleMakerArtifact _toArtifact;

  /** - */
	private DefaultArtifactLabelProvider _artifactLabelProvider;

  /** - */
	private IDsmContentProvider _dsmContentProvider;

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

		_dsmContentProvider = new DefaultBundleMakerArtifactDsmContentProvider();
		_artifactLabelProvider = new DefaultArtifactLabelProvider();
    _viewWidget = new DsmViewWidget(_dsmContentProvider, _artifactLabelProvider, new DefaultDependencyLabelProvider(),
        parent);
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
						_detailComposite.setSelectionCount(
							getNullSafeString(
								_viewWidget.getDependenciesAsStrings()[event.getX()][event.getY()],
								"0"
							)
						);
						_detailComposite.setFrom(
							((IBundleMakerArtifact) _dsmContentProvider.getNodes()[event.getY()])
							.getQualifiedName()
						);
						_detailComposite.setTo(
							((IBundleMakerArtifact) _dsmContentProvider.getNodes()[event.getX()])
							.getQualifiedName()
						);
					}

					//
					else if (_selectedCell != null) {
						_detailComposite.setSelectionCount(
							getNullSafeString(
								_viewWidget.getDependenciesAsStrings()[_selectedCell[0]][
									_selectedCell[1]],
								"0"
							)
						);
						_detailComposite.setFrom(
							((IBundleMakerArtifact) _dsmContentProvider.getNodes()[_selectedCell[1]])
							.getQualifiedName()
						);
						_detailComposite.setTo(
							((IBundleMakerArtifact) _dsmContentProvider.getNodes()[_selectedCell[0]])
							.getQualifiedName()
						);
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

          IDependency dependency = (IDependency) _dsmContentProvider.getDependency(event.getX(), event.getY());

          Selection.instance().getDependencySelectionService()
              .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSMArtifactModelEditor.DSM_EDITOR_ID, dependency);

          _fromArtifact = (IBundleMakerArtifact) _dsmContentProvider.getNodes()[event.getX()];
          _toArtifact = (IBundleMakerArtifact) _dsmContentProvider.getNodes()[event.getY()];
					}
				}
    });

		// create the context menu
		// createContextMenu(_viewWidget);

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

	protected String getNullSafeString(String string, String defaultValue) {
		return string == null ? defaultValue : string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentArtifactSelection(IArtifactSelection selection) {

		//
		if (selection.getProviderId().equals(DSM_EDITOR_ID)) {
			return;
		}

		super.setCurrentArtifactSelection(selection);

		initSelection(selection);
	}

	private void initSelection(IArtifactSelection selection) {

		if (_viewWidget != null && _detailComposite != null) {

			//
      _dsmContentProvider = new DefaultBundleMakerArtifactDsmContentProvider(selection.getEffectiveSelectedArtifacts());
      _artifactLabelProvider.setLabelPresentationMode(_detailComposite.getLabelPresentationMode());
			_viewWidget.setModel(_dsmContentProvider);
			_detailComposite.setViolationCount(_dsmContentProvider.getViolationCount());

			// clear the dependency selection
			resetDependencySelection();

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
	 * @param  list
	 */
	private void resetDependencySelection() {

		//
		List<?> artifacts = Arrays.asList(_dsmContentProvider.getNodes());

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

      IDependency dependency = (IDependency) _dsmContentProvider.getDependency(_selectedCell[0], _selectedCell[1]);

      Selection.instance().getDependencySelectionService()
          .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DSMArtifactModelEditor.DSM_EDITOR_ID, dependency);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getProviderId() {
		return DSM_EDITOR_ID;
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @param dsmViewWidget
	// */
	// private void createContextMenu(DsmViewWidget dsmViewWidget) {
	//
	// MenuManager menuManager = new MenuManager("#PopupMenu");
	// menuManager.setRemoveAllWhenShown(true);
	// menuManager.addMenuListener(new IMenuListener() {
	//
	// private MenuItem _menuItem;
	//
	// @Override
	// public void menuAboutToShow(IMenuManager manager) {
	// manager.add(new Separator("edit"));
	// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	// manager.appendToGroup("edit", new ContributionItem("Test") {
	//
	// /**
	// * {@inheritDoc}
	// */
	// @Override
	// public void fill(Menu menu, int index) {
	// _menuItem = new MenuItem(menu, SWT.PUSH);
	// _menuItem.setText("Export...");
	// _menuItem.addSelectionListener(new SelectionListener() {
	//
	// @Override
	// public void widgetSelected(SelectionEvent e) {
	//
	// final int sideMarkerOffset = FigureUtilities.getTextWidth(
	// DsmUtils.getLongestString(_viewWidget.getModel().getLabels()), Display.getCurrent().getSystemFont());
	//
	// //
	// int matrixWidth = _viewWidget.getModel().getConfiguration().getHorizontalBoxSize()
	// * _viewWidget.getModel().getItemCount();
	// int matrixHeight = _viewWidget.getModel().getConfiguration().getVerticalBoxSize()
	// * _viewWidget.getModel().getItemCount();
	// final Matrix matrix = new Matrix(_viewWidget.getModel(), new DependencyLabelProvider(), _viewWidget
	// .getModel());
	// matrix.setSize(matrixWidth, matrixHeight);
	// matrix.setFont(Display.getCurrent().getSystemFont());
	//
	// //
	// final VerticalSideMarker verticalSideMarker = new VerticalSideMarker(_viewWidget.getModel());
	// verticalSideMarker.setSize(sideMarkerOffset, matrixHeight);
	// verticalSideMarker.setFont(Display.getCurrent().getSystemFont());
	//
	// //
	// final HorizontalSideMarker horizontalSideMarker = new HorizontalSideMarker(_viewWidget.getModel());
	// horizontalSideMarker.setSize(matrixWidth, sideMarkerOffset);
	// horizontalSideMarker.setFont(Display.getCurrent().getSystemFont());
	//
	// IFigure mainFigure = new Figure() {
	//
	// /**
	// * {@inheritDoc}
	// */
	// @Override
	// public void paint(Graphics graphics) {
	// super.paint(graphics);
	//
	// graphics.pushState();
	// graphics.translate(0, sideMarkerOffset);
	// verticalSideMarker.paint(graphics);
	// graphics.restoreState();
	//
	// graphics.pushState();
	// graphics.translate(sideMarkerOffset, sideMarkerOffset);
	// matrix.paint(graphics);
	// graphics.restoreState();
	//
	// graphics.pushState();
	// graphics.translate(sideMarkerOffset, 0);
	// horizontalSideMarker.paint(graphics);
	// graphics.restoreState();
	// }
	// };
	// mainFigure.setSize(matrix.getSize().width + sideMarkerOffset + 1, matrix.getSize().height
	// + sideMarkerOffset + 1);
	//
	// FigurePrinter.save(mainFigure);
	// }
	//
	// @Override
	// public void widgetDefaultSelected(SelectionEvent e) {
	// }
	// });
	// }
	// });
	// }
	// });
	//
	// Menu menu = menuManager.createContextMenu(dsmViewWidget);
	// dsmViewWidget.setMenu(menu);
	// }

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
	 * @param   event
	 * @return
	 */
	private boolean isCellSelected(MatrixEvent event) {
		return event.getX() <= _dsmContentProvider.getItemCount() && event.getX() >= 0
		&& event.getY() <= _dsmContentProvider.getItemCount() && event.getY() >= 0;
	}

	/**
	 * <p>
	 * </p>
   * 
	 */
	private void setDefaultDependencyDescription() {
		_detailComposite.setSelectionCount("0");
		_detailComposite.setFrom("-");
		_detailComposite.setTo("-");
	}
}
