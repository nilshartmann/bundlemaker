package org.bundlemaker.itest.spring.experimental;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.PatternUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PatternBasedTypeSelector implements ITypeSelector {

  /** - */
  private Set<IModuleIdentifier> _sourceModuleIdentifier;

  /** - */
  private String[]               _includes;

  /** - */
  private String[]               _excludes;

  /** - */
  private ModuleIdentifier       _selectedModuleIdentifier;

  /**
   * <p>
   * Creates a new instance of type {@link PatternBasedTypeSelector}.
   * </p>
   * 
   * @param includes
   * @param excludes
   */
  public PatternBasedTypeSelector(String[] typeIncludes, String[] typeExcludes,
      ModuleIdentifier selectedModuleIdentifier) {

    //
    this(null, typeIncludes, typeExcludes, selectedModuleIdentifier);
  }

  /**
   * <p>
   * Creates a new instance of type {@link PatternBasedTypeSelector}.
   * </p>
   * 
   * @param typeIncludes
   * @param typeExcludes
   * @param name
   * @param version
   */
  public PatternBasedTypeSelector(String[] typeIncludes, String[] typeExcludes, String name, String version) {
    this(typeIncludes, typeExcludes, new ModuleIdentifier(name, version));
  }

  /**
   * <p>
   * Creates a new instance of type {@link PatternBasedTypeSelector}.
   * </p>
   * 
   * @param sourceModuleIdentifier
   * @param typeIncludes
   * @param typeExcludes
   * @param moduleIdentifier
   */
  public PatternBasedTypeSelector(ModuleIdentifier sourceModuleIdentifier, String[] typeIncludes,
      String[] typeExcludes, ModuleIdentifier selectedModuleIdentifier) {

    Assert.isNotNull(selectedModuleIdentifier);

    _sourceModuleIdentifier = new HashSet<IModuleIdentifier>();

    if (sourceModuleIdentifier != null) {
      _sourceModuleIdentifier.add(sourceModuleIdentifier);
    }

    _includes = typeIncludes != null ? typeIncludes : new String[] {};
    _excludes = typeExcludes != null ? typeExcludes : new String[] {};
    _selectedModuleIdentifier = selectedModuleIdentifier;
  }

  /**
   * <p>
   * Creates a new instance of type {@link PatternBasedTypeSelector}.
   * </p>
   * 
   * @param sourceName
   * @param sourceVersion
   * @param typeIncludes
   * @param typeExcludes
   * @param targetName
   * @param targetVersion
   */
  public PatternBasedTypeSelector(String sourceName, String sourceVersion, String[] typeIncludes,
      String[] typeExcludes, String targetName, String targetVersion) {

    this(new ModuleIdentifier(sourceName, sourceVersion), typeIncludes, typeExcludes, new ModuleIdentifier(targetName,
        targetVersion));
  }

  /**
   * {@inheritDoc}
   */
  public boolean matchesAllModules() {
    return _sourceModuleIdentifier.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  public Set<IModuleIdentifier> getSourceModules() {
    return _sourceModuleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  public IType selectType(IResourceModule referencingModule, String fullyQualifiedName, Set<IType> types) {

    Assert.isTrue(referencingModule.hasModularizedSystem());

    //
    if (!_sourceModuleIdentifier.isEmpty()
        && !_sourceModuleIdentifier.contains(referencingModule.getModuleIdentifier())) {
      return null;
    }

    //
    boolean included = false;

    //
    for (String include : _includes) {
      if (fullyQualifiedName.matches(PatternUtils.convertAntStylePattern(include))) {
        included = true;
      }
    }

    //
    if (!included) {
      return null;
    }

    //
    for (String exclude : _excludes) {
      if (fullyQualifiedName.matches(PatternUtils.convertAntStylePattern(exclude))) {
        return null;
      }
    }

    //
    for (IType iType : types) {
      if (iType.getModule(referencingModule.getModularizedSystem()).getModuleIdentifier()
          .equals(_selectedModuleIdentifier)) {
        return iType;
      }
    }

    //
    return null;
  }
}
