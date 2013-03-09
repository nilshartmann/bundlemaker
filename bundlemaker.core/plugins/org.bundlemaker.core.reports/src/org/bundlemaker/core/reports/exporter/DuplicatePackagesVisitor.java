package org.bundlemaker.core.reports.exporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.util.collections.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DuplicatePackagesVisitor extends IAnalysisModelVisitor.Adapter {

  /** - */
  private Map<String, IPackageArtifact>                      _packages;

  /** - */
  private GenericCache<String, Collection<IPackageArtifact>> _duplicatePackages;

  /**
   * <p>
   * Creates a new instance of type {@link DuplicatePackagesVisitor}.
   * </p>
   */
  @SuppressWarnings("serial")
  public DuplicatePackagesVisitor() {

    //
    _packages = new HashMap<String, IPackageArtifact>();

    //
    _duplicatePackages = new GenericCache<String, Collection<IPackageArtifact>>() {
      @Override
      protected Collection<IPackageArtifact> create(String key) {
        return new LinkedList<IPackageArtifact>();
      }
    };

  }

  /**
   * <p>
   * Returns <code>true</code>, if duplicate packages were found, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code>, if duplicate packages were found, <code>false</code> otherwise.
   */
  public boolean hasDuplicatePackages() {
    return !_duplicatePackages.isEmpty();
  }

  /**
   * <p>
   * Returns the map of all duplicate packages. If no duplicate packages were found, an empty map will be returned.
   * </p>
   * 
   * @return the map of all duplicate packages (may empty) .
   */
  public Map<String, Collection<IPackageArtifact>> getDuplicatePackages() {
    return _duplicatePackages;
  }

  /**
   * Returns all providers for the specified package or null if either the package is not known or not a duplicate
   * package
   * <p>
   * </p>
   * 
   * @param packageName
   * @return a collection with at least two IPackageArtifacts or null
   */
  public Collection<IPackageArtifact> getDuplicatePackageProvider(String packageName) {
    return _duplicatePackages.get(packageName);
  }

  /**
   * <p>
   * Returns a map with all packages that exist only once in the system.
   * </p>
   * 
   * @return a map with all packages that exist only once in the system.
   */
  public Map<String, IPackageArtifact> getSinglePackages() {
    return _packages;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IPackageArtifact packageArtifact) {

    // return if package is virtual
    if (packageArtifact.isVirtual()) {
      return false;
    }

    // return if package is the default package
    if (packageArtifact.getQualifiedName().isEmpty()) {
      return true;
    }

    // return if package is the META-INF
    if (!packageArtifact.containsTypes()) {
      return true;
    }

    //
    if (_packages.containsKey(packageArtifact.getQualifiedName())) {

      // remove...
      IPackageArtifact firstPackageArtifact = _packages.remove(packageArtifact.getQualifiedName());

      // ...and add
      Collection<IPackageArtifact> artifacts = _duplicatePackages.getOrCreate(packageArtifact.getQualifiedName());
      artifacts.add(firstPackageArtifact);
      artifacts.add(packageArtifact);
    }

    //
    else if (_duplicatePackages.containsKey(packageArtifact.getQualifiedName())) {

      // add
      Collection<IPackageArtifact> artifacts = _duplicatePackages.getOrCreate(packageArtifact.getQualifiedName());
      artifacts.add(packageArtifact);
    }

    //
    else {
      _packages.put(packageArtifact.getQualifiedName(), packageArtifact);
    }

    // return true
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(IModuleArtifact moduleArtifact) {
    return !moduleArtifact.isVirtual();
  }
}