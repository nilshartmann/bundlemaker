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

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.selectors.SubtypesAndImplementorsSelector;
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
public class SubtypesAndImplementorsSelectorTest extends AbstractSelectorTest {

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

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.itest.selectors.AbstractSelectorTest#ensureCorrectTestModel()
	 */
	@Override
	protected void ensureCorrectTestModel() throws Exception {
		assertDependencyTo("a.A2", "a.A1", DependencyKind.EXTENDS);
		assertDependencyFrom("a.A1", "a.A2", DependencyKind.USES);
	}

	@Test
	public void oneSubclass() throws Exception {
		SubtypesAndImplementorsSelector selector = new SubtypesAndImplementorsSelector(getTypeArtifact("a.A1"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		
		assertTypeArtifacts(result, "a.A2");
	}
	
	@Test
	public void twoInheritanceLevels() throws Exception {
		SubtypesAndImplementorsSelector selector = new SubtypesAndImplementorsSelector(getTypeArtifact("a.B1"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		
		assertTypeArtifacts(result, "a.B2", "a.B3");
	}

	@Test
	public void noSubclass() throws Exception {
		SubtypesAndImplementorsSelector selector = new SubtypesAndImplementorsSelector(getTypeArtifact("a.SimpleClass"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void interfaceInheritance() throws Exception {
		SubtypesAndImplementorsSelector selector = new SubtypesAndImplementorsSelector(getTypeArtifact("a.I1"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		assertTypeArtifacts(result, "a.I2", "a.I3", "a.IS", "a.SimpleClass");
	}

}
