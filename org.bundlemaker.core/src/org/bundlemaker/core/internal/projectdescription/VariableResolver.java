package org.bundlemaker.core.internal.projectdescription;

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VariableResolver {

  /**
   * <p>
   * </p>
   * 
   * @param expression
   * @return
   * @throws CoreException
   */
  public static File resolveVariable(IPath expression) throws CoreException {
    
    //
    return resolveVariable(expression.toString());
  }

  /**
   * <p>
   * </p>
   *
   * @param expression
   * @return
   * @throws CoreException
   */
  public static File resolveVariable(String expression) throws CoreException {

    //
    Assert.isNotNull(expression);

    //
    IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();

    //
    String substitutedValue = stringVariableManager.performStringSubstitution(expression.toString());

    //
    return new File(substitutedValue);
  }
}
