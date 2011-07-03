package org.bundlemaker.dependencyanalysis.rules;

import org.bundlemaker.dependencyanalysis.base.model.IDependency;


public interface IRulesSet {
	
	
	public void addRule(IRule rule);
	
	public IRule getRule(String name);
	
	public void check(IDependency dependency);
	
	

}
