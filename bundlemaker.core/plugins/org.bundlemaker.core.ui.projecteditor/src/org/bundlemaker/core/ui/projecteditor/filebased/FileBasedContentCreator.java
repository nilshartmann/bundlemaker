package org.bundlemaker.core.ui.projecteditor.filebased;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectDescription;
import org.bundlemaker.core.util.IFileBasedProjectContentInfo;

/**
 * Util class that creates FileBasedContent instances based on a set of selected files.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentCreator {

  //
  // private final static String[] SOURCE_EXTENSIONS = { ".src", ".source", "-src", "-source" };

  /**
   * <p>
   * </p>
   * 
   * @param modifiableProjectDescription
   * @param fileNames
   */
  public void addFiles(IModifiableProjectDescription modifiableProjectDescription, String[] fileNames) {

    VariablePath[] paths = new VariablePath[fileNames.length];

    for (int i = 0; i < fileNames.length; i++) {
      paths[i] = new VariablePath(fileNames[i]);
    }

    addFiles(modifiableProjectDescription, paths);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modifiableProjectDescription
   * @param variablePaths
   */
  private void addFiles(IModifiableProjectDescription modifiableProjectDescription, VariablePath[] variablePaths) {

    //
    final Map<IFileBasedProjectContentInfo, VariablePath> modules = new HashMap<IFileBasedProjectContentInfo, VariablePath>();

    // STEP 1:
    for (VariablePath variablePath : variablePaths) {

      //
      File file = getFile(variablePath);

      if (file == null) {
        // variable cannot be resolved. use name only

        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            variablePath
                .getUnresolvedPath().toOSString(), null, AnalyzeMode.BINARIES_ONLY);

        continue;
      }

      if (file.isDirectory()) {

        // TODO: for now we assume a directory is always binary content
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            file.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);

        continue;
      }

      IFileBasedProjectContentInfo jarInfo = IFileBasedProjectContentInfo.Factory
          .extractFileBasedProjectContentInfo(file);

      //
      modules.put(jarInfo, variablePath);
    }

    // STEP 2:
    for (IFileBasedProjectContentInfo info : new LinkedList<IFileBasedProjectContentInfo>(modules.keySet())) {

      // ignore potential source modules for now
      if (info.isSource()) {
        continue;
      }

      // already handled?
      VariablePath variablePath = modules.remove(info);
      if (variablePath == null) {
        continue;
      }

      //
      IFileBasedProjectContentInfo sourceInfo = getAssociatedFileBasedProjectContent(info,
          modules.keySet());

      //
      VariablePath sourcePath = modules.remove(sourceInfo);

      if (sourceInfo != null) {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            variablePath,
            sourcePath,
            AnalyzeMode.BINARIES_AND_SOURCES);

      } else {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            variablePath, null,
            AnalyzeMode.BINARIES_ONLY);
      }
    }

    // add rest of files (most probably source files according to our naming conventions. Add it anyway) TODO: we could
    // issue a warning here (sources without binaries)
    for (IFileBasedProjectContentInfo info : modules.keySet()) {
      FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
          modules.get(info), null,
          AnalyzeMode.BINARIES_ONLY);
    }
  }

  /**
   * Returns the resolved path as file or null if it couldn't be resolved
   * 
   * @param variablePath
   * @return
   */
  private File getFile(VariablePath variablePath) {
    try {
      return variablePath.getAsFile();
    } catch (Exception ex) {
      return null;
    }
  }

  private IFileBasedProjectContentInfo getAssociatedFileBasedProjectContent(IFileBasedProjectContentInfo info,
      Collection<IFileBasedProjectContentInfo> allInfos) {

    //
    for (IFileBasedProjectContentInfo i : allInfos) {

      if (info.isSource()) {
        // in case info IS SOURCE, i must not be source
        if (i.isSource() == false) {
          if (info.getBinaryName().equals(i.getName()) && info.getVersion().equals(i.getVersion())) {
            return i;
          }
        }
      } else {
        if (i.isSource()) {
          if (info.getName().equals(i.getBinaryName()) && info.getVersion().equals(i.getVersion())) {
            return i;
          }
        }
      }

      //
      if (i.getName().equals(info.getName()) && i.getVersion().equals(info.getVersion())
          && i.isSource() != info.isSource()) {
        return i;
      }
    }

    //
    return null;
  }

}
