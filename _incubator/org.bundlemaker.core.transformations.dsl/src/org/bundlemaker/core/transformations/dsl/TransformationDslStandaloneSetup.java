
package org.bundlemaker.core.transformations.dsl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TransformationDslStandaloneSetup extends TransformationDslStandaloneSetupGenerated{

	public static void doSetup() {
		new TransformationDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

