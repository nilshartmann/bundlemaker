package org.bundlemaker.core.jdt.parser;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

public class ParserTest {

  @Test
  public void test() {

    // create the parser
    ASTParser parser = ASTParser.newParser(AST.JLS4);

    // Globals.getClasspath()
    parser.setSource("package xya; public class Test {}".toCharArray());
    parser.setEnvironment(new String[] { }, new String[] { }, null, true);
    parser.setKind(ASTParser.K_COMPILATION_UNIT);
    parser.setResolveBindings(true);
    parser.setUnitName("xya/Test.java");
    
    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
    
    for (Iterator iterator = compilationUnit.types().iterator(); iterator.hasNext();) {
      AbstractTypeDeclaration type = (AbstractTypeDeclaration) iterator.next();
      System.out.println("Binding: " +  type.resolveBinding());
    }
    

    
    System.out.println(compilationUnit);
  }
}
