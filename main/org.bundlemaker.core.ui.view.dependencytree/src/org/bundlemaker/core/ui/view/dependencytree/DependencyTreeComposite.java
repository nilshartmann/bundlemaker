package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.ArtifactUtilities;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeComposite extends Composite {

  /** - */
  private TreeViewer                                            _fromTreeViewer;

  /** - */
  private TreeViewer                                            _toTreeViewer;

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _targetArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _sourceArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  private List<IDependency>                                     _leafDependencies;

  /** - */
  private String                                                _detailDependencySelectionId;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyTreeComposite}.
   * </p>
   * 
   * @param parent
   */
  public DependencyTreeComposite(Composite parent, String detailDependencySelectionId) {
    super(parent, SWT.NONE);

    Assert.isNotNull(detailDependencySelectionId);

    _detailDependencySelectionId = detailDependencySelectionId;

    init();
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   */
  public void setDependencies(List<IDependency> dependencies) {

    //
    _leafDependencies = ArtifactUtilities.getAllLeafDependencies(dependencies);

    //
    _sourceArtifactMap.clear();
    _targetArtifactMap.clear();

    //
    for (IDependency dependency : _leafDependencies) {
      _sourceArtifactMap.getOrCreate(dependency.getFrom()).add(dependency);
      _targetArtifactMap.getOrCreate(dependency.getTo()).add(dependency);
    }

    // update 'from' and 'to' tree, no filtering
    setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
    setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());
  }

  /**
   * <p>
   * </p>
   */
  private void init() {

    //
    this.setLayout(new GridLayout(2, true));

    //
    _fromTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);

    //
    _toTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);

    // add SelectionListeners
    _fromTreeViewer.addSelectionChangedListener(new FromArtifactsSelectionChangedListener());
    _toTreeViewer.addSelectionChangedListener(new ToArtifactSelectionChangedListener());
  }

  /**
   * <p>
   * </p>
   * 
   * @param treeViewer
   * @param visibleArtifacts
   */
  private void setVisibleArtifacts(TreeViewer treeViewer, Collection<IBundleMakerArtifact> visibleArtifacts) {
    Assert.isNotNull(treeViewer);
    Assert.isNotNull(visibleArtifacts);

    if (visibleArtifacts.size() > 0) {

      // set the root if necessary
      IRootArtifact rootArtifact = visibleArtifacts.toArray(new IBundleMakerArtifact[0])[0].getRoot();
      if (!rootArtifact.equals(treeViewer.getInput())) {
        
        treeViewer.getTree().setRedraw(false); 
        treeViewer.setInput(rootArtifact);

        // expand
        for (IBundleMakerArtifact artifact : ArtifactHelper.findChildren(rootArtifact, IModuleArtifact.class)) {
          treeViewer.expandToLevel(artifact, 0);
        }

        //
        treeViewer.getTree().setRedraw(true); 
      }

      // set the filter
      treeViewer.setFilters(new ViewerFilter[] { new VisibleArtifactsFilter(visibleArtifacts) });
    }

    // set empty list
    else {
      treeViewer.setInput(Collections.emptyList());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getDependencySelectionId() {
    return Selection.DETAIL_DEPENDENCY_SELECTION_ID;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class FromArtifactsSelectionChangedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      // check for selected root element
      boolean containsRoot = false;
      for (Object object : structuredSelection.toList()) {
        if (object instanceof IRootArtifact) {
          containsRoot = true;
          break;
        }
      }

      if (structuredSelection.size() > 0 && !containsRoot) {

        //
        List<IBundleMakerArtifact> visibleArtifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;
            for (IBundleMakerArtifact artifact : ArtifactUtilities.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_sourceArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _sourceArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getTo());
                }
              }
            }
          }
        }

        _toTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_toTreeViewer, visibleArtifacts);
        Selection.instance().getDependencySelectionService()
            .setSelection(getDependencySelectionId(), _detailDependencySelectionId, selectedDetailDependencies);
      } else {
        setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());
        Selection.instance().getDependencySelectionService()
            .setSelection(getDependencySelectionId(), _detailDependencySelectionId, _leafDependencies);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class ToArtifactSelectionChangedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      // check for selected root element
      boolean containsRoot = false;
      for (Object object : structuredSelection.toList()) {
        if (object instanceof IRootArtifact) {
          containsRoot = true;
          break;
        }
      }

      if (structuredSelection.size() > 0 && !containsRoot) {

        //
        List<IBundleMakerArtifact> visibleArtifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;
            for (IBundleMakerArtifact artifact : ArtifactUtilities.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_targetArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _targetArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getFrom());
                }
              }
            }
          }
        }

        _fromTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_fromTreeViewer, visibleArtifacts);
        Selection.instance().getDependencySelectionService()
            .setSelection(getDependencySelectionId(), _detailDependencySelectionId, selectedDetailDependencies);
      } else {
        setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
        Selection.instance().getDependencySelectionService()
            .setSelection(getDependencySelectionId(), _detailDependencySelectionId, _leafDependencies);
      }
    }
  }
}
