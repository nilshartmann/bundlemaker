package org.bundlemaker.core.jtype;

import org.eclipse.core.runtime.Assert;

public class JavaUtils {

  // /**
  // * <p>
  // * Returns <code>true</code>, if the package is a valid java package.
  // * </p>
  // *
  // * @return <code>true</code>, if the package is a valid java package.
  // */
  // // TODO: move to Helper class
  // boolean isValidJavaPackage();
  //
  // /**
  // * <p>
  // * Returns the package name, e.g. 'org.example'.
  // * </p>
  // *
  // * @return the package name.
  // */
  // // TODO: move to Helper class
  // String getPackageName();

  /**
   * {@inheritDoc}
   */
  public static boolean isValidJavaPackage(String path) {

    Assert.isNotNull(path);

    //
    String[] elements = path.split("/");

    //
    for (int i = 0; i < elements.length - 1; i++) {

      String element = elements[i];

      if (!isValidJavaIdentifier(element)) {
        return false;
      }
    }

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public static String getPackageNameFromDirectory(String directory) {
    return directory.replace('/', '.');
  }

  /**
   * <p>
   * </p>
   * 
   * @param s
   * @return
   */
  public final static boolean isValidJavaIdentifier(String s) {

    // an empty or null string cannot be a valid identifier
    if (s == null || s.length() == 0) {
      return false;
    }

    char[] c = s.toCharArray();
    if (!Character.isJavaIdentifierStart(c[0])) {
      return false;
    }

    for (int i = 1; i < c.length; i++) {
      if (!Character.isJavaIdentifierPart(c[i])) {
        return false;
      }
    }

    return true;
  }
}
