package org.bundlemaker.core.ui.mvn;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.mvn.content.MvnContentProvider;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentRenderer;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.bundlemaker.core.ui.projecteditor.provider.impl.AbstractProjectContentProviderEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnProjectContentProviderEditor extends AbstractProjectContentProviderEditor {

  /** - */
  private final FileBasedContentRenderer             _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  /** - */
  private ReloadFromRepositoryAction                 _reloadFromLocalRepositoryAction;

  /** - */
  private ReloadFromRepositoryAction                 _reloadFromRemoteRepositoryAction;

  /** - */
  private List<IAction>                              _actionList;

  /** - */
  private List<IProjectContentProviderEditorElement> _currentSelection;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof MvnContentProvider);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  @Override
  public List<? extends Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) throws Exception {

    if (!(rootElement instanceof MvnContentProvider)) {
      return _fileBasedContentRenderer.getChildren(project, rootElement);
    }

    MvnContentProvider projectContentProvider = (MvnContentProvider) rootElement;

    return getContentFromProvider(project, projectContentProvider);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Image getImage(Object element) {

    //
    if (element instanceof MvnContentProvider) {
      return BundleMakerImages.JDT_PROJECT_CONTENT_PROVIDER.getImage();
    }

    //
    return _fileBasedContentRenderer.getImage(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel(Object element) {

    //
    if (element instanceof MvnContentProvider) {

      //
      MvnContentProvider projectContentProvider = (MvnContentProvider) element;

      //

      //
      List<MvnArtifactType> artifactTypes = projectContentProvider.getMvnArtifacts();

      //
      if (artifactTypes.size() > 0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(artifactTypes.get(0).getGroupId() + ":" + artifactTypes.get(0).getArtifactId() + ":"
            + artifactTypes.get(0).getVersion());

        if (artifactTypes.size() > 1) {
          stringBuilder.append(" / ...");
        }
        return stringBuilder.toString();
      }

      //
      return "-";
    }

    // get the label
    return _fileBasedContentRenderer.getLabel(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    return _fileBasedContentRenderer.getAnalyzeMode(element);
  }

  @Override
  public boolean canEdit(Object selectedObject) {
    return selectedObject instanceof MvnContentProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean edit(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {

    //
    if (!(selectedObject instanceof MvnContentProvider)) {
      return false;
    }

    //
    MvnContentProvider content = (MvnContentProvider) selectedObject;

    //
    DialogEditMvnArtifacts dialog = new DialogEditMvnArtifacts(shell, content.getMvnArtifacts());

    //
    if (dialog.open() != Window.OK) {
      return false;
    }

    //
    List<MvnArtifactType> newMvnArtifactTypes = dialog.getResult();

    //
    if (!newMvnArtifactTypes.equals(content.getMvnArtifacts())) {

      // clear the artifact list
      content.clearArtifactList();

      // set the new content
      for (MvnArtifactType type : dialog.getResult()) {
        content.addMvnArtifact(type);
      }
    }

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IAction> getContextMenuActions(IBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {

    // lazy init
    if (_actionList == null) {

      _actionList = new LinkedList<IAction>();

      //
      _reloadFromLocalRepositoryAction = new ReloadFromRepositoryAction("Reload from local repository", this,
          false);

      //
      _reloadFromRemoteRepositoryAction = new ReloadFromRepositoryAction("Reload from remote repository", this, true);

      _actionList.add(_reloadFromLocalRepositoryAction);
      _actionList.add(_reloadFromRemoteRepositoryAction);
    }

    //
    _currentSelection = selectedElements;

    // set enabled
    boolean enabled = instanceOfMvnContentProvider(selectedElements);
    _reloadFromLocalRepositoryAction.setEnabled(enabled);
    _reloadFromRemoteRepositoryAction.setEnabled(enabled);

    // return the list
    return instanceOfMvnContentProvider(selectedElements) ? _actionList : new LinkedList<IAction>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IProjectContentProviderEditorElement> getCurrentSelection() {
    return _currentSelection;
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectedElements
   */
  private boolean instanceOfMvnContentProvider(List<IProjectContentProviderEditorElement> selectedElements) {

    //
    for (IProjectContentProviderEditorElement element : selectedElements) {

      //
      if (!(element.getElement() instanceof MvnContentProvider)) {
        return false;
      }
    }

    //
    return true;
  }
}
