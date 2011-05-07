package org.bundlemaker.dependencyanalysis.base.model;

/**
 * <p>
 * Describes the common methods of a dependency model.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public interface IDependencyModel {

  /**
   * <p>
   * Returns the name of the model.
   * </p>
   * 
   * @return the name of the model.
   */
  public String getName();

  /**
   * <p>
   * Returns the root node of the model.
   * </p>
   * 
   * @return the root node of the model.
   */
  public IArtifact getRoot();

  /**
   * <p>
   * Creates a copy of this model with a new name. You can use this copy as the core of subsequent transformations.
   * </p>
   * 
   * @param name
   *          the name of the model
   * 
   * @return the new dependency model
   */
  public IDependencyModel createDependencyModel(String name);

  /**
   * <p>
   * Creates an new artifact with the given artifact type.
   * </p>
   * 
   * @param name
   *          the name of the artifact
   * @param type
   *          the type of the artifact
   * @param id
   *          the id of the artifact
   * 
   * @return the newly created artifact
   */
  public IArtifact createArtifact(String name, ArtifactType type);

  /**
   * <p>
   * Creates a new artifact container.
   * </p>
   * 
   * @param name
   *          the name of the artifact container
   * @param type
   *          the type of the artifact container
   * @param id
   *          the id of the artifact container
   * 
   * @return the newly created artifact container
   */
  public IArtifact createArtifactContainer(String name, String qualifiedName, ArtifactType type);
}
