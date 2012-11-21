package org.bundlemaker.core.ui.projecteditor.filebased;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfo;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfoService;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProviderFactory;
import org.bundlemaker.core.projectdescription.file.VariablePath;

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
    final List<FileBasedProjectContentInfo<VariablePath>> modules = new LinkedList<FileBasedProjectContentInfo<VariablePath>>();

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

      FileBasedProjectContentInfo<VariablePath> jarInfo = FileBasedProjectContentInfoService.Factory
          .getJarInfoService()
          .extractJarInfo(file);

      //
      jarInfo.setUserObject(variablePath);

      //
      modules.add(jarInfo);
    }

    // STEP 2:
    for (FileBasedProjectContentInfo<VariablePath> info : new LinkedList<FileBasedProjectContentInfo<VariablePath>>(
        modules)) {

      // ignore potential source modules for now
      if (info.isSource()) {
        continue;
      }

      // already handled?
      if (!modules.remove(info)) {
        continue;
      }

      //
      FileBasedProjectContentInfo<VariablePath> sourceInfo = FileBasedProjectContentInfoService.Factory
          .getJarInfoService()
          .getAssociatedFileBasedProjectContent(info, modules);

      modules.remove(sourceInfo);

      if (sourceInfo != null) {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            info.getUserObject(),
            sourceInfo.getUserObject(),
            AnalyzeMode.BINARIES_AND_SOURCES);

      } else {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            info.getUserObject(), null,
            AnalyzeMode.BINARIES_ONLY);
      }
    }

    // add rest of files (most probably source files according to our naming conventions. Add it anyway) TODO: we could
    // issue a warning here (sources without binaries)
    for (FileBasedProjectContentInfo<VariablePath> info : modules) {
      FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
          info.getUserObject(), null,
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

}
