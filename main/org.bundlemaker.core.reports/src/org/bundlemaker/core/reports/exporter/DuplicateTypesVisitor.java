package org.bundlemaker.core.reports.exporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.util.collections.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DuplicateTypesVisitor extends IAnalysisModelVisitor.Adapter {

  /** helper list to store types */
  private Map<String, ITypeArtifact>                      _types;

  /** - */
  private GenericCache<String, Collection<ITypeArtifact>> _duplicateTypes;

  /**
   * <p>
   * Creates a new instance of type {@link DuplicateTypesVisitor}.
   * </p>
   */
  @SuppressWarnings("serial")
  public DuplicateTypesVisitor() {

    //
    _types = new HashMap<String, ITypeArtifact>();

    //
    _duplicateTypes = new GenericCache<String, Collection<ITypeArtifact>>() {
      @Override
      protected Collection<ITypeArtifact> create(String key) {
        return new LinkedList<ITypeArtifact>();
      }
    };
  }

  /**
   * <p>
   * Returns the map of all duplicate types. If no duplicate types were found, an empty map will be returned.
   * </p>
   * 
   * @return the map of all duplicate types (may empty) .
   */
  public Map<String, Collection<ITypeArtifact>> getDuplicateTypes() {
    return _duplicateTypes;
  }

  /**
   * <p>
   * Returns <code>true</code>, if duplicate types were found, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code>, if duplicate types were found, <code>false</code> otherwise.
   */
  public boolean hasDuplicateTypes() {
    return !_duplicateTypes.isEmpty();
  }

  /**
   * <p>
   * Returns a map with all types that exist only once in the system.
   * </p>
   * 
   * @return a map with all types that exist only once in the system.
   */
  public Map<String, ITypeArtifact> getSingleTypes() {
    return _types;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(ITypeArtifact typeArtifact) {

    // return if package is virtual
    if (typeArtifact.isVirtual()) {
      return false;
    }

    //
    if (_types.containsKey(typeArtifact.getQualifiedName())) {

      // remove...
      ITypeArtifact firstTypeArtifact = _types.remove(typeArtifact.getQualifiedName());

      // ...and add
      Collection<ITypeArtifact> artifacts = _duplicateTypes.getOrCreate(typeArtifact.getQualifiedName());
      artifacts.add(firstTypeArtifact);
      artifacts.add(typeArtifact);
    }

    //
    else if (_duplicateTypes.containsKey(typeArtifact.getQualifiedName())) {

      // add
      Collection<ITypeArtifact> artifacts = _duplicateTypes.getOrCreate(typeArtifact.getQualifiedName());
      artifacts.add(typeArtifact);
    }

    //
    else {
      _types.put(typeArtifact.getQualifiedName(), typeArtifact);
    }

    //
    return false;
  }
}