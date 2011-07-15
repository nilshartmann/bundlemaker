package org.bundlemaker.analysis.rules;

import org.bundlemaker.analysis.model.IDependency;

/**
 * <p>
 * Defines an architecture rule.
 * </p>
 * 
 * @author Kai Lehmann
 * 
 */
public interface IRule {

  public void setName(String name);

  public String getName();

  /**
   * Prueft, ob die uebergebene Abhaengigkeit gegen die Regel verstoeﬂt
   * 
   * @param dependency
   *          die Abhaengigkeit
   * @return <code>true</bode>, falls die Abhaengigkeit gegen die Regel verstoest. 
   * Ansonsten <code>false</code>
   */
  public boolean isViolatedBy(IDependency dependency);

  public Violation getViolation();

}
