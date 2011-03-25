package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class ResourceAwareNameEnvironmentProxy implements INameEnvironment {

  /** the name environment */
  private INameEnvironment                          _nameEnvironment;

  /** - */
  private Map<String, IResource>                    _sources;

  /** - */
  private GenericCache<IResource, ICompilationUnit> _genericCache;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceAwareNameEnvironmentProxy}.
   * </p>
   * 
   * @param projectDescription
   */
  public ResourceAwareNameEnvironmentProxy(INameEnvironment nameEnvironment,
      IBundleMakerProjectDescription projectDescription) {
    Assert.isNotNull(nameEnvironment);
    Assert.isNotNull(projectDescription);

    //
    _nameEnvironment = nameEnvironment;

    //
    _sources = new HashMap<String, IResource>();

    //
    for (IFileBasedContent content : projectDescription.getFileBasedContent()) {

      if (content.isResourceContent() && !content.getSourceResources().isEmpty() && content.isAnalyzeSourceResources()) {

        for (IResource resource : content.getSourceResources()) {

          //
          if (!_sources.containsKey(resource.getPath())) {
            _sources.put(resource.getPath(), resource);
          }
        }
      }
    }

    //
    _genericCache = new GenericCache<IResource, ICompilationUnit>() {

      @Override
      protected ICompilationUnit create(IResource resource) {
        return new CompilationUnitImpl(resource, null);
      }
    };
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public ICompilationUnit getCompilationUnit(IResource resource) {
    return _genericCache.getOrCreate(resource);
  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[][] compoundTypeName) {

    // find class
    NameEnvironmentAnswer answer = findSourceFile(NameEnvironmentUtils.getAsString(compoundTypeName, '/'));

    //
    if (answer != null) {
      return answer;
    }

    //
    return _nameEnvironment.findType(compoundTypeName);

  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {

    // find class
    NameEnvironmentAnswer answer = findSourceFile(NameEnvironmentUtils.getAsString(typeName, packageName, '/')
        + ".java");

    //
    if (answer != null) {
      return answer;
    }

    //
    return _nameEnvironment.findType(typeName, packageName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPackage(char[][] parentPackageName, char[] packageName) {
    return _nameEnvironment.isPackage(parentPackageName, packageName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  private NameEnvironmentAnswer findSourceFile(String path) {

    //
    if (_sources.containsKey(path)) {
      return new NameEnvironmentAnswer(_genericCache.getOrCreate(_sources.get(path)), null);
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanup() {
    _nameEnvironment.cleanup();
  }
}
