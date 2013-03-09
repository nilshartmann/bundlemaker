package org.bundlemaker.core.itest;

import junit.framework.Assert;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtTest {

  @Test
  public void test() {

    //
    IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
    
    //
    Assert.assertNotNull(vmInstall);
    
    //
    System.out.println(String.format("VM name: '%s'", vmInstall.getName()));
    System.out.println(String.format("VM install location: '%s'", vmInstall.getInstallLocation().getAbsolutePath()));
  }
}
