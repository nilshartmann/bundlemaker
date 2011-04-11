/*
* generated by Xtext
*/
grammar InternalTransformationDsl;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package org.bundlemaker.core.transformations.dsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.bundlemaker.core.transformations.dsl.parser.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parsetree.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.bundlemaker.core.transformations.dsl.services.TransformationDslGrammarAccess;

}

@parser::members {

 	private TransformationDslGrammarAccess grammarAccess;
 	
    public InternalTransformationDslParser(TokenStream input, IAstFactory factory, TransformationDslGrammarAccess grammarAccess) {
        this(input);
        this.factory = factory;
        registerRules(grammarAccess.getGrammar());
        this.grammarAccess = grammarAccess;
    }
    
    @Override
    protected InputStream getTokenFile() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.tokens");
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "TransformationModel";	
   	}
   	
   	@Override
   	protected TransformationDslGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleTransformationModel
entryRuleTransformationModel returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getTransformationModelRule(), currentNode); }
	 iv_ruleTransformationModel=ruleTransformationModel 
	 { $current=$iv_ruleTransformationModel.current; } 
	 EOF 
;

// Rule TransformationModel
ruleTransformationModel returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0(), currentNode); 
	    }
		lv_transformations_0_0=ruleTransformation		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getTransformationModelRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"transformations",
	        		lv_transformations_0_0, 
	        		"Transformation", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)*
;





// Entry rule entryRuleTransformation
entryRuleTransformation returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getTransformationRule(), currentNode); }
	 iv_ruleTransformation=ruleTransformation 
	 { $current=$iv_ruleTransformation.current; } 
	 EOF 
;

// Rule Transformation
ruleTransformation returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(
    { 
        currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0(), currentNode); 
    }
    this_RemoveFrom_0=ruleRemoveFrom
    { 
        $current = $this_RemoveFrom_0.current; 
        currentNode = currentNode.getParent();
    }

    |
    { 
        currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1(), currentNode); 
    }
    this_EmbedInto_1=ruleEmbedInto
    { 
        $current = $this_EmbedInto_1.current; 
        currentNode = currentNode.getParent();
    }

    |
    { 
        currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2(), currentNode); 
    }
    this_CreateModule_2=ruleCreateModule
    { 
        $current = $this_CreateModule_2.current; 
        currentNode = currentNode.getParent();
    }
)
;





// Entry rule entryRuleRemoveFrom
entryRuleRemoveFrom returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getRemoveFromRule(), currentNode); }
	 iv_ruleRemoveFrom=ruleRemoveFrom 
	 { $current=$iv_ruleRemoveFrom.current; } 
	 EOF 
;

// Rule RemoveFrom
ruleRemoveFrom returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(	'remove-from' 
    {
        createLeafNode(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
	    }
		lv_resourceSet_1_0=ruleResourceSet		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getRemoveFromRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"resourceSet",
	        		lv_resourceSet_1_0, 
	        		"ResourceSet", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)	';' 
    {
        createLeafNode(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2(), null); 
    }
)
;





// Entry rule entryRuleEmbedInto
entryRuleEmbedInto returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getEmbedIntoRule(), currentNode); }
	 iv_ruleEmbedInto=ruleEmbedInto 
	 { $current=$iv_ruleEmbedInto.current; } 
	 EOF 
;

// Rule EmbedInto
ruleEmbedInto returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(	'embed-into' 
    {
        createLeafNode(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
	    }
		lv_hostModule_1_0=ruleModuleIdentifier		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"hostModule",
	        		lv_hostModule_1_0, 
	        		"ModuleIdentifier", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)	'modules' 
    {
        createLeafNode(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0(), currentNode); 
	    }
		lv_modules_3_0=ruleModuleIdentifier		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"modules",
	        		lv_modules_3_0, 
	        		"ModuleIdentifier", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)(	',' 
    {
        createLeafNode(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0(), currentNode); 
	    }
		lv_modules_5_0=ruleModuleIdentifier		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"modules",
	        		lv_modules_5_0, 
	        		"ModuleIdentifier", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
))*	';' 
    {
        createLeafNode(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5(), null); 
    }
)
;





// Entry rule entryRuleCreateModule
entryRuleCreateModule returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getCreateModuleRule(), currentNode); }
	 iv_ruleCreateModule=ruleCreateModule 
	 { $current=$iv_ruleCreateModule.current; } 
	 EOF 
;

// Rule CreateModule
ruleCreateModule returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(	'create-module' 
    {
        createLeafNode(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
	    }
		lv_module_1_0=ruleModuleIdentifier		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"module",
	        		lv_module_1_0, 
	        		"ModuleIdentifier", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_2_0(), currentNode); 
	    }
		lv_from_2_0=ruleFrom		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"from",
	        		lv_from_2_0, 
	        		"From", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)*	';' 
    {
        createLeafNode(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_3(), null); 
    }
)
;





// Entry rule entryRuleFrom
entryRuleFrom returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getFromRule(), currentNode); }
	 iv_ruleFrom=ruleFrom 
	 { $current=$iv_ruleFrom.current; } 
	 EOF 
