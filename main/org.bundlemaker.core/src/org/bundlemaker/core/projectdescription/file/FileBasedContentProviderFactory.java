package org.bundlemaker.core.projectdescription.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.file.JarInfo;
import org.bundlemaker.core.internal.projectdescription.file.JarInfoService;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IModifiableBundleMakerProjectDescription;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

public class FileBasedContentProviderFactory {

  /**
   * <p>
   * </p>
   * 
   * @param description
   * @param name
   * @param version
   * @param binaryRoot
   * @param sourceRoot
   * @return
   */
  public static FileBasedContentProvider addNewFileBasedContentProvider(
      IModifiableBundleMakerProjectDescription description, String name, String version, String binaryRoot,
      String sourceRoot) {
    return addNewFileBasedContentProvider(description, name, version, toList(binaryRoot), toList(sourceRoot),
        AnalyzeMode.BINARIES_AND_SOURCES);
  }

  /**
   * <p>
   * </p>
   * 
   * @param description
   * @param binaryRoot
   * @return
   */
  public static FileBasedContentProvider addNewFileBasedContentProvider(
      IModifiableBundleMakerProjectDescription description, String binaryRoot) {
    return addNewFileBasedContentProvider(description, binaryRoot, null, AnalyzeMode.BINARIES_AND_SOURCES);
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
  public static FileBasedContentProvider addNewFileBasedContentProvider(
      IModifiableBundleMakerProjectDescription description, String binaryRoot, String sourceRoot,
      AnalyzeMode analyzeMode) {
    Assert.isNotNull(binaryRoot);
    Assert.isNotNull(analyzeMode);

    try {

      // get the jar info
      JarInfo jarInfo = JarInfoService.extractJarInfo(getAsFile(binaryRoot));

      //
      return addNewFileBasedContentProvider(description, jarInfo.getName(), jarInfo.getVersion(), toList(binaryRoot),
          toList(sourceRoot), analyzeMode);

    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   * @param binaryRoots
   * @param sourceRoots
   * @param analyzeMode
   * @return
   */
  public static FileBasedContentProvider addNewFileBasedContentProvider(
      IModifiableBundleMakerProjectDescription description, String name, String version, List<String> binaryRoots,
      List<String> sourceRoots, AnalyzeMode analyzeMode) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);
    Assert.isNotNull(binaryRoots);
    Assert.isNotNull(analyzeMode);

    // create new file based content
    FileBasedContentProvider fileBasedContentProvider = new FileBasedContentProvider();
    fileBasedContentProvider.setName(name);
    fileBasedContentProvider.setVersion(version);

    // add the binary roots
    for (String string : binaryRoots) {
      fileBasedContentProvider.getFileBasedContent().addRootPath(new VariablePath(string), ContentType.BINARY);
    }

    if (sourceRoots != null) {
      // add the source roots
      for (String string : sourceRoots) {
        fileBasedContentProvider.getFileBasedContent().addRootPath(new VariablePath(string), ContentType.SOURCE);
      }
    }
    // add the analyze flag
    fileBasedContentProvider.setAnalyzeMode(analyzeMode);

    //
    description.addContentProvider(fileBasedContentProvider);

    // return result
    return fileBasedContentProvider;
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