package org.bundlemaker.core.util;

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdkCreator {

  /** ID_STANDARD_VM_TYPE */
  private static final String ID_STANDARD_VM_TYPE = "org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType";

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @return
   * @throws CoreException
   */
  public static final IVMInstall getIVMInstall(String name) throws CoreException {

    // asserts
    Assert.isNotNull(name);

    // get the VMInstallType
    IVMInstallType type = JavaRuntime.getVMInstallType(ID_STANDARD_VM_TYPE);

    //
    return type.findVMInstallByName(name);
  }

  /**
   * <p>
   * </p>
   * 
   * @param directoryName
   * @return
   * @throws CoreException
   */
  public static final IVMInstall getOrCreateIVMInstall(String name, String directoryName) throws CoreException {

    // asserts
    Assert.isNotNull(name);
    Assert.isNotNull(directoryName);

    // get the VMInstallType
    IVMInstallType type = JavaRuntime.getVMInstallType(ID_STANDARD_VM_TYPE);

    //
    IVMInstall result = type.findVMInstallByName(name);
    if (result != null) {
      return result;
    }

    // create the jre directory
    File jreDirectory = new File(directoryName);

    // check...
    if (!type.validateInstallLocation(new File(directoryName)).isOK()) {
      // TODO
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }

    // get the library locations
    LibraryLocation[] locations = type.getDefaultLibraryLocations(jreDirectory);

    // create the vm
    IVMInstall install = type.createVMInstall(name);
    install.setName(name);
    install.setInstallLocation(jreDirectory);
    install.setLibraryLocations(locations);

    // return the install
    return install;
  }
}
