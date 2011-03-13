package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;

/**
 * <p>
 * </p>
 * 
 * @author wuetherich
 */
@SuppressWarnings("restriction")
public class NameEnvironmentProxy implements INameEnvironment {

  /** the name environment */
  private INameEnvironment _nameEnvironment;

  /** the set of requested types */
  private Set<String>      _requestedTypes;

  /**
   * <p>
   * </p>
   * 
   * @param nameEnvironment
   */
  public NameEnvironmentProxy(INameEnvironment nameEnvironment) {
    Assert.isNotNull(nameEnvironment);

    // set the name environment
    this._nameEnvironment = nameEnvironment;

    // created the requested types map
    _requestedTypes = new HashSet<String>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<String> getRequestedTypes() {
    return _requestedTypes;
  }

  /**
   * <p>
   * </p>
   */
  public void resetRequestedTypes() {
    _requestedTypes.clear();
  }

  /**
   * {@inheritDoc}
   */
  public void cleanup() {
    _nameEnvironment.cleanup();
  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {

    NameEnvironmentAnswer answer = _nameEnvironment.findType(typeName, packageName);

    if (answer != null) {
      _requestedTypes.add(getAsString(packageName) + "." + new String(typeName));
    }

    return answer;
  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[][] compoundTypeName) {

    NameEnvironmentAnswer answer = _nameEnvironment.findType(compoundTypeName);

    if (answer != null) {
      _requestedTypes.add(getAsString(compoundTypeName));
    }

    return answer;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isPackage(char[][] parentPackageName, char[] packageName) {
    return _nameEnvironment.isPackage(parentPackageName, packageName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param compoundTypeName
   * @return
   */
  private String getAsString(char[][] compoundTypeName) {

    //
    StringBuilder builder = new StringBuilder();

    //
    for (int i = 0; i < compoundTypeName.length; i++) {

      //
      char[] cs = compoundTypeName[i];

      //
      builder.append(new String(cs));

      //
      if (i + 1 < compoundTypeName.length) {
        builder.append('.');
      }
    }

    return builder.toString();
  }
}