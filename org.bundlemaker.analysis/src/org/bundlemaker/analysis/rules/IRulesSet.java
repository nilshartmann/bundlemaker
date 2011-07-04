package org.bundlemaker.analysis.rules;

import org.bundlemaker.analysis.model.IDependency;


public interface IRulesSet {
	
	
	public void addRule(IRule rule);
	
	public IRule getRule(String name);
	
	public void check(IDependency dependency);
	
	

}
