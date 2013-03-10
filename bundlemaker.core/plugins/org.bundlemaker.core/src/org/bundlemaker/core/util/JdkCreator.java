package org.bundlemaker.core.util;

import java.io.File;

import org.bundlemaker.core.BundleMakerCore;
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

    // get the VMInstallType
    IVMInstallType type = JavaRuntime.getVMInstallType(ID_STANDARD_VM_TYPE);

    if (type == null || name == null) {
      return getDefaultVMInstall(name);
    }

    //
    IVMInstall result;
    result = type.findVMInstallByName(name);

    if (result == null) {
      return getDefaultVMInstall(name);
    }

    return result;
  }

  // public static final IVMInstall getOrCreateIVMInstall(String name, File installLocation) throws CoreException {
  // IVMInstall install = getIVMInstall(name);
  //
  // if (install != null) {
  // return install;
  // }
  //
  // IVMInstallType type = JavaRuntime.getVMInstallType(ID_STANDARD_VM_TYPE);
  // IVMInstall newInstall = type.createVMInstall(name);
  // newInstall.setInstallLocation(installLocation);
  //
  // return newInstall;
  //
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IVMInstall getDefaultVMInstall(String requestedVmInstall) {

    System.out.println("*****************************************************************************");
    System.out.println(String.format("*  Specified VM '%s' does not exist. Using default VM.", requestedVmInstall));
    System.out.println("*****************************************************************************");

    return JavaRuntime.getDefaultVMInstall();
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

    if (type == null) {
      return getDefaultVMInstall(name);
    }

    //
    IVMInstall result = type.findVMInstallByName(name);
    if (result != null) {
      return result;
    }

    // create the jre directory
    File jreDirectory = new File(directoryName);

    // check...
    if (!type.validateInstallLocation(new File(directoryName)).isOK()) {
      throw new CoreException(new Status(IStatus.ERROR, BundleMakerCore.BUNDLE_ID, "Install location '" + directoryName
          + "' not valid!"));
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
