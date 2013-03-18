/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.itest.misc_models.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.selectors.SuperTypeSelector;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class SuperTypeSelectorTest extends AbstractSelectorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest#
	 * computeTestProjectName()
	 */
	@Override
	protected String computeTestProjectName() {
		return "InheritanceBasedSelectorTest";
	}



	/**
	 * 
	 */
	protected  void ensureCorrectTestModel() throws Exception {
		
		
		assertDependencyTo("a.BaseClass", "a.AbstractBaseClass", DependencyKind.EXTENDS);
		assertDependencyTo("a.ImplClass", "a.BaseClass", DependencyKind.EXTENDS);
		assertDependencyTo("a.ImplClass", "a.ServiceInterface", DependencyKind.IMPLEMENTS);
	
	}

	@Test
	public void oneInheritanceLevel() throws Exception {
		
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.A2"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		assertNotNull(artifacts);
		assertEquals(1, artifacts.size());
		
		IBundleMakerArtifact superArtifact = artifacts.get(0);
		assertEquals(getTypeArtifact("a.A1"), superArtifact);
	}
	
	@Test
	public void twoInheritanceLevels() throws Exception {
		
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.B3"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		
		assertArtifacts(artifacts, getTypeArtifact("a.B1"), getTypeArtifact("a.B2"));
	}
	
	@Test 
	public void noSupertype() throws Exception {
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.ServiceInterface"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		assertNotNull(artifacts);
		assertTrue(artifacts.isEmpty());
	}
	
	@Test
	public void interfaceInheritance() throws Exception {
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.I3"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		
		assertArtifacts(artifacts, getTypeArtifact("a.I1"), getTypeArtifact("a.I2"));
	}
	
	@Test
	public void implementingInterface() throws Exception {
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.SimpleClass"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		
		assertArtifacts(artifacts, getTypeArtifact("a.I1"));
	}
	
	@Test
	public void multipleInterfaceInheritance() throws Exception {
		SuperTypeSelector superTypeSelector = new SuperTypeSelector(getTypeArtifact("a.IS"));
		List<? extends IBundleMakerArtifact> artifacts = superTypeSelector.getBundleMakerArtifacts();
		
		assertArtifacts(artifacts, getTypeArtifact("a.I1"), getTypeArtifact("a.II1"));
	}
	

}
