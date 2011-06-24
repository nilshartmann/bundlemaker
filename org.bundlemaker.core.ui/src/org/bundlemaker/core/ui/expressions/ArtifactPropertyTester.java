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
package org.bundlemaker.core.ui.expressions;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Assert;

/**
 * A {@link PropertyTester} that allows testing of several {@link IArtifact} properties in an Eclipse expression
 * definition
 * 
 * <p>
 * Currently supported properties
 * </p>
 * <ul>
 * <li><b>type</b>: Tests for the {@link ArtifactType} of the selected {@link IArtifact}</li>
 * </ul>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactPropertyTester extends PropertyTester {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
   * java.lang.Object)
   */
  @Override
  public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {

    IArtifact artifact = (IArtifact) receiver;

    if ("type".equalsIgnoreCase(property)) {
      ArtifactType expectedType = ArtifactType.valueOf((String) expectedValue);
      return artifact.getType().equals(expectedType);
    }

    // Unsupported property
    Assert.isTrue(false, "Property-Name '" + property + "' not supported by ArtifactPropertyTester");
    return false;
  }

}
