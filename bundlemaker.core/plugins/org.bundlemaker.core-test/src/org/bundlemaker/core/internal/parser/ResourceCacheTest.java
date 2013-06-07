package org.bundlemaker.core.internal.parser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.bundlemaker.core.parser.IParsableResource;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class ResourceCacheTest {

  @Test
  public void testProjectDescription() throws CoreException {

    //
    IPersistentDependencyStore dependencyStore = mock(IPersistentDependencyStore.class);

    //
    ResourceCache resourceCache = new ResourceCache(dependencyStore);
    IParsableResource modifiableResource = resourceCache.getOrCreateResource(new DefaultProjectContentResource("1", "12", "123"));
    
    //
    resourceCache.commit(null);

    //
    verify(dependencyStore, times(1)).updateResource(modifiableResource);
    
    //
    System.out.println(modifiableResource);
  }
}
