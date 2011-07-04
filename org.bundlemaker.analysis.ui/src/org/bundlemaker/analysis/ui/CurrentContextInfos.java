package org.bundlemaker.analysis.ui;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;

public class CurrentContextInfos {
	private static List<IDependency> contextDependencies = new ArrayList<IDependency>();
	private static List<String> contextClassNames = new ArrayList<String>();
	private static List<String> contextBundleNames = new ArrayList<String>();
	
	public static void addContextClassName( String contextClassName ) {
		contextClassNames.add(contextClassName);
	}

	public static void clearContextClassNames( ) {
		contextClassNames.clear();
	}

	public static List<String> getContextClassNames() {
		return contextClassNames;
	}
	
	public static void addContextBundleName( String contextBundleName ) {
		contextBundleNames.add(contextBundleName);
	}
	
	public static void clearContextBundleNames( ) {
		contextBundleNames.clear();
	}

	public static List<String> getContextBundleNames() {
		return contextBundleNames;
	}
	
	public static void addContextDependency( IDependency contextDependency ) {
		contextDependencies.add(contextDependency);
	}

	public static void clearContextDependencies( ) {
		contextDependencies.clear();
	}

	public static List<IDependency> getContextDependencies() {
		return contextDependencies;
	}
}
