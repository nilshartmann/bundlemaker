package org.bundlemaker.core.modules;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencedModulesQueryResult implements
		IReferencedModulesQueryResult {

	/** - */
	private Map<String, ITypeModule> _referencedModules;

	/** - */
	private Map<String, List<ITypeModule>> _typesWithAmbiguousModules;

	/** - */
	private List<String> _missingTypes;

	/**
	 * <p>
	 * Creates a new instance of type {@link ReferencedModulesQueryResult}.
	 * </p>
	 */
	public ReferencedModulesQueryResult() {

		//
		_referencedModules = new HashMap<String, ITypeModule>();

		//
		_typesWithAmbiguousModules = new HashMap<String, List<ITypeModule>>();

		//
		_missingTypes = new LinkedList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasErrors() {

		//
		return _missingTypes.isEmpty() && _typesWithAmbiguousModules.isEmpty();
	}

	@Override
	public Map<String, ITypeModule> getReferencedModulesMap() {

		//
		return _referencedModules;
	}

	@Override
	public List<ITypeModule> getReferencedModules() {

		//
		return new LinkedList<ITypeModule>(_referencedModules.values());
	}

	@Override
	public boolean hasTypesWithAmbiguousModules() {

		//
		return !_typesWithAmbiguousModules.isEmpty();
	}

	@Override
	public Map<String, List<ITypeModule>> getTypesWithAmbiguousModules() {

		//
		return _typesWithAmbiguousModules;
	}

	@Override
	public boolean hasMissingTypes() {
		//
		return !_missingTypes.isEmpty();
	}

	@Override
	public List<String> getMissingTypes() {

		//
		return _missingTypes;
	}
}
