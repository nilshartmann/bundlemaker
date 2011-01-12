/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.bundlemaker.core.model.transformation.impl;

import org.bundlemaker.core.internal.model.transformation.BasicProjectContentTransformationImplDelegate;
import org.bundlemaker.core.model.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.model.transformation.TransformationPackage;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Basic Project Content Transformation</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class BasicProjectContentTransformationImpl extends EObjectImpl
		implements BasicProjectContentTransformation {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BasicProjectContentTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformationPackage.Literals.BASIC_PROJECT_CONTENT_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void apply(ModularizedSystem modularizedSystem) {

		BasicProjectContentTransformationImplDelegate.apply(this,
				modularizedSystem);
	}

} // BasicProjectContentTransformationImpl
