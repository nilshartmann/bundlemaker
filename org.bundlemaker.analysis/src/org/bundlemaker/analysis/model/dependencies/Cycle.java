package org.bundlemaker.analysis.model.dependencies;

import java.util.Stack;

import org.bundlemaker.analysis.model.IDependency;

public class Cycle implements Cloneable {

  private Stack<IDependency> dependencies;

  public Cycle() {
    dependencies = new Stack<IDependency>();
  }

  public Cycle(Cycle cycle) {
    this();
    this.dependencies.addAll(cycle.getDependencies());
  }

  public boolean addDependency(IDependency dependency) {
    if (dependencies.isEmpty() || dependencies.peek().getTo().equals(dependency.getFrom())) {
      dependencies.add(dependency);
      return true;
    }

    return false;
  }

  /**
   * @return the dependencies
   */
  public Stack<IDependency> getDependencies() {
    return dependencies;
  }

  public boolean isCycle() {

    Stack<IDependency> stackNew = new Stack<IDependency>();
    stackNew.addAll(this.dependencies);

    IDependency lastDependency = stackNew.pop();
    IDependency firstDependency = null;

    while (!stackNew.isEmpty()) {
      firstDependency = stackNew.pop();
    }

    return firstDependency != null && lastDependency.getTo().equals(firstDependency.getFrom());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    for (IDependency dependency : dependencies) {
      result += dependency.hashCode();
    }

    return result;
  }

  @Override
  public Object clone() {

    return new Cycle(this);

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Cycle other = (Cycle) obj;

    return dependencies.size() == other.getDependencies().size() && dependencies.containsAll(other.getDependencies());
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n");
    sb.append("Cycle: [");

    for (IDependency dependency : dependencies) {
      sb.append(dependency.toString());
      sb.append(", ");
    }
    sb.append(" ]");

    return sb.toString();
  }

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
