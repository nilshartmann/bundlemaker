package org.bundlemaker.core.projectdescription;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VariablePathTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void test() throws CoreException {

    //
    VariablePath variablePath = new VariablePath("${workspace_loc}");

    System.out.println(variablePath.getResolvedPath());
  }

  @Test(expected = CoreException.class)
  public void testNonExistingVariable() throws CoreException {

    //
    VariablePath variablePath = new VariablePath("${pi_pa_po}");

    System.out.println(variablePath.getResolvedPath());
  }
}
