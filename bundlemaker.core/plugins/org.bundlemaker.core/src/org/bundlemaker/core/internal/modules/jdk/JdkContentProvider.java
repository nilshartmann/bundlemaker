package org.bundlemaker.core.internal.modules.jdk;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.common.utils.VMInstallUtils;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class JdkContentProvider extends AbstractProjectContentProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onInitializeProjectContent(IProgressMonitor progressMonitor) throws CoreException {

    //
    String jre = getBundleMakerProject().getProjectDescription().getJRE();

    // get the vm install (has to exist exist)
    IVMInstall vmInstall = VMInstallUtils.getIVMInstall(jre);

    if (vmInstall == null) {
      vmInstall = JavaRuntime.getDefaultVMInstall();
    }

    //
    List<File> binaries = new LinkedList<File>();

    //
    for (LibraryLocation libraryLocation : JavaRuntime.getLibraryLocations(vmInstall)) {

      // get the root
      File root = libraryLocation.getSystemLibraryPath().toFile();

      //
      binaries.add(root);
    }

    //
    IProjectContentEntry contentEntry = createFileBasedContent(vmInstall.getName(), vmInstall.getName(),
        binaries.toArray(new File[0]), null,
        AnalyzeMode.DO_NOT_ANALYZE);

    contentEntry.getUserAttributes().put("EXECUTION_ENVIRONMENT", true);
  }
}
