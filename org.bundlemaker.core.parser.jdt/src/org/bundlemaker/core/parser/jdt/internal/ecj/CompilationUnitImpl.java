package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.io.File;
import java.util.StringTokenizer;

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
public class CompilationUnitImpl implements ICompilationUnit {

  /** the constant JAVA_FILE_POSTFIX */
  private static final String JAVA_FILE_POSTFIX = ".java";

  /** - */
  private IResource           _sourceFile;

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
   * @param sourceFile
   *          the source file
   */
  public CompilationUnitImpl(IResource sourceFile) {
    Assert.isNotNull(sourceFile);

    this._sourceFile = sourceFile;

    this._fileName = this._sourceFile.getPath().toCharArray();

    // compute qualified name
    String qualifiedTypeName = getQualifiedTypeName(this._sourceFile.getPath());

    // compute package and main type name
    int v = qualifiedTypeName.lastIndexOf('.');
    this._mainTypeName = qualifiedTypeName.substring(v + 1).toCharArray();
    if ((v > 0) && (v < qualifiedTypeName.length())) {
      String packageName = qualifiedTypeName.substring(0, v);
      StringTokenizer packages = new StringTokenizer(packageName, ".");
      this._packageName = new char[packages.countTokens()][];
      for (int i = 0; i < this._packageName.length; i++) {
        this._packageName[i] = packages.nextToken().toCharArray();
      }
    } else {
      this._packageName = new char[0][];
    }
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

    if (_cachedContent == null) {
      _cachedContent = new String(_sourceFile.getContent()).toCharArray();
    }

    return _cachedContent;
  }

  /**
   * <p>
   * Returns the qualified type name for the given type name.
   * </p>
   * 
   * @param fileName
   *          the file name to resolve
   * @return the qualified type name for the given type name.
   */
  private String getQualifiedTypeName(String fileName) {
    if (fileName.toLowerCase().endsWith(JAVA_FILE_POSTFIX)) {
      return fileName.substring(0, fileName.length() - 5).replace(File.separatorChar, '.');
    } else {
      return fileName.replace(File.separatorChar, '.');
    }
  }
}