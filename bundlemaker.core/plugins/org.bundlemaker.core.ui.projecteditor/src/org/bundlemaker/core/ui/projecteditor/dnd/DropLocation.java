package org.bundlemaker.core.ui.projecteditor.dnd;

/**
 * Represents the relative location to the target element in a drop operation
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public enum DropLocation {

  before, on, after, nothing;

  public static DropLocation getDropLocation(int location) {

    switch (location) {
    case 1:
      return before;
    case 2:
      return after;
    case 3:
      return on;
    case 4:
      return nothing;
    default:
    }

    throw new IllegalArgumentException("Drop location '" + location + "' unknown");
  }

}