;

// Rule From
ruleFrom returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
(	'from' 
    {
        createLeafNode(grammarAccess.getFromAccess().getFromKeyword_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
	    }
		lv_resourceSet_1_0=ruleResourceSet		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getFromRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"resourceSet",
	        		lv_resourceSet_1_0, 
	        		"ResourceSet", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
))
;





// Entry rule entryRuleResourceSet
entryRuleResourceSet returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getResourceSetRule(), currentNode); }
	 iv_ruleResourceSet=ruleResourceSet 
	 { $current=$iv_ruleResourceSet.current; } 
	 EOF 
;

// Rule ResourceSet
ruleResourceSet returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
((
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0(), currentNode); 
	    }
		lv_moduleIdentifier_0_0=ruleModuleIdentifier		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"moduleIdentifier",
	        		lv_moduleIdentifier_0_0, 
	        		"ModuleIdentifier", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)(	'include' 
    {
        createLeafNode(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0(), currentNode); 
	    }
		lv_includeResources_2_0=ruleResourceList		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"includeResources",
	        		lv_includeResources_2_0, 
	        		"ResourceList", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
))?(	'exclude' 
    {
        createLeafNode(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0(), null); 
    }
(
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0(), currentNode); 
	    }
		lv_excludeResources_4_0=ruleResourceList		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"excludeResources",
	        		lv_excludeResources_4_0, 
	        		"ResourceList", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
))?)
;





// Entry rule entryRuleMODULEID
entryRuleMODULEID returns [String current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getMODULEIDRule(), currentNode); } 
	 iv_ruleMODULEID=ruleMODULEID 
	 { $current=$iv_ruleMODULEID.current.getText(); }  
	 EOF 
;

// Rule MODULEID
ruleMODULEID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
	    lastConsumedNode = currentNode;
    }:
(    this_BMID_0=RULE_BMID    {
		$current.merge(this_BMID_0);
    }

    { 
    createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0(), null); 
    }
(
	kw='.' 
    {
        $current.merge(kw);
        createLeafNode(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0(), null); 
    }
    this_BMID_2=RULE_BMID    {
		$current.merge(this_BMID_2);
    }

    { 
    createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1(), null); 
    }
)*)
    ;





// Entry rule entryRuleModuleIdentifier
entryRuleModuleIdentifier returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getModuleIdentifierRule(), currentNode); }
	 iv_ruleModuleIdentifier=ruleModuleIdentifier 
	 { $current=$iv_ruleModuleIdentifier.current; } 
	 EOF 
;

// Rule ModuleIdentifier
ruleModuleIdentifier returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
((
(
		{ 
	        currentNode=createCompositeNode(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0(), currentNode); 
	    }
		lv_modulename_0_0=ruleMODULEID		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getModuleIdentifierRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode.getParent(), $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"modulename",
	        		lv_modulename_0_0, 
	        		"MODULEID", 
	        		currentNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	        currentNode = currentNode.getParent();
	    }

)
)(
(
		lv_version_1_0=RULE_STRING
		{
			createLeafNode(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0(), "version"); 
		}
		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getModuleIdentifierRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode, $current);
	        }
	        try {
	       		set(
	       			$current, 
	       			"version",
	        		lv_version_1_0, 
	        		"STRING", 
	        		lastConsumedNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	    }

)
))
;





// Entry rule entryRuleResourceList
entryRuleResourceList returns [EObject current=null] 
	:
	{ currentNode = createCompositeNode(grammarAccess.getResourceListRule(), currentNode); }
	 iv_ruleResourceList=ruleResourceList 
	 { $current=$iv_ruleResourceList.current; } 
	 EOF 
;

// Rule ResourceList
ruleResourceList returns [EObject current=null] 
    @init { EObject temp=null; setCurrentLookahead(); resetLookahead(); 
    }
    @after { resetLookahead(); 
    	lastConsumedNode = currentNode;
    }:
((
(
		lv_resources_0_0=RULE_STRING
		{
			createLeafNode(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0(), "resources"); 
		}
		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getResourceListRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode, $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"resources",
	        		lv_resources_0_0, 
	        		"STRING", 
	        		lastConsumedNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	    }

)
)(	',' 
    {
        createLeafNode(grammarAccess.getResourceListAccess().getCommaKeyword_1_0(), null); 
    }
(
(
		lv_resources_2_0=RULE_STRING
		{
			createLeafNode(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0(), "resources"); 
		}
		{
	        if ($current==null) {
	            $current = factory.create(grammarAccess.getResourceListRule().getType().getClassifier());
	            associateNodeWithAstElement(currentNode, $current);
	        }
	        try {
	       		add(
	       			$current, 
	       			"resources",
	        		lv_resources_2_0, 
	        		"STRING", 
	        		lastConsumedNode);
	        } catch (ValueConverterException vce) {
				handleValueConverterException(vce);
	        }
	    }

)
))*)
;





RULE_BMID : '^'? ('a'..'z'|'A'..'Z'|'_'|'-') ('a'..'z'|'A'..'Z'|'_'|'-'|'0'..'9')*;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;

