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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_BMID", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'remove-from'", "';'", "'embed-into'", "'modules'", "','", "'create-module'", "'classify-modules'", "'but not '", "'as'", "'layer'", "'from'", "'include'", "'exclude'", "'.'"
    };
    public static final int RULE_BMID=5;
    public static final int RULE_ID=6;
    public static final int RULE_STRING=4;
    public static final int RULE_ANY_OTHER=11;
    public static final int RULE_INT=7;
    public static final int RULE_WS=10;
    public static final int RULE_SL_COMMENT=9;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=8;

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

                if ( (LA1_0==12||LA1_0==14||(LA1_0>=17 && LA1_0<=18)) ) {
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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:133:1: ruleTransformation returns [EObject current=null] : (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule | this_ClassifyModules_3= ruleClassifyModules ) ;
    public final EObject ruleTransformation() throws RecognitionException {
        EObject current = null;

        EObject this_RemoveFrom_0 = null;

        EObject this_EmbedInto_1 = null;

        EObject this_CreateModule_2 = null;

        EObject this_ClassifyModules_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:138:6: ( (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule | this_ClassifyModules_3= ruleClassifyModules ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule | this_ClassifyModules_3= ruleClassifyModules )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule | this_ClassifyModules_3= ruleClassifyModules )
            int alt2=4;
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
            case 18:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("139:1: (this_RemoveFrom_0= ruleRemoveFrom | this_EmbedInto_1= ruleEmbedInto | this_CreateModule_2= ruleCreateModule | this_ClassifyModules_3= ruleClassifyModules )", 2, 0, input);

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
                case 4 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:170:5: this_ClassifyModules_3= ruleClassifyModules
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getTransformationAccess().getClassifyModulesParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleClassifyModules_in_ruleTransformation304);
                    this_ClassifyModules_3=ruleClassifyModules();
                    _fsp--;

                     
                            current = this_ClassifyModules_3; 
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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:186:1: entryRuleRemoveFrom returns [EObject current=null] : iv_ruleRemoveFrom= ruleRemoveFrom EOF ;
    public final EObject entryRuleRemoveFrom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRemoveFrom = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:187:2: (iv_ruleRemoveFrom= ruleRemoveFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:188:2: iv_ruleRemoveFrom= ruleRemoveFrom EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRemoveFromRule(), currentNode); 
            pushFollow(FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom339);
            iv_ruleRemoveFrom=ruleRemoveFrom();
            _fsp--;

             current =iv_ruleRemoveFrom; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRemoveFrom349); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:195:1: ruleRemoveFrom returns [EObject current=null] : ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' ) ;
    public final EObject ruleRemoveFrom() throws RecognitionException {
        EObject current = null;

        EObject lv_resourceSet_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:200:6: ( ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:201:1: ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:201:1: ( 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:201:3: 'remove-from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ';'
            {
            match(input,12,FOLLOW_12_in_ruleRemoveFrom384); 

                    createLeafNode(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:205:1: ( (lv_resourceSet_1_0= ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:206:1: (lv_resourceSet_1_0= ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:206:1: (lv_resourceSet_1_0= ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:207:3: lv_resourceSet_1_0= ruleResourceSet
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleResourceSet_in_ruleRemoveFrom405);
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

            match(input,13,FOLLOW_13_in_ruleRemoveFrom415); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:241:1: entryRuleEmbedInto returns [EObject current=null] : iv_ruleEmbedInto= ruleEmbedInto EOF ;
    public final EObject entryRuleEmbedInto() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEmbedInto = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:242:2: (iv_ruleEmbedInto= ruleEmbedInto EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:243:2: iv_ruleEmbedInto= ruleEmbedInto EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEmbedIntoRule(), currentNode); 
            pushFollow(FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto451);
            iv_ruleEmbedInto=ruleEmbedInto();
            _fsp--;

             current =iv_ruleEmbedInto; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEmbedInto461); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:250:1: ruleEmbedInto returns [EObject current=null] : ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' ) ;
    public final EObject ruleEmbedInto() throws RecognitionException {
        EObject current = null;

        EObject lv_hostModule_1_0 = null;

        EObject lv_modules_3_0 = null;

        EObject lv_modules_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:255:6: ( ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:256:1: ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:256:1: ( 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:256:3: 'embed-into' ( (lv_hostModule_1_0= ruleModuleIdentifier ) ) 'modules' ( (lv_modules_3_0= ruleModuleIdentifier ) ) ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )* ';'
            {
            match(input,14,FOLLOW_14_in_ruleEmbedInto496); 

                    createLeafNode(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:260:1: ( (lv_hostModule_1_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:261:1: (lv_hostModule_1_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:261:1: (lv_hostModule_1_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:262:3: lv_hostModule_1_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto517);
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

            match(input,15,FOLLOW_15_in_ruleEmbedInto527); 

                    createLeafNode(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:288:1: ( (lv_modules_3_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:289:1: (lv_modules_3_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:289:1: (lv_modules_3_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:290:3: lv_modules_3_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto548);
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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:312:2: ( ',' ( (lv_modules_5_0= ruleModuleIdentifier ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:312:4: ',' ( (lv_modules_5_0= ruleModuleIdentifier ) )
            	    {
            	    match(input,16,FOLLOW_16_in_ruleEmbedInto559); 

            	            createLeafNode(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0(), null); 
            	        
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:316:1: ( (lv_modules_5_0= ruleModuleIdentifier ) )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:317:1: (lv_modules_5_0= ruleModuleIdentifier )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:317:1: (lv_modules_5_0= ruleModuleIdentifier )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:318:3: lv_modules_5_0= ruleModuleIdentifier
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto580);
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

            match(input,13,FOLLOW_13_in_ruleEmbedInto592); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:352:1: entryRuleCreateModule returns [EObject current=null] : iv_ruleCreateModule= ruleCreateModule EOF ;
    public final EObject entryRuleCreateModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCreateModule = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:353:2: (iv_ruleCreateModule= ruleCreateModule EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:354:2: iv_ruleCreateModule= ruleCreateModule EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCreateModuleRule(), currentNode); 
            pushFollow(FOLLOW_ruleCreateModule_in_entryRuleCreateModule628);
            iv_ruleCreateModule=ruleCreateModule();
            _fsp--;

             current =iv_ruleCreateModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCreateModule638); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:361:1: ruleCreateModule returns [EObject current=null] : ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_layer_2_0= ruleLayer ) )? ( (lv_from_3_0= ruleFrom ) )* ';' ) ;
    public final EObject ruleCreateModule() throws RecognitionException {
        EObject current = null;

        EObject lv_module_1_0 = null;

        EObject lv_layer_2_0 = null;

        EObject lv_from_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:366:6: ( ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_layer_2_0= ruleLayer ) )? ( (lv_from_3_0= ruleFrom ) )* ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:367:1: ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_layer_2_0= ruleLayer ) )? ( (lv_from_3_0= ruleFrom ) )* ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:367:1: ( 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_layer_2_0= ruleLayer ) )? ( (lv_from_3_0= ruleFrom ) )* ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:367:3: 'create-module' ( (lv_module_1_0= ruleModuleIdentifier ) ) ( (lv_layer_2_0= ruleLayer ) )? ( (lv_from_3_0= ruleFrom ) )* ';'
            {
            match(input,17,FOLLOW_17_in_ruleCreateModule673); 

                    createLeafNode(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:371:1: ( (lv_module_1_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:372:1: (lv_module_1_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:372:1: (lv_module_1_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:373:3: lv_module_1_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleCreateModule694);
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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:395:2: ( (lv_layer_2_0= ruleLayer ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==21) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:396:1: (lv_layer_2_0= ruleLayer )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:396:1: (lv_layer_2_0= ruleLayer )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:397:3: lv_layer_2_0= ruleLayer
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getLayerLayerParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLayer_in_ruleCreateModule715);
                    lv_layer_2_0=ruleLayer();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"layer",
                    	        		lv_layer_2_0, 
                    	        		"Layer", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }
                    break;

            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:419:3: ( (lv_from_3_0= ruleFrom ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==22) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:420:1: (lv_from_3_0= ruleFrom )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:420:1: (lv_from_3_0= ruleFrom )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:421:3: lv_from_3_0= ruleFrom
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_3_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleFrom_in_ruleCreateModule737);
            	    lv_from_3_0=ruleFrom();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getCreateModuleRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"from",
            	    	        		lv_from_3_0, 
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
            	    break loop5;
                }
            } while (true);

            match(input,13,FOLLOW_13_in_ruleCreateModule748); 

                    createLeafNode(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_4(), null); 
                

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


    // $ANTLR start entryRuleClassifyModules
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:455:1: entryRuleClassifyModules returns [EObject current=null] : iv_ruleClassifyModules= ruleClassifyModules EOF ;
    public final EObject entryRuleClassifyModules() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifyModules = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:456:2: (iv_ruleClassifyModules= ruleClassifyModules EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:457:2: iv_ruleClassifyModules= ruleClassifyModules EOF
            {
             currentNode = createCompositeNode(grammarAccess.getClassifyModulesRule(), currentNode); 
            pushFollow(FOLLOW_ruleClassifyModules_in_entryRuleClassifyModules784);
            iv_ruleClassifyModules=ruleClassifyModules();
            _fsp--;

             current =iv_ruleClassifyModules; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleClassifyModules794); 

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
    // $ANTLR end entryRuleClassifyModules


    // $ANTLR start ruleClassifyModules
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:464:1: ruleClassifyModules returns [EObject current=null] : ( 'classify-modules' ( (lv_modules_1_0= RULE_STRING ) ) ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )? 'as' ( (lv_classification_5_0= RULE_STRING ) ) ';' ) ;
    public final EObject ruleClassifyModules() throws RecognitionException {
        EObject current = null;

        Token lv_modules_1_0=null;
        Token lv_excludedModules_3_0=null;
        Token lv_classification_5_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:469:6: ( ( 'classify-modules' ( (lv_modules_1_0= RULE_STRING ) ) ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )? 'as' ( (lv_classification_5_0= RULE_STRING ) ) ';' ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:470:1: ( 'classify-modules' ( (lv_modules_1_0= RULE_STRING ) ) ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )? 'as' ( (lv_classification_5_0= RULE_STRING ) ) ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:470:1: ( 'classify-modules' ( (lv_modules_1_0= RULE_STRING ) ) ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )? 'as' ( (lv_classification_5_0= RULE_STRING ) ) ';' )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:470:3: 'classify-modules' ( (lv_modules_1_0= RULE_STRING ) ) ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )? 'as' ( (lv_classification_5_0= RULE_STRING ) ) ';'
            {
            match(input,18,FOLLOW_18_in_ruleClassifyModules829); 

                    createLeafNode(grammarAccess.getClassifyModulesAccess().getClassifyModulesKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:474:1: ( (lv_modules_1_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:475:1: (lv_modules_1_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:475:1: (lv_modules_1_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:476:3: lv_modules_1_0= RULE_STRING
            {
            lv_modules_1_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleClassifyModules846); 

            			createLeafNode(grammarAccess.getClassifyModulesAccess().getModulesSTRINGTerminalRuleCall_1_0(), "modules"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getClassifyModulesRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"modules",
            	        		lv_modules_1_0, 
            	        		"STRING", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:498:2: ( 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==19) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:498:4: 'but not ' ( (lv_excludedModules_3_0= RULE_STRING ) )
                    {
                    match(input,19,FOLLOW_19_in_ruleClassifyModules862); 

                            createLeafNode(grammarAccess.getClassifyModulesAccess().getButNotKeyword_2_0(), null); 
                        
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:502:1: ( (lv_excludedModules_3_0= RULE_STRING ) )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:503:1: (lv_excludedModules_3_0= RULE_STRING )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:503:1: (lv_excludedModules_3_0= RULE_STRING )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:504:3: lv_excludedModules_3_0= RULE_STRING
                    {
                    lv_excludedModules_3_0=(Token)input.LT(1);
                    match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleClassifyModules879); 

                    			createLeafNode(grammarAccess.getClassifyModulesAccess().getExcludedModulesSTRINGTerminalRuleCall_2_1_0(), "excludedModules"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getClassifyModulesRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"excludedModules",
                    	        		lv_excludedModules_3_0, 
                    	        		"STRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;

            }

            match(input,20,FOLLOW_20_in_ruleClassifyModules896); 

                    createLeafNode(grammarAccess.getClassifyModulesAccess().getAsKeyword_3(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:530:1: ( (lv_classification_5_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:531:1: (lv_classification_5_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:531:1: (lv_classification_5_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:532:3: lv_classification_5_0= RULE_STRING
            {
            lv_classification_5_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleClassifyModules913); 

            			createLeafNode(grammarAccess.getClassifyModulesAccess().getClassificationSTRINGTerminalRuleCall_4_0(), "classification"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getClassifyModulesRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"classification",
            	        		lv_classification_5_0, 
            	        		"STRING", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            match(input,13,FOLLOW_13_in_ruleClassifyModules928); 

                    createLeafNode(grammarAccess.getClassifyModulesAccess().getSemicolonKeyword_5(), null); 
                

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
    // $ANTLR end ruleClassifyModules


    // $ANTLR start entryRuleLayer
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:566:1: entryRuleLayer returns [EObject current=null] : iv_ruleLayer= ruleLayer EOF ;
    public final EObject entryRuleLayer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLayer = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:567:2: (iv_ruleLayer= ruleLayer EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:568:2: iv_ruleLayer= ruleLayer EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLayerRule(), currentNode); 
            pushFollow(FOLLOW_ruleLayer_in_entryRuleLayer964);
            iv_ruleLayer=ruleLayer();
            _fsp--;

             current =iv_ruleLayer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLayer974); 

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
    // $ANTLR end entryRuleLayer


    // $ANTLR start ruleLayer
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:575:1: ruleLayer returns [EObject current=null] : ( 'layer' ( (lv_layer_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleLayer() throws RecognitionException {
        EObject current = null;

        Token lv_layer_1_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:580:6: ( ( 'layer' ( (lv_layer_1_0= RULE_STRING ) ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:581:1: ( 'layer' ( (lv_layer_1_0= RULE_STRING ) ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:581:1: ( 'layer' ( (lv_layer_1_0= RULE_STRING ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:581:3: 'layer' ( (lv_layer_1_0= RULE_STRING ) )
            {
            match(input,21,FOLLOW_21_in_ruleLayer1009); 

                    createLeafNode(grammarAccess.getLayerAccess().getLayerKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:585:1: ( (lv_layer_1_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:586:1: (lv_layer_1_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:586:1: (lv_layer_1_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:587:3: lv_layer_1_0= RULE_STRING
            {
            lv_layer_1_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleLayer1026); 

            			createLeafNode(grammarAccess.getLayerAccess().getLayerSTRINGTerminalRuleCall_1_0(), "layer"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getLayerRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"layer",
            	        		lv_layer_1_0, 
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
    // $ANTLR end ruleLayer


    // $ANTLR start entryRuleFrom
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:617:1: entryRuleFrom returns [EObject current=null] : iv_ruleFrom= ruleFrom EOF ;
    public final EObject entryRuleFrom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFrom = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:618:2: (iv_ruleFrom= ruleFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:619:2: iv_ruleFrom= ruleFrom EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFromRule(), currentNode); 
            pushFollow(FOLLOW_ruleFrom_in_entryRuleFrom1067);
            iv_ruleFrom=ruleFrom();
            _fsp--;

             current =iv_ruleFrom; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFrom1077); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:626:1: ruleFrom returns [EObject current=null] : ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ) ;
    public final EObject ruleFrom() throws RecognitionException {
        EObject current = null;

        EObject lv_resourceSet_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:631:6: ( ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:632:1: ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:632:1: ( 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:632:3: 'from' ( (lv_resourceSet_1_0= ruleResourceSet ) )
            {
            match(input,22,FOLLOW_22_in_ruleFrom1112); 

                    createLeafNode(grammarAccess.getFromAccess().getFromKeyword_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:636:1: ( (lv_resourceSet_1_0= ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:637:1: (lv_resourceSet_1_0= ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:637:1: (lv_resourceSet_1_0= ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:638:3: lv_resourceSet_1_0= ruleResourceSet
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleResourceSet_in_ruleFrom1133);
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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:668:1: entryRuleResourceSet returns [EObject current=null] : iv_ruleResourceSet= ruleResourceSet EOF ;
    public final EObject entryRuleResourceSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleResourceSet = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:669:2: (iv_ruleResourceSet= ruleResourceSet EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:670:2: iv_ruleResourceSet= ruleResourceSet EOF
            {
             currentNode = createCompositeNode(grammarAccess.getResourceSetRule(), currentNode); 
            pushFollow(FOLLOW_ruleResourceSet_in_entryRuleResourceSet1169);
            iv_ruleResourceSet=ruleResourceSet();
            _fsp--;

             current =iv_ruleResourceSet; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceSet1179); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:677:1: ruleResourceSet returns [EObject current=null] : ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? ) ;
    public final EObject ruleResourceSet() throws RecognitionException {
        EObject current = null;

        EObject lv_moduleIdentifier_0_0 = null;

        EObject lv_includeResources_2_0 = null;

        EObject lv_excludeResources_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:682:6: ( ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:683:1: ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:683:1: ( ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )? )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:683:2: ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) ) ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )? ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )?
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:683:2: ( (lv_moduleIdentifier_0_0= ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:684:1: (lv_moduleIdentifier_0_0= ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:684:1: (lv_moduleIdentifier_0_0= ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:685:3: lv_moduleIdentifier_0_0= ruleModuleIdentifier
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleModuleIdentifier_in_ruleResourceSet1225);
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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:707:2: ( 'include' ( (lv_includeResources_2_0= ruleResourceList ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==23) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:707:4: 'include' ( (lv_includeResources_2_0= ruleResourceList ) )
                    {
                    match(input,23,FOLLOW_23_in_ruleResourceSet1236); 

                            createLeafNode(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0(), null); 
                        
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:711:1: ( (lv_includeResources_2_0= ruleResourceList ) )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:712:1: (lv_includeResources_2_0= ruleResourceList )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:712:1: (lv_includeResources_2_0= ruleResourceList )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:713:3: lv_includeResources_2_0= ruleResourceList
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleResourceList_in_ruleResourceSet1257);
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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:735:4: ( 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==24) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:735:6: 'exclude' ( (lv_excludeResources_4_0= ruleResourceList ) )
                    {
                    match(input,24,FOLLOW_24_in_ruleResourceSet1270); 

                            createLeafNode(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0(), null); 
                        
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:739:1: ( (lv_excludeResources_4_0= ruleResourceList ) )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:740:1: (lv_excludeResources_4_0= ruleResourceList )
                    {
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:740:1: (lv_excludeResources_4_0= ruleResourceList )
                    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:741:3: lv_excludeResources_4_0= ruleResourceList
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleResourceList_in_ruleResourceSet1291);
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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:771:1: entryRuleMODULEID returns [String current=null] : iv_ruleMODULEID= ruleMODULEID EOF ;
    public final String entryRuleMODULEID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMODULEID = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:772:2: (iv_ruleMODULEID= ruleMODULEID EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:773:2: iv_ruleMODULEID= ruleMODULEID EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMODULEIDRule(), currentNode); 
            pushFollow(FOLLOW_ruleMODULEID_in_entryRuleMODULEID1330);
            iv_ruleMODULEID=ruleMODULEID();
            _fsp--;

             current =iv_ruleMODULEID.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMODULEID1341); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:780:1: ruleMODULEID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* ) ;
    public final AntlrDatatypeRuleToken ruleMODULEID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_BMID_0=null;
        Token kw=null;
        Token this_BMID_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:785:6: ( (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:786:1: (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:786:1: (this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )* )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:786:6: this_BMID_0= RULE_BMID (kw= '.' this_BMID_2= RULE_BMID )*
            {
            this_BMID_0=(Token)input.LT(1);
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_ruleMODULEID1381); 

            		current.merge(this_BMID_0);
                
             
                createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0(), null); 
                
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:793:1: (kw= '.' this_BMID_2= RULE_BMID )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==25) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:794:2: kw= '.' this_BMID_2= RULE_BMID
            	    {
            	    kw=(Token)input.LT(1);
            	    match(input,25,FOLLOW_25_in_ruleMODULEID1400); 

            	            current.merge(kw);
            	            createLeafNode(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0(), null); 
            	        
            	    this_BMID_2=(Token)input.LT(1);
            	    match(input,RULE_BMID,FOLLOW_RULE_BMID_in_ruleMODULEID1415); 

            	    		current.merge(this_BMID_2);
            	        
            	     
            	        createLeafNode(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1(), null); 
            	        

            	    }
            	    break;

            	default :
            	    break loop9;
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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:814:1: entryRuleModuleIdentifier returns [EObject current=null] : iv_ruleModuleIdentifier= ruleModuleIdentifier EOF ;
    public final EObject entryRuleModuleIdentifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleIdentifier = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:815:2: (iv_ruleModuleIdentifier= ruleModuleIdentifier EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:816:2: iv_ruleModuleIdentifier= ruleModuleIdentifier EOF
            {
             currentNode = createCompositeNode(grammarAccess.getModuleIdentifierRule(), currentNode); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier1462);
            iv_ruleModuleIdentifier=ruleModuleIdentifier();
            _fsp--;

             current =iv_ruleModuleIdentifier; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleIdentifier1472); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:823:1: ruleModuleIdentifier returns [EObject current=null] : ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleModuleIdentifier() throws RecognitionException {
        EObject current = null;

        Token lv_version_1_0=null;
        AntlrDatatypeRuleToken lv_modulename_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:828:6: ( ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:829:1: ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:829:1: ( ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:829:2: ( (lv_modulename_0_0= ruleMODULEID ) ) ( (lv_version_1_0= RULE_STRING ) )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:829:2: ( (lv_modulename_0_0= ruleMODULEID ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:830:1: (lv_modulename_0_0= ruleMODULEID )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:830:1: (lv_modulename_0_0= ruleMODULEID )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:831:3: lv_modulename_0_0= ruleMODULEID
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMODULEID_in_ruleModuleIdentifier1518);
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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:853:2: ( (lv_version_1_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:854:1: (lv_version_1_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:854:1: (lv_version_1_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:855:3: lv_version_1_0= RULE_STRING
            {
            lv_version_1_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleModuleIdentifier1535); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:885:1: entryRuleResourceList returns [EObject current=null] : iv_ruleResourceList= ruleResourceList EOF ;
    public final EObject entryRuleResourceList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleResourceList = null;


        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:886:2: (iv_ruleResourceList= ruleResourceList EOF )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:887:2: iv_ruleResourceList= ruleResourceList EOF
            {
             currentNode = createCompositeNode(grammarAccess.getResourceListRule(), currentNode); 
            pushFollow(FOLLOW_ruleResourceList_in_entryRuleResourceList1576);
            iv_ruleResourceList=ruleResourceList();
            _fsp--;

             current =iv_ruleResourceList; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceList1586); 

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
    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:894:1: ruleResourceList returns [EObject current=null] : ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* ) ;
    public final EObject ruleResourceList() throws RecognitionException {
        EObject current = null;

        Token lv_resources_0_0=null;
        Token lv_resources_2_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:899:6: ( ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:900:1: ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:900:1: ( ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )* )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:900:2: ( (lv_resources_0_0= RULE_STRING ) ) ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )*
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:900:2: ( (lv_resources_0_0= RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:901:1: (lv_resources_0_0= RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:901:1: (lv_resources_0_0= RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:902:3: lv_resources_0_0= RULE_STRING
            {
            lv_resources_0_0=(Token)input.LT(1);
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleResourceList1628); 

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

            // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:924:2: ( ',' ( (lv_resources_2_0= RULE_STRING ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==16) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:924:4: ',' ( (lv_resources_2_0= RULE_STRING ) )
            	    {
            	    match(input,16,FOLLOW_16_in_ruleResourceList1644); 

            	            createLeafNode(grammarAccess.getResourceListAccess().getCommaKeyword_1_0(), null); 
            	        
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:928:1: ( (lv_resources_2_0= RULE_STRING ) )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:929:1: (lv_resources_2_0= RULE_STRING )
            	    {
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:929:1: (lv_resources_2_0= RULE_STRING )
            	    // ../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g:930:3: lv_resources_2_0= RULE_STRING
            	    {
            	    lv_resources_2_0=(Token)input.LT(1);
            	    match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleResourceList1661); 

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
            	    break loop10;
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
    public static final BitSet FOLLOW_ruleTransformation_in_ruleTransformationModel130 = new BitSet(new long[]{0x0000000000065002L});
    public static final BitSet FOLLOW_ruleTransformation_in_entryRuleTransformation166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransformation176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_ruleTransformation223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_ruleTransformation250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_ruleTransformation277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClassifyModules_in_ruleTransformation304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom339 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRemoveFrom349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleRemoveFrom384 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleResourceSet_in_ruleRemoveFrom405 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleRemoveFrom415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto451 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEmbedInto461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleEmbedInto496 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto517 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleEmbedInto527 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto548 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_16_in_ruleEmbedInto559 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleEmbedInto580 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_13_in_ruleEmbedInto592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_entryRuleCreateModule628 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCreateModule638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleCreateModule673 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleCreateModule694 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_ruleLayer_in_ruleCreateModule715 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_ruleFrom_in_ruleCreateModule737 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_13_in_ruleCreateModule748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClassifyModules_in_entryRuleClassifyModules784 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleClassifyModules794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleClassifyModules829 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleClassifyModules846 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_19_in_ruleClassifyModules862 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleClassifyModules879 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleClassifyModules896 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleClassifyModules913 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleClassifyModules928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLayer_in_entryRuleLayer964 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLayer974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleLayer1009 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleLayer1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_entryRuleFrom1067 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFrom1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleFrom1112 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleResourceSet_in_ruleFrom1133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_entryRuleResourceSet1169 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceSet1179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_ruleResourceSet1225 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_23_in_ruleResourceSet1236 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleResourceList_in_ruleResourceSet1257 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_ruleResourceSet1270 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleResourceList_in_ruleResourceSet1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_entryRuleMODULEID1330 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMODULEID1341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_ruleMODULEID1381 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_ruleMODULEID1400 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_BMID_in_ruleMODULEID1415 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier1462 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleIdentifier1472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_ruleModuleIdentifier1518 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleModuleIdentifier1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_entryRuleResourceList1576 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceList1586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleResourceList1628 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_ruleResourceList1644 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleResourceList1661 = new BitSet(new long[]{0x0000000000010002L});

}