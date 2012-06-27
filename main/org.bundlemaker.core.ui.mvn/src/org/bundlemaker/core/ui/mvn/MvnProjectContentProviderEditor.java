package org.bundlemaker.core.ui.mvn;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.mvn.content.MvnContentProvider;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentRenderer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnProjectContentProviderEditor extends AbstractContentProviderEditor {

  /** - */
  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

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

    List<IProjectContentEntry> bundleMakerProjectContent =
        projectContentProvider.getBundleMakerProjectContent(project);

    return bundleMakerProjectContent;

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
}
