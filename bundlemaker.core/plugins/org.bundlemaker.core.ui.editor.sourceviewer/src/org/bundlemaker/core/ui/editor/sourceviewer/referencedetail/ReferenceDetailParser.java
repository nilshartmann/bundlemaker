package org.bundlemaker.core.ui.editor.sourceviewer.referencedetail;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.common.IResource;
import org.bundlemaker.core.common.utils.VMInstallUtils;
import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.parser.IReferenceDetailParser;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class ReferenceDetailParser implements IReferenceDetailParser {

  @Override
  public Map<String, List<IPosition>> parseReferencePositions(IModuleResource resource,
      IModularizedSystem modularizedSystem) {

    //
    try {
      IMovableUnit movableUnit = resource.getMovableUnit();
      CompilationUnit compilationUnit = parse(movableUnit.getAssociatedSourceResource(),
          modularizedSystem.getBundleMakerProject());
      JdtAstVisitor jdtAstVisitor = new JdtAstVisitor();
      compilationUnit.accept(jdtAstVisitor);
      return jdtAstVisitor.getReferences();
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return Collections.emptyMap();
  }

  protected CompilationUnit parse(IResource resource, IProjectDescriptionAwareBundleMakerProject iBundleMakerProject) throws CoreException {

    ASTParser parser = ASTParser.newParser(AST.JLS4);
    parser.setKind(ASTParser.K_COMPILATION_UNIT);
    String[][] result = get(iBundleMakerProject);
    parser.setSource(new String(resource.getContent()).toCharArray()); // set source
    parser.setResolveBindings(true); // we need bindings later on
    parser.setUnitName(resource.getPath());
    parser.setEnvironment(result[0], result[1], null, false);

    org.eclipse.jdt.core.dom.ASTNode node = parser.createAST(null /* IProgressMonitor */);

    return (CompilationUnit) node; // parse
  }

  private String[][] get(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) throws CoreException {

    //
    List<String> classpathEntries = new LinkedList<String>();
    List<String> sourcepathEntries = new LinkedList<String>();

    for (IProjectContentEntry entry : bundleMakerProject.getProjectDescription().getContent()) {
      for (VariablePath path : entry.getBinaryRootPaths()) {
        classpathEntries.add(path.getAsFile().getAbsolutePath());
      }
      for (VariablePath path : entry.getSourceRootPaths()) {
        sourcepathEntries.add(path.getAsFile().getAbsolutePath());
      }
    }

    // step 3.1: add the vm path
    IVMInstall vm = VMInstallUtils.getIVMInstall(bundleMakerProject.getProjectDescription().getJRE());
    for (LibraryLocation libraryLocation : JavaRuntime.getLibraryLocations(vm)) {
      classpathEntries.add(libraryLocation.getSystemLibraryPath().toFile().getAbsolutePath());
    }

    return new String[][] { classpathEntries.toArray(new String[0]), sourcepathEntries.toArray(new String[0]) };
  }
}
