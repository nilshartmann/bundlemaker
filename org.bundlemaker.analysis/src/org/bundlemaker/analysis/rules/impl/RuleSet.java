package org.bundlemaker.analysis.rules.impl;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.rules.IRule;
import org.bundlemaker.analysis.rules.IRulesSet;

public class RuleSet implements IRulesSet {

	private Map<String, IRule> rules;
	
	public RuleSet(){
		this.rules = new HashMap<String, IRule>();
	}
	
	@Override
	public void addRule(IRule rule) {
		rules.put(rule.getName(), rule);

	}

	@Override
	public IRule getRule(String name) {
		return rules.get(name);
	}

	@Override
	public void check(IDependency dependency) {
		for(IRule rule : rules.values()){
			if(!rule.isViolatedBy(dependency)){
				dependency.addViolation(rule.getViolation());
			}
		}
	}

}
