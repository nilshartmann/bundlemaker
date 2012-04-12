package org.bundlemaker.core.ui.projecteditor.jdt;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.graphics.Image;

public class JdtProjectContentProviderEditor implements IProjectContentProviderEditor {

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof JdtProjectContentProvider);
  }

  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  @Override
  public List<Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider, Object rootElement) {
    JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) rootElement;
    IJavaProject javaProject = projectContentProvider.getJavaProject();
    try {
      IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();
      return new LinkedList<Object>(Arrays.asList(rawClasspath));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return Collections.emptyList();

  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      return BundleMakerImages.JDT_PROJECT_CONTENT_PROVIDER.getImage();
    }
    return null;
  }

  @Override
  public String getLabel(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) element;
      return projectContentProvider.getJavaProject().getElementName();
    }

    return String.valueOf(element);
  }

  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      return AnalyzeMode.BINARIES_AND_SOURCES;
    }
    return null;
  }

}
