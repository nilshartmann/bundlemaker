package org.bundlemaker.analysis.rules.impl;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.rules.IRule;
import org.bundlemaker.analysis.rules.Violation;

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
