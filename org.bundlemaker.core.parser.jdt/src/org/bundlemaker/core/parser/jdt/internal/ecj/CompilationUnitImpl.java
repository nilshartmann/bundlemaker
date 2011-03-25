package org.bundlemaker.core.parser.jdt.internal.ecj;

import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;

/**
 * <p>
 * Adapter class for providing java source files to the eclipse java compiler.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class CompilationUnitImpl implements ICompilationUnit {

  /** the constant JAVA_FILE_POSTFIX */
  private static final String JAVA_FILE_POSTFIX = ".java";

  /** - */
  private IResource           _resource;

  /** - */
  private char[]              _cachedContent;

  /** the file name, relative to the source folder */
  private char[]              _fileName;

  /** the name of the top level public type, e.g. {Hashtable} */
  private char[]              _mainTypeName;

  /** the name of the package , e.g. {java, lang} */
  private char[][]            _packageName;

  /**
   * <p>
   * Creates a new instance of type {@link CompilationUnitImpl}.
   * </p>
   * 
   * @param resource
   *          the source file
   */
  public CompilationUnitImpl(IResource resource, char[] content) {
    Assert.isNotNull(resource);

    // set the resource
    this._resource = resource;

    // set the content
    if (content != null) {
      this._cachedContent = content;
    } else {
      this._cachedContent = new String(resource.getContent()).toCharArray();
    }

    // set the file name
    this._fileName = this._resource.getPath().toCharArray();

    // set the package name
    String[] splittedPackageNames = this._resource.getPackageName().split("\\.");
    this._packageName = new char[splittedPackageNames.length][];
    for (int i = 0; i < splittedPackageNames.length; i++) {
      this._packageName[i] = splittedPackageNames[i].toCharArray();
    }

    // set the type name
    this._mainTypeName = this._resource.getName()
        .substring(0, this._resource.getName().length() - JAVA_FILE_POSTFIX.length()).toCharArray();
  }

  /**
   * {@inheritDoc}
   */
  public final char[] getMainTypeName() {
    return this._mainTypeName;
  }

  /**
   * {@inheritDoc}
   */
  public final char[][] getPackageName() {
    return this._packageName;
  }

  /**
   * {@inheritDoc}
   */
  public final char[] getFileName() {
    return this._fileName;
  }

  /**
   * {@inheritDoc}
   */
  public final char[] getContents() {
    return _cachedContent;
  }
}