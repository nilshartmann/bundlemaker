package org.bundlemaker.core.store.db4o.internal;

import org.bundlemaker.core.store.IPersistentDependencyStoreFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import com.db4o.osgi.Db4oService;

/**
 * <p>
 * This bundle activator registers a {@link IPersistentDependencyStoreFactory}
 * if a {@link Db4oService} becomes available in the system and unregisters it
 * if it disappears.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

	/** the plugin id */
	public static final String PLUGIN_ID = "org.bundlemaker.core.db4o";

	/** the service tracker */
	private ServiceTracker _serviceTracker;

	/**
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		// create the service tracker
		_serviceTracker = new ServiceTracker(context, Db4oService.class
				.getName(), null) {

			private ServiceRegistration _factoryRegistration;

			@Override
			public Object addingService(ServiceReference reference) {
				Db4oService db4oService = (Db4oService) super
						.addingService(reference);

				_factoryRegistration = context.registerService(
						IPersistentDependencyStoreFactory.class.getName(),
						new PersistentDependencyStoreFactoryImpl(db4oService),
						null);

				return db4oService;
			}

			@Override
			public void removedService(ServiceReference reference,
					Object service) {

				_factoryRegistration.unregister();

				super.removedService(reference, service);
			}

		};

		// open the service tracker
		_serviceTracker.open();
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		// close the tracker
		_serviceTracker.close();
	}
}
