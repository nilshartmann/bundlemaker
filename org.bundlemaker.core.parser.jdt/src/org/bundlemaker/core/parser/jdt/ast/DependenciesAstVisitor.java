package org.bundlemaker.core.parser.jdt.ast;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.Reference;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.ResourceStandin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.Flags;
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
public class DependenciesAstVisitor extends ASTVisitor {

	/** - */
	private Resource _sourceFileResource;

	/** - */
	private Stack<ITypeBinding> _typeBindings;

	/** - */
	private Set<String> _typeNames;

	/** */
	private Message[] _messages = new Message[0];

	/** */
	private IProblem[] _problems = new IProblem[0];

	/**
	 * <p>
	 * Creates a new instance of type {@link DependenciesAstVisitor}.
	 * </p>
	 * 
	 * @param javaSourceFileElement
	 * @param mapTypeInfo
	 */
	public DependenciesAstVisitor(Resource sourceFileResource) {
		this();
		Assert.isNotNull(sourceFileResource);
		_sourceFileResource = sourceFileResource;
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link DependenciesAstVisitor}.
	 * </p>
	 */
	public DependenciesAstVisitor() {
		_typeBindings = new Stack<ITypeBinding>();
		_typeNames = new HashSet<String>();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param sourceFileResource
	 */
	public void setSourceFileResource(Resource sourceFileResource) {
		Assert.isNotNull(sourceFileResource);
		_sourceFileResource = sourceFileResource;
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
	public Set<String> getTypeNames() {
		return _typeNames;
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
				System.err.println(iProblem);
				System.err.println(" - "
						+ new String(iProblem.getOriginatingFileName()));
			}

			// TODO: KEY?
			IResourceKey resourceStandin = new ResourceKey(
					_sourceFileResource.getContentId(),
					_sourceFileResource.getRoot(),
					_sourceFileResource.getPath());

			result.add(new JdtProblemAdapter(resourceStandin, iProblem));
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
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * CompilationUnit)
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
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      ImportDeclaration )
	 */
	@Override
	public boolean visit(ImportDeclaration node) {

		// binding exists
		if (node.resolveBinding() != null
				&& !node.resolveBinding().isRecovered()) {

			// get the binding
			IBinding binding = node.resolveBinding();

			// add referenced package
			if (binding instanceof IPackageBinding) {

				/* Reference packageReference = */
				_sourceFileResource.createReference(
						((IPackageBinding) binding).getName(),
						ReferenceType.PACKAGE_REFERENCE, true, false);

				// packageReference.addPosition(new ReferencedPackage.Position(
				// node.getStartPosition(), node.getLength()));
			}

			// add referenced type
			else if (binding instanceof ITypeBinding) {

				resolveTypeBinding((ITypeBinding) binding,
						node.getStartPosition(), node.getLength());
			}

			// add referenced type
			else if (binding instanceof IVariableBinding) {

				IVariableBinding variableBinding = (IVariableBinding) binding;
				ITypeBinding typeBinding = variableBinding.getDeclaringClass();

				resolveTypeBinding(typeBinding, node.getStartPosition(),
						node.getLength());
			}
		}

		// no binding exists
		else {

			if (!node.isOnDemand() && !node.isStatic()) {

				addReferencedType(node.getName().getFullyQualifiedName(),
						node.getStartPosition(), node.getLength());

			} else if (node.isOnDemand() && !node.isStatic()) {

				addReferencedType(node.getName().getFullyQualifiedName(),
						node.getStartPosition(), node.getLength());

			} else if (!node.isOnDemand() && node.isStatic()) {

				Name importElementName = node.getName();
				String fullQualifiedName = importElementName
						.getFullyQualifiedName().substring(
								0,
								importElementName.getFullyQualifiedName()
										.lastIndexOf('.'));

				addReferencedType(fullQualifiedName, node.getStartPosition(),
						node.getLength());
			}
		}

		// don't visit children
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * TypeDeclaration)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(TypeDeclaration node) {

		// TODO: auswerten
		_typeNames.add(node.resolveBinding().getBinaryName());

		// declared type superclass type (also resolve indirectly referenced
		// classes)
		resolveType(node.getSuperclassType());
		//
		// if (node.getSuperclassType() != null) {
		// IMethodBinding[] methodBinding = node.getSuperclassType()
		// .resolveBinding().getDeclaredMethods();
		// for (IMethodBinding iMethodBinding : methodBinding) {
		// res
		// iMethodBinding.get
		// System.err.println(" - " + iMethodBinding.getName());
		// }
		// }

		// declared type implemented interfaces types
		List<Type> interfaces = node.superInterfaceTypes();
		for (Type type : interfaces) {
			resolveType(type);
		}

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * EnumDeclaration)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(EnumDeclaration node) {

		// add super interfaces
		List<Type> superInterfaces = node.superInterfaceTypes();
		for (Type type : superInterfaces) {
			resolveType(type);
		}

		// visit the child nodes
		return true;
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
		resolveType(node.getType());

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * MethodDeclaration)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(MethodDeclaration node) {

		// declared method return type
		resolveType(node.getReturnType2());

		// declared method argument types
		List<SingleVariableDeclaration> variableDeclarations = node
				.parameters();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * FieldDeclaration)
	 */
	@Override
	public boolean visit(FieldDeclaration node) {

		// declared field type
		resolveType(node.getType());

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * VariableDeclarationStatement)
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {

		// resolve type name
		resolveType(node.getType());

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * CatchClause)
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
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      MarkerAnnotation)
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {

		// resolve type name
		resolveTypeName(node.getTypeName(), node.getStartPosition(),
				node.getLength());

		// visit the child nodes
		return true;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      NormalAnnotation)
	 */
	@Override
	public boolean visit(NormalAnnotation node) {

		// resolve type name
		resolveTypeName(node.getTypeName(), node.getStartPosition(),
				node.getLength());

		// visit the child nodes
		return true;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      SingleMemberAnnotation)
	 */
	@Override
	public boolean visit(SingleMemberAnnotation node) {

		// resolve type name
		resolveTypeName(node.getTypeName(), node.getStartPosition(),
				node.getLength());

		// visit the child nodes
		return true;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      ArrayCreation)
	 */
	@Override
	public boolean visit(ArrayCreation node) {

		// resolve type
		resolveType(node.getType());

		// visit the child nodes
		return true;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      CastExpression)
	 */
	@Override
	public boolean visit(CastExpression node) {

		// resolve type
		resolveType(node.getType());

		// visit the child nodes
		return true;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 *      ClassInstanceCreation)
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {

		// resolve binding
		IMethodBinding methodBinding = node.resolveConstructorBinding();

		if (methodBinding != null) {

			// resolve type arguments
			ITypeBinding[] typeArguments = methodBinding.getParameterTypes();
			for (ITypeBinding typeBinding : typeArguments) {
				resolveTypeBinding(typeBinding, node.getStartPosition(),
						node.getLength());
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
			resolveTypeBinding(variableBinding.getType(),
					node.getStartPosition(), node.getLength());
		}
		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * InstanceofExpression)
	 */
	@Override
	public boolean visit(InstanceofExpression node) {

		// resolve type
		resolveType(node.getRightOperand());

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * MethodInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(MethodInvocation node) {

		// access to static methods
		IMethodBinding methodBinding = node.resolveMethodBinding();

		resolveMethodBinding(methodBinding, node.getStartPosition(),
				node.getLength());

		// resolve the associated expression
		resolveExpressionType(node.getExpression());

		// resolve the type arguments
		List<Expression> typeArguments = node.arguments();
		for (Expression exp : typeArguments) {
			resolveExpressionType(exp);
		}

		// SICK: resolve parameters for 'ambigious' methods
		// resolveAmbigousMethods(node.resolveTypeBinding(), methodBinding
		// .getName());
		// Expression expression = node.getExpression();
		// if (expression != null) {
		// ITypeBinding typeBinding = expression.resolveTypeBinding();
		// if (typeBinding != null) {
		// resolveAmbigousMethods(typeBinding, methodBinding.getName());
		// }
		// }

		// visit the child nodes
		return true;
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @param typeBinding
	// * @param name
	// */
	// private void resolveAmbigousMethods(ITypeBinding typeBinding, String
	// name) {
	//
	// if (typeBinding == null
	// || typeBinding.equals(_ast
	// .resolveWellKnownType("java.lang.Object"))) {
	//
	// return;
	// }
	//
	// // TODO: declared methods liefert hier
	// for (IMethodBinding methodBinding : typeBinding.getDeclaredMethods()) {
	// if (name.equals(methodBinding.getName())) {
	// // TODO: Position
	// resolveMethodBinding(methodBinding, 0, 0);
	// }
	// }
	//
	// resolveAmbigousMethods(typeBinding.getSuperclass(), name);
	//
	// for (ITypeBinding interfaceBinding : typeBinding.getInterfaces()) {
	// resolveAmbigousMethods(interfaceBinding.getSuperclass(), name);
	// }
	// }

	@Override
	public boolean visit(ConstructorInvocation node) {

		IMethodBinding methodBinding = node.resolveConstructorBinding();

		// resolve type arguments
		ITypeBinding[] parameterTypes = methodBinding.getParameterTypes();
		for (ITypeBinding typeBinding : parameterTypes) {
			resolveTypeBinding(typeBinding, node.getStartPosition(),
					node.getLength());
		}

		// List<Expression> typeArguments = node.arguments();
		// for (Expression expression : typeArguments) {
		// resolveTypeBinding(expression.resolveTypeBinding(), node
		// .getStartPosition(), node.getLength());
		// }

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
			resolveTypeBinding(typeBinding, node.getStartPosition(),
					node.getLength());
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
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * QualifiedName)
	 */
	@Override
	public boolean visit(QualifiedName node) {

		// access to static fields
		IBinding binding = node.resolveBinding();
		if (binding != null) {
			if (binding.getKind() == IBinding.VARIABLE) {
				IVariableBinding variableBinding = (IVariableBinding) binding;
				if (Flags.isStatic(variableBinding.getModifiers())
						&& variableBinding.getDeclaringClass() != null) {
					resolveTypeBinding(variableBinding.getDeclaringClass(),
							node.getStartPosition(), node.getLength());
				}
				resolveTypeBinding(variableBinding.getType(),
						node.getStartPosition(), node.getLength());
			}
		}

		return true;
	}

	@Override
	public boolean visit(SimpleName node) {

		IBinding binding = node.resolveBinding();

		if (binding != null) {
			if (binding.getKind() == IBinding.METHOD) {

				resolveMethodBinding((IMethodBinding) binding,
						node.getStartPosition(), node.getLength());
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
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * ThisExpression)
	 */
	@Override
	public boolean visit(ThisExpression node) {

		// resolve type name
		resolveTypeName(node.getQualifier(), node.getStartPosition(),
				node.getLength());

		// visit the child nodes
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * TypeLiteral)
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
	 * @seeorg.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * VariableDeclarationExpression)
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

	/**
	 * <p>
	 * Resolves the type name for the given type. If the type is
	 * <code>null</code> or a primitive type, <code>null</code> will be
	 * returned.
	 * </p>
	 * 
	 * @param type
	 *            the type to resolve
	 * @param resolveSuperTypes
	 */
	private void resolveType(Type type) {

		// return null if type == null
		if (type == null) {
			return;
		}

		// resolve the type binding
		resolveTypeBinding(type.resolveBinding(), type.getStartPosition(),
				type.getLength());
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
		addReferencedType(typeBinding.getQualifiedName(), startPosition, length);

		// if (resolveSuperTypes) {
		// resolveTypeBinding(typeBinding.getSuperclass(), startPosition,
		// length, true, false);
		// }
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
	 */
	private void resolveTypeBinding(ITypeBinding typeBinding,
			int startPosition, int length) {

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
			resolveTypeBinding(typeBinding.getComponentType(), startPosition,
					length);
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

			if (typeBinding.getJavaElement() == null) {
				// System.err.println("*****");
				// System.err.println(typeBinding);
				// System.err.println("*****");
			} else {

				addReferencedType(
						((IType) typeBinding.getJavaElement())
								.getFullyQualifiedName('$'),
						startPosition, length);
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
	private void resolveMethodBinding(IMethodBinding methodBinding,
			int startPosition, int length) {

		// resolve declaring class if method is static
		if (methodBinding != null) {

			// static?
			if (Flags.isStatic(methodBinding.getModifiers())) {
				resolveTypeBinding(methodBinding.getDeclaringClass(),
						startPosition, length);
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
				resolveMethodBinding(methodBinding.getMethodDeclaration(),
						startPosition, length);
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

	/**
	 * <p>
	 * Adds the given referenced type to the set of referenced types.
	 * </p>
	 * 
	 * @param referencedType
	 *            the name of the referenced type
	 * @param startPosition
	 * @param length
	 */
	private void addReferencedType(String referencedType, int startPosition,
			int length) {

		if (referencedType != null) {

			_sourceFileResource.createReference(referencedType,
					ReferenceType.TYPE_REFERENCE, true, false);

			// reference.setSourceDependency();
			//
			// // set the indirectly referenced flag
			// reference.setDirectlyReferenced();
		}
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @param typeBinding
	// */
	// private void resolveSuperTypes(ITypeBinding typeBinding, int
	// startPosition,
	// int length, boolean isSuperTypeHierarchy) {
	//
	// // return if typeBindung == null
	// if (typeBinding == null) {
	// return;
	// }
	//
	// ITypeBinding superClassBinding = typeBinding.getSuperclass();
	//
	// if (superClassBinding == null
	// || superClassBinding.equals(_ast
	// .resolveWellKnownType("java.lang.Object"))) {
	// // do nothing...
	// } else {
	// resolveTypeBinding(superClassBinding, startPosition, length);
	//
	// // if (isSuperTypeHierarchy) {
	// // resolveIndirectlyReferencedMethodParameters(superClassBinding,
	// // startPosition, length);
	// // }
	// }
	//
	// for (ITypeBinding interfaceBinding : typeBinding.getInterfaces()) {
	//
	// if (interfaceBinding == null) {
	// // do nothing yet...
	// } else {
	//
	// resolveTypeBinding(interfaceBinding, startPosition, length);
	//
	// // if (isSuperTypeHierarchy) {
	// // resolveIndirectlyReferencedMethodParameters(
	// // interfaceBinding, startPosition, length);
	// // }
	// }
	// }
	// }

	// /**
	// * <p>
	// * </p>
	// *
	// * @param typeBinding
	// */
	// private void resolveIndirectlyReferencedMethodParameters(
	// ITypeBinding typeBinding, int startPosition, int length) {
	//
	// for (IMethodBinding methodBinding : typeBinding.getDeclaredMethods()) {
	//
	// if (typeBinding.isInterface()
	// || (typeBinding.getDeclaredModifiers() & Modifier.ABSTRACT) ==
	// Modifier.ABSTRACT
	// || (methodBinding.getModifiers() & Modifier.ABSTRACT) ==
	// Modifier.ABSTRACT) {
	// resolveMethodBinding(methodBinding, startPosition, length, true);
	// }
	// }
	// }

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
				resolveTypeBinding(typeBinding, expression.getStartPosition(),
						expression.getLength());
			}
		}
	}
}
