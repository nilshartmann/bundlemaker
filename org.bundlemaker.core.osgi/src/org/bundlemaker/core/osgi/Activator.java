package org.bundlemaker.core.osgi;

import org.eclipse.pde.core.project.IBundleProjectService;

import org.eclipse.pde.internal.core.target.provisional.ITargetPlatformService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("restriction")
public class Activator implements BundleActivator {

	/** - */
	private static BundleContext _context;

	/** - */
	private static ServiceTracker _bundleProjectServiceTracker;

	/** - */
	private static ServiceTracker _targetPlatformServiceTracker;

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	static BundleContext getContext() {
		return _context;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public static IBundleProjectService getBundleProjectService() {
		return (IBundleProjectService) _bundleProjectServiceTracker
				.getService();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public static ITargetPlatformService getTargetPlatformService() {
		return (ITargetPlatformService) _targetPlatformServiceTracker
				.getService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {

		//
		Activator._context = bundleContext;

		//
		_bundleProjectServiceTracker = new ServiceTracker(bundleContext,
				IBundleProjectService.class.getName(), null);

		//
		_targetPlatformServiceTracker = new ServiceTracker(bundleContext,
				ITargetPlatformService.class.getName(), null);

		//
		_bundleProjectServiceTracker.open();
		_targetPlatformServiceTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator._context = null;

		//
		_bundleProjectServiceTracker.close();
	}

}
