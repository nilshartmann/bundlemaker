package org.bundlemaker.analysis.model;


/**
 * <p>
 * </p>
 *
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public interface DependencyVisitor {
	public void visit( IDependency dependency);
	
	public void addIgnoreDependency( IDependency dependency );
	
	public void reload();
}
