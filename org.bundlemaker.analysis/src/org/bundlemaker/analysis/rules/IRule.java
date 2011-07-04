package org.bundlemaker.analysis.rules;

import org.bundlemaker.analysis.model.IDependency;

/**
 * <p>Das Interface einer Regel
 * 
 * <p>Regeln sind von der Architektur vorgegebene Ordnungen, die
 * bei nicht Einhaltung einen Regelverstoﬂ hervorbringen
 * 
 * @author Kai Lehmann
 *
 */
public interface IRule {

	
	public void setName(String name);
	
	public String getName();

	/**
	 * Prueft, ob die uebergebene Abhaengigkeit gegen die Regel verstoeﬂt
	 * @param dependency die Abhaengigkeit
	 * @return <code>true</bode>, falls die Abhaengigkeit gegen die Regel verstoest. 
	 * Ansonsten <code>false</code>
	 */
	public boolean isViolatedBy(IDependency dependency);
	
	public Violation getViolation();
	
}
