package org.bundlemaker.core.osgi.internal.manifest;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceContainer;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.IPath;

public class DelegatingResourceModule implements IResourceModule {

  private IResourceModule _resourceModule;

  public DelegatingResourceModule(IResourceModule resourceModule) {
    _resourceModule = resourceModule;
  }

  public boolean containsSources() {
    return _resourceModule.containsSources();
  }

  public IModularizedSystem getModularizedSystem() {
    return _resourceModule.getModularizedSystem();
  }

  public Collection<IType> getContainedTypes(IQueryFilter<IType> filter) {
    return _resourceModule.getContainedTypes(filter);
  }

  public IResourceContainer getSelfResourceContainer() {
    return _resourceModule.getSelfResourceContainer();
  }

  public IModuleIdentifier getModuleIdentifier() {
    return _resourceModule.getModuleIdentifier();
  }

  public boolean containsAll(Set<String> typeNames) {
    return _resourceModule.containsAll(typeNames);
  }

  public Map<String, ? extends IResourceContainer> getContainedResourceContainers() {
    return _resourceModule.getContainedResourceContainers();
  }

  public IPath getClassification() {
    return _resourceModule.getClassification();
  }

  public boolean containsResource(String path, ContentType contentType) {
    return _resourceModule.containsResource(path, contentType);
  }

  public boolean hasClassification() {
    return _resourceModule.hasClassification();
  }

  public IType getType(String fullyQualifiedName) {
    return _resourceModule.getType(fullyQualifiedName);
  }

  public Map<String, Object> getUserAttributes() {
    return _resourceModule.getUserAttributes();
  }

  public IResource getResource(String path, ContentType conentType) {
    return _resourceModule.getResource(path, conentType);
  }

  public Set<IResource> getResources(ContentType conentType) {
    return _resourceModule.getResources(conentType);
  }

  public Set<String> getReferencedTypeNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {
    return _resourceModule.getReferencedTypeNames(hideContainedTypes, includeSourceReferences,
        includeIndirectReferences);
  }

  public Collection<IType> getContainedTypes() {
    return _resourceModule.getContainedTypes();
  }

  public Set<IReference> getAllReferences(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {
    return _resourceModule.getAllReferences(hideContainedTypes, includeSourceReferences, includeIndirectReferences);
  }

  public Collection<String> getContainedTypeNames() {
    return _resourceModule.getContainedTypeNames();
  }

  public Collection<String> getContainedTypeNames(IQueryFilter<String> filter) {
    return _resourceModule.getContainedTypeNames(filter);
  }

  public Set<String> getReferencedPackageNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {
    return _resourceModule.getReferencedPackageNames(hideContainedTypes, includeSourceReferences,
        includeIndirectReferences);
  }

  public Set<String> getContainedPackageNames() {
    return _resourceModule.getContainedPackageNames();
  }

  public Set<String> getContainedPackageNames(IQueryFilter<String> filter) {
    return _resourceModule.getContainedPackageNames(filter);
  }

  public IResourceModule getResourceModule() {
    return _resourceModule.getResourceModule();
  }

}
