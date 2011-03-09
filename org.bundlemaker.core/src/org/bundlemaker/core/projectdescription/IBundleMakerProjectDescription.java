package org.bundlemaker.core.projectdescription;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;

/**
 * <p>
 * Defines the interface of a bundle maker project description.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IBundleMakerProjectDescription {

  /**
   * <p>
   * Returns the containing bundle maker project.
   * </p>
   * 
   * @return the containing bundle maker project.
   */
  IBundleMakerProject getBundleMakerProject();

  /**
   * <p>
   * Returns a list with all the defined {@link IFileBasedContent}.
   * </p>
   * 
   * @return a list with all the defined {@link IFileBasedContent}.
   */
  List<? extends IFileBasedContent> getFileBasedContent();

  /**
   * <p>
   * Returns the {@link IFileBasedContent} with the specified identifier.
   * </p>
   * 
   * @param id
   *          the identifier
   * @return the {@link IFileBasedContent} with the specified identifier.
   */
  IFileBasedContent getFileBasedContent(String id);

  /**
   * <p>
   * Returns the name of the associated JRE.
   * </p>
   * 
   * @return the name of the associated JRE.
   */
  String getJRE();

  /**
   * <p>
   * Sets the JRE:
   * </p>
   * 
   * @param jre
   */
  void setJre(String jre);

  /**
   * <p>
   * Adds a new type content entry with the specified binary root. The name and the version of the content entry are
   * automatically extracted from the binary root.
   * </p>
   * 
   * @param binaryRoot
   *          the binary root.
   */
  void addTypeContent(String binaryRoot);

  /**
   * <p>
   * Adds a new type content entry with the specified name, version and binary root.
   * </p>
   * 
   * @param name
   *          the name of the content entry
   * @param version
   *          the version of the content entry
   * @param binaryRoot
   *          the binary root
   */
  void addTypeContent(String name, String version, String binaryRoot);

  /**
   * <p>
   * Adds a new type content entry with the specified name, version and binary roots.
   * </p>
   * 
   * @param name
   *          the name of the content entry
   * @param version
   *          the version of the content entry
   * @param binaryRoots
   *          the binary roots
   */
  void addTypeContent(String name, String version, List<String> binaryRoots);

  /**
   * <p>
   * Adds a new resource content entry with the specified binary. The name and the version of the content entry are
   * automatically extracted from the binary root.
   * </p>
   * <p>
   * This method is a convenience method an fully equivalent to <code><pre>
   * addResourceContent(binaryRoot, null);
   * </pre></code>
   * </p>
   * 
   * @param binaryRoot
   */
  void addResourceContent(String binaryRoot);

  /**
   * <p>
   * Adds a new resource content entry with the specified binary and source root. The name and the version of the
   * content entry are automatically extracted from the binary root.
   * </p>
   * 
   * @param binaryRoot
   * @param sourceRoot
   */
  void addResourceContent(String binaryRoot, String sourceRoot);

  /**
   * <p>
   * Adds a new resource content entry with the specified name, version, binary root.
   * </p>
   * 
   * @param name
   *          the name of the content entry
   * @param version
   *          the version of the content entry
   * @param binaryRoot
   */
  void addResourceContent(String name, String version, String binaryRoot);

  /**
   * <p>
   * Adds a new resource content entry with the specified name, version, binary and source root.
   * </p>
   * 
   * @param name
   * @param version
   * @param binaryRoot
   * @param sourceRoot
   */
  void addResourceContent(String name, String version, String binaryRoot, String sourceRoot);

  /**
   * <p>
   * Adds a new resource content entry with the specified name, version, binary and source roots.
   * </p>
   * 
   * @param name
   * @param version
   * @param binaryRoots
   * @param sourceRoots
   */
  void addResourceContent(String name, String version, List<String> binaryRoots, List<String> sourceRoots);

  /**
   * <p>
   * Removes the content entry with the specified identifier.
   * </p>
   * 
   * @param identifier
   *          the identifier
   */
  void removeContent(String identifier);

  /**
   * <p>
   * Clears the list of content entries. After calling this method the list of content entries is empty.
   * </p>
   */
  void clear();
}
