package org.bundlemaker.core.resource;

import java.util.List;

import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.spi.resource.Resource;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;

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

}
