package org.bundlemaker.core.transformations.selectors;

import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.util.FileUtils;

/**
 * <p>
 * Absstract base class for all pattern based selectors.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPatternBasedSelector {

  /** all the includes */
  private List<String> _includes;

  /** all the includes */
  private List<String> _excludes;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractPatternBasedSelector}.
   * </p>
   */
  public AbstractPatternBasedSelector() {

    // creates the lists
    _includes = new LinkedList<String>();
    _excludes = new LinkedList<String>();
  }

  /**
   * <p>
   * Returns the (modifiable) list of the 'includes' patterns.
   * </p>
   * 
   * @return
   */
  public List<String> getIncludes() {
    return _includes;
  }

  /**
   * <p>
   * Returns the (modifiable) list of the 'excludes' patterns.
   * </p>
   * 
   * @return
   */
  public List<String> getExcludes() {
    return _excludes;
  }

  /**
   * <p>
   * Returns <code>true</code>, if the given path is included, <code>false</code> otherwise.
   * </p>
   * 
   * @param path
   *          the path to test
   * @return <code>true</code>, if the given path is included, <code>false</code> otherwise.
   */
  public boolean isIncluded(String path) {

    // set the path
    path = FileUtils.normalize(path);

    // set 'included' to false
    boolean included = false;

    // get all includes
    for (String include : _includes) {
      if (SelectorUtils.matchPath(FileUtils.normalize(include), path)) {
        included = true;
        break;
      }
    }

    // return 'false' if not included yet
    if (!included) {
      return false;
    }

    // get all the excludes
    for (String exclude : _excludes) {
      if (SelectorUtils.matchPath(FileUtils.normalize(exclude), path)) {
        return false;
      }
    }

    // return true, as the resource module matches
    return true;
  }
}
