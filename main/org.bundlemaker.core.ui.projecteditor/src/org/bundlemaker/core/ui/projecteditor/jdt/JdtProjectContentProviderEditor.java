package org.bundlemaker.core.ui.projecteditor.jdt;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentRenderer;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.swt.graphics.Image;

public class JdtProjectContentProviderEditor implements IProjectContentProviderEditor {

  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof JdtProjectContentProvider);
  }

  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  @Override
  public List<? extends Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) throws Exception {
    // TODO: return IProjectContentEntries instead. Tree should show: Java Project Name and Version -> Content Entry ->
    // Content Entry paths
    // where Content Entry is a FileBasedContent

    if (!(rootElement instanceof JdtProjectContentProvider)) {
      return _fileBasedContentRenderer.getChildren(project, rootElement);
    }

    JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) rootElement;

    List<IProjectContentEntry> bundleMakerProjectContent = projectContentProvider.getBundleMakerProjectContent(project);

    return bundleMakerProjectContent;

  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      return BundleMakerImages.JDT_PROJECT_CONTENT_PROVIDER.getImage();
    }

    return _fileBasedContentRenderer.getImage(element);
  }

  @Override
  public String getLabel(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) element;
      return projectContentProvider.getJavaProject().getElementName();
    }

    return _fileBasedContentRenderer.getLabel(element);
  }

  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    return _fileBasedContentRenderer.getAnalyzeMode(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canEdit(java.lang.Object)
   */
  @Override
  public boolean canEdit(Object selectedObject) {
    return false;
  }

}
