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
import org.bundlemaker.core.util.JarInfo;
import org.bundlemaker.core.util.JarInfoService;

public class FileBasedContentCreator {
  private final static String[] SOURCE_EXTENSIONS = { ".src", ".source" };

  public void addFiles(IModifiableProjectDescription modifiableProjectDescription, String[] fileNames) {

    final Map<String, File> modules = new Hashtable<String, File>();

    for (String fileName : fileNames) {
      File file = new File(fileName);

      if (file.isDirectory()) {
        // for now we assume a directory is always binary content
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            file.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);

        continue;
      }

      JarInfo jarInfo = JarInfoService.extractJarInfo(file);
      modules.put(jarInfo.getName(), file);
    }

    List<String> moduleNames = new LinkedList<String>(modules.keySet());

    for (String moduleName : moduleNames) {
      if (isSourceModule(moduleName)) {
        // ignore potential source modules for now
        continue;
      }

      File file = modules.remove(moduleName);
      if (file == null) {
        // already handled
        continue;
      }

      File sourceFile = getSourceFile(modules, moduleName);
      if (sourceFile != null) {
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            file.getAbsolutePath(), sourceFile.getAbsolutePath(), AnalyzeMode.BINARIES_AND_SOURCES);

      } else {
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
            file.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);
      }
    }

    // add rest of files (most probably source files according to our naming conventions. Add it anyway)
    Collection<File> files = modules.values();
    for (File file : files) {
      FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription,
          file.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);
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
  protected File getSourceFile(Map<String, File> modules, String binaryModuleName) {

    for (String sourceExtension : SOURCE_EXTENSIONS) {
      File sourceModule = modules.remove(binaryModuleName + sourceExtension);
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

}
