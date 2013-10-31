package org.bundlemaker.core.jtype.internal;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.common.collections.SymetricGenericCache;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeArtifact;
import org.bundlemaker.core.jtype.ITypeModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.bundlemaker.core.spi.modext.IAnalysisModelContext;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeArtifactCache extends
		SymetricGenericCache<TypeKey, ITypeArtifact> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** - */
	private IAnalysisModelContext _context;

	/**
	 * <p>
	 * Creates a new instance of type {@link TypeArtifactCache}.
	 * </p>
	 * 
	 * @param context
	 */
	public TypeArtifactCache(IAnalysisModelContext context) {
		Assert.isNotNull(context);

		//
		_context = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ITypeArtifact create(TypeKey type) {

		Assert.isNotNull(type);

		// step 1: if the type contains a 'real' type, we have to create a real
		// type artifact...
		if (type.hasType()) {
			return createTypeArtifactFromType(type.getType());
		}

		// step 2: ...otherwise we have to create a 'virtual' one
		else {
			return createTypeArtifactFromTypeName(type.getTypeName());
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeName
	 * @return
	 */
	private ITypeArtifact createTypeArtifactFromTypeName(String typeName) {

		//
		IBundleMakerArtifact parent = null;

		//
		int index = typeName.lastIndexOf('.');

		//
		if (index != -1) {

			// get the module package
			// get the parent
			parent = _context.getOrCreatePackage("<< Missing Types >>",
					typeName.substring(0, index));

		} else {
			parent = _context.getOrCreateModuleArtifact("<< Missing Types >>");
		}

		//
		return new VirtualType2IArtifact(typeName.substring(index + 1),
				typeName, parent);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	private ITypeArtifact createTypeArtifactFromType(IType type) {

		//
		IBundleMakerArtifact parent = getTypeParent(type);

		//
		return new AdapterType2IArtifact(type, this, parent);
	}

	/**
	 * <p>
	 * Returns the parent (package or resource) artifact for the given type.
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	private IBundleMakerArtifact getTypeParent(IType type) {

		Assert.isNotNull(type);

		// get the associated resources
		IModuleResource resource = null;

		resource = _context.getConfiguration().getContentType()
				.equals(ResourceType.SOURCE)
				&& type.hasSourceResource() ? type.getSourceResource() : type
				.getBinaryResource();

		// get the associated module
		IModule module = resource != null ? resource.getModule(_context
				.getModularizedSystem()) : type.getModule(_context
				.getModularizedSystem());

		if (resource != null && module.isResourceModule()) {

			if (resource == null) {
				System.out.println("Type without resource: " + type);
			}

			// force cast
			return (AbstractArtifactContainer) _context
					.getOrCreateResource(resource);

		} else {

			// get the parent
			return _context.getOrCreatePackage(module, type.getPackageName());
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	public final ITypeArtifact getTypeArtifact(IType type,
			boolean createIfMissing) {
		Assert.isNotNull(type);

		//
		try {
			if (createIfMissing) {
				return getOrCreate(new TypeKey(type));
			} else {
				return get(new TypeKey(type));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(type);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @throws Exception
	 */
	public final ITypeArtifact getTypeArtifact(String fullyQualifiedName,
			boolean createIfMissing) {

		//
		IType targetType = _context.getModularizedSystem()
				.adaptAs(ITypeModularizedSystem.class)
				.getType(fullyQualifiedName);

		//
		if (targetType == null) {
			if (createIfMissing) {
				return getOrCreate(new TypeKey(fullyQualifiedName));
			} else {
				return get(new TypeKey(fullyQualifiedName));
			}
		} else {
			return getTypeArtifact(targetType, createIfMissing);
		}
	}
}