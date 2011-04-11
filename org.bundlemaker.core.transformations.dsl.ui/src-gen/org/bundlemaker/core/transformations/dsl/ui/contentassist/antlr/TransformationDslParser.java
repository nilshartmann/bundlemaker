/*
* generated by Xtext
*/
package org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.bundlemaker.core.transformations.dsl.services.TransformationDslGrammarAccess;

public class TransformationDslParser extends AbstractContentAssistParser {
	
	@Inject
	private TransformationDslGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal.InternalTransformationDslParser createParser() {
		org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal.InternalTransformationDslParser result = new org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal.InternalTransformationDslParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getTransformationAccess().getAlternatives(), "rule__Transformation__Alternatives");
					put(grammarAccess.getRemoveFromAccess().getGroup(), "rule__RemoveFrom__Group__0");
					put(grammarAccess.getEmbedIntoAccess().getGroup(), "rule__EmbedInto__Group__0");
					put(grammarAccess.getEmbedIntoAccess().getGroup_4(), "rule__EmbedInto__Group_4__0");
					put(grammarAccess.getCreateModuleAccess().getGroup(), "rule__CreateModule__Group__0");
					put(grammarAccess.getFromAccess().getGroup(), "rule__From__Group__0");
					put(grammarAccess.getResourceSetAccess().getGroup(), "rule__ResourceSet__Group__0");
					put(grammarAccess.getResourceSetAccess().getGroup_1(), "rule__ResourceSet__Group_1__0");
					put(grammarAccess.getResourceSetAccess().getGroup_2(), "rule__ResourceSet__Group_2__0");
					put(grammarAccess.getMODULEIDAccess().getGroup(), "rule__MODULEID__Group__0");
					put(grammarAccess.getMODULEIDAccess().getGroup_1(), "rule__MODULEID__Group_1__0");
					put(grammarAccess.getModuleIdentifierAccess().getGroup(), "rule__ModuleIdentifier__Group__0");
					put(grammarAccess.getResourceListAccess().getGroup(), "rule__ResourceList__Group__0");
					put(grammarAccess.getResourceListAccess().getGroup_1(), "rule__ResourceList__Group_1__0");
					put(grammarAccess.getTransformationModelAccess().getTransformationsAssignment(), "rule__TransformationModel__TransformationsAssignment");
					put(grammarAccess.getRemoveFromAccess().getResourceSetAssignment_1(), "rule__RemoveFrom__ResourceSetAssignment_1");
					put(grammarAccess.getEmbedIntoAccess().getHostModuleAssignment_1(), "rule__EmbedInto__HostModuleAssignment_1");
					put(grammarAccess.getEmbedIntoAccess().getModulesAssignment_3(), "rule__EmbedInto__ModulesAssignment_3");
					put(grammarAccess.getEmbedIntoAccess().getModulesAssignment_4_1(), "rule__EmbedInto__ModulesAssignment_4_1");
					put(grammarAccess.getCreateModuleAccess().getModuleAssignment_1(), "rule__CreateModule__ModuleAssignment_1");
					put(grammarAccess.getCreateModuleAccess().getFromAssignment_2(), "rule__CreateModule__FromAssignment_2");
					put(grammarAccess.getFromAccess().getResourceSetAssignment_1(), "rule__From__ResourceSetAssignment_1");
					put(grammarAccess.getResourceSetAccess().getModuleIdentifierAssignment_0(), "rule__ResourceSet__ModuleIdentifierAssignment_0");
					put(grammarAccess.getResourceSetAccess().getIncludeResourcesAssignment_1_1(), "rule__ResourceSet__IncludeResourcesAssignment_1_1");
					put(grammarAccess.getResourceSetAccess().getExcludeResourcesAssignment_2_1(), "rule__ResourceSet__ExcludeResourcesAssignment_2_1");
					put(grammarAccess.getModuleIdentifierAccess().getModulenameAssignment_0(), "rule__ModuleIdentifier__ModulenameAssignment_0");
					put(grammarAccess.getModuleIdentifierAccess().getVersionAssignment_1(), "rule__ModuleIdentifier__VersionAssignment_1");
					put(grammarAccess.getResourceListAccess().getResourcesAssignment_0(), "rule__ResourceList__ResourcesAssignment_0");
					put(grammarAccess.getResourceListAccess().getResourcesAssignment_1_1(), "rule__ResourceList__ResourcesAssignment_1_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal.InternalTransformationDslParser typedParser = (org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal.InternalTransformationDslParser) parser;
			typedParser.entryRuleTransformationModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public TransformationDslGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(TransformationDslGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}