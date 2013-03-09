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

package org.bundlemaker.core.itest.selectors;

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
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractSelectorTest extends AbstractModularizedSystemTest {

	private IRootArtifact _rootArtifact;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest#
	 * applyBasicTransformation
	 * (org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem)
	 */
	@Override
	protected void applyBasicTransformation(
			IModifiableModularizedSystem modularizedSystem) {
		// nothing
	}

	protected IRootArtifact getRootArtifact() {
		if (_rootArtifact == null) {
			IRootArtifact model = getModularizedSystem().getAnalysisModel(
					new AnalysisModelConfiguration(false,
							ProjectContentType.BINARY, false));
			_rootArtifact = model;
		}
		return _rootArtifact;
	}
	
	protected IGroupArtifact getGroupArtifact(String name) {
		IGroupArtifact groupArtifact = AnalysisModelQueries.findGroupArtifactByQualifiedName(getRootArtifact(), name);
		assertNotNull(groupArtifact);
		return groupArtifact;
	}
	
	protected IModuleArtifact getModuleArtifact(String moduleName) {
		IModuleArtifact moduleArtifact = AnalysisModelQueries.getModuleArtifact(getRootArtifact(), moduleName);
		assertNotNull(moduleArtifact);
		return moduleArtifact;
	}

	protected IPackageArtifact getPackageArtifact(String name) {
		IPackageArtifact packageArtifact = AnalysisModelQueries.findPackageArtifactByQualifiedName(getRootArtifact(), name);
		assertNotNull(packageArtifact);
		
		return packageArtifact;
	}

	protected ITypeArtifact getTypeArtifact(String name) {
		ITypeArtifact typeArtifact = AnalysisModelQueries.findTypeArtifactByQualifiedName(getRootArtifact(), name);
		assertNotNull("Type '" + name + "' not found in test model", typeArtifact);
		
		return typeArtifact;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest#before
	 * ()
	 */
	@Override
	@Before
	public void before() throws CoreException {
		super.before();

		try {
			ensureCorrectTestModel();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 
	 */
	protected abstract void ensureCorrectTestModel() throws Exception;
	
	protected void assertDependencyTo(String from, String to, DependencyKind expectedKind) {
		ITypeArtifact fromArtifact = AnalysisModelQueries.findTypeArtifactByQualifiedName(getRootArtifact(), from);
		assertNotNull("From-Artifact '" + from + "' not found in model", fromArtifact);
		
		Collection<IDependency> dependenciesTo = fromArtifact.getDependenciesTo();
		boolean found = false;
		
		for (IDependency iDependency : dependenciesTo) {
			System.out.println("dependency: " + iDependency);
			if (iDependency.getTo().getQualifiedName().equals(to)) {
				assertEquals(expectedKind, iDependency.getDependencyKind());
				found = true;
				break;
			}
		}
		assertTrue("No " + expectedKind + " Dependency found from '" + fromArtifact.getQualifiedName() + "' to '" + to,found);
	}
	
	protected void assertDependencyFrom(String from, String to, DependencyKind expectedKind) {
		ITypeArtifact fromArtifact = AnalysisModelQueries.findTypeArtifactByQualifiedName(getRootArtifact(), from);
		assertNotNull("From-Artifact '" + from + "' not found in model", fromArtifact);
		
		Collection<IDependency> dependenciesFrom = fromArtifact.getDependenciesFrom();
		boolean found = false;
		
		for (IDependency iDependency : dependenciesFrom) {
			System.out.println("dependency: " + iDependency);
//			if (iDependency.getTo().getQualifiedName().equals(to)) {
//				assertEquals(expectedKind, iDependency.getDependencyKind());
//				found = true;
//				break;
//			}
		}
//		assertTrue("No " + expectedKind + " Dependency found from '" + fromArtifact.getQualifiedName() + "' to '" + to,found);
	}
	
	protected void assertTypeArtifacts(List<? extends IBundleMakerArtifact> actual, String... expectedTypeNames) {
		assertNotNull("Artifacts must not be null", actual);
		//
		List<IBundleMakerArtifact> expectedTypes = new LinkedList<IBundleMakerArtifact>();
		
		for (String name : expectedTypeNames) {
			expectedTypes.add(getTypeArtifact(name));
		}
		
		assertArtifacts(actual, expectedTypes.toArray(new IBundleMakerArtifact[0]));
	}
	
	protected void assertArtifacts(List<? extends IBundleMakerArtifact> actual, IBundleMakerArtifact... expected) {
		assertNotNull("Artifacts must not be null", actual);
		List<IBundleMakerArtifact> expectedArtifacts = 
				new LinkedList<IBundleMakerArtifact>(
				Arrays.asList(expected));
		
		for (IBundleMakerArtifact actualArtifact : actual) {
			
			assertTrue("Artifact " + actualArtifact + " not expected",
					expectedArtifacts.remove(actualArtifact));
			
		}
		
		assertTrue("Expected Artifacts not found: " + expectedArtifacts, expectedArtifacts.isEmpty());
		
	}


}
