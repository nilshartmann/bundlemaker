/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.store.db4o.internal;

import java.util.List;

import org.bundlemaker.core.common.classloading.BundleDelegatingClassLoader;
import org.bundlemaker.core.common.classloading.CompoundClassLoader;
import org.bundlemaker.core.internal.modelext.ModelExtFactory;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.db4o.osgi.Db4oService;
import com.db4o.query.Query;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreImpl extends
		AbstractPersistentDependencyStore {

	/** - */
	private BundleContext _bundleContext;

	/**
	 * <p>
	 * Creates a new instance of type {@link PersistentDependencyStoreImpl}.
	 * </p>
	 * 
	 * @param fileName
	 */
	public PersistentDependencyStoreImpl(String fileName, BundleContext bundleContext) {
		super(fileName);

		_bundleContext = bundleContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IParsableResource> getResources() {
		Query query = getDatabase().query();
		query.constrain(Resource.class);
		List<IParsableResource> result = query.execute();

		//
		Thread.currentThread().setContextClassLoader(getReflectorClassLoader());
		for (IParsableResource r : result) {
			System.out.println(r);
		}

		//
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateResource(IParsableResource bundleElement) {
		getDatabase().store(bundleElement);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(IParsableResource resource) {
		getDatabase().delete(resource);
	}

	@Override
	protected ClassLoader getReflectorClassLoader() {

		//
		List<String> namespaces = ModelExtFactory.getModelExtensionFactory()
				.getExtensionBundleNamespaces();

		//
		CompoundClassLoader compoundClassLoader = new CompoundClassLoader();

		//
		for (Bundle bundle : _bundleContext.getBundles()) {

			if (namespaces.contains(bundle.getSymbolicName())) {
				compoundClassLoader
						.add(new BundleDelegatingClassLoader(bundle));
			}
		}

		//
		return compoundClassLoader;
	}
}
