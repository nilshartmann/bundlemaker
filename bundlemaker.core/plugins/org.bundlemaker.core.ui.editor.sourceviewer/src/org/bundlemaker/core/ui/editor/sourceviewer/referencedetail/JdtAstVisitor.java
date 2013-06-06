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
package org.bundlemaker.core.ui.editor.sourceviewer.referencedetail;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.parser.IReferenceDetailParser;
import org.bundlemaker.core.parser.IReferenceDetailParser.IPosition;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;erich (gerd@gerd-wuetherich.de)
 */
public class JdtAstVisitor extends ASTVisitor {

  /** - */
  private Stack<ITypeBinding>           _typeBindings;

  /** */
  private Message[]                     _messages   = new Message[0];

  /** */
  private IProblem[]                    _problems   = new IProblem[0];

  GenericCache<String, List<IPosition>> _references = new GenericCache<String, List<IPosition>>() {

                                                      /** - */
                                                      private static final long serialVersionUID = 1L;

                                                      @Override
                                                      protected List<IPosition> create(String key) {
                                                        return new LinkedList<IPosition>();
                                                      }
                                                    };

  public JdtAstVisitor() {

    //
    _typeBindings = new Stack<ITypeBinding>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final GenericCache<String, List<IPosition>> getReferences() {
    return _references;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Message[] getMessages() {
    return _messages;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<org.bundlemaker.core.parser.IProblem> getProblems() {

    List<org.bundlemaker.core.parser.IProblem> result = new LinkedList<org.bundlemaker.core.parser.IProblem>();
    //
    // for (IProblem iProblem : _problems) {
    //
    // if (iProblem.isError()) {
    //
    // result.add(new JdtProblemAdapter(_javaSourceResource, iProblem));
    // }
    // }

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasErrors() {

    //
    for (IProblem problem : _problems) {
      if (problem.isError()) {
        return true;
      }
    }

    //
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. CompilationUnit)
   */
  @Override
  public boolean visit(CompilationUnit node) {

    // get messages/problems
    _messages = node.getMessages();
    _problems = node.getProblems();

    for (IProblem problem : node.getProblems()) {
      System.out.println(problem);
    }
    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. ImportDeclaration )
   */
  @Override
  public boolean visit(ImportDeclaration node) {

    if (node.isOnDemand()) {
      return false;
    }

    // binding exists
    if (node.resolveBinding() != null && !node.resolveBinding().isRecovered()) {

      // get the binding
      IBinding binding = node.resolveBinding();

      // add referenced package
      if (binding instanceof IPackageBinding) {

        /* Reference packageReference = */
        // _javaSourceResource.recordReference(((IPackageBinding) binding).getName());
      }

      // add referenced type
      else if (binding instanceof ITypeBinding) {
        resolveTypeBinding((ITypeBinding) binding, node.getStartPosition(), node.getLength());
      }

      // add referenced type
      else if (binding instanceof IVariableBinding) {

        IVariableBinding variableBinding = (IVariableBinding) binding;
        ITypeBinding typeBinding = variableBinding.getDeclaringClass();

        resolveTypeBinding(typeBinding, node.getStartPosition(), node.getLength());
      }
    }

    // no binding exists
    else {

      if (!node.isOnDemand() && !node.isStatic()) {

        addReferencedType(node.getName().getFullyQualifiedName(), node.getStartPosition(), node.getLength());

      } else if (node.isOnDemand() && !node.isStatic()) {

        addReferencedType(node.getName().getFullyQualifiedName(), node.getStartPosition(), node.getLength());

      } else if (!node.isOnDemand() && node.isStatic()) {

        Name importElementName = node.getName();
        String fullQualifiedName = importElementName.getFullyQualifiedName().substring(0,
            importElementName.getFullyQualifiedName().lastIndexOf('.'));

        addReferencedType(fullQualifiedName, node.getStartPosition(), node.getLength());
      }
    }

    // don't visit children
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. TypeDeclaration)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(TypeDeclaration node) {

    // declared type superclass type
    Type superclassType = node.getSuperclassType();
    if (superclassType != null) {
      resolveType(superclassType);
    }
    // declared type implemented interfaces types
    List<Type> interfaces = node.superInterfaceTypes();
    for (Type iface : interfaces) {
      resolveType(iface);
    }

    //
    ITypeBinding typeBinding = node.resolveBinding();
    if (typeBinding != null) {
      IAnnotationBinding[] annotationBindings = typeBinding.getAnnotations();
      for (IAnnotationBinding annotationBinding : annotationBindings) {
        resolveTypeBinding(annotationBinding.getAnnotationType(), node.getStartPosition(), node.getLength());
      }
    }
    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(EnumDeclaration node) {

    // add super interfaces
    List<Type> superInterfaces = node.superInterfaceTypes();
    for (Type superInterface : superInterfaces) {
      resolveType(superInterface);
    }

    //
    IAnnotationBinding[] annotationBindings = node.resolveBinding().getAnnotations();
    for (IAnnotationBinding annotationBinding : annotationBindings) {
      resolveTypeBinding(annotationBinding.getAnnotationType(), node.getStartPosition(), node.getLength());
    }

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(AnnotationTypeMemberDeclaration node) {

    // resolve the member type
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(MethodDeclaration node) {

    // declared method return type
    Type returnType = node.getReturnType2();
    if (returnType != null) {
      resolveType(returnType);
    }
    // declared method argument types
    List<SingleVariableDeclaration> variableDeclarations = node.parameters();

    for (SingleVariableDeclaration singleVariableDeclaration : variableDeclarations) {
      resolveType(singleVariableDeclaration.getType());
    }

    // declared method exception types
    List<Name> exceptions = node.thrownExceptions();
    for (Name name : exceptions) {
      resolveTypeName(name, name.getStartPosition(), name.getLength());
    }

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(FieldDeclaration node) {

    // declared field type
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(VariableDeclarationStatement node) {

    // resolve type name
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(CatchClause node) {

    // resolve exception type
    resolveType(node.getException().getType());

    // visit the child nodes
    return true;
  }

  /****************/
  /** Expressions */
  /****************/

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. MarkerAnnotation)
   */
  @Override
  public boolean visit(MarkerAnnotation node) {

    // resolve type name
    resolveTypeName(node.getTypeName(), node.getStartPosition(), node.getLength());

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. NormalAnnotation)
   */
  @Override
  public boolean visit(NormalAnnotation node) {

    // resolve type name
    resolveTypeName(node.getTypeName(), node.getStartPosition(), node.getLength());

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. SingleMemberAnnotation)
   */
  @Override
  public boolean visit(SingleMemberAnnotation node) {

    // resolve type name
    resolveTypeName(node.getTypeName(), node.getStartPosition(), node.getLength());

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. ArrayCreation)
   */
  @Override
  public boolean visit(ArrayCreation node) {

    // resolve type
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. CastExpression)
   */
  @Override
  public boolean visit(CastExpression node) {

    // resolve type
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. ClassInstanceCreation)
   */
  @Override
  public boolean visit(ClassInstanceCreation node) {

    // resolve binding
    IMethodBinding methodBinding = node.resolveConstructorBinding();

    if (methodBinding != null) {

      // resolve type arguments
      ITypeBinding[] typeArguments = methodBinding.getParameterTypes();
      for (ITypeBinding typeBinding : typeArguments) {
        resolveTypeBinding(typeBinding, node.getStartPosition(), node.getLength());
      }

      // resolve type
      resolveType(node.getType());
    }

    // visit the child nodes
    return true;
  }

  @Override
  public boolean visit(FieldAccess node) {

    // resolve type
    IVariableBinding variableBinding = node.resolveFieldBinding();

    if (variableBinding != null) {
      resolveTypeBinding(variableBinding.getType(), node.getStartPosition(), node.getLength());
    }
    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. InstanceofExpression)
   */
  @Override
  public boolean visit(InstanceofExpression node) {

    // resolve type
    resolveType(node.getRightOperand());

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(MethodInvocation node) {

    // access to static methods
    IMethodBinding methodBinding = node.resolveMethodBinding();

    //
    resolveMethodBinding(methodBinding, node.getStartPosition(), node.getLength());

    // resolve the associated expression
    resolveExpressionType(node.getExpression());

    // resolve the type arguments
    List<Expression> typeArguments = node.arguments();
    for (Expression exp : typeArguments) {
      resolveExpressionType(exp);
    }

    // visit the child nodes
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean visit(ConstructorInvocation node) {

    IMethodBinding methodBinding = node.resolveConstructorBinding();

    // resolve type arguments
    ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
    for (ITypeBinding typeBinding : parameterTypes) {
      resolveTypeBinding(typeBinding, node.getStartPosition(), node.getLength());
    }

    // visit the child nodes
    return true;
  }

  @Override
  public boolean visit(SuperConstructorInvocation node) {

    // TODO: Zusammenlegen mit ConstructorInvocation

    IMethodBinding methodBinding = node.resolveConstructorBinding();

    // resolve type arguments
    ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
    for (ITypeBinding typeBinding : parameterTypes) {
      resolveTypeBinding(typeBinding, node.getStartPosition(), node.getLength());
    }

    // List<Expression> typeArguments = node.arguments();
    // for (Expression expression : typeArguments) {
    // resolveTypeBinding(expression.resolveTypeBinding(), node
    // .getStartPosition(), node.getLength());
    // }

    // visit the child nodes
    return true;

  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. QualifiedName)
   */
  @Override
  public boolean visit(QualifiedName node) {

    // access to static fields
    IBinding binding = node.resolveBinding();
    if (binding != null) {
      if (binding.getKind() == IBinding.VARIABLE) {
        IVariableBinding variableBinding = (IVariableBinding) binding;
        if (Flags.isStatic(variableBinding.getModifiers()) && variableBinding.getDeclaringClass() != null) {
          resolveTypeBinding(variableBinding.getDeclaringClass(), node.getStartPosition(), node.getLength());
        }
        resolveTypeBinding(variableBinding.getType(), node.getStartPosition(), node.getLength());
      }
    }

    return true;
  }

  @Override
  public boolean visit(SimpleName node) {

    IBinding binding = node.resolveBinding();

    if (binding != null) {
      if (binding.getKind() == IBinding.METHOD) {
        resolveMethodBinding((IMethodBinding) binding, node.getStartPosition(), node.getLength());
      }
    }

    // else if (binding.getKind() == IBinding.VARIABLE) {
    //
    // IVariableBinding variableBinding = (IVariableBinding) binding;
    //
    // ITypeBinding typeBinding = variableBinding.getType();
    //
    // resolveTypeBinding(typeBinding, node.getStartPosition(), node
    // .getLength(), true, false);
    // }

    return super.visit(node);
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. ThisExpression)
   */
  @Override
  public boolean visit(ThisExpression node) {

    // resolve type name
    resolveTypeName(node.getQualifier(), node.getStartPosition(), node.getLength());

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. TypeLiteral)
   */
  @Override
  public boolean visit(TypeLiteral node) {

    // resolve type name
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. VariableDeclarationExpression)
   */
  @Override
  public boolean visit(VariableDeclarationExpression node) {

    // resolve type name
    resolveType(node.getType());

    // visit the child nodes
    return true;
  }

  /***************/
  /** Statements */
  /***************/

  @Override
  public boolean visit(ThrowStatement node) {

    if (node.getExpression() != null) {
      resolveExpressionType(node.getExpression());
    }

    return true;
  }

  @Override
  public boolean visit(ReturnStatement node) {

    if (node.getExpression() != null) {
      resolveExpressionType(node.getExpression());
    }

    return true;
  }

  /***************/
  /** Internal resolve methods **/
  /***************/
  private void resolveType(Type type) {

    // return null if type == null
    if (type == null) {
      return;
    }

    if (type.resolveBinding() != null && !type.resolveBinding().isRecovered()) {
      // resolve the type binding
      resolveTypeBinding(type.resolveBinding(), type.getStartPosition(), type.getLength());
    } else {
      //
      System.out.println("Resolve Type " + type.toString());
    }
  }

  /**
   * @param typeName
   * @return
   */
  private void resolveTypeName(Name typeName, int startPosition, int length) {

    // return null if typeName == null
    if (typeName == null) {
      return;
    }

    // get the type binding
    ITypeBinding typeBinding = typeName.resolveTypeBinding();

    // return null if typeBinding == null
    if (typeBinding == null) {
      // TODO: Fehlermeldung!
      return;
    }

    // return the qualified name
    addReferencedType(typeBinding.getBinaryName(), startPosition, length);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeBinding
   * @param startPosition
   * @param length
   * @param resolveSuperTypes
   */
  // private void resolveTypeBinding(ITypeBinding typeBinding,
  // int startPosition, int length, boolean resolveSuperTypes) {
  //
  // resolveTypeBinding(typeBinding, startPosition, length,
  // resolveSuperTypes, false);
  // }

  private void resolveTypeBinding(ITypeBinding typeBinding, int startPosition, int length) {

    // return null if type == null
    if (typeBinding == null || typeBinding.isRecovered()) {
      return;
    }

    //
    if (_typeBindings.contains(typeBinding)) {
      return;
    } else {
      _typeBindings.push(typeBinding);
    }

    // handle array types
    if (typeBinding.isArray()) {
      resolveTypeBinding(typeBinding.getComponentType(), startPosition, length);
    }
    // handle parameterized types
    else if (typeBinding.isParameterizedType()) {

      // add the type
      resolveTypeBinding(typeBinding.getErasure(), startPosition, length);

      // add the type parameters
      for (ITypeBinding iTypeBinding : typeBinding.getTypeArguments()) {
        resolveTypeBinding(iTypeBinding, startPosition, length);
      }

    }

    // handle primitive types
    else if (typeBinding.isPrimitive()) {
      // do nothing...
    }

    // handle wildcard types
    else if (typeBinding.isWildcardType()) {
      // handle bound
      resolveTypeBinding(typeBinding.getBound(), startPosition, length);
    }

    // handle type variable
    else if (typeBinding.isTypeVariable()) {

      ITypeBinding[] bindings = typeBinding.getTypeBounds();
      for (ITypeBinding iTypeBinding : bindings) {
        resolveTypeBinding(iTypeBinding, startPosition, length);
      }
    }

    // handle capture
    else if (typeBinding.isCapture()) {
      // System.err.println("isCapture: " + typeBinding);
    }

    // handle others
    else {

      System.out.println(typeBinding.getBinaryName());
      if (typeBinding.getBinaryName() != null) {
        addReferencedType(typeBinding.getBinaryName(), startPosition, length);
      } else {
        
      }
    }

    _typeBindings.pop();
  }

  /**
   * <p>
   * </p>
   * 
   * @param methodBinding
   */
  private void resolveMethodBinding(IMethodBinding methodBinding, int startPosition, int length) {

    // resolve declaring class if method is static
    if (methodBinding != null) {

      // static?
      if (Flags.isStatic(methodBinding.getModifiers())) {
        ITypeBinding typeBinding = methodBinding.getDeclaringClass();
        resolveTypeBinding(typeBinding, startPosition, length);
      }

      // resolve type arguments
      ITypeBinding[] typeArguments = methodBinding.getParameterTypes();
      for (ITypeBinding typeBinding : typeArguments) {
        resolveTypeBinding(typeBinding, startPosition, length);
      }

      // resolve Exceptions
      ITypeBinding[] exceptionTypes = methodBinding.getExceptionTypes();
      for (ITypeBinding exceptionType : exceptionTypes) {
        resolveTypeBinding(exceptionType, startPosition, length);
      }

      // resolve return type
      ITypeBinding returnType = methodBinding.getReturnType();
      resolveTypeBinding(returnType, startPosition, length);

      //
      if (methodBinding.isParameterizedMethod()) {
        resolveMethodBinding(methodBinding.getMethodDeclaration(), startPosition, length);
      }
      //
      else if (methodBinding.isGenericMethod()) {
        ITypeBinding[] typeBindings = methodBinding.getTypeArguments();
        for (ITypeBinding typeBinding : typeBindings) {
          resolveTypeBinding(typeBinding, startPosition, length);
        }
      }
    }
  }

  private void addReferencedType(String referencedType, int startPosition, int length) {
    _references.getOrCreate(referencedType).add(new Position(startPosition, length));
  }

  /**
   * <p>
   * </p>
   * 
   * @param expression
   */
  private void resolveExpressionType(Expression expression) {

    if (expression != null) {

      ITypeBinding typeBinding = expression.resolveTypeBinding();

      if (typeBinding != null) {
        resolveTypeBinding(typeBinding, expression.getStartPosition(), expression.getLength());
      }
    }
  }

  /**
   * <p>
   * </p>
   *
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static class Position implements IReferenceDetailParser.IPosition {

    /** - */
    private int offset;

    /** - */
    private int length;

    /**
     * <p>
     * Creates a new instance of type {@link Position}.
     * </p>
     * 
     * @param offset
     * @param length
     */
    public Position(int offset, int length) {
      this.offset = offset;
      this.length = length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffset() {
      return offset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
      return length;
    }

  }
}
