package org.bundlemaker.core.store.db4o.internal;

import org.bundlemaker.core.internal.resource.Reference;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.osgi.Db4oService;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPersistentDependencyStore implements
		IPersistentDependencyStore {

	/** the db4o service */
	private Db4oService _db4oService;

	/** the (absolute) file name of the database file */
	private String _fileName;

	/** the db4o based database */
	private ObjectContainer _database;

	/** indicates if the store is initialized */
	private boolean _isInitialized = false;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractPersistentDependencyStore}.
	 * </p>
	 * 
	 * @param db4oService
	 *            the db4o service
	 * @param fileName
	 *            the file name
	 */
	public AbstractPersistentDependencyStore(Db4oService db4oService,
			String fileName) {

		Assert.isNotNull(db4oService);
		Assert.isNotNull(fileName);

		_db4oService = db4oService;
		_fileName = fileName;
	}

	/**
	 * <p>
	 * Initializes the {@link IPersistentDependencyStore}.
	 * </p>
	 */
	public final void init() {

		// create a new configuration
		Configuration configuration = Db4o.newConfiguration();

		// set cascade on update
		configuration.objectClass(Resource.class).cascadeOnUpdate(true);
		configuration.objectClass(Reference.class).cascadeOnUpdate(true);
		configuration.objectClass(ResourceKey.class).cascadeOnUpdate(
				true);

		// set cascade on activation
		configuration.objectClass(Resource.class).cascadeOnActivate(true);
		configuration.objectClass(Reference.class).cascadeOnActivate(true);
		configuration.objectClass(ResourceKey.class).cascadeOnActivate(
				true);

		// set the activation depth
		configuration.activationDepth(10);

		// open file
		_database = _db4oService.openFile(configuration, _fileName);

		// set initialized
		_isInitialized = true;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if this store is initialized.
	 * </p>
	 * 
	 * @return <code>true</code> if this store is initialized.
	 */
	public final boolean isInitialized() {
		return _isInitialized;
	}

	/**
	 * <p>
	 * Disposes the dependency store.
	 * </p>
	 */
	public final void dispose() {

		// commit all changes
		_database.commit();

		// close the database
		_database.close();

		// set initialized to false
		_isInitialized = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void commit() {
		Assert.isTrue(isInitialized(),
				"The persistent dependency store has not been initialized.");

		// commits the database
		_database.commit();
	}

	/**
	 * <p>
	 * Returns the database.
	 * </p>
	 * 
	 * @return the database.
	 */
	protected final ObjectContainer getDatabase() {
		return _database;
	}
}
