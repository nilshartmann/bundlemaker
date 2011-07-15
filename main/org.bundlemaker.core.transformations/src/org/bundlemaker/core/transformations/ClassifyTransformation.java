/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ClassifyTransformation implements ITransformation {

  /**
   * A pattern describing the modules this transformation should get applied to
   */
  private final String _includeModulePattern;

  /**
   * Might be null
   */
  private final String _excludeModulePattern;

  /**
   * A template with the classification path
   * 
   * <p>
   * The following variables are supported:
   * <li>
   * <ul>
   * <b>%ModuleName%</b>: Replaced by the module name
   * <ul>
   * <b>%MODULE_NAME%</b>: Replaced by the module name in uppercase</li>
   */
  private final String _classificationTemplate;

  /**
   * @param modulePattern
   * @param classificationTemplate
   */
  public ClassifyTransformation(String includeModulePattern, String excludeModulePattern, String classificationTemplate) {
    super();
    _includeModulePattern = includeModulePattern;
    if (excludeModulePattern == null || excludeModulePattern.trim().isEmpty()) {
      _excludeModulePattern = null;
    } else {
      _excludeModulePattern = excludeModulePattern;
    }
    _classificationTemplate = classificationTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformation.ITransformation#apply(org.bundlemaker.core.modules.modifiable.
   * IModifiableModularizedSystem)
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {

    Collection<IModifiableResourceModule> modules = modularizedSystem.getModifiableResourceModules();

    SubMonitor subMonitor = SubMonitor.convert(monitor, modules.size());
    subMonitor.subTask("Classify modules");

    for (IModifiableResourceModule module : modules) {
      applyClassification(module);
      subMonitor.worked(1);
    }
  }

  public void applyClassification(IModifiableResourceModule targetModule) {
    if (matches(targetModule.getModuleIdentifier())) {
      IPath classification = getClassification(targetModule);
      targetModule.setClassification(classification);
    }

  }

  /**
   * @param value
   * @return
   */
  private IPath getClassification(IModifiableResourceModule value) {

    String classification = _classificationTemplate.replaceAll("%MODULE_NAME%", value.getModuleIdentifier().getName()
        .toUpperCase());
    classification = classification.replaceAll("%ModuleName%", value.getModuleIdentifier().getName());

    return new Path(classification);
  }

  /**
   * @param identifier
   * @return
   */
  private boolean matches(IModuleIdentifier identifier) {
    String name = identifier.getName();
    boolean isIncluded = simpleMatch(_includeModulePattern, name);
    if (!isIncluded) {
      return false;
    }
    return (_excludeModulePattern == null) || !(simpleMatch(_excludeModulePattern, name));
  }

  /**
   * Match a String against the given pattern, supporting the following simple pattern styles: "xxx*", "*xxx", "*xxx*"
   * and "xxx*yyy" matches (with an arbitrary number of pattern parts), as well as direct equality.
   * 
   * <p>
   * Taken from Springframework PatternMatchUtils class
   * </p>
   * 
   * @param pattern
   *          the pattern to match against
   * @param str
   *          the String to match
   * @return whether the String matches the given pattern
   * @see org.springframework.util.PatternMatchUtils
   */
  public static boolean simpleMatch(String pattern, String str) {
    if (pattern == null || str == null) {
      return false;
    }
    int firstIndex = pattern.indexOf('*');
    if (firstIndex == -1) {
      return pattern.equals(str);
    }
    if (firstIndex == 0) {
      if (pattern.length() == 1) {
        return true;
      }
      int nextIndex = pattern.indexOf('*', firstIndex + 1);
      if (nextIndex == -1) {
        return str.endsWith(pattern.substring(1));
      }
      String part = pattern.substring(1, nextIndex);
      int partIndex = str.indexOf(part);
      while (partIndex != -1) {
        if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
          return true;
        }
        partIndex = str.indexOf(part, partIndex + 1);
      }
      return false;
    }
    return (str.length() >= firstIndex && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) && simpleMatch(
        pattern.substring(firstIndex), str.substring(firstIndex)));
  }

  // public static void main(String[] args) {
  //
  // ClassifyTransformation transformation = new ClassifyTransformation("org.bm.*", "BUNDLEMAKER/%MODULE_NAME%");
  //
  // System.out.println(transformation.matches(new ModuleIdentifier("org.bm.a", "1")));
  // System.out.println(transformation.matches(new ModuleIdentifier("org.bm.aa", "1")));
  // System.out.println(transformation.matches(new ModuleIdentifier("org.bm", "1")));
  // System.out.println(transformation.matches(new ModuleIdentifier("org.bm.c", "1")));
  // System.out.println(transformation.matches(new ModuleIdentifier("org.ba.c", "1")));
  // String classification = "A/%MODULE_NAME%/%ModuleName%/".replaceAll("%MODULE_NAME%", "MEINMODUL");
  // classification = classification.replaceAll("%ModuleName%", "MeinModul")
  // ;
  // System.out.println("classification:" + classification);
  //
  // // transformation.applyClassification(new ModuleIdentifier("org.bm.a", "1"), new ResourceModule());
  // }

}
