package org.bundlemaker.core.analysis;


/**
 * <p>
 * Defines an {@link IArtifact} that represents a group.
 * </p>
 * <p>
 * An {@link IRootArtifact} can contain {@link IGroupArtifact IGroupArtifacts} and {@link IModuleArtifact
 * IModuleArtifacts} and therefore extends the interface {@link IGroupAndModuleContainer}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IGroupArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  /**
   * <p>
   * Sets the name of this group artifact.
   * </p>
   * 
   * @param name
   */
  void setName(String name);

  /**
   * <p>
   * Returns the simple group name, e.g. {@code group1}, never {@code null}.
   * </p>
   * 
   * @return the simple group name, e.g. {@code group1}, never {@code null}.
   */
  String getName();

  /**
   * <p>
   * Returns the fully qualified group name, e.g. {@code group2/group1}, never {@code null}.
   * </p>
   * 
   * @return the fully qualified group name, e.g. {@code group2/group1}, never {@code null}.
   */
  String getQualifiedName();
}
