package org.bundlemaker.core.projectdescription.file;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.JarInfo;
import org.bundlemaker.core.internal.projectdescription.JarInfoService;
import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.internal.projectdescription.RootPath;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

public class FileBasedContentFactory {

  /** - */
  // TODO
  private static NumberFormat FORMATTER  = new DecimalFormat("000000");

  /** - */
  // TODO
  private static int          _currentId = 0;

  public static FileBasedContentProvider addContent(String name, String version, String binaryRoot, String sourceRoot) {
    return addContent(name, version, toList(binaryRoot), toList(sourceRoot), AnalyzeMode.BINARIES_AND_SOURCES);
  }

  public static FileBasedContentProvider addContent(String binaryRoot) {
    return addContent(binaryRoot, null, AnalyzeMode.BINARIES_AND_SOURCES);
  }

  /**
   * <p>
   * Adds a new content with the specified binary and source roots. The source root might be null. The name and version
   * of the content are determined automatically by the binaryRoot
   * 
   * @param binaryRoot
   *          the binary root. Must not be null
   * @param sourceRoot
   *          the source root path. Might be null
   * @param analyzeMode
   *          the analyze mode. Not null
   * @return
   */
  @Deprecated
  public static FileBasedContentProvider addContent(String binaryRoot, String sourceRoot, AnalyzeMode analyzeMode) {
    Assert.isNotNull(binaryRoot);
    Assert.isNotNull(analyzeMode);

    try {

      // get the jar info
      JarInfo jarInfo = JarInfoService.extractJarInfo(getAsFile(binaryRoot));

      //
      return addContent(jarInfo.getName(), jarInfo.getVersion(), toList(binaryRoot), toList(sourceRoot), analyzeMode);

    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  public static FileBasedContentProvider addContent(String name, String version, List<String> binaryRoots,
      List<String> sourceRoots, AnalyzeMode analyzeMode) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);
    Assert.isNotNull(binaryRoots);
    Assert.isNotNull(analyzeMode);

    // create new file based content
    FileBasedContentProvider fileBasedContent = new FileBasedContentProvider();

    // TODO: THREADING
    _currentId++;

    // TODO
    fileBasedContent.getFileBasedContent().setId(FORMATTER.format(_currentId));
    fileBasedContent.setName(name);
    fileBasedContent.setVersion(version);

    // add the binary roots
    for (String string : binaryRoots) {
      fileBasedContent.getModifiableBinaryPaths().add(new RootPath(string, true));
    }

    //
    ResourceContent resourceContent = fileBasedContent.getFileBasedContent().getModifiableResourceContent();

    if (sourceRoots != null) {
      // add the source roots
      for (String string : sourceRoots) {
        resourceContent.getModifiableSourcePaths().add(new RootPath(string, false));
      }
    }
    // add the analyze flag
    fileBasedContent.setAnalyzeMode(analyzeMode);

    // return result
    return fileBasedContent;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  private static File getAsFile(String path) throws CoreException {

    //
    IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();

    //
    return new File(stringVariableManager.performStringSubstitution(path));
  }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  private static List<String> toList(String string) {

    //
    List<String> list = new LinkedList<String>();

    //
    if (string != null) {
      list.add(string);
    }

    //
    return list;
  }
}