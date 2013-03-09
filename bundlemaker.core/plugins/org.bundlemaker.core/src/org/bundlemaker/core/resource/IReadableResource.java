package org.bundlemaker.core.resource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReadableResource {

  /**
   * <p>
   * Returns the full path of the resource, e.g. 'org/example/Test.java'. Note that resource paths are always
   * slash-delimited ('/').
   * </p>
   * 
   * @return the full path of the resource.
   */
  String getPath();

  /**
   * <p>
   * Returns the directory of the resource, e.g. 'org/example'. Note that resource paths are always slash-delimited
   * ('/').
   * </p>
   * 
   * @return the directory of the resource
   */
  String getDirectory();

  /**
   * <p>
   * Returns the name of the resource, e.g. 'Test.java'.
   * </p>
   * 
   * @return the name of the resource
   */
  String getName();

  /**
   * <p>
   * Returns <code>true</code>, if the package is a valid java package.
   * </p>
   * 
   * @return <code>true</code>, if the package is a valid java package.
   */
  // TODO: move to Helper class
  boolean isValidJavaPackage();

  /**
   * <p>
   * Returns the package name, e.g. 'org.example'.
   * </p>
   * 
   * @return the package name.
   */
  // TODO: move to Helper class
  String getPackageName();

  /**
   * <p>
   * Returns the content of this resource.
   * </p>
   * 
   * @return the content of this resource.
   */
  byte[] getContent();
}
