package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ITypeContainer;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.NameQueryFilters;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Abstract base class for all modules.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <I>
 * @param <T>
 */
public abstract class AbstractModule<I extends ITypeContainer, T extends I>
		implements IModule {

	/** the module identifier */
	private IModuleIdentifier _moduleIdentifier;

	/** the classification */
	private IPath _classification;

	/** the user attributes */
	private Map<String, Object> _userAttributes;

	/** the self container */
	private T _selfContainer;

	/** the embedded container */
	private Map<String, T> _embeddedContainers;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractModule}.
	 * </p>
	 * 
	 * @param moduleIdentifier
	 * @param selfContainer
	 */
	public AbstractModule(IModuleIdentifier moduleIdentifier, T selfContainer) {
		Assert.isNotNull(moduleIdentifier);
		Assert.isNotNull(selfContainer);

		// set the parameters
		_moduleIdentifier = moduleIdentifier;
		_selfContainer = selfContainer;

		// create the hash map
		_userAttributes = new HashMap<String, Object>();
		_embeddedContainers = new HashMap<String, T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IModuleIdentifier getModuleIdentifier() {
		return _moduleIdentifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPath getClassification() {
		return _classification;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasClassification() {
		return _classification != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getUserAttributes() {
		return _userAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedTypeNames() {
		return getContainedTypeNames(NameQueryFilters.TRUE_QUERY_FILTER);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param typeNames
	 * @return
	 */
	public boolean containsAll(Set<String> typeNames) {

		try {
			for (String typeName : typeNames) {
				if (getType(typeName) == null) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedTypeNames(final IQueryFilter<String> filter) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<T>() {
			@Override
			public boolean doWithContainer(T resourceContainer) {
				result.addAll(resourceContainer.getContainedTypeNames(filter));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IType getType(final String fullyQualifiedName) {

		// create the result set
		final IType[] result = new IType[1];

		//
		doWithAllContainers(new ContainerClosure<T>() {
			@Override
			public boolean doWithContainer(T resourceContainer) {

				result[0] = resourceContainer.getType(fullyQualifiedName);

				return result[0] == null;
			}
		});

		// return the result
		return result[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IType> getContainedTypes() {

		// create the result set
		final Set<IType> result = new HashSet<IType>();

		//
		doWithAllContainers(new ContainerClosure<T>() {
			@Override
			public boolean doWithContainer(T resourceContainer) {
				result.addAll(resourceContainer.getContainedTypes());
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedPackageNames() {
		return getContainedPackageNames(NameQueryFilters.TRUE_QUERY_FILTER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getContainedPackageNames(
			final IQueryFilter<String> filter) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<T>() {
			@Override
			public boolean doWithContainer(T resourceContainer) {
				result.addAll(resourceContainer
						.getContainedPackageNames(filter));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public T getModifiableSelfResourceContainer() {
		return _selfContainer;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, T> getEmbeddedContainers() {
		return _embeddedContainers;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param classification
	 */
	public void setClassification(IPath classification) {
		_classification = classification;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param closure
	 */
	protected void doWithAllContainers(ContainerClosure<T> closure) {

		// do with the 'self' container
		if (closure.doWithContainer(getModifiableSelfResourceContainer())) {
			return;
		}

		// do with the embedded containers
		for (T embeddedContainer : _embeddedContainers.values()) {

			//
			if (closure.doWithContainer(embeddedContainer)) {
				return;
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [_moduleIdentifier="
				+ _moduleIdentifier + ", _classification=" + _classification
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((_moduleIdentifier == null) ? 0 : _moduleIdentifier
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModule other = (AbstractModule) obj;
		if (_moduleIdentifier == null) {
			if (other._moduleIdentifier != null)
				return false;
		} else if (!_moduleIdentifier.equals(other._moduleIdentifier))
			return false;
		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
	 */
	protected interface ContainerClosure<T> {

		/**
		 * <p>
		 * </p>
		 * 
		 * @param container
		 */
		boolean doWithContainer(T container);
	}
}
