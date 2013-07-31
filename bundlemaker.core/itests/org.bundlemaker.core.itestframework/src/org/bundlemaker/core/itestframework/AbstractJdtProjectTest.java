package org.bundlemaker.core.itestframework;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.common.utils.EclipseProjectUtils;
import org.bundlemaker.core.itestframework.internal.TestProjectCreator;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.launching.environments.IExecutionEnvironmentsManager;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.After;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AbstractJdtProjectTest {

  private static final String SIMPLE_ARTIFACT_MODEL_TEST_JDT = "SimpleArtifactModelTest-JDT";

  /** - */
  private IJavaProject        _javaProject;

  private IBundleMakerProject _bundleMakerProject;

  private IModularizedSystem  _modularizedSystem;

  /**
   * <p>
   * </p>
   * 
   * @return the modularizedSystem
   */
  public IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Before
  public void before() throws CoreException {

    //
    createJavaProject();

    //
    IProject project = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(this.getClass().getSimpleName());

    _bundleMakerProject = BundleMakerCore.getBundleMakerProject(project);

    //
    JdtProjectContentProvider contentProvider = new JdtProjectContentProvider();
    contentProvider.addJavaProject(_javaProject);
    _bundleMakerProject.getModifiableProjectDescription().addContentProvider(contentProvider);
    _bundleMakerProject.getModifiableProjectDescription().save();
    TestProjectCreator.initializeParseAndOPen(_bundleMakerProject);

    //
    _modularizedSystem = _bundleMakerProject.getModularizedSystemWorkingCopy();
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @After
  public void after() throws CoreException {

    //
    _javaProject.getProject().close(null);
    _bundleMakerProject.getProject().close(null);

    //
    EclipseProjectUtils.deleteProjectIfExists(_javaProject.getProject().getName());
    EclipseProjectUtils.deleteProjectIfExists(_bundleMakerProject.getProject().getName());
  }

  /**
   * <p>
   * </p>
   * 
   * @return the _javaProject
   */
  public IJavaProject getJavaProject() {
    return _javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the bundleMakerProject
   */
  public IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  public void modifyClassKlasse() throws Exception {

    //
    ICompilationUnit compilationUnit = getJavaProject()
        .getPackageFragmentRoot(getJavaProject().getProject().getFolder("src")).getPackageFragment("de.test")
        .getCompilationUnit("Klasse.java");

    // create an AST
    ASTParser parser = ASTParser.newParser(AST.JLS4);
    parser.setSource(compilationUnit);
    parser.setResolveBindings(false);
    CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);
    AST ast = astRoot.getAST();

    // create the descriptive ast rewriter
    ASTRewrite rewrite = ASTRewrite.create(ast);

    // get the block node that contains the statements in the method body
    TypeDeclaration typeDecl = (TypeDeclaration) astRoot.types().get(0);
    MethodDeclaration methodDecl = typeDecl.getMethods()[0];
    Block block = methodDecl.getBody();

    // String value = javax.naming.Context.AUTHORITATIVE;

    VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
    fragment.setName(ast.newSimpleName("value"));

    Expression expression = ast.newQualifiedName(
        ast.newQualifiedName(ast.newQualifiedName(ast.newSimpleName("value"), ast.newSimpleName("value")),
            ast.newSimpleName("Context")), ast.newSimpleName("AUTHORITATIVE"));
    fragment.setInitializer(expression);
    VariableDeclarationStatement statement = ast.newVariableDeclarationStatement(fragment);
    statement.setType(ast.newSimpleType(ast.newSimpleName("String")));

    // describe that the first node is inserted as first statement in block, the other one as last statement
    // note: AST is not modified by this
    ListRewrite listRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
    listRewrite.insertFirst(statement, null);

    // evaluate the text edits corresponding to the described changes. AST and CU still unmodified.
    TextEdit res = rewrite.rewriteAST();

    // apply the text edits to the compilation unit
    Document document = new Document(compilationUnit.getSource());
    res.apply(document);
    compilationUnit.getBuffer().setContents(document.get());
    compilationUnit.save(null, true);
  }

  private void createJavaProject() throws CoreException, JavaModelException {

    // create the eclipse project
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject project = root.getProject(SIMPLE_ARTIFACT_MODEL_TEST_JDT);
    project.create(null);
    project.open(null);

    // add java nature
    IProjectDescription description = project.getDescription();
    description.setNatureIds(new String[] { JavaCore.NATURE_ID });
    project.setDescription(description, null);

    // create the java project
    _javaProject = JavaCore.create(project);

    // set the bin folder
    IFolder binFolder = project.getFolder("bin");
    binFolder.create(false, true, null);
    _javaProject.setOutputLocation(binFolder.getFullPath(), null);

    // set the class path (src and JDK)
    List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();
    IFolder srcFolder = project.getFolder("src");
    srcFolder.create(false, true, null);
    entries.add(JavaCore.newSourceEntry(srcFolder.getFullPath()));
    IExecutionEnvironmentsManager executionEnvironmentsManager = JavaRuntime.getExecutionEnvironmentsManager();
    IExecutionEnvironment[] executionEnvironments = executionEnvironmentsManager.getExecutionEnvironments();
    for (IExecutionEnvironment iExecutionEnvironment : executionEnvironments) {
      // We will look for JavaSE-1.6 as the JRE container to add to our classpath
      if ("JavaSE-1.6".equals(iExecutionEnvironment.getId())) {
        entries.add(JavaCore.newContainerEntry(JavaRuntime.newJREContainerPath(iExecutionEnvironment)));
        break;
      }
    }
    _javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[0]), null);

    // copy the test files
    Copy copy = new Copy();
    copy.setProject(new Project());
    copy.setTodir(srcFolder.getRawLocation().toFile());
    FileSet fileSet = new FileSet();
    fileSet.setDir(new File(TestProjectCreator.getSourcesPath(TestProjectCreator
        .getTestDataDirectory("SimpleArtifactModelTest"))));
    copy.addFileset(fileSet);
    copy.execute();

    // refresh and build
    srcFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
    project.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
    project.build(IncrementalProjectBuilder.FULL_BUILD, null);
  }
}
