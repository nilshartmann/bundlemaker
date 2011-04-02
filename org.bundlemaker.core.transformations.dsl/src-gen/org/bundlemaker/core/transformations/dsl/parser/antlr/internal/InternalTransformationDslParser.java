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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTransformationDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_BMID", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'remove-from'", "';'", "'embed-into'", "'modules'", "','", "'create-module'", "'from'", "'include'", "'exclude'", "'.'"
    };
    public static final int RULE_ML_COMMENT=8;
    public static final int RULE_ID=6;
    public static final int RULE_WS=10;
    public static final int EOF=-1;
    public static final int RULE_INT=7;
    public static final int RULE_STRING=5;
    public static final int RULE_ANY_OTHER=11;
    public static final int RULE_BMID=4;
    public static final int RULE_SL_COMMENT=9;

        public InternalTransformationDslParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g"; }



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



    // $ANTLR start entryRuleTransformationModel
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:77:1: entryRuleTransformationModel returns [EObject current=null] : iv_ruleTransformationModel= ruleTransformationModel EOF ;
    public final EObject entryRuleTransformationModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransformationModel = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:78:2: (iv_ruleTransformationModel= ruleTransformationModel EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:79:2: iv_ruleTransformationModel= ruleTransformationModel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTransformationModelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTransformationModel_in_entryRuleTransformationModel75);
            iv_ruleTransformationModel=ruleTransformationModel();
            _fsp--;

             current =iv_ruleTransformationModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransformationModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleTransformationModel


    // $ANTLR start ruleTransformationModel
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:86:1: ruleTransformationModel returns [EObject current=null] : ( (lv_transformations_0_0= ruleTransformation ) )* ;
    public final EObject ruleTransformationModel() throws RecognitionException {
        EObject current = null;

        EObject lv_transformations_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:91:6: ( ( (lv_transformations_0_0= ruleTransformation ) )* )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:92:1: ( (lv_transformations_0_0= ruleTransformation ) )*
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:92:1: ( (lv_transformations_0_0= ruleTransformation ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12||LA1_0==14||LA1_0==17) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:93:1: (lv_transformations_0_0= ruleTransformation )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:93:1: (lv_transformations_0_0= ruleTransformation )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:94:3: lv_transformations_0_0= ruleTransformation
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleTransformation_in_ruleTransformationModel130);
            	    lv_transformations_0_0=ruleTransformation();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getTransformationModelRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"transformations",
            	    	        		lv_transformations_0_0, 
            	    	        		"Transformation", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleTransformationModel


    // $ANTLR start entryRuleTransformation
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:124:1: entryRuleTransformation returns [EObject current=null] : iv_ruleTransformation= ruleTransformation EOF ;
    public final EObject entryRuleTransformation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransformation = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:125:2: (iv_ruleTransformation= ruleTransformation EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:126:2: iv_ruleTransformation= ruleTransformation EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTransformationRule(), currentNode); 
            pushFollow(FOLLOW_ruleTransformation_in_entryRuleTransformation166);
            iv_ruleTransformation=ruleTransformation();
            _fsp--;

             current =iv_ruleTransformation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransformation176); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleTransformation


    // $ANTLR start ruleTransformation
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:133:1: ruleTransformation returns [EObject current=null] : (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule ) ;
    public final EObject ruleTransformation() throws RecognitionException {
        EObject current = null;

        EObject this_RemoveFrom_0 = null;

        EObject this_EmbedInto_1 = null;

        EObject this_CreateModule_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:138:6: ( (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 12:
                {
                alt2=1;
                }
                break;
            case 14:
                {
                alt2=2;
                }
                break;
            case 17:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule )", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:140:5: this_RemoveFrom_0= ruleRemoveFrom
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleRemoveFrom_in_ruleTransformation223);
                    this_RemoveFrom_0=ruleRemoveFrom();
                    _fsp--;

                     
                            current = this_RemoveFrom_0; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 2 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:150:5: this_EmbedInto_1= ruleEmbedInto
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleEmbedInto_in_ruleTransformation250);
                    this_EmbedInto_1=ruleEmbedInto();
                    _fsp--;

                     
                            current = this_EmbedInto_1; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 3 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:160:5: this_CreateModule_2= ruleCreateModule
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleCreateModule_in_ruleTransformation277);
                    this_CreateModule_2=ruleCreateModule();
                    _fsp--;

                     
                            current = this_CreateModule_2; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleTransformation


    // $ANTLR start entryRuleRemoveFrom
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:176:1: entryRuleRemoveFrom returns [EObject current=null] : iv_ruleRemoveFrom= ruleRemoveFrom EOF ;
    public final EObject entryRuleRemoveFrom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRemoveFrom = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:177:2: (iv_ruleRemoveFrom= ruleRemoveFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:178:2: iv_ruleRemoveFrom= ruleRemoveFrom EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRemoveFromRule(), currentNode); 
            pushFollow(FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom312);
            iv_ruleRemoveFrom=ruleRemoveFrom();
            _fsp--;

             current =iv_ruleRemoveFrom; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRemoveFrom322); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleRemoveFrom


    // $ANTLR start ruleRemoveFrom
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:185:1: ruleRemoveFrom returns [EObject current=null] : ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' ) ;
    public final EObject ruleRemoveFrom() throws RecognitionException {
        EObject current = null;

        EObject lv_resourceSet_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:190:6: ( ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:191:1: ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:191:1: ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:191:3: 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';'
            {
            match(input,12,FOLLOW_12_in_ruleRemoveFrom357); 

                    createLeafNode(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:195:1: ( (lv_resourceSet_1_0= ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:196:1: (lv_resourceSet_1_0= ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:196:1: (lv_resourceSet_1_0= ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:197:3: lv_resourceSet_1_0= ruleResourceSet
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleResourceSet_in_ruleRemoveFrom378);
            lv_resourceSet_1_0=ruleResourceSet();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getRemoveFromRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"resourceSet",
            	        		lv_resourceSet_1_0, 
            	        		"ResourceSet", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,13,FOLLOW_13_in_ruleRemoveFrom388); 

                    createLeafNode(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleRemoveFrom


    // $ANTLR start entryRuleEmbedInto
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:231:1: entryRuleEmbedInto returns [EObject current=null] : iv_ruleEmbedInto= ruleEmbedInto EOF ;
    public final EObject entryRuleEmbedInto() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEmbedInto = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:232:2: (iv_ruleEmbedInto= ruleEmbedInto EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:233:2: iv_ruleEmbedInto= ruleEmbedInto EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEmbedIntoRule(), currentNode); 
            pushFollow(FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto424);
            iv_ruleEmbedInto=ruleEmbedInto();
            _fsp--;

             current =iv_ruleEmbedInto; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEmbedInto434); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleEmbedInto


    // $ANTLR start ruleEmbedInto
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:240:1: ruleEmbedInto returns [EObject current=null] : ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' ) ;
    public final EObject ruleEmbedInto() throws RecognitionException {
        EObject current = null;

        EObject lv_hostModule_1_0 = null;

        EObject lv_modules_3_0 = null;

        EObject lv_modules_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:245:6: ( ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:246:1: ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:246:1: ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:246:3: 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';'
            {
            match(input,14,FOLLOW_14_in_ruleEmbedInto469); 

                    createLeafNode(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:250:1: ( (lv_hostModule_1_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:251:1: (lv_hostModule_1_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:251:1: (lv_hostModule_1_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:252:3: lv_hostModule_1_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto490);
            lv_hostModule_1_0=ruleModuleIdentifier();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"hostModule",
            	        		lv_hostModule_1_0, 
            	        		"ModuleIdentifier", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,15,FOLLOW_15_in_ruleEmbedInto500); 

                    createLeafNode(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:278:1: ( (lv_modules_3_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:279:1: (lv_modules_3_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:279:1: (lv_modules_3_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:280:3: lv_modules_3_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto521);
            lv_modules_3_0=ruleModuleIdentifier();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"modules",
            	        		lv_modules_3_0, 
            	        		"ModuleIdentifier", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:302:2: ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:302:4: ',' ( (lv_modules_5_0= ruleModuleIdentifier ) )
            	    {
            	    match(input,16,FOLLOW_16_in_ruleEmbedInto532); 

            	            createLeafNode(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0(), null); 
            	        
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:306:1: ( (lv_modules_5_0= ruleModuleIdentifier ) )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:307:1: (lv_modules_5_0= ruleModuleIdentifier )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:307:1: (lv_modules_5_0= ruleModuleIdentifier )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:308:3: lv_modules_5_0= ruleModuleIdentifier
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto553);
            	    lv_modules_5_0=ruleModuleIdentifier();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getEmbedIntoRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"modules",
            	    	        		lv_modules_5_0, 
            	    	        		"ModuleIdentifier", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match(input,13,FOLLOW_13_in_ruleEmbedInto565); 

                    createLeafNode(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleEmbedInto


    // $ANTLR start entryRuleCreateModule
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:342:1: entryRuleCreateModule returns [EObject current=null] : iv_ruleCreateModule= ruleCreateModule EOF ;
    public final EObject entryRuleCreateModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCreateModule = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:343:2: (iv_ruleCreateModule= ruleCreateModule EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:344:2: iv_ruleCreateModule= ruleCreateModule EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCreateModuleRule(), currentNode); 
            pushFollow(FOLLOW_ruleCreateModule_in_entryRuleCreateModule601);
            iv_ruleCreateModule=ruleCreateModule();
            _fsp--;

             current =iv_ruleCreateModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCreateModule611); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleCreateModule


    // $ANTLR start ruleCreateModule
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:351:1: ruleCreateModule returns [EObject current=null] : ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_from_2_0= ruleFrom ) )* ';' ) ;
    public final EObject ruleCreateModule() throws RecognitionException {
        EObject current = null;

        EObject lv_module_1_0 = null;

        EObject lv_from_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:356:6: ( ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_from_2_0= ruleFrom ) )* ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:357:1: ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_from_2_0= ruleFrom ) )* ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:357:1: ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_from_2_0= ruleFrom ) )* ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:357:3: 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_from_2_0= ruleFrom ) )* ';'
            {
            match(input,17,FOLLOW_17_in_ruleCreateModule646); 

                    createLeafNode(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:361:1: ( (lv_module_1_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:362:1: (lv_module_1_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:362:1: (lv_module_1_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:363:3: lv_module_1_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleCreateModule667);
            lv_module_1_0=ruleModuleIdentifier();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"module",
            	        		lv_module_1_0, 
            	        		"ModuleIdentifier", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:385:2: ( (lv_from_2_0= ruleFrom ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==18) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:386:1: (lv_from_2_0= ruleFrom )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:386:1: (lv_from_2_0= ruleFrom )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:387:3: lv_from_2_0= ruleFrom
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleFrom_in_ruleCreateModule688);
            	    lv_from_2_0=ruleFrom();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"from",
            	    	        		lv_from_2_0, 
            	    	        		"From", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match(input,13,FOLLOW_13_in_ruleCreateModule699); 

                    createLeafNode(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_3(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleCreateModule


    // $ANTLR start entryRuleFrom
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:421:1: entryRuleFrom returns [EObject current=null] : iv_ruleFrom= ruleFrom EOF ;
    public final EObject entryRuleFrom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFrom = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:422:2: (iv_ruleFrom= ruleFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:423:2: iv_ruleFrom= ruleFrom EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFromRule(), currentNode); 
            pushFollow(FOLLOW_ruleFrom_in_entryRuleFrom735);
            iv_ruleFrom=ruleFrom();
            _fsp--;

             current =iv_ruleFrom; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFrom745); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFrom


    // $ANTLR start ruleFrom
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:430:1: ruleFrom returns [EObject current=null] : ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ) ;
    public final EObject ruleFrom() throws RecognitionException {
        EObject current = null;

        EObject lv_resourceSet_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:435:6: ( ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:436:1: ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:436:1: ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:436:3: 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) )
            {
            match(input,18,FOLLOW_18_in_ruleFrom780); 

                    createLeafNode(grammarAccess.getFromAccess().getFromKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:440:1: ( (lv_resourceSet_1_0= ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:441:1: (lv_resourceSet_1_0= ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:441:1: (lv_resourceSet_1_0= ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:442:3: lv_resourceSet_1_0= ruleResourceSet
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleResourceSet_in_ruleFrom801);
            lv_resourceSet_1_0=ruleResourceSet();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFromRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"resourceSet",
            	        		lv_resourceSet_1_0, 
            	        		"ResourceSet", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFrom


    // $ANTLR start entryRuleResourceSet
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:472:1: entryRuleResourceSet returns [EObject current=null] : iv_ruleResourceSet= ruleResourceSet EOF ;
    public final EObject entryRuleResourceSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleResourceSet = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:473:2: (iv_ruleResourceSet= ruleResourceSet EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:474:2: iv_ruleResourceSet= ruleResourceSet EOF
            {
             currentNode = createCompositeNode(grammarAccess.getResourceSetRule(), currentNode); 
            pushFollow(FOLLOW_ruleResourceSet_in_entryRuleResourceSet837);
            iv_ruleResourceSet=ruleResourceSet();
            _fsp--;

             current =iv_ruleResourceSet; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceSet847); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleResourceSet


    // $ANTLR start ruleResourceSet
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:481:1: ruleResourceSet returns [EObject current=null] : ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? ) ;
    public final EObject ruleResourceSet() throws RecognitionException {
        EObject current = null;

        EObject lv_moduleIdentifier_0_0 = null;

        EObject lv_includeResources_2_0 = null;

        EObject lv_excludeResources_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:486:6: ( ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:487:1: ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:487:1: ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:487:2: ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )?
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:487:2: ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:488:1: (lv_moduleIdentifier_0_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:488:1: (lv_moduleIdentifier_0_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:489:3: lv_moduleIdentifier_0_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleResourceSet893);
            lv_moduleIdentifier_0_0=ruleModuleIdentifier();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"moduleIdentifier",
            	        		lv_moduleIdentifier_0_0, 
            	        		"ModuleIdentifier", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:511:2: ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:511:4: 'include' ( (lv_includeResources_2_0= ruleResourceList ) )
                    {
                    match(input,19,FOLLOW_19_in_ruleResourceSet904); 

                            createLeafNode(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0(), null); 
                        
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:515:1: ( (lv_includeResources_2_0= ruleResourceList ) )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:516:1: (lv_includeResources_2_0= ruleResourceList )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:516:1: (lv_includeResources_2_0= ruleResourceList )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:517:3: lv_includeResources_2_0= ruleResourceList
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleResourceList_in_ruleResourceSet925);
                    lv_includeResources_2_0=ruleResourceList();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"includeResources",
                    	        		lv_includeResources_2_0, 
                    	        		"ResourceList", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:539:4: ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==20) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:539:6: 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) )
                    {
                    match(input,20,FOLLOW_20_in_ruleResourceSet938); 

                            createLeafNode(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0(), null); 
                        
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:543:1: ( (lv_excludeResources_4_0= ruleResourceList ) )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:544:1: (lv_excludeResources_4_0= ruleResourceList )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:544:1: (lv_excludeResources_4_0= ruleResourceList )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:545:3: lv_excludeResources_4_0= ruleResourceList
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleResourceList_in_ruleResourceSet959);
                    lv_excludeResources_4_0=ruleResourceList();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getResourceSetRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"excludeResources",
                    	        		lv_excludeResources_4_0, 
                    	        		"ResourceList", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleResourceSet


    // $ANTLR start entryRuleMODULEID
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:575:1: entryRuleMODULEID returns [String current=null] : iv_ruleMODULEID= ruleMODULEID EOF ;
    public final String entryRuleMODULEID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMODULEID = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:576:2: (iv_ruleMODULEID= ruleMODULEID EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:577:2: iv_ruleMODULEID= ruleMODULEID EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMODULEIDRule(), currentNode); 
            pushFollow(FOLLOW_ruleMODULEID_in_entryRuleMODULEID998);
            iv_ruleMODULEID=ruleMODULEID();
            _fsp--;

             current =iv_ruleMODULEID.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMODULEID1009); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMODULEID


    // $ANTLR start ruleMODULEID
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:584:1: ruleMODULEID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* ) ;
    public final AntlrDatatypeRuleToken ruleMODULEID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_BMID_0=null;
        Token kw=null;
        Token this_BMID_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:589:6: ( (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:590:1: (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:590:1: (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:590:6: this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )*
            {
            this_BMID_0=(Token)input.LT(1);
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_ruleMODULEID1049); 

            		current.merge(this_BMID_0);
                
             
                createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:597:1: (kw= '.' this_BMID_2= RULE_BMID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==21) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:598:2: kw= '.' this_BMID_2= RULE_BMID
            	    {
            	    kw=(Token)input.LT(1);
            	    match(input,21,FOLLOW_21_in_ruleMODULEID1068); 

            	            current.merge(kw);
            	            createLeafNode(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0(), null); 
            	        
            	    this_BMID_2=(Token)input.LT(1);
            	    match(input,RULE_BMID,FOLLOW_RULE_BMID_in_ruleMODULEID1083); 

            	    		current.merge(this_BMID_2);
            	        
            	     
            	        createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1(), null); 
            	        

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }


            }

             resetLookahead(); 
            	    lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMODULEID


    // $ANTLR start entryRuleModuleIdentifier
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:618:1: entryRuleModuleIdentifier returns [EObject current=null] : iv_ruleModuleIdentifier= ruleModuleIdentifier EOF ;
    public final EObject entryRuleModuleIdentifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleIdentifier = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:619:2: (iv_ruleModuleIdentifier= ruleModuleIdentifier EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:620:2: iv_ruleModuleIdentifier= ruleModuleIdentifier EOF
            {
             currentNode = createCompositeNode(grammarAccess.getModuleIdentifierRule(), currentNode); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier1130);
            iv_ruleModuleIdentifier=ruleModuleIdentifier();
            _fsp--;

             current =iv_ruleModuleIdentifier; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleIdentifier1140); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleModuleIdentifier


    // $ANTLR start ruleModuleIdentifier
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:627:1: ruleModuleIdentifier returns [EObject current=null] : ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleModuleIdentifier() throws RecognitionException {
        EObject current = null;

        Token lv_version_1_0=null;
        AntlrDatatypeRuleToken lv_modulename_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:632:6: ( ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:633:1: ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:633:1: ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:633:2: ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:633:2: ( (lv_modulename_0_0= ruleMODULEID ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:634:1: (lv_modulename_0_0= ruleMODULEID )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:634:1: (lv_modulename_0_0= ruleMODULEID )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:635:3: lv_modulename_0_0= ruleMODULEID
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMODULEID_in_ruleModuleIdentifier1186);
            lv_modulename_0_0=ruleMODULEID();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getModuleIdentifierRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"modulename",
            	        		lv_modulename_0_0, 
            	        		"MODULEID", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:657:2: ( (lv_version_1_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:658:1: (lv_version_1_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:658:1: (lv_version_1_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:659:3: lv_version_1_0= RULE_STRING
            {
            lv_version_1_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleModuleIdentifier1203); 

            			createLeafNode(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0(), "version"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getModuleIdentifierRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"version",
            	        		lv_version_1_0, 
            	        		"STRING", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleModuleIdentifier


    // $ANTLR start entryRuleResourceList
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:689:1: entryRuleResourceList returns [EObject current=null] : iv_ruleResourceList= ruleResourceList EOF ;
    public final EObject entryRuleResourceList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleResourceList = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:690:2: (iv_ruleResourceList= ruleResourceList EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:691:2: iv_ruleResourceList= ruleResourceList EOF
            {
             currentNode = createCompositeNode(grammarAccess.getResourceListRule(), currentNode); 
            pushFollow(FOLLOW_ruleResourceList_in_entryRuleResourceList1244);
            iv_ruleResourceList=ruleResourceList();
            _fsp--;

             current =iv_ruleResourceList; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceList1254); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleResourceList


    // $ANTLR start ruleResourceList
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:698:1: ruleResourceList returns [EObject current=null] : ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* ) ;
    public final EObject ruleResourceList() throws RecognitionException {
        EObject current = null;

        Token lv_resources_0_0=null;
        Token lv_resources_2_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:703:6: ( ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:704:1: ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:704:1: ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:704:2: ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )*
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:704:2: ( (lv_resources_0_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:705:1: (lv_resources_0_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:705:1: (lv_resources_0_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:706:3: lv_resources_0_0= RULE_STRING
            {
            lv_resources_0_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleResourceList1296); 

            			createLeafNode(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0(), "resources"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getResourceListRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"resources",
            	        		lv_resources_0_0, 
            	        		"STRING", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:728:2: ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==16) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:728:4: ',' ( (lv_resources_2_0= RULE_STRING ) )
            	    {
            	    match(input,16,FOLLOW_16_in_ruleResourceList1312); 

            	            createLeafNode(grammarAccess.getResourceListAccess().getCommaKeyword_1_0(), null); 
            	        
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:732:1: ( (lv_resources_2_0= RULE_STRING ) )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:733:1: (lv_resources_2_0= RULE_STRING )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:733:1: (lv_resources_2_0= RULE_STRING )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:734:3: lv_resources_2_0= RULE_STRING
            	    {
            	    lv_resources_2_0=(Token)input.LT(1);
            	    match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleResourceList1329); 

            	    			createLeafNode(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0(), "resources"); 
            	    		

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getResourceListRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"resources",
            	    	        		lv_resources_2_0, 
            	    	        		"STRING", 
            	    	        		lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleResourceList


 

    public static final BitSet FOLLOW_ruleTransformationModel_in_entryRuleTransformationModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransformationModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransformation_in_ruleTransformationModel130 = new BitSet(new long[]{0x0000000000025002L});
    public static final BitSet FOLLOW_ruleTransformation_in_entryRuleTransformation166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransformation176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_ruleTransformation223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_ruleTransformation250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_ruleTransformation277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom312 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRemoveFrom322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleRemoveFrom357 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleResourceSet_in_ruleRemoveFrom378 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleRemoveFrom388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto424 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEmbedInto434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleEmbedInto469 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto490 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleEmbedInto500 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto521 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_16_in_ruleEmbedInto532 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto553 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_13_in_ruleEmbedInto565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_entryRuleCreateModule601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCreateModule611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleCreateModule646 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleCreateModule667 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_ruleFrom_in_ruleCreateModule688 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_13_in_ruleCreateModule699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_entryRuleFrom735 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFrom745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleFrom780 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleResourceSet_in_ruleFrom801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_entryRuleResourceSet837 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceSet847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleResourceSet893 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_19_in_ruleResourceSet904 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleResourceList_in_ruleResourceSet925 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_20_in_ruleResourceSet938 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleResourceList_in_ruleResourceSet959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_entryRuleMODULEID998 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMODULEID1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_ruleMODULEID1049 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_ruleMODULEID1068 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_BMID_in_ruleMODULEID1083 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier1130 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleIdentifier1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_ruleModuleIdentifier1186 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleModuleIdentifier1203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_entryRuleResourceList1244 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceList1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleResourceList1296 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_ruleResourceList1312 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleResourceList1329 = new BitSet(new long[]{0x0000000000010002L});

}