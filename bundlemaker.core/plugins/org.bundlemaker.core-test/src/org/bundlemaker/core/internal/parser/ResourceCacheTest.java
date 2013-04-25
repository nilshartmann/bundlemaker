package org.bundlemaker.core.internal.parser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.resource.IModifiableResource;
import org.bundlemaker.core.resource.ResourceKey;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class ResourceCacheTest {

  @Test
  public void testProjectDescription() throws CoreException {

    //
    IPersistentDependencyStore dependencyStore = mock(IPersistentDependencyStore.class);

    //
    ResourceCache resourceCache = new ResourceCache(dependencyStore);
    IModifiableResource modifiableResource = resourceCache.getOrCreateResource(new ResourceKey("1", "12", "123"));
    
    //
    resourceCache.commit(null);

    //
    verify(dependencyStore, times(1)).updateResource(modifiableResource);
    
    //
    System.out.println(modifiableResource);
  }
}
