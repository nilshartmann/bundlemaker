package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeViewerFactory {

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @return
   */
  public static TreeViewer createDefaultArtifactTreeViewer(Composite parent) {
    int style = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;

    return createDefaultArtifactTreeViewer(parent, style);
  }

  public static TreeViewer createDefaultArtifactTreeViewer(Composite parent, int style) {
    final TreeViewer treeViewer = new TreeViewer(parent, style);
    treeViewer.setUseHashlookup(true);
    treeViewer.setContentProvider(new ArtifactTreeContentProvider(true));
    treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    treeViewer.setSorter(new ArtifactTreeViewerSorter());
    treeViewer.setLabelProvider(new ArtifactTreeLabelProvider());

    int operations = DND.DROP_MOVE;
    Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
    treeViewer.addDragSupport(operations, transferTypes, new ArtifactTreeDragAdapter(treeViewer));
    treeViewer.addDropSupport(operations, transferTypes, new ArtifactTreeDropAdapter(treeViewer));

    // menu manager
    MenuManager menuMgr = new MenuManager();
    Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
    menuMgr.addMenuListener(new IMenuListener() {

      @Override
      public void menuAboutToShow(IMenuManager manager) {

        //
        if (treeViewer.getSelection().isEmpty()) {
          return;
        }

        //
        if (treeViewer.getSelection() instanceof IStructuredSelection) {
          IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

          // if (object.getType() == DATABASE_OBJECT_TYPE.TABLE) {
          // manager.add(new ExpandActionHandler("Expand"));

          MenuManager expandTill = new MenuManager("Expand till");
          manager.add(expandTill);
          expandTill.add(new ExpandActionHandler("Package", IPackageArtifact.class, selection, treeViewer));
          expandTill.add(new ExpandActionHandler("Resource", IResourceArtifact.class, selection, treeViewer));
          // }
        }
      }
    });

    menuMgr.setRemoveAllWhenShown(true);
    treeViewer.getControl().setMenu(menu);

    //
    return treeViewer;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static class ExpandActionHandler extends Action {

    /** - */
    private Class<? extends IBundleMakerArtifact> _artifactType;

    /** - */
    private IStructuredSelection                  _selection;

    private TreeViewer                            _treeViewer;

    /**
     * <p>
     * Creates a new instance of type {@link ExpandActionHandler}.
     * </p>
     * 
     * @param text
     * @param artifactType
     */
    public ExpandActionHandler(String text, Class<? extends IBundleMakerArtifact> artifactType,
        IStructuredSelection selection, TreeViewer treeViewer) {
      super(text);

      //
      _artifactType = artifactType;

      //
      _selection = selection;

      //
      _treeViewer = treeViewer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

      _treeViewer.getTree().setRedraw(false);

      //
      for (Object object : _selection.toList()) {

        if (object instanceof IBundleMakerArtifact) {
          // expand
          for (IBundleMakerArtifact artifact : ArtifactHelper.findChildren((IBundleMakerArtifact) object,
              IPackageArtifact.class)) {

            //
            if (_artifactType.equals(IResourceArtifact.class)) {

              _treeViewer.collapseToLevel(artifact, 1);
              _treeViewer.expandToLevel(artifact, 1);
            } else {
              _treeViewer.collapseToLevel(artifact, 0);
              _treeViewer.expandToLevel(artifact, 0);
            }
          }
        }
      }
      _treeViewer.getTree().setRedraw(true);
    }
  }
}
