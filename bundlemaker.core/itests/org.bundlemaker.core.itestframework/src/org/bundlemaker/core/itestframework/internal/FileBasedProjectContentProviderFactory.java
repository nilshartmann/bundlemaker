package org.bundlemaker.core.itestframework.internal;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProvider;
import org.bundlemaker.core.util.IFileBasedProjectContentInfo;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

public class FileBasedProjectContentProviderFactory {

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
  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String name, String version, String binaryRoot, String sourceRoot) {
    return addNewFileBasedContentProvider(description, name, version, (binaryRoot == null ? null : new VariablePath(
        binaryRoot)), (sourceRoot == null ? null : new VariablePath(sourceRoot)));
  }

  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String name, String version, VariablePath binaryRoot,
      VariablePath sourceRoot) {
    return addNewFileBasedContentProvider(description, name, version, toList(binaryRoot), toList(sourceRoot),
        AnalyzeMode.BINARIES_AND_SOURCES);
  }

  /**
   * <p>
   * </p>
   * 
   * @param description
   * @param name
   * @param version
   * @param binaryRoot
   * @param sourceRoot
   * @param analyzeMode
   * @return
   */
  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String name, String version, VariablePath binaryRoot,
      VariablePath sourceRoot, AnalyzeMode analyzeMode) {
    return addNewFileBasedContentProvider(description, name, version, toList(binaryRoot), toList(sourceRoot),
        analyzeMode);
  }

  /**
   * <p>
   * </p>
   * 
   * @param description
   * @param binaryRoot
   * @return
   */
  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String binaryRoot) {
    return addNewFileBasedContentProvider(description, (binaryRoot == null ? null : new VariablePath(binaryRoot)),
        null, AnalyzeMode.BINARIES_AND_SOURCES);
  }

  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String binaryRoot, String sourceRoot, AnalyzeMode analyzeMode) {
    Assert.isNotNull(binaryRoot);
    Assert.isNotNull(analyzeMode);

    return addNewFileBasedContentProvider(description, new VariablePath(binaryRoot), null, analyzeMode);
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
  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, VariablePath binaryRoot, VariablePath sourceRoot,
      AnalyzeMode analyzeMode) {
    Assert.isNotNull(binaryRoot);
    Assert.isNotNull(analyzeMode);

    try {

      // get the jar info
      IFileBasedProjectContentInfo jarInfo = IFileBasedProjectContentInfo.Factory
          .extractFileBasedProjectContentInfo(getAsFile(binaryRoot));

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
  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String name, String version, String[] binaryRoots,
      String[] sourceRoots, AnalyzeMode analyzeMode) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);
    Assert.isNotNull(binaryRoots);
    Assert.isNotNull(analyzeMode);

    List<VariablePath> binaryPaths = new LinkedList<VariablePath>();
    for (String binaryRoot : binaryRoots) {
      binaryPaths.add(new VariablePath(binaryRoot));
    }

    List<VariablePath> sourcePaths = null;
    if (sourceRoots != null) {
      sourcePaths = new LinkedList<VariablePath>();
      for (String sourceRoot : sourceRoots) {
        sourcePaths.add(new VariablePath(sourceRoot));
      }
    }

    return addNewFileBasedContentProvider(description, name, version, binaryPaths, sourcePaths, analyzeMode);
  }

  public static FileBasedProjectContentProvider addNewFileBasedContentProvider(
      IModifiableProjectDescription description, String name, String version, List<VariablePath> binaryRoots,
      List<VariablePath> sourceRoots, AnalyzeMode analyzeMode) {

    // create new file based content
    FileBasedProjectContentProvider fileBasedContentProvider = new FileBasedProjectContentProvider();
    fileBasedContentProvider.setName(name);
    fileBasedContentProvider.setVersion(version);

    // add the binary roots
    for (VariablePath path : binaryRoots) {
      fileBasedContentProvider.addRootPath(path, ProjectContentType.BINARY);
    }

    if (sourceRoots != null) {
      // add the source roots
      for (VariablePath path : sourceRoots) {
        fileBasedContentProvider.addRootPath(path, ProjectContentType.SOURCE);
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
  private static File getAsFile(VariablePath path) throws CoreException {

    return path.getAsFile();
    //
    // //
    // IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();
    //
    // //
    // return new File(stringVariableManager.performStringSubstitution(path));
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  private static List<VariablePath> toList(VariablePath path) {

    //
    List<VariablePath> list = new LinkedList<VariablePath>();

    //
    if (path != null) {
      list.add(path);
    }

    //
    return list;
  }
}