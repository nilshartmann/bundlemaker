package org.bundlemaker.core.ui.referencedetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Position;

public class ReferenceDetailParser implements IReferenceDetailParser {

  @Override
  public Map<String, List<Position>> parseReferencePositions(IResource resource, IModularizedSystem modularizedSystem) {

    //
    Map<String, List<Position>> result = new HashMap<String, List<Position>>();

    // //
    // for (IResource binaryResource : movableUnit.getAssociatedBinaryResources()) {
    //
    // // parse the class file
    // byte[] bytes = binaryResource.getContent();
    // ClassReader reader = new ClassReader(bytes);
    // AsmReferenceRecorder recorder = new AsmReferenceRecorder();
    // reader.accept(new ArtefactAnalyserClassVisitor(recorder), 0);
    //
    // //
    // System.out.println();
    // for (Map.Entry<String, List<Integer>> entry : recorder.getLines().entrySet()) {
    // System.out.println(entry);
    // }
    // }

    IProject project = modularizedSystem.getBundleMakerProject().getProject();
    IJavaProject javaProject = getAssociatedJavaProject(project);

    IMovableUnit movableUnit = MovableUnit.createFromResource(resource, modularizedSystem);
    for (IResource binaryResource : movableUnit.getAssociatedBinaryResources()) {
      try {
        IJavaElement element = javaProject.findElement(new Path(binaryResource.getPath()));
        CompilationUnit compilationUnit = parse((IClassFile) element);
        JdtAstVisitor jdtAstVisitor = new JdtAstVisitor(movableUnit);
        compilationUnit.accept(jdtAstVisitor);
        return jdtAstVisitor.getReferences();
      } catch (JavaModelException e) {
        e.printStackTrace();
      }
    }

    // CompilationUnit compilationUnit = parse((IClassFile) javaElement);
    // JdtAstVisitor jdtAstVisitor = new JdtAstVisitor();
    // compilationUnit.accept(jdtAstVisitor);

    return result;
  }

  protected static CompilationUnit parse(IClassFile classFile) {

    try {
      ASTParser parser = ASTParser.newParser(AST.JLS4);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setProject(classFile.getJavaProject());
      parser.setSource(classFile.getSource().toCharArray()); // set source
      parser.setResolveBindings(true); // we need bindings later on
      parser.setBindingsRecovery(true);
      parser.setUnitName("/" + classFile.getJavaProject().getProject().getName()
          + "/bla/blubb/Test.java");

      org.eclipse.jdt.core.dom.ASTNode node = parser.createAST(null /* IProgressMonitor */);

      // IBinding[] bindings = parser.createBindings(new IJavaElement[] { classFile }, null);
      // for (IBinding iBinding : bindings) {
      // System.out.println(iBinding);
      // }

      return (CompilationUnit) node; // parse
    } catch (JavaModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  private static IJavaProject getAssociatedJavaProject(IProject bundleMakerProject) {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    IJavaProject javaProject = JavaCore.create(associatedProject);

    try {
      javaProject.open(null);
    } catch (JavaModelException e) {
      throw new RuntimeException(e.getMessage());
    }

    return javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  private static String getAssociatedJavaProjectName(IProject project) {
    return project.getName() + "$bundlemakerJdt";
  }

}
