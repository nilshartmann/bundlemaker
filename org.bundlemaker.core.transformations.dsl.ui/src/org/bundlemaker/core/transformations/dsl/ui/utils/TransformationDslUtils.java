package org.bundlemaker.core.transformations.dsl.ui.utils;

import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;
import org.bundlemaker.core.transformations.dsl.ui.internal.TransformationDslActivator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;


public class TransformationDslUtils {

	public static TransformationModel parse(String uri) throws Exception {
		
		System.out.println("Reading: '" + uri + "'");
		
		Injector injector = TransformationDslActivator.getInstance().getInjector("org.bundlemaker.core.transformations.dsl.TransformationDsl");
		
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.getResource(URI.createURI(uri), true);
		TransformationModel model = (TransformationModel) resource.getContents().get(0);
		
		System.out.println("model: " + model);
		
		return model;
	}
	
	public static void register(Injector injector) {
		if (!EPackage.Registry.INSTANCE.containsKey("http://www.bundlemaker.org/core/transformations/dsl/TransformationDsl")) {
			EPackage.Registry.INSTANCE.put("http://www.bundlemaker.org/core/transformations/dsl/TransformationDsl", org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage.eINSTANCE);
		}

			org.eclipse.xtext.resource.IResourceFactory resourceFactory = injector.getInstance(org.eclipse.xtext.resource.IResourceFactory.class);
			org.eclipse.xtext.resource.IResourceServiceProvider serviceProvider = injector.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bmt", resourceFactory);
			org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("bmt", serviceProvider);
			




		}
	
}
