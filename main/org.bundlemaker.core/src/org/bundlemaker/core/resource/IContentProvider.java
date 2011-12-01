package org.bundlemaker.core.resource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IContentProvider {

  /**
   * <p>
   * Returns the path of the resource. Note that resource paths are always slash-delimited ('/').
   * </p>
   * 
   * @return the path of the resource.
   */
  String getPath();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getDirectory();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isValidJavaPackage();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getPackageName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  byte[] getContent();
}
