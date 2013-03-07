package org.bundlemaker.core.analysis;

/**
 * <p>
 * </p>
 * 
 */
@Deprecated
// TODO REPLACE WITH ATTRIBUTES IN IDependency
public enum DependencyKind {
  USES, IMPLEMENTS, EXTENDS, ANNOTATES;

  public boolean isInheritance() {
    return this == IMPLEMENTS || this == EXTENDS;
  }
}
