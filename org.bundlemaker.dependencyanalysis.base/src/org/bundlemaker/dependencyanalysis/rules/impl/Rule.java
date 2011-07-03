package org.bundlemaker.dependencyanalysis.rules.impl;

import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.rules.Violation;
import org.bundlemaker.dependencyanalysis.rules.IRule;

public abstract class Rule implements IRule{
	
	private String name;
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public abstract boolean isViolatedBy(IDependency dependency);
	
	public abstract Violation getViolation();
	
}
