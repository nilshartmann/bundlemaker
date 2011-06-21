/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.parser.jdt.internal;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.jdt.CoreParserJdt;
import org.bundlemaker.core.parser.jdt.IJdtSourceParserHook;
import org.bundlemaker.core.parser.jdt.internal.ecj.IndirectlyReferencesAnalyzer;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtParser extends AbstractHookAwareJdtParser {

  /** the AST parser */
  private ASTParser                    _parser;

  /** the associated java project */
  private IJavaProject                 _javaProject;

  /** the indirectly references analyzer **/
  private IndirectlyReferencesAnalyzer _indirectlyReferencesAnalyzer;

  /** - */
  private boolean                      _parseIndirectReferences;

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public JdtParser(IBundleMakerProject bundleMakerProject, ExtensionRegistryTracker<IJdtSourceParserHook> hookRegistry,
      boolean parseIndirectReferences) throws CoreException {

    super(hookRegistry);

    Assert.isNotNull(bundleMakerProject);

    // create the AST parser
    _parser = ASTParser.newParser(AST.JLS3);

    // the associated java project
    _javaProject = JdtProjectHelper.getAssociatedJavaProject(bundleMakerProject);

    //
    _indirectlyReferencesAnalyzer = new IndirectlyReferencesAnalyzer(_javaProject,
        bundleMakerProject.getProjectDescription());

    //
    _parseIndirectReferences = parseIndirectReferences;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParserType getParserType() {
    return ParserType.SOURCE;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public List<IProblem> parse(IFileBasedContent content, List<IDirectory> directoryList, IResourceCache cache,
  // IProgressMonitor progressMonitor) throws CoreException {
  //
  // // create the error list
  // List<IProblem> _errors = new LinkedList<IProblem>();
  //
  // // parse the compilation units
  // if (content.isResourceContent() && !content.getSourceResources().isEmpty() && content.isAnalyzeSourceResources()) {
  //
  // _errors.addAll(parseCompilationUnits(directoryList, cache, content, progressMonitor));
  // }
  //
  // // return the errors
  // return _errors;
  // }
  //
  // @Override
  // public List<IProblem> parseResources(IFileBasedContent content, List<IResource> resources, IResourceCache cache,
  // IProgressMonitor _progressMonitor) throws CoreException {
  // return null;
  // }
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param progressMonitor
  // *
  // * @param compilationUnits
  // * @throws JavaModelException
  // */
  // private List<IProblem> parseCompilationUnits(List<IDirectory> directoryList, IResourceCache cache,
  // IFileBasedContent fileBasedContent, IProgressMonitor progressMonitor) throws CoreException {
  //
  // //
  // List<IProblem> problems = new LinkedList<IProblem>();
  //
  // //
  // for (IDirectory directory : directoryList) {
  //
  // //
  // if (!directory.getDirectoryName().equals(new Path("META-INF")) && directory.hasSourceContent()) {
  //
  // //
  // for (IDirectoryFragment directoryFragment : directory.getSourceDirectoryFragments()) {
  //
  // //
  // for (IResourceKey resourceKey : directoryFragment.getResourceKeys()) {
  //
  // //
  // parseResource(resourceKey, cache, problems);
  //
  // progressMonitor.worked(1);
  // }
  // }
  // }
  // }
  //
  // //
  // return problems;
  // }

  @SuppressWarnings("unchecked")
  /**
   * {@inheritDoc}
   */
  @Override
  public void parseResource(IFileBasedContent fileBasedContent, IResourceKey resourceKey, IResourceCache cache) {

    //
    if (!canParse(resourceKey)) {
      return;
    }

    // get the modifiable resource
    IModifiableResource modifiableResource = cache.getOrCreateResource(resourceKey);

    try {

      // TODO configurable
      // @SuppressWarnings("rawtypes")
      // Map options = new HashMap();
      // options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
      // options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
      // options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);

      // _parser.setSource(iCompilationUnit);
      char[] content = new String(modifiableResource.getContent()).toCharArray();
      _parser.setProject(_javaProject);
      _parser.setSource(content);

      // TODO
      _parser.setUnitName("/" + _javaProject.getProject().getName() + "/" + modifiableResource.getPath());
      _parser.setCompilerOptions(CoreParserJdt.getCompilerOptionsWithComplianceLevel(null));
      _parser.setResolveBindings(true);

      CompilationUnit compilationUnit = (CompilationUnit) _parser.createAST(null);

      List<IProblem> problems = analyzeCompilationUnit(modifiableResource, compilationUnit);
      getProblems().addAll(problems);

      // set the primary type
      String primaryTypeName = JavaTypeUtils.convertToFullyQualifiedName(modifiableResource.getPath(), ".java");
      IType primaryType = modifiableResource.getType(primaryTypeName);
      modifiableResource.setPrimaryType((Type) primaryType);

      // step 3: compute the indirectly referenced types
      if (_parseIndirectReferences) {
        computeIndirectlyReferencedTypes(modifiableResource, content);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public boolean canParse(IResourceKey resourceKey) {
    return resourceKey.getPath().endsWith(".java");
  }

  /**
   * <p>
   * </p>
   * 
   * @param modifiableResource
   * @param content
   * @throws IOException
   */
  private void computeIndirectlyReferencedTypes(IModifiableResource modifiableResource, char[] content)
      throws IOException {

    // get all the referenced types (directly and indirectly)
    Set<String> directlyAndIndirectlyReferencedTypes = _indirectlyReferencesAnalyzer.getAllReferencedTypes(
        modifiableResource, content);

    // get all directly referenced types
    Set<String> directlyReferenced = new HashSet<String>();
    for (IReference reference : modifiableResource.getReferences()) {
      if (reference.getReferenceType().equals(ReferenceType.TYPE_REFERENCE)) {
        directlyReferenced.add(reference.getFullyQualifiedName());
      }
    }
    for (IType type : modifiableResource.getContainedTypes()) {
      for (IReference reference : type.getReferences()) {
        if (reference.getReferenceType().equals(ReferenceType.TYPE_REFERENCE)) {
          directlyReferenced.add(reference.getFullyQualifiedName());
        }
      }
    }

    // add only the indirectly referenced types
    for (String type : directlyAndIndirectlyReferencedTypes) {

      if (!directlyReferenced.contains(type)) {
        modifiableResource.recordReference(type, new ReferenceAttributes(ReferenceType.TYPE_REFERENCE, false, false,
            false, true, false, false, true));
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootMap
   * @param progressMonitor
   * 
   * @param entry
   * @param content
   * @throws JavaModelException
   */
  private List<IProblem> analyzeCompilationUnit(IModifiableResource modifiableResource, CompilationUnit compilationUnit)
      throws CoreException {

    // step 1: set the directly referenced types
    JdtAstVisitor visitor = new JdtAstVisitor(modifiableResource);
    compilationUnit.accept(visitor);

    // org.eclipse.jdt.core.IType primaryType = compilationUnit.getTypeRoot().findPrimaryType();
    // if (primaryType != null) {
    // Type type = (Type) modifiableResource.getType(primaryType.getFullyQualifiedName());
    // modifiableResource.setMainType(type);
    // } else {
    // // TODO
    // throw new RuntimeException(compilationUnit.toString());
    // }

    // step 2:
    callSourceParserHooks(modifiableResource, compilationUnit);

    List<IProblem> problems = new LinkedList<IProblem>();

    // step 4: add the errors to the error list
    for (IProblem problem : visitor.getProblems()) {

      // add errors
      if (problem.isError()) {
        System.out.println(problem.getMessage());
        problems.add(problem);
      }
    }

    // step 5: finally return
    return problems;
  }

  private class CompilerOptions extends Hashtable<Object, Object> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#isEmpty()
     */
    @Override
    public synchronized boolean isEmpty() {
      // TODO Auto-generated method stub
      return super.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#elements()
     */
    @Override
    public synchronized Enumeration<Object> elements() {
      // TODO Auto-generated method stub
      return super.elements();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#keySet()
     */
    @Override
    public Set<Object> keySet() {
      // TODO Auto-generated method stub
      return super.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#entrySet()
     */
    @Override
    public Set<Entry<Object, Object>> entrySet() {
      // TODO Auto-generated method stub
      return super.entrySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#values()
     */
    @Override
    public Collection<Object> values() {
      // TODO Auto-generated method stub
      return super.values();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#containsKey(java.lang.Object)
     */
    @Override
    public synchronized boolean containsKey(Object key) {
      // TODO Auto-generated method stub
      return super.containsKey(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Hashtable#get(java.lang.Object)
     */
    @Override
    public synchronized Object get(Object key) {
      // TODO Auto-generated method stub
      return super.get(key);
    }

  }
}
