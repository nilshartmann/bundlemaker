package org.bundlemaker.core.modules;

import java.util.Set;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceContainer extends ITypeContainer {

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @param contentType
   * @return
   */
  boolean containsResource(String path, ContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @param conentType
   * @return
   */
  IResource getResource(String path, ContentType conentType);

  /**
   * <p>
   * </p>
   * 
   * @param conentType
   * @return
   */
  Set<IResource> getResources(ContentType conentType);

  /**
   * <p>
   * </p>
   * 
   * @param hideContainedTypes
   * @param includeSourceReferences
   * @return
   */
  Set<String> getReferencedTypeNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences);

  /**
   * <p>
   * </p>
   * 
   * @param hideContainedTypes
   * @param includeSourceReferences
   * @return
   */
  Set<IReference> getAllReferences(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences);

  /**
   * <p>
   * </p>
   * 
   * @param hideContainedTypes
   * @param includeSourceReferences
   * @return
   */
  Set<String> getReferencedPackageNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences);

  /**
   * <p>
   * Returns the containing {@link IResourceModule}. If the {@link IResourceContainer} is a {@link IResourceModule}, the
   * {@link IResourceModule} itself will be returned.
   * </p>
   * 
   * @return
   */
  IResourceModule getResourceModule();
}