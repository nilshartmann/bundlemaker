package org.bundlemaker.core.projectdescription;

import junit.framework.Assert;

import org.bundlemaker.core.project.util.VariablePath;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
    VariablePath variablePath = new VariablePath("${workspace_loc}");
    IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation();
    Assert.assertEquals(workspacePath, variablePath.getResolvedPath());
  }

  @Test(expected = CoreException.class)
  public void testNonExistingVariable() throws CoreException {
    new VariablePath("${pi_pa_po}").getResolvedPath();
  }
}
