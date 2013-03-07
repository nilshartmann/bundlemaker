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

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelector;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.selectors.TransitiveClosureSelector;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class TransitiveClosureSelectorTest extends AbstractSelectorTest {

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.itest.selectors.AbstractSelectorTest#ensureCorrectTestModel()
	 */
	@Override
	protected void ensureCorrectTestModel() throws Exception {
	
		assertDependencyTo("a.Aa", "a.A", DependencyKind.USES);
		
	}

	
	@Override
	protected String computeTestProjectName() {
		return "ReferencesSelectorTest";
	}
	
	@Test
	public void directReference() throws Exception {
		TransitiveClosureSelector selector = new TransitiveClosureSelector(getTypeArtifact("a.Aa"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		assertTypeArtifacts(result, "a.A");
	}
	
	@Test
	public void indirectlyReferenced() throws Exception {
		TransitiveClosureSelector selector = new TransitiveClosureSelector(getTypeArtifact("a.Aaa"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		assertTypeArtifacts(result, "a.A", "a.Aa");
	}
	
	@Test
	public void multipleReferences() {
		TransitiveClosureSelector selector = new TransitiveClosureSelector(getTypeArtifact("b.AB"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		assertTypeArtifacts(result, "a.Aaa", "a.Aa", "a.A", "b.Bb", "b.B");
	}
	
	
	@Test
	public void packageReferences() {
		TransitiveClosureSelector selector = new TransitiveClosureSelector(getPackageArtifact("b"));
		List<? extends IBundleMakerArtifact> result = selector.getBundleMakerArtifacts();
		System.out.println("RESULT: " + result);
		assertTypeArtifacts(result, "a.Aaa", "a.Aa", "a.A", "b.Bb", "b.B");
	}

}
