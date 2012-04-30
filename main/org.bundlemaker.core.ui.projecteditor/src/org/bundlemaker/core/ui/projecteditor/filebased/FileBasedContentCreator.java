package org.bundlemaker.core.ui.projecteditor.filebased;

import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProviderFactory;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.util.JarInfo;
import org.bundlemaker.core.util.JarInfoService;

/**
 * Util class that creates FileBasedContent instances based on a set of selected files.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentCreator {
  private final static String[] SOURCE_EXTENSIONS = { ".src", ".source", "-src", "-source" };

  public void addFiles(IModifiableProjectDescription modifiableProjectDescription, String[] fileNames) {

    VariablePath[] paths = new VariablePath[fileNames.length];

    for (int i = 0; i < fileNames.length; i++) {
      paths[i] = new VariablePath(fileNames[i]);
    }

    addFiles(modifiableProjectDescription, paths);
  }

  public void addFiles(IModifiableProjectDescription modifiableProjectDescription, VariablePath[] variablePaths) {

    final Map<String, VariablePath> modules = new Hashtable<String, VariablePath>();

    for (VariablePath variablePath : variablePaths) {
      File file = getFile(variablePath);

      if (file == null) {
        // variable cannot be resolved. use name only

        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, variablePath
            .getUnresolvedPath().toOSString(), null, AnalyzeMode.BINARIES_ONLY);

        continue;
      }

      if (file.isDirectory()) {
        // for now we assume a directory is always binary content
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            file.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);

        continue;
      }

      JarInfo jarInfo = JarInfoService.extractJarInfo(file);
      modules.put(jarInfo.getName(), variablePath);
    }

    List<String> moduleNames = new LinkedList<String>(modules.keySet());

    for (String moduleName : moduleNames) {
      if (isSourceModule(moduleName)) {
        // ignore potential source modules for now
        continue;
      }

      VariablePath path = modules.remove(moduleName);
      if (path == null) {
        // already handled
        continue;
      }

      VariablePath sourceFile = getSourceFile(modules, moduleName);
      if (sourceFile != null) {
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, path, sourceFile,
            AnalyzeMode.BINARIES_AND_SOURCES);

      } else {
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, path, null,
            AnalyzeMode.BINARIES_ONLY);
      }
    }

    // add rest of files (most probably source files according to our naming conventions. Add it anyway)
    Collection<VariablePath> files = modules.values();
    for (VariablePath file : files) {
      FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, file, null,
          AnalyzeMode.BINARIES_ONLY);
    }

  }

  /**
   * Try to find the corresponding source module by naming convention.
   * 
   * <p>
   * If a source module has been found it will be removed from the map
   * 
   * @param modules
   * @param binaryModuleName
   * @return
   */
  protected VariablePath getSourceFile(Map<String, VariablePath> modules, String binaryModuleName) {

    for (String sourceExtension : SOURCE_EXTENSIONS) {
      VariablePath sourceModule = modules.remove(binaryModuleName + sourceExtension);
      if (sourceModule != null) {
        return sourceModule;
      }
    }

    return null;
  }

  public static boolean isSourceModule(String moduleName) {
    moduleName = moduleName.toLowerCase();

    for (String sourceExtension : SOURCE_EXTENSIONS) {
      if (moduleName.endsWith(sourceExtension)) {
        return true;
      }
    }

    return false;
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
