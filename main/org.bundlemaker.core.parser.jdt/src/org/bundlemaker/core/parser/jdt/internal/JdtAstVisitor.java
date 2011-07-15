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

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.IModifiableType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
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
  private IModifiableResource    _javaSourceResource;

  /** - */
  private Stack<IModifiableType> _currentTypes;

  /** - */
  private Stack<String>          _realTypes;

  /** - */
  private Stack<ITypeBinding>    _typeBindings;

  /** */
  private Message[]              _messages = new Message[0];

  /** */
  private IProblem[]             _problems = new IProblem[0];

  /**
   * <p>
   * Creates a new instance of type {@link JdtAstVisitor}.
   * </p>
   * 
   * @param javaSourceResource
   * @param mapTypeInfo
   */
  public JdtAstVisitor(IModifiableResource javaSourceResource) {
    Assert.isNotNull(javaSourceResource);

    _javaSourceResource = javaSourceResource;

    _typeBindings = new Stack<ITypeBinding>();
    _currentTypes = new Stack<IModifiableType>();
    _realTypes = new Stack<String>();
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
  public List<org.bundlemaker.core.IProblem> getProblems() {

    List<org.bundlemaker.core.IProblem> result = new LinkedList<org.bundlemaker.core.IProblem>();

    for (IProblem iProblem : _problems) {

      if (iProblem.isError()) {

        result.add(new JdtProblemAdapter(_javaSourceResource, iProblem));
      }
    }

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

    // visit the child nodes
    return !hasErrors();
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
        _javaSourceResource.recordReference(((IPackageBinding) binding).getName(), new ReferenceAttributes(
            ReferenceType.PACKAGE_REFERENCE, false, false, false, true, false, true, false));
      }

      // add referenced type
      else if (binding instanceof ITypeBinding) {

        resolveTypeBinding((ITypeBinding) binding, false, false, false);
      }

      // add referenced type
      else if (binding instanceof IVariableBinding) {

        IVariableBinding variableBinding = (IVariableBinding) binding;
        ITypeBinding typeBinding = variableBinding.getDeclaringClass();

        resolveTypeBinding(typeBinding, false, false, false);
      }
    }

    // no binding exists
    else {

      if (!node.isOnDemand() && !node.isStatic()) {

        addReferencedType(node.getName().getFullyQualifiedName(), false, false, false);

      } else if (node.isOnDemand() && !node.isStatic()) {

        addReferencedType(node.getName().getFullyQualifiedName(), false, false, false);

      } else if (!node.isOnDemand() && node.isStatic()) {

        Name importElementName = node.getName();
        String fullQualifiedName = importElementName.getFullyQualifiedName().substring(0,
            importElementName.getFullyQualifiedName().lastIndexOf('.'));

        addReferencedType(fullQualifiedName, false, false, false);
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

    // add the type name
    ITypeBinding typeBinding = node.resolveBinding();

    TypeEnum typeEnum = null;
    if (typeBinding.isInterface()) {
      typeEnum = TypeEnum.INTERFACE;
    } else if (typeBinding.isClass()) {
      typeEnum = TypeEnum.CLASS;
    } else {
      // TODO
      throw new RuntimeException("HAE " + node);
    }

    //
    String binaryName = node.resolveBinding().getBinaryName();

    // //
    // IModifiableType realType =
    // _javaSourceResource.getOrCreateType(binaryName,
    // typeEnum);

    IModifiableType currentType = null;

    if (JavaTypeUtils.isLocalOrAnonymousTypeName(binaryName)) {

      //
      String outerType = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(binaryName);

      //
      currentType = _javaSourceResource.getType(outerType);

    } else {

      //
      currentType = _javaSourceResource.getOrCreateType(binaryName, typeEnum);
    }

    _realTypes.push(binaryName);
    _currentTypes.push(currentType);

    // declared type superclass type
    resolveType(node.getSuperclassType(), true, false, false);

    // declared type implemented interfaces types
    List<Type> interfaces = node.superInterfaceTypes();
    for (Type iface : interfaces) {
      resolveType(iface, false, true, false);
    }

    //
    IAnnotationBinding[] annotationBindings = node.resolveBinding().getAnnotations();
    for (IAnnotationBinding annotationBinding : annotationBindings) {
      resolveTypeBinding(annotationBinding.getAnnotationType(), false, false, true);
    }

    // visit the child nodes
    return true;
  }

  @Override
  public void endVisit(TypeDeclaration node) {
    _currentTypes.pop();
    _realTypes.pop();
  }

  @Override
  public boolean visit(AnnotationTypeDeclaration node) {

    // add the type name
    IModifiableType type = _javaSourceResource.getOrCreateType(node.resolveBinding().getBinaryName(),
        TypeEnum.ANNOTATION);
    _currentTypes.push(type);
    _realTypes.push(node.resolveBinding().getBinaryName());

    return true;
  }

  @Override
  public void endVisit(AnnotationTypeDeclaration node) {
    _currentTypes.pop();
    _realTypes.pop();
  }

  // @Override
  // public boolean visit(AnonymousClassDeclaration node) {
  //
  // // add the type name
  // ITypeBinding typeBinding = node.resolveBinding();
  //
  // TypeEnum typeEnum = null;
  // if (typeBinding.isInterface()) {
  // typeEnum = TypeEnum.INTERFACE;
  // } else if (typeBinding.isEnum()) {
  // typeEnum = TypeEnum.ENUM;
  // } else if (typeBinding.isAnnotation()) {
  // typeEnum = TypeEnum.ANNOTATION;
  // } else if (typeBinding.isClass()) {
  // typeEnum = TypeEnum.CLASS;
  // } else if (typeBinding.isAnonymous()) {
  // typeEnum = TypeEnum.CLASS;
  // } else {
  // // TODO
  // throw new RuntimeException("HAE " + node);
  // }
  //
  // //
  // String fullyQualifiedName = node.resolveBinding().getBinaryName();
  //
  // //
  // if (fullyQualifiedName != null) {
  //
  // IModifiableType type = _javaSourceResource.getOrCreateType(
  // fullyQualifiedName, typeEnum);
  //
  // _currentTypes.push(type);
  //
  // } else {
  //
  // // if the anonymous class has an empty body, no binary type will
  // // be created and the binary name is null.
  // // In this case we push the current type again to satisfy the
  // // 'pop' in the end visit.
  // _currentTypes.push(_currentTypes.peek());
  // }
  //
  // // //
  // // IAnnotationBinding[] annotationBindings = node.resolveBinding()
  // // .getAnnotations();
  // // for (IAnnotationBinding annotationBinding : annotationBindings) {
  // // resolveTypeBinding(annotationBinding.getAnnotationType(), false,
  // // false, true);
  // // }
  //
  // // visit the child nodes
  // return true;
  // }
  //
  // @Override
  // public void endVisit(AnonymousClassDeclaration node) {
  // _currentTypes.pop();
  // }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. EnumDeclaration)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(EnumDeclaration node) {

    // add the type name
    IModifiableType type = _javaSourceResource.getOrCreateType(node.resolveBinding().getBinaryName(), TypeEnum.ENUM);
    _currentTypes.push(type);
    _realTypes.push(node.resolveBinding().getBinaryName());

    // add super interfaces
    List<Type> superInterfaces = node.superInterfaceTypes();
    for (Type superInterface : superInterfaces) {
      resolveType(superInterface, false, false, false);
    }

    //
    IAnnotationBinding[] annotationBindings = node.resolveBinding().getAnnotations();
    for (IAnnotationBinding annotationBinding : annotationBindings) {
      resolveTypeBinding(annotationBinding.getAnnotationType(), false, false, true);
    }

    // visit the child nodes
    return true;
  }

  @Override
  public void endVisit(EnumDeclaration node) {
    _currentTypes.pop();
    _realTypes.pop();
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @return
   */
  @Override
  public boolean visit(AnnotationTypeMemberDeclaration node) {

    // resolve the member type
    resolveType(node.getType(), false, false, false);

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. MethodDeclaration)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(MethodDeclaration node) {

    // declared method return type
    resolveType(node.getReturnType2(), false, false, false);

    // declared method argument types
    List<SingleVariableDeclaration> variableDeclarations = node.parameters();

    for (SingleVariableDeclaration singleVariableDeclaration : variableDeclarations) {
      resolveType(singleVariableDeclaration.getType(), false, false, false);
    }

    // declared method exception types
    List<Name> exceptions = node.thrownExceptions();
    for (Name name : exceptions) {
      resolveTypeName(name, false, false, false);
    }

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. FieldDeclaration)
   */
  @Override
  public boolean visit(FieldDeclaration node) {

    // declared field type
    resolveType(node.getType(), false, false, false);

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. VariableDeclarationStatement)
   */
  @Override
  public boolean visit(VariableDeclarationStatement node) {

    // resolve type name
    resolveType(node.getType(), false, false, false);

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. CatchClause)
   */
  @Override
  public boolean visit(CatchClause node) {

    // resolve exception type
    resolveType(node.getException().getType(), false, false, false);

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
    resolveTypeName(node.getTypeName(), false, false, false);

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. NormalAnnotation)
   */
  @Override
  public boolean visit(NormalAnnotation node) {

    // resolve type name
    resolveTypeName(node.getTypeName(), false, false, false);

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. SingleMemberAnnotation)
   */
  @Override
  public boolean visit(SingleMemberAnnotation node) {

    // resolve type name
    resolveTypeName(node.getTypeName(), false, false, false);

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. ArrayCreation)
   */
  @Override
  public boolean visit(ArrayCreation node) {

    // resolve type
    resolveType(node.getType(), false, false, false);

    // visit the child nodes
    return true;
  }

  /**
   * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. CastExpression)
   */
  @Override
  public boolean visit(CastExpression node) {

    // resolve type
    resolveType(node.getType(), false, false, false);

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
        resolveTypeBinding(typeBinding, false, false, false);
      }

      // resolve type
      resolveType(node.getType(), false, false, false);
    }

    // visit the child nodes
    return true;
  }

  @Override
  public boolean visit(FieldAccess node) {

    // resolve type
    IVariableBinding variableBinding = node.resolveFieldBinding();

    if (variableBinding != null) {
      resolveTypeBinding(variableBinding.getType(), false, false, false);
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
    resolveType(node.getRightOperand(), false, false, false);

    // visit the child nodes
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom. MethodInvocation)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean visit(MethodInvocation node) {

    // access to static methods
    IMethodBinding methodBinding = node.resolveMethodBinding();

    resolveMethodBinding(methodBinding);

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

  @Override
  public boolean visit(ConstructorInvocation node) {

    IMethodBinding methodBinding = node.resolveConstructorBinding();

    // resolve type arguments
    ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
    for (ITypeBinding typeBinding : parameterTypes) {
      resolveTypeBinding(typeBinding, false, false, false);
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
      resolveTypeBinding(typeBinding, false, false, false);
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
          resolveTypeBinding(variableBinding.getDeclaringClass(), false, false, false);
        }
        resolveTypeBinding(variableBinding.getType(), false, false, false);
      }
    }

    return true;
  }

  @Override
  public boolean visit(SimpleName node) {

    IBinding binding = node.resolveBinding();

    if (binding != null) {
      if (binding.getKind() == IBinding.METHOD) {

        resolveMethodBinding((IMethodBinding) binding);
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
    resolveTypeName(node.getQualifier(), false, false, false);

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
    resolveType(node.getType(), false, false, false);

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
    resolveType(node.getType(), false, false, false);

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

  /**
   * <p>
   * Resolves the type name for the given type. If the type is <code>null</code> or a primitive type, <code>null</code>
   * will be returned.
   * </p>
   * 
   * @param type
   *          the type to resolve
   * @param isExtends
   *          TODO
   * @param isImplements
   *          TODO
   * @param resolveSuperTypes
   */
  private void resolveType(Type type, boolean isExtends, boolean isImplements, boolean isClassAnnotation) {

    // return null if type == null
    if (type == null) {
      return;
    }

    // resolve the type binding
    resolveTypeBinding(type.resolveBinding(), isExtends, isImplements, isClassAnnotation);
  }

  /**
   * @param typeName
   * @return
   */
  private void resolveTypeName(Name typeName, boolean isExtends, boolean isImplements, boolean isClassAnnotation) {

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
    addReferencedType(typeBinding.getBinaryName(), isExtends, isImplements, isClassAnnotation);
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

  /**
   * @param typeBinding
   * @param isExtends
   *          TODO
   * @param isImplements
   *          TODO
   */
  private void resolveTypeBinding(ITypeBinding typeBinding, boolean isExtends, boolean isImplements,
      boolean isClassAnnotation) {

    // return null if type == null
    if (typeBinding == null) {
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
      resolveTypeBinding(typeBinding.getComponentType(), isExtends, isImplements, isClassAnnotation);
    }
    // handle parameterized types
    else if (typeBinding.isParameterizedType()) {

      // add the type
      resolveTypeBinding(typeBinding.getErasure(), isExtends, isImplements, isClassAnnotation);

      // add the type parameters
      for (ITypeBinding iTypeBinding : typeBinding.getTypeArguments()) {
        resolveTypeBinding(iTypeBinding, false, false, false);
      }

    }

    // handle primitive types
    else if (typeBinding.isPrimitive()) {
      // do nothing...
    }

    // handle wildcard types
    else if (typeBinding.isWildcardType()) {
      // handle bound
      resolveTypeBinding(typeBinding.getBound(), isExtends, isImplements, isClassAnnotation);
    }

    // handle type variable
    else if (typeBinding.isTypeVariable()) {

      ITypeBinding[] bindings = typeBinding.getTypeBounds();
      for (ITypeBinding iTypeBinding : bindings) {
        resolveTypeBinding(iTypeBinding, isExtends, isImplements, isClassAnnotation);
      }
    }

    // handle capture
    else if (typeBinding.isCapture()) {
      // System.err.println("isCapture: " + typeBinding);
    }

    // handle others
    else {

      if (typeBinding.getJavaElement() == null) {
        // System.err.println("*****");
        // System.err.println(typeBinding);
        // System.err.println("*****");
      } else {

        addReferencedType(((IType) typeBinding.getJavaElement()).getFullyQualifiedName('$'), isExtends, isImplements,
            isClassAnnotation);
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
  private void resolveMethodBinding(IMethodBinding methodBinding) {

    // resolve declaring class if method is static
    if (methodBinding != null) {

      // static?
      if (Flags.isStatic(methodBinding.getModifiers())) {
        resolveTypeBinding(methodBinding.getDeclaringClass(), false, false, false);
      }

      // resolve type arguments
      ITypeBinding[] typeArguments = methodBinding.getParameterTypes();
      for (ITypeBinding typeBinding : typeArguments) {
        resolveTypeBinding(typeBinding, false, false, false);
      }

      // resolve Exceptions
      ITypeBinding[] exceptionTypes = methodBinding.getExceptionTypes();
      for (ITypeBinding exceptionType : exceptionTypes) {
        resolveTypeBinding(exceptionType, false, false, false);
      }

      // resolve return type
      ITypeBinding returnType = methodBinding.getReturnType();
      resolveTypeBinding(returnType, false, false, false);

      //
      if (methodBinding.isParameterizedMethod()) {
        resolveMethodBinding(methodBinding.getMethodDeclaration());
      }
      //
      else if (methodBinding.isGenericMethod()) {
        ITypeBinding[] typeBindings = methodBinding.getTypeArguments();
        for (ITypeBinding typeBinding : typeBindings) {
          resolveTypeBinding(typeBinding, false, false, false);
        }
      }
    }
  }

  /**
   * <p>
   * Adds the given referenced type to the set of referenced types.
   * </p>
   * 
   * @param referencedType
   *          the name of the referenced type
   * @param startPosition
   * @param length
   * @param isExtends
   *          TODO
   * @param isImplements
   *          TODO
   */
  private void addReferencedType(String referencedType, boolean isExtends, boolean isImplements,
      boolean isClassAnnotation) {

    if (referencedType != null) {

      if (!_currentTypes.isEmpty()) {

        ReferenceAttributes referenceAttributes = null;

        String currentTypeName = _currentTypes.peek().getFullyQualifiedName();

        if (currentTypeName.equals(_realTypes.peek())) {

          //
          referenceAttributes = new ReferenceAttributes(ReferenceType.TYPE_REFERENCE, isExtends, isImplements,
              isClassAnnotation, true, false, true, false);
        } else {

          //
          referenceAttributes = new ReferenceAttributes(ReferenceType.TYPE_REFERENCE, false, false, false, true, false,
              true, false);
        }

        //
        _currentTypes.peek().recordReference(referencedType, referenceAttributes);

      } else {

        _javaSourceResource.recordReference(referencedType, new ReferenceAttributes(ReferenceType.TYPE_REFERENCE,
            isExtends, isImplements, isClassAnnotation, true, false, true, false));
      }
    }
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
        resolveTypeBinding(typeBinding, false, false, false);
      }
    }
  }
}
