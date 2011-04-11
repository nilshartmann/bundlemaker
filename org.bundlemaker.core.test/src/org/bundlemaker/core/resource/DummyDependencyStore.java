package org.bundlemaker.core.resource;

import java.util.List;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;

public class DummyDependencyStore implements IPersistentDependencyStore {

  @Override
  public List<Resource> getResources() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateResource(IModifiableResource resource) {
    // TODO Auto-generated method stub

  }

  @Override
  public void commit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isInitialized() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
    
  }
}
