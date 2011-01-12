package org.bundlemaker.core.internal.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.parser.IParserFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ParserFactoryRegistry implements IExtensionChangeHandler {

	/** - */
	public static final String EXTENSION_POINT_ID = "org.bundlemaker.core.parserfactory";

	/** - */
	private ExtensionTracker _tracker;

	/** - */
	private List<IParserFactory> _parserFactories;

	/**
	 * <p>
	 * </p>
	 */
	public void initialize() {

		//
		_parserFactories = new LinkedList<IParserFactory>();

		// get the extension registry
		IExtensionRegistry registry = RegistryFactory.getRegistry();

		// get the extension points
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint(EXTENSION_POINT_ID);

		// get the extension tracker
		_tracker = new ExtensionTracker(registry);

		// 
		for (IExtension extension : extensionPoint.getExtensions()) {
			addExtension(_tracker, extension);
		}

		// register IExtensionChangeHandler
		_tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(extensionPoint));
	}

	/**
	 * <p>
	 * </p>
	 */
	public void dispose() {
		_tracker.unregisterHandler(this);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<IParserFactory> getParserFactories() {
		return Collections.unmodifiableList(_parserFactories);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addExtension(IExtensionTracker tracker, IExtension extension) {

		try {

			IParserFactory parserFactory = createParserFactoryFromExtension(extension);
			parserFactory.initialize();

			_tracker.registerObject(extension, parserFactory,
					IExtensionTracker.REF_STRONG);

			// the parser factories
			_parserFactories.add(parserFactory);

		} catch (CoreException e) {
			// 
		}
	}

	public void removeExtension(IExtension extension, Object[] objects) {

		for (Object object : objects) {
			IParserFactory parserFactory = (IParserFactory) object;
			parserFactory.dispose();
			_parserFactories.remove(parserFactory);
			_tracker.unregisterObject(extension, parserFactory);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param extension
	 * @return
	 * @throws CoreException
	 */
	private IParserFactory createParserFactoryFromExtension(IExtension extension)
			throws CoreException {

		// 
		IConfigurationElement actionElement = extension
				.getConfigurationElements()[0];

		//
		IParserFactory result = (IParserFactory) actionElement
				.createExecutableExtension("class");

		//
		return result;
	}
}