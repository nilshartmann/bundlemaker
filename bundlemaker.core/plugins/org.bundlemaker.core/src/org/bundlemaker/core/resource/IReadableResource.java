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
   * Returns the content of this resource.
   * </p>
   * 
   * @return the content of this resource.
   */
  byte[] getContent();
}
