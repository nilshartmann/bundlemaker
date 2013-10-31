package org.bundlemaker.core.jdt.internal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

	/** - */
	private static Activator _instance;

	/** - */
	private IResourceChangeListener _listener;

	/** - */
	GenericCache<IProject, List<JdtProjectContentProvider>> _project2provider;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return the _instance
	 */
	public static Activator getInstance() {
		return _instance;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return the provider
	 */
	public GenericCache<IProject, List<JdtProjectContentProvider>> getProject2ProviderMap() {
		return _project2provider;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("serial")
	@Override
	public void start(BundleContext context) throws Exception {

		//
		_project2provider = new GenericCache<IProject, List<JdtProjectContentProvider>>() {
			@Override
			protected List<JdtProjectContentProvider> create(IProject key) {
				return new CopyOnWriteArrayList<JdtProjectContentProvider>();
			}
		};

		//
		_instance = this;

		//
		_listener = new InternalResourceChangedListener(_project2provider);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(_listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(_listener);
	}
}
