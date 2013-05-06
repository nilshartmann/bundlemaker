package org.bundlemaker.core.resource;

/**
 * <p>
 * Defines the common interface for a resource. Normally a resource is either a file or a entry in an archive file. A
 * resource has a name, a path a a containing directory.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResource {

  /**
   * <p>
   * Returns the full path of the resource, e.g. <code>'org/example/Test.java'</code>. Note that resource paths are
   * always slash-delimited ('/').
   * </p>
   * 
   * @return the full path of the resource.
   */
  String getPath();

  /**
   * <p>
   * Returns the directory of the resource, e.g. <code>'org/example'</code>. Note that resource paths are always
   * slash-delimited ('/').
   * </p>
   * 
   * @return the directory of the resource
   */
  String getDirectory();

  /**
   * <p>
   * Returns the name of the resource, e.g. <code>'Test.java'</code>.
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
