package org.bundlemaker.core.ui.view.problemview;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class ProblemView extends AbstractArtifactSelectionAwareViewPart {

  public static final String   PROBLEM_VIEW_ID    = "org.bundlemaker.core.ui.view.problemview.ProblemView";

  private final List<IProblem> EMPTY_PROBLEM_LIST = new LinkedList<IProblem>();

  /** - */
  private TableViewer          _viewer;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {
    FillLayout fillLayout = new FillLayout();
    fillLayout.type = SWT.VERTICAL;

    parent.setLayout(fillLayout);

    Composite tableComposite = new Composite(parent, SWT.NONE);
    tableComposite.setLayout(new TableColumnLayout());

    _viewer = new TableViewer(tableComposite, SWT.VIRTUAL | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
        | SWT.FULL_SELECTION);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(ArrayContentProvider.getInstance());
    createColumns(tableComposite, _viewer);
  }

  private void createColumns(Composite parent, TableViewer viewer) {

    createTableViewerColumn(parent, viewer, "Message", 80, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        IProblem problem = (IProblem) element;
        return problem.getMessage();
      }

      @Override
      public Image getImage(Object element) {
        IProblem problem = (IProblem) element;
        if (problem.isError()) {
          return Images.ERROR_ICON.getImage();
        }
        return Images.WARNING_ICON.getImage();
      }

    });

    createTableViewerColumn(parent, viewer, "Resource", 35, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        IProblem problem = (IProblem) element;
        return problem.getResource().getName();
      }
    });

    createTableViewerColumn(parent, viewer, "Path", 35, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        IProblem problem = (IProblem) element;
        return problem.getResource().getPath();
      }
    });

    createTableViewerColumn(parent, viewer, "Root", 35, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        IProblem problem = (IProblem) element;
        return problem.getResource().getRoot();
      }
    });
    createTableViewerColumn(parent, viewer, "Location", 20, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        IProblem problem = (IProblem) element;
        if (problem.getSourceLineNumber() >= 0) {
          return "line " + problem.getSourceLineNumber();
        }

        return "unknown";
      }
    });

  }

  private TableViewerColumn createTableViewerColumn(Composite tableComposite, TableViewer viewer, String title,
      int weight, CellLabelProvider labelProvider) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setResizable(true);
    column.setMoveable(false);

    TableColumnLayout tableLayout = (TableColumnLayout) tableComposite.getLayout();
    ColumnLayoutData columnLayoutData = new ColumnWeightData(weight);
    tableLayout.setColumnData(column, columnLayoutData);
    if (labelProvider != null) {
      viewerColumn.setLabelProvider(labelProvider);
    }
    return viewerColumn;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart#
   * setCurrentArtifactSelection(org.bundlemaker.core.ui.event.selection.IArtifactSelection)
   */
  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    initFromSelection(artifactSelection);
  }

  /**
   * @param artifactSelection
   */
  private void initFromSelection(IArtifactSelection artifactSelection) {

    if (!artifactSelection.hasSelectedArtifacts()) {
      setModel(EMPTY_PROBLEM_LIST);
      return;
    }

    IRootArtifact rootArtifact = artifactSelection.getRootArtifact();

    // Get project
    IBundleMakerProject bundleMakerProject = rootArtifact.getModularizedSystem().getBundleMakerProject();

    // get associated problems
    List<IProblem> problems = bundleMakerProject.getProblems();

    // set the model for the table
    setModel(problems);

  }

  protected void setModel(List<IProblem> problems) {
    if (problems == null) {
      problems = EMPTY_PROBLEM_LIST;
    }

    _viewer.setInput(problems);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart#getProviderId()
   */
  @Override
  protected String getProviderId() {
    return PROBLEM_VIEW_ID;
  }

}
