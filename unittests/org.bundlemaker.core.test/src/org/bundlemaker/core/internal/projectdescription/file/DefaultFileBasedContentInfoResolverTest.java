/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.projectdescription.file;

import junit.framework.TestCase;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class DefaultFileBasedContentInfoResolverTest extends TestCase {
  
  public void test_sourcePattern() throws Exception {
    assertTrue(DefaultFileBasedContentInfoResolver.isSourcePattern("org.eclipse.source_1.2.3"));
    assertFalse(DefaultFileBasedContentInfoResolver.isSourcePattern("org.eclipse"));
    assertFalse(DefaultFileBasedContentInfoResolver.isSourcePattern("org.eclipse_1.2.3"));  
    assertTrue(DefaultFileBasedContentInfoResolver.isSourcePattern("joda-time-1.6-sources"));  
    
  }
  
  public void test_extractBinaryName() throws Exception {
    
    assertEquals("org.eclipse", DefaultFileBasedContentInfoResolver.extractAssociatedBinaryName("org.eclipse.source_1.2.3"));
    assertEquals("org.eclipse", DefaultFileBasedContentInfoResolver.extractAssociatedBinaryName("org.eclipse"));
    assertEquals("joda-time-1.6", DefaultFileBasedContentInfoResolver.extractAssociatedBinaryName("joda-time-1.6-sources"));

  }

}
