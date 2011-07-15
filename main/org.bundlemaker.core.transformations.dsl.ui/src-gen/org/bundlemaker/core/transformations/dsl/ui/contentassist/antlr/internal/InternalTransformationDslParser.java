package org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parsetree.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import org.bundlemaker.core.transformations.dsl.services.TransformationDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTransformationDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_BMID", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'remove-from'", "';'", "'embed-into'", "'modules'", "','", "'create-module'", "'classify-modules'", "'as'", "'but not '", "'layer'", "'from'", "'include'", "'exclude'", "'.'"
    };
    public static final int RULE_BMID=4;
    public static final int RULE_ID=6;
    public static final int RULE_STRING=5;
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
    public String getGrammarFileName() { return "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g"; }


     
     	private TransformationDslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(TransformationDslGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start entryRuleTransformationModel
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:61:1: entryRuleTransformationModel : ruleTransformationModel EOF ;
    public final void entryRuleTransformationModel() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:62:1: ( ruleTransformationModel EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:63:1: ruleTransformationModel EOF
            {
             before(grammarAccess.getTransformationModelRule()); 
            pushFollow(FOLLOW_ruleTransformationModel_in_entryRuleTransformationModel61);
            ruleTransformationModel();
            _fsp--;

             after(grammarAccess.getTransformationModelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransformationModel68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleTransformationModel


    // $ANTLR start ruleTransformationModel
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:70:1: ruleTransformationModel : ( ( rule__TransformationModel__TransformationsAssignment )* ) ;
    public final void ruleTransformationModel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:74:2: ( ( ( rule__TransformationModel__TransformationsAssignment )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:75:1: ( ( rule__TransformationModel__TransformationsAssignment )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:75:1: ( ( rule__TransformationModel__TransformationsAssignment )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:76:1: ( rule__TransformationModel__TransformationsAssignment )*
            {
             before(grammarAccess.getTransformationModelAccess().getTransformationsAssignment()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:77:1: ( rule__TransformationModel__TransformationsAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12||LA1_0==14||(LA1_0>=17 && LA1_0<=18)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:77:2: rule__TransformationModel__TransformationsAssignment
            	    {
            	    pushFollow(FOLLOW_rule__TransformationModel__TransformationsAssignment_in_ruleTransformationModel94);
            	    rule__TransformationModel__TransformationsAssignment();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getTransformationModelAccess().getTransformationsAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleTransformationModel


    // $ANTLR start entryRuleTransformation
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:89:1: entryRuleTransformation : ruleTransformation EOF ;
    public final void entryRuleTransformation() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:90:1: ( ruleTransformation EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:91:1: ruleTransformation EOF
            {
             before(grammarAccess.getTransformationRule()); 
            pushFollow(FOLLOW_ruleTransformation_in_entryRuleTransformation122);
            ruleTransformation();
            _fsp--;

             after(grammarAccess.getTransformationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransformation129); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleTransformation


    // $ANTLR start ruleTransformation
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:98:1: ruleTransformation : ( ( rule__Transformation__Alternatives ) ) ;
    public final void ruleTransformation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:102:2: ( ( ( rule__Transformation__Alternatives ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:103:1: ( ( rule__Transformation__Alternatives ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:103:1: ( ( rule__Transformation__Alternatives ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:104:1: ( rule__Transformation__Alternatives )
            {
             before(grammarAccess.getTransformationAccess().getAlternatives()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:105:1: ( rule__Transformation__Alternatives )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:105:2: rule__Transformation__Alternatives
            {
            pushFollow(FOLLOW_rule__Transformation__Alternatives_in_ruleTransformation155);
            rule__Transformation__Alternatives();
            _fsp--;


            }

             after(grammarAccess.getTransformationAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleTransformation


    // $ANTLR start entryRuleRemoveFrom
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:117:1: entryRuleRemoveFrom : ruleRemoveFrom EOF ;
    public final void entryRuleRemoveFrom() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:118:1: ( ruleRemoveFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:119:1: ruleRemoveFrom EOF
            {
             before(grammarAccess.getRemoveFromRule()); 
            pushFollow(FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom182);
            ruleRemoveFrom();
            _fsp--;

             after(grammarAccess.getRemoveFromRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRemoveFrom189); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleRemoveFrom


    // $ANTLR start ruleRemoveFrom
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:126:1: ruleRemoveFrom : ( ( rule__RemoveFrom__Group__0 ) ) ;
    public final void ruleRemoveFrom() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:130:2: ( ( ( rule__RemoveFrom__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:131:1: ( ( rule__RemoveFrom__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:131:1: ( ( rule__RemoveFrom__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:132:1: ( rule__RemoveFrom__Group__0 )
            {
             before(grammarAccess.getRemoveFromAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:133:1: ( rule__RemoveFrom__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:133:2: rule__RemoveFrom__Group__0
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__0_in_ruleRemoveFrom215);
            rule__RemoveFrom__Group__0();
            _fsp--;


            }

             after(grammarAccess.getRemoveFromAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleRemoveFrom


    // $ANTLR start entryRuleEmbedInto
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:145:1: entryRuleEmbedInto : ruleEmbedInto EOF ;
    public final void entryRuleEmbedInto() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:146:1: ( ruleEmbedInto EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:147:1: ruleEmbedInto EOF
            {
             before(grammarAccess.getEmbedIntoRule()); 
            pushFollow(FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto242);
            ruleEmbedInto();
            _fsp--;

             after(grammarAccess.getEmbedIntoRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEmbedInto249); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleEmbedInto


    // $ANTLR start ruleEmbedInto
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:154:1: ruleEmbedInto : ( ( rule__EmbedInto__Group__0 ) ) ;
    public final void ruleEmbedInto() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:158:2: ( ( ( rule__EmbedInto__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:159:1: ( ( rule__EmbedInto__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:159:1: ( ( rule__EmbedInto__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:160:1: ( rule__EmbedInto__Group__0 )
            {
             before(grammarAccess.getEmbedIntoAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:161:1: ( rule__EmbedInto__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:161:2: rule__EmbedInto__Group__0
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__0_in_ruleEmbedInto275);
            rule__EmbedInto__Group__0();
            _fsp--;


            }

             after(grammarAccess.getEmbedIntoAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleEmbedInto


    // $ANTLR start entryRuleCreateModule
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:173:1: entryRuleCreateModule : ruleCreateModule EOF ;
    public final void entryRuleCreateModule() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:174:1: ( ruleCreateModule EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:175:1: ruleCreateModule EOF
            {
             before(grammarAccess.getCreateModuleRule()); 
            pushFollow(FOLLOW_ruleCreateModule_in_entryRuleCreateModule302);
            ruleCreateModule();
            _fsp--;

             after(grammarAccess.getCreateModuleRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCreateModule309); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleCreateModule


    // $ANTLR start ruleCreateModule
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:182:1: ruleCreateModule : ( ( rule__CreateModule__Group__0 ) ) ;
    public final void ruleCreateModule() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:186:2: ( ( ( rule__CreateModule__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:187:1: ( ( rule__CreateModule__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:187:1: ( ( rule__CreateModule__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:188:1: ( rule__CreateModule__Group__0 )
            {
             before(grammarAccess.getCreateModuleAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:189:1: ( rule__CreateModule__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:189:2: rule__CreateModule__Group__0
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__0_in_ruleCreateModule335);
            rule__CreateModule__Group__0();
            _fsp--;


            }

             after(grammarAccess.getCreateModuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleCreateModule


    // $ANTLR start entryRuleClassifyModules
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:201:1: entryRuleClassifyModules : ruleClassifyModules EOF ;
    public final void entryRuleClassifyModules() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:202:1: ( ruleClassifyModules EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:203:1: ruleClassifyModules EOF
            {
             before(grammarAccess.getClassifyModulesRule()); 
            pushFollow(FOLLOW_ruleClassifyModules_in_entryRuleClassifyModules362);
            ruleClassifyModules();
            _fsp--;

             after(grammarAccess.getClassifyModulesRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleClassifyModules369); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleClassifyModules


    // $ANTLR start ruleClassifyModules
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:210:1: ruleClassifyModules : ( ( rule__ClassifyModules__Group__0 ) ) ;
    public final void ruleClassifyModules() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:214:2: ( ( ( rule__ClassifyModules__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__ClassifyModules__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__ClassifyModules__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:216:1: ( rule__ClassifyModules__Group__0 )
            {
             before(grammarAccess.getClassifyModulesAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:1: ( rule__ClassifyModules__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:2: rule__ClassifyModules__Group__0
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__0_in_ruleClassifyModules395);
            rule__ClassifyModules__Group__0();
            _fsp--;


            }

             after(grammarAccess.getClassifyModulesAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleClassifyModules


    // $ANTLR start entryRuleLayer
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:229:1: entryRuleLayer : ruleLayer EOF ;
    public final void entryRuleLayer() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:230:1: ( ruleLayer EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:231:1: ruleLayer EOF
            {
             before(grammarAccess.getLayerRule()); 
            pushFollow(FOLLOW_ruleLayer_in_entryRuleLayer422);
            ruleLayer();
            _fsp--;

             after(grammarAccess.getLayerRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLayer429); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleLayer


    // $ANTLR start ruleLayer
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:238:1: ruleLayer : ( ( rule__Layer__Group__0 ) ) ;
    public final void ruleLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:242:2: ( ( ( rule__Layer__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__Layer__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__Layer__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:244:1: ( rule__Layer__Group__0 )
            {
             before(grammarAccess.getLayerAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:1: ( rule__Layer__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:2: rule__Layer__Group__0
            {
            pushFollow(FOLLOW_rule__Layer__Group__0_in_ruleLayer455);
            rule__Layer__Group__0();
            _fsp--;


            }

             after(grammarAccess.getLayerAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleLayer


    // $ANTLR start entryRuleFrom
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:257:1: entryRuleFrom : ruleFrom EOF ;
    public final void entryRuleFrom() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:258:1: ( ruleFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:259:1: ruleFrom EOF
            {
             before(grammarAccess.getFromRule()); 
            pushFollow(FOLLOW_ruleFrom_in_entryRuleFrom482);
            ruleFrom();
            _fsp--;

             after(grammarAccess.getFromRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFrom489); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleFrom


    // $ANTLR start ruleFrom
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:266:1: ruleFrom : ( ( rule__From__Group__0 ) ) ;
    public final void ruleFrom() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:270:2: ( ( ( rule__From__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__From__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__From__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:272:1: ( rule__From__Group__0 )
            {
             before(grammarAccess.getFromAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:1: ( rule__From__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:2: rule__From__Group__0
            {
            pushFollow(FOLLOW_rule__From__Group__0_in_ruleFrom515);
            rule__From__Group__0();
            _fsp--;


            }

             after(grammarAccess.getFromAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleFrom


    // $ANTLR start entryRuleResourceSet
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:285:1: entryRuleResourceSet : ruleResourceSet EOF ;
    public final void entryRuleResourceSet() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:286:1: ( ruleResourceSet EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:287:1: ruleResourceSet EOF
            {
             before(grammarAccess.getResourceSetRule()); 
            pushFollow(FOLLOW_ruleResourceSet_in_entryRuleResourceSet542);
            ruleResourceSet();
            _fsp--;

             after(grammarAccess.getResourceSetRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceSet549); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleResourceSet


    // $ANTLR start ruleResourceSet
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:294:1: ruleResourceSet : ( ( rule__ResourceSet__Group__0 ) ) ;
    public final void ruleResourceSet() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:298:2: ( ( ( rule__ResourceSet__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__ResourceSet__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__ResourceSet__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:300:1: ( rule__ResourceSet__Group__0 )
            {
             before(grammarAccess.getResourceSetAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:1: ( rule__ResourceSet__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:2: rule__ResourceSet__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet575);
            rule__ResourceSet__Group__0();
            _fsp--;


            }

             after(grammarAccess.getResourceSetAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleResourceSet


    // $ANTLR start entryRuleMODULEID
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:313:1: entryRuleMODULEID : ruleMODULEID EOF ;
    public final void entryRuleMODULEID() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:314:1: ( ruleMODULEID EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:315:1: ruleMODULEID EOF
            {
             before(grammarAccess.getMODULEIDRule()); 
            pushFollow(FOLLOW_ruleMODULEID_in_entryRuleMODULEID602);
            ruleMODULEID();
            _fsp--;

             after(grammarAccess.getMODULEIDRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMODULEID609); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleMODULEID


    // $ANTLR start ruleMODULEID
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:322:1: ruleMODULEID : ( ( rule__MODULEID__Group__0 ) ) ;
    public final void ruleMODULEID() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:326:2: ( ( ( rule__MODULEID__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__MODULEID__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__MODULEID__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:328:1: ( rule__MODULEID__Group__0 )
            {
             before(grammarAccess.getMODULEIDAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:1: ( rule__MODULEID__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:2: rule__MODULEID__Group__0
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID635);
            rule__MODULEID__Group__0();
            _fsp--;


            }

             after(grammarAccess.getMODULEIDAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleMODULEID


    // $ANTLR start entryRuleModuleIdentifier
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:341:1: entryRuleModuleIdentifier : ruleModuleIdentifier EOF ;
    public final void entryRuleModuleIdentifier() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:342:1: ( ruleModuleIdentifier EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:343:1: ruleModuleIdentifier EOF
            {
             before(grammarAccess.getModuleIdentifierRule()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier662);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getModuleIdentifierRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleIdentifier669); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleModuleIdentifier


    // $ANTLR start ruleModuleIdentifier
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:350:1: ruleModuleIdentifier : ( ( rule__ModuleIdentifier__Group__0 ) ) ;
    public final void ruleModuleIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:354:2: ( ( ( rule__ModuleIdentifier__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:355:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:355:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:356:1: ( rule__ModuleIdentifier__Group__0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:357:1: ( rule__ModuleIdentifier__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:357:2: rule__ModuleIdentifier__Group__0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier695);
            rule__ModuleIdentifier__Group__0();
            _fsp--;


            }

             after(grammarAccess.getModuleIdentifierAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleModuleIdentifier


    // $ANTLR start entryRuleResourceList
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:369:1: entryRuleResourceList : ruleResourceList EOF ;
    public final void entryRuleResourceList() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:370:1: ( ruleResourceList EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:371:1: ruleResourceList EOF
            {
             before(grammarAccess.getResourceListRule()); 
            pushFollow(FOLLOW_ruleResourceList_in_entryRuleResourceList722);
            ruleResourceList();
            _fsp--;

             after(grammarAccess.getResourceListRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceList729); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end entryRuleResourceList


    // $ANTLR start ruleResourceList
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:378:1: ruleResourceList : ( ( rule__ResourceList__Group__0 ) ) ;
    public final void ruleResourceList() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:382:2: ( ( ( rule__ResourceList__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:383:1: ( ( rule__ResourceList__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:383:1: ( ( rule__ResourceList__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:384:1: ( rule__ResourceList__Group__0 )
            {
             before(grammarAccess.getResourceListAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:385:1: ( rule__ResourceList__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:385:2: rule__ResourceList__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList755);
            rule__ResourceList__Group__0();
            _fsp--;


            }

             after(grammarAccess.getResourceListAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleResourceList


    // $ANTLR start rule__Transformation__Alternatives
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:397:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) | ( ruleClassifyModules ) );
    public final void rule__Transformation__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:401:1: ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) | ( ruleClassifyModules ) )
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
                    new NoViableAltException("397:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) | ( ruleClassifyModules ) );", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:402:1: ( ruleRemoveFrom )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:402:1: ( ruleRemoveFrom )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:403:1: ruleRemoveFrom
                    {
                     before(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives791);
                    ruleRemoveFrom();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:408:6: ( ruleEmbedInto )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:408:6: ( ruleEmbedInto )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:409:1: ruleEmbedInto
                    {
                     before(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives808);
                    ruleEmbedInto();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:414:6: ( ruleCreateModule )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:414:6: ( ruleCreateModule )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:415:1: ruleCreateModule
                    {
                     before(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2()); 
                    pushFollow(FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives825);
                    ruleCreateModule();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:420:6: ( ruleClassifyModules )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:420:6: ( ruleClassifyModules )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:421:1: ruleClassifyModules
                    {
                     before(grammarAccess.getTransformationAccess().getClassifyModulesParserRuleCall_3()); 
                    pushFollow(FOLLOW_ruleClassifyModules_in_rule__Transformation__Alternatives842);
                    ruleClassifyModules();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getClassifyModulesParserRuleCall_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Transformation__Alternatives


    // $ANTLR start rule__RemoveFrom__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:433:1: rule__RemoveFrom__Group__0 : rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 ;
    public final void rule__RemoveFrom__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:437:1: ( rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:438:2: rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0872);
            rule__RemoveFrom__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0875);
            rule__RemoveFrom__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__0


    // $ANTLR start rule__RemoveFrom__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:445:1: rule__RemoveFrom__Group__0__Impl : ( 'remove-from' ) ;
    public final void rule__RemoveFrom__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:449:1: ( ( 'remove-from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:450:1: ( 'remove-from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:450:1: ( 'remove-from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:451:1: 'remove-from'
            {
             before(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl903); 
             after(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__0__Impl


    // $ANTLR start rule__RemoveFrom__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:464:1: rule__RemoveFrom__Group__1 : rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 ;
    public final void rule__RemoveFrom__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:468:1: ( rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:469:2: rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1934);
            rule__RemoveFrom__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1937);
            rule__RemoveFrom__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__1


    // $ANTLR start rule__RemoveFrom__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:476:1: rule__RemoveFrom__Group__1__Impl : ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) ;
    public final void rule__RemoveFrom__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:480:1: ( ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:481:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:481:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:482:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:483:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:483:2: rule__RemoveFrom__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl964);
            rule__RemoveFrom__ResourceSetAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getRemoveFromAccess().getResourceSetAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__1__Impl


    // $ANTLR start rule__RemoveFrom__Group__2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:493:1: rule__RemoveFrom__Group__2 : rule__RemoveFrom__Group__2__Impl ;
    public final void rule__RemoveFrom__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:497:1: ( rule__RemoveFrom__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:498:2: rule__RemoveFrom__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2994);
            rule__RemoveFrom__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__2


    // $ANTLR start rule__RemoveFrom__Group__2__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:504:1: rule__RemoveFrom__Group__2__Impl : ( ';' ) ;
    public final void rule__RemoveFrom__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:508:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:509:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:509:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:510:1: ';'
            {
             before(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2()); 
            match(input,13,FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl1022); 
             after(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__Group__2__Impl


    // $ANTLR start rule__EmbedInto__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:529:1: rule__EmbedInto__Group__0 : rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 ;
    public final void rule__EmbedInto__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:533:1: ( rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:534:2: rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__01059);
            rule__EmbedInto__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__01062);
            rule__EmbedInto__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__0


    // $ANTLR start rule__EmbedInto__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:541:1: rule__EmbedInto__Group__0__Impl : ( 'embed-into' ) ;
    public final void rule__EmbedInto__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:545:1: ( ( 'embed-into' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:546:1: ( 'embed-into' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:546:1: ( 'embed-into' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:547:1: 'embed-into'
            {
             before(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__EmbedInto__Group__0__Impl1090); 
             after(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__0__Impl


    // $ANTLR start rule__EmbedInto__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:560:1: rule__EmbedInto__Group__1 : rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 ;
    public final void rule__EmbedInto__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:564:1: ( rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:565:2: rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__11121);
            rule__EmbedInto__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__11124);
            rule__EmbedInto__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__1


    // $ANTLR start rule__EmbedInto__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:572:1: rule__EmbedInto__Group__1__Impl : ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) ;
    public final void rule__EmbedInto__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:576:1: ( ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:577:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:577:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:578:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:579:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:579:2: rule__EmbedInto__HostModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1151);
            rule__EmbedInto__HostModuleAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getEmbedIntoAccess().getHostModuleAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__1__Impl


    // $ANTLR start rule__EmbedInto__Group__2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:589:1: rule__EmbedInto__Group__2 : rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 ;
    public final void rule__EmbedInto__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:593:1: ( rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:594:2: rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21181);
            rule__EmbedInto__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21184);
            rule__EmbedInto__Group__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__2


    // $ANTLR start rule__EmbedInto__Group__2__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:601:1: rule__EmbedInto__Group__2__Impl : ( 'modules' ) ;
    public final void rule__EmbedInto__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:605:1: ( ( 'modules' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:606:1: ( 'modules' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:606:1: ( 'modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:607:1: 'modules'
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2()); 
            match(input,15,FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1212); 
             after(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__2__Impl


    // $ANTLR start rule__EmbedInto__Group__3
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:620:1: rule__EmbedInto__Group__3 : rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 ;
    public final void rule__EmbedInto__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:624:1: ( rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:625:2: rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31243);
            rule__EmbedInto__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31246);
            rule__EmbedInto__Group__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__3


    // $ANTLR start rule__EmbedInto__Group__3__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:632:1: rule__EmbedInto__Group__3__Impl : ( ( rule__EmbedInto__ModulesAssignment_3 ) ) ;
    public final void rule__EmbedInto__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:636:1: ( ( ( rule__EmbedInto__ModulesAssignment_3 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:637:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:637:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:638:1: ( rule__EmbedInto__ModulesAssignment_3 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_3()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:639:1: ( rule__EmbedInto__ModulesAssignment_3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:639:2: rule__EmbedInto__ModulesAssignment_3
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1273);
            rule__EmbedInto__ModulesAssignment_3();
            _fsp--;


            }

             after(grammarAccess.getEmbedIntoAccess().getModulesAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__3__Impl


    // $ANTLR start rule__EmbedInto__Group__4
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:649:1: rule__EmbedInto__Group__4 : rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 ;
    public final void rule__EmbedInto__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:653:1: ( rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:654:2: rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41303);
            rule__EmbedInto__Group__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41306);
            rule__EmbedInto__Group__5();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__4


    // $ANTLR start rule__EmbedInto__Group__4__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:661:1: rule__EmbedInto__Group__4__Impl : ( ( rule__EmbedInto__Group_4__0 )* ) ;
    public final void rule__EmbedInto__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:665:1: ( ( ( rule__EmbedInto__Group_4__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:666:1: ( ( rule__EmbedInto__Group_4__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:666:1: ( ( rule__EmbedInto__Group_4__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:667:1: ( rule__EmbedInto__Group_4__0 )*
            {
             before(grammarAccess.getEmbedIntoAccess().getGroup_4()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:668:1: ( rule__EmbedInto__Group_4__0 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:668:2: rule__EmbedInto__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1333);
            	    rule__EmbedInto__Group_4__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

             after(grammarAccess.getEmbedIntoAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__4__Impl


    // $ANTLR start rule__EmbedInto__Group__5
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:678:1: rule__EmbedInto__Group__5 : rule__EmbedInto__Group__5__Impl ;
    public final void rule__EmbedInto__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:682:1: ( rule__EmbedInto__Group__5__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:683:2: rule__EmbedInto__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51364);
            rule__EmbedInto__Group__5__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__5


    // $ANTLR start rule__EmbedInto__Group__5__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:689:1: rule__EmbedInto__Group__5__Impl : ( ';' ) ;
    public final void rule__EmbedInto__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:693:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:694:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:694:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:695:1: ';'
            {
             before(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5()); 
            match(input,13,FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1392); 
             after(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group__5__Impl


    // $ANTLR start rule__EmbedInto__Group_4__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:720:1: rule__EmbedInto__Group_4__0 : rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 ;
    public final void rule__EmbedInto__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:724:1: ( rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:725:2: rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01435);
            rule__EmbedInto__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01438);
            rule__EmbedInto__Group_4__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group_4__0


    // $ANTLR start rule__EmbedInto__Group_4__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:732:1: rule__EmbedInto__Group_4__0__Impl : ( ',' ) ;
    public final void rule__EmbedInto__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:736:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:737:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:737:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:738:1: ','
            {
             before(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0()); 
            match(input,16,FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1466); 
             after(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group_4__0__Impl


    // $ANTLR start rule__EmbedInto__Group_4__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:751:1: rule__EmbedInto__Group_4__1 : rule__EmbedInto__Group_4__1__Impl ;
    public final void rule__EmbedInto__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:755:1: ( rule__EmbedInto__Group_4__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:756:2: rule__EmbedInto__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11497);
            rule__EmbedInto__Group_4__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group_4__1


    // $ANTLR start rule__EmbedInto__Group_4__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:762:1: rule__EmbedInto__Group_4__1__Impl : ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) ;
    public final void rule__EmbedInto__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:766:1: ( ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:767:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:767:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:768:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_4_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:769:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:769:2: rule__EmbedInto__ModulesAssignment_4_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1524);
            rule__EmbedInto__ModulesAssignment_4_1();
            _fsp--;


            }

             after(grammarAccess.getEmbedIntoAccess().getModulesAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__Group_4__1__Impl


    // $ANTLR start rule__CreateModule__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:783:1: rule__CreateModule__Group__0 : rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 ;
    public final void rule__CreateModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:787:1: ( rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:788:2: rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01558);
            rule__CreateModule__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01561);
            rule__CreateModule__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__0


    // $ANTLR start rule__CreateModule__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:795:1: rule__CreateModule__Group__0__Impl : ( 'create-module' ) ;
    public final void rule__CreateModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:799:1: ( ( 'create-module' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:800:1: ( 'create-module' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:800:1: ( 'create-module' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:801:1: 'create-module'
            {
             before(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__CreateModule__Group__0__Impl1589); 
             after(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__0__Impl


    // $ANTLR start rule__CreateModule__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:814:1: rule__CreateModule__Group__1 : rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 ;
    public final void rule__CreateModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:818:1: ( rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:819:2: rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11620);
            rule__CreateModule__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11623);
            rule__CreateModule__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__1


    // $ANTLR start rule__CreateModule__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:826:1: rule__CreateModule__Group__1__Impl : ( ( rule__CreateModule__ModuleAssignment_1 ) ) ;
    public final void rule__CreateModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:830:1: ( ( ( rule__CreateModule__ModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:831:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:831:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:832:1: ( rule__CreateModule__ModuleAssignment_1 )
            {
             before(grammarAccess.getCreateModuleAccess().getModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:833:1: ( rule__CreateModule__ModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:833:2: rule__CreateModule__ModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1650);
            rule__CreateModule__ModuleAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getCreateModuleAccess().getModuleAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__1__Impl


    // $ANTLR start rule__CreateModule__Group__2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:843:1: rule__CreateModule__Group__2 : rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 ;
    public final void rule__CreateModule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:847:1: ( rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:848:2: rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21680);
            rule__CreateModule__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21683);
            rule__CreateModule__Group__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__2


    // $ANTLR start rule__CreateModule__Group__2__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:855:1: rule__CreateModule__Group__2__Impl : ( ( rule__CreateModule__LayerAssignment_2 )? ) ;
    public final void rule__CreateModule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:859:1: ( ( ( rule__CreateModule__LayerAssignment_2 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:860:1: ( ( rule__CreateModule__LayerAssignment_2 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:860:1: ( ( rule__CreateModule__LayerAssignment_2 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:861:1: ( rule__CreateModule__LayerAssignment_2 )?
            {
             before(grammarAccess.getCreateModuleAccess().getLayerAssignment_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:862:1: ( rule__CreateModule__LayerAssignment_2 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==21) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:862:2: rule__CreateModule__LayerAssignment_2
                    {
                    pushFollow(FOLLOW_rule__CreateModule__LayerAssignment_2_in_rule__CreateModule__Group__2__Impl1710);
                    rule__CreateModule__LayerAssignment_2();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getCreateModuleAccess().getLayerAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__2__Impl


    // $ANTLR start rule__CreateModule__Group__3
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:872:1: rule__CreateModule__Group__3 : rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4 ;
    public final void rule__CreateModule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:876:1: ( rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:877:2: rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31741);
            rule__CreateModule__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__4_in_rule__CreateModule__Group__31744);
            rule__CreateModule__Group__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__3


    // $ANTLR start rule__CreateModule__Group__3__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:884:1: rule__CreateModule__Group__3__Impl : ( ( rule__CreateModule__FromAssignment_3 )* ) ;
    public final void rule__CreateModule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:888:1: ( ( ( rule__CreateModule__FromAssignment_3 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:889:1: ( ( rule__CreateModule__FromAssignment_3 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:889:1: ( ( rule__CreateModule__FromAssignment_3 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:890:1: ( rule__CreateModule__FromAssignment_3 )*
            {
             before(grammarAccess.getCreateModuleAccess().getFromAssignment_3()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:891:1: ( rule__CreateModule__FromAssignment_3 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==22) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:891:2: rule__CreateModule__FromAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__CreateModule__FromAssignment_3_in_rule__CreateModule__Group__3__Impl1771);
            	    rule__CreateModule__FromAssignment_3();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             after(grammarAccess.getCreateModuleAccess().getFromAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__3__Impl


    // $ANTLR start rule__CreateModule__Group__4
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:901:1: rule__CreateModule__Group__4 : rule__CreateModule__Group__4__Impl ;
    public final void rule__CreateModule__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:905:1: ( rule__CreateModule__Group__4__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:906:2: rule__CreateModule__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__4__Impl_in_rule__CreateModule__Group__41802);
            rule__CreateModule__Group__4__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__4


    // $ANTLR start rule__CreateModule__Group__4__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:912:1: rule__CreateModule__Group__4__Impl : ( ';' ) ;
    public final void rule__CreateModule__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:916:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:917:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:917:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:918:1: ';'
            {
             before(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_4()); 
            match(input,13,FOLLOW_13_in_rule__CreateModule__Group__4__Impl1830); 
             after(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__Group__4__Impl


    // $ANTLR start rule__ClassifyModules__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:941:1: rule__ClassifyModules__Group__0 : rule__ClassifyModules__Group__0__Impl rule__ClassifyModules__Group__1 ;
    public final void rule__ClassifyModules__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:945:1: ( rule__ClassifyModules__Group__0__Impl rule__ClassifyModules__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:946:2: rule__ClassifyModules__Group__0__Impl rule__ClassifyModules__Group__1
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__0__Impl_in_rule__ClassifyModules__Group__01871);
            rule__ClassifyModules__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group__1_in_rule__ClassifyModules__Group__01874);
            rule__ClassifyModules__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__0


    // $ANTLR start rule__ClassifyModules__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:953:1: rule__ClassifyModules__Group__0__Impl : ( 'classify-modules' ) ;
    public final void rule__ClassifyModules__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:957:1: ( ( 'classify-modules' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:958:1: ( 'classify-modules' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:958:1: ( 'classify-modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:959:1: 'classify-modules'
            {
             before(grammarAccess.getClassifyModulesAccess().getClassifyModulesKeyword_0()); 
            match(input,18,FOLLOW_18_in_rule__ClassifyModules__Group__0__Impl1902); 
             after(grammarAccess.getClassifyModulesAccess().getClassifyModulesKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__0__Impl


    // $ANTLR start rule__ClassifyModules__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:972:1: rule__ClassifyModules__Group__1 : rule__ClassifyModules__Group__1__Impl rule__ClassifyModules__Group__2 ;
    public final void rule__ClassifyModules__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:976:1: ( rule__ClassifyModules__Group__1__Impl rule__ClassifyModules__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:977:2: rule__ClassifyModules__Group__1__Impl rule__ClassifyModules__Group__2
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__1__Impl_in_rule__ClassifyModules__Group__11933);
            rule__ClassifyModules__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group__2_in_rule__ClassifyModules__Group__11936);
            rule__ClassifyModules__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__1


    // $ANTLR start rule__ClassifyModules__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:984:1: rule__ClassifyModules__Group__1__Impl : ( ( rule__ClassifyModules__ModulesAssignment_1 ) ) ;
    public final void rule__ClassifyModules__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:988:1: ( ( ( rule__ClassifyModules__ModulesAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:989:1: ( ( rule__ClassifyModules__ModulesAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:989:1: ( ( rule__ClassifyModules__ModulesAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:990:1: ( rule__ClassifyModules__ModulesAssignment_1 )
            {
             before(grammarAccess.getClassifyModulesAccess().getModulesAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:991:1: ( rule__ClassifyModules__ModulesAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:991:2: rule__ClassifyModules__ModulesAssignment_1
            {
            pushFollow(FOLLOW_rule__ClassifyModules__ModulesAssignment_1_in_rule__ClassifyModules__Group__1__Impl1963);
            rule__ClassifyModules__ModulesAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getClassifyModulesAccess().getModulesAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__1__Impl


    // $ANTLR start rule__ClassifyModules__Group__2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1001:1: rule__ClassifyModules__Group__2 : rule__ClassifyModules__Group__2__Impl rule__ClassifyModules__Group__3 ;
    public final void rule__ClassifyModules__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1005:1: ( rule__ClassifyModules__Group__2__Impl rule__ClassifyModules__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1006:2: rule__ClassifyModules__Group__2__Impl rule__ClassifyModules__Group__3
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__2__Impl_in_rule__ClassifyModules__Group__21993);
            rule__ClassifyModules__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group__3_in_rule__ClassifyModules__Group__21996);
            rule__ClassifyModules__Group__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__2


    // $ANTLR start rule__ClassifyModules__Group__2__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1013:1: rule__ClassifyModules__Group__2__Impl : ( ( rule__ClassifyModules__Group_2__0 )? ) ;
    public final void rule__ClassifyModules__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1017:1: ( ( ( rule__ClassifyModules__Group_2__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1018:1: ( ( rule__ClassifyModules__Group_2__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1018:1: ( ( rule__ClassifyModules__Group_2__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1019:1: ( rule__ClassifyModules__Group_2__0 )?
            {
             before(grammarAccess.getClassifyModulesAccess().getGroup_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1020:1: ( rule__ClassifyModules__Group_2__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==20) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1020:2: rule__ClassifyModules__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ClassifyModules__Group_2__0_in_rule__ClassifyModules__Group__2__Impl2023);
                    rule__ClassifyModules__Group_2__0();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getClassifyModulesAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__2__Impl


    // $ANTLR start rule__ClassifyModules__Group__3
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1030:1: rule__ClassifyModules__Group__3 : rule__ClassifyModules__Group__3__Impl rule__ClassifyModules__Group__4 ;
    public final void rule__ClassifyModules__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1034:1: ( rule__ClassifyModules__Group__3__Impl rule__ClassifyModules__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1035:2: rule__ClassifyModules__Group__3__Impl rule__ClassifyModules__Group__4
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__3__Impl_in_rule__ClassifyModules__Group__32054);
            rule__ClassifyModules__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group__4_in_rule__ClassifyModules__Group__32057);
            rule__ClassifyModules__Group__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__3


    // $ANTLR start rule__ClassifyModules__Group__3__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1042:1: rule__ClassifyModules__Group__3__Impl : ( 'as' ) ;
    public final void rule__ClassifyModules__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1046:1: ( ( 'as' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1047:1: ( 'as' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1047:1: ( 'as' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1048:1: 'as'
            {
             before(grammarAccess.getClassifyModulesAccess().getAsKeyword_3()); 
            match(input,19,FOLLOW_19_in_rule__ClassifyModules__Group__3__Impl2085); 
             after(grammarAccess.getClassifyModulesAccess().getAsKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__3__Impl


    // $ANTLR start rule__ClassifyModules__Group__4
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1061:1: rule__ClassifyModules__Group__4 : rule__ClassifyModules__Group__4__Impl rule__ClassifyModules__Group__5 ;
    public final void rule__ClassifyModules__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1065:1: ( rule__ClassifyModules__Group__4__Impl rule__ClassifyModules__Group__5 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1066:2: rule__ClassifyModules__Group__4__Impl rule__ClassifyModules__Group__5
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__4__Impl_in_rule__ClassifyModules__Group__42116);
            rule__ClassifyModules__Group__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group__5_in_rule__ClassifyModules__Group__42119);
            rule__ClassifyModules__Group__5();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__4


    // $ANTLR start rule__ClassifyModules__Group__4__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1073:1: rule__ClassifyModules__Group__4__Impl : ( ( rule__ClassifyModules__ClassificationAssignment_4 ) ) ;
    public final void rule__ClassifyModules__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1077:1: ( ( ( rule__ClassifyModules__ClassificationAssignment_4 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1078:1: ( ( rule__ClassifyModules__ClassificationAssignment_4 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1078:1: ( ( rule__ClassifyModules__ClassificationAssignment_4 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1079:1: ( rule__ClassifyModules__ClassificationAssignment_4 )
            {
             before(grammarAccess.getClassifyModulesAccess().getClassificationAssignment_4()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1080:1: ( rule__ClassifyModules__ClassificationAssignment_4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1080:2: rule__ClassifyModules__ClassificationAssignment_4
            {
            pushFollow(FOLLOW_rule__ClassifyModules__ClassificationAssignment_4_in_rule__ClassifyModules__Group__4__Impl2146);
            rule__ClassifyModules__ClassificationAssignment_4();
            _fsp--;


            }

             after(grammarAccess.getClassifyModulesAccess().getClassificationAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__4__Impl


    // $ANTLR start rule__ClassifyModules__Group__5
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1090:1: rule__ClassifyModules__Group__5 : rule__ClassifyModules__Group__5__Impl ;
    public final void rule__ClassifyModules__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1094:1: ( rule__ClassifyModules__Group__5__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1095:2: rule__ClassifyModules__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group__5__Impl_in_rule__ClassifyModules__Group__52176);
            rule__ClassifyModules__Group__5__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__5


    // $ANTLR start rule__ClassifyModules__Group__5__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1101:1: rule__ClassifyModules__Group__5__Impl : ( ';' ) ;
    public final void rule__ClassifyModules__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1105:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1106:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1106:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1107:1: ';'
            {
             before(grammarAccess.getClassifyModulesAccess().getSemicolonKeyword_5()); 
            match(input,13,FOLLOW_13_in_rule__ClassifyModules__Group__5__Impl2204); 
             after(grammarAccess.getClassifyModulesAccess().getSemicolonKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group__5__Impl


    // $ANTLR start rule__ClassifyModules__Group_2__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1132:1: rule__ClassifyModules__Group_2__0 : rule__ClassifyModules__Group_2__0__Impl rule__ClassifyModules__Group_2__1 ;
    public final void rule__ClassifyModules__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1136:1: ( rule__ClassifyModules__Group_2__0__Impl rule__ClassifyModules__Group_2__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1137:2: rule__ClassifyModules__Group_2__0__Impl rule__ClassifyModules__Group_2__1
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group_2__0__Impl_in_rule__ClassifyModules__Group_2__02247);
            rule__ClassifyModules__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ClassifyModules__Group_2__1_in_rule__ClassifyModules__Group_2__02250);
            rule__ClassifyModules__Group_2__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group_2__0


    // $ANTLR start rule__ClassifyModules__Group_2__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1144:1: rule__ClassifyModules__Group_2__0__Impl : ( 'but not ' ) ;
    public final void rule__ClassifyModules__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1148:1: ( ( 'but not ' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1149:1: ( 'but not ' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1149:1: ( 'but not ' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1150:1: 'but not '
            {
             before(grammarAccess.getClassifyModulesAccess().getButNotKeyword_2_0()); 
            match(input,20,FOLLOW_20_in_rule__ClassifyModules__Group_2__0__Impl2278); 
             after(grammarAccess.getClassifyModulesAccess().getButNotKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group_2__0__Impl


    // $ANTLR start rule__ClassifyModules__Group_2__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1163:1: rule__ClassifyModules__Group_2__1 : rule__ClassifyModules__Group_2__1__Impl ;
    public final void rule__ClassifyModules__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1167:1: ( rule__ClassifyModules__Group_2__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1168:2: rule__ClassifyModules__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ClassifyModules__Group_2__1__Impl_in_rule__ClassifyModules__Group_2__12309);
            rule__ClassifyModules__Group_2__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group_2__1


    // $ANTLR start rule__ClassifyModules__Group_2__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1174:1: rule__ClassifyModules__Group_2__1__Impl : ( ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 ) ) ;
    public final void rule__ClassifyModules__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1178:1: ( ( ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1179:1: ( ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1179:1: ( ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1180:1: ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 )
            {
             before(grammarAccess.getClassifyModulesAccess().getExcludedModulesAssignment_2_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1181:1: ( rule__ClassifyModules__ExcludedModulesAssignment_2_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1181:2: rule__ClassifyModules__ExcludedModulesAssignment_2_1
            {
            pushFollow(FOLLOW_rule__ClassifyModules__ExcludedModulesAssignment_2_1_in_rule__ClassifyModules__Group_2__1__Impl2336);
            rule__ClassifyModules__ExcludedModulesAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getClassifyModulesAccess().getExcludedModulesAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__Group_2__1__Impl


    // $ANTLR start rule__Layer__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1195:1: rule__Layer__Group__0 : rule__Layer__Group__0__Impl rule__Layer__Group__1 ;
    public final void rule__Layer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1199:1: ( rule__Layer__Group__0__Impl rule__Layer__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1200:2: rule__Layer__Group__0__Impl rule__Layer__Group__1
            {
            pushFollow(FOLLOW_rule__Layer__Group__0__Impl_in_rule__Layer__Group__02370);
            rule__Layer__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Layer__Group__1_in_rule__Layer__Group__02373);
            rule__Layer__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Layer__Group__0


    // $ANTLR start rule__Layer__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1207:1: rule__Layer__Group__0__Impl : ( 'layer' ) ;
    public final void rule__Layer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1211:1: ( ( 'layer' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1212:1: ( 'layer' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1212:1: ( 'layer' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1213:1: 'layer'
            {
             before(grammarAccess.getLayerAccess().getLayerKeyword_0()); 
            match(input,21,FOLLOW_21_in_rule__Layer__Group__0__Impl2401); 
             after(grammarAccess.getLayerAccess().getLayerKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Layer__Group__0__Impl


    // $ANTLR start rule__Layer__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1226:1: rule__Layer__Group__1 : rule__Layer__Group__1__Impl ;
    public final void rule__Layer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1230:1: ( rule__Layer__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1231:2: rule__Layer__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Layer__Group__1__Impl_in_rule__Layer__Group__12432);
            rule__Layer__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Layer__Group__1


    // $ANTLR start rule__Layer__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1237:1: rule__Layer__Group__1__Impl : ( ( rule__Layer__LayerAssignment_1 ) ) ;
    public final void rule__Layer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1241:1: ( ( ( rule__Layer__LayerAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1242:1: ( ( rule__Layer__LayerAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1242:1: ( ( rule__Layer__LayerAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1243:1: ( rule__Layer__LayerAssignment_1 )
            {
             before(grammarAccess.getLayerAccess().getLayerAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1244:1: ( rule__Layer__LayerAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1244:2: rule__Layer__LayerAssignment_1
            {
            pushFollow(FOLLOW_rule__Layer__LayerAssignment_1_in_rule__Layer__Group__1__Impl2459);
            rule__Layer__LayerAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getLayerAccess().getLayerAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Layer__Group__1__Impl


    // $ANTLR start rule__From__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1258:1: rule__From__Group__0 : rule__From__Group__0__Impl rule__From__Group__1 ;
    public final void rule__From__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1262:1: ( rule__From__Group__0__Impl rule__From__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1263:2: rule__From__Group__0__Impl rule__From__Group__1
            {
            pushFollow(FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__02493);
            rule__From__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__From__Group__1_in_rule__From__Group__02496);
            rule__From__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__From__Group__0


    // $ANTLR start rule__From__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1270:1: rule__From__Group__0__Impl : ( 'from' ) ;
    public final void rule__From__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1274:1: ( ( 'from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1275:1: ( 'from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1275:1: ( 'from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1276:1: 'from'
            {
             before(grammarAccess.getFromAccess().getFromKeyword_0()); 
            match(input,22,FOLLOW_22_in_rule__From__Group__0__Impl2524); 
             after(grammarAccess.getFromAccess().getFromKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__From__Group__0__Impl


    // $ANTLR start rule__From__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1289:1: rule__From__Group__1 : rule__From__Group__1__Impl ;
    public final void rule__From__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1293:1: ( rule__From__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1294:2: rule__From__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__12555);
            rule__From__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__From__Group__1


    // $ANTLR start rule__From__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1300:1: rule__From__Group__1__Impl : ( ( rule__From__ResourceSetAssignment_1 ) ) ;
    public final void rule__From__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1304:1: ( ( ( rule__From__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1305:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1305:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1306:1: ( rule__From__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1307:1: ( rule__From__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1307:2: rule__From__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl2582);
            rule__From__ResourceSetAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getFromAccess().getResourceSetAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__From__Group__1__Impl


    // $ANTLR start rule__ResourceSet__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1321:1: rule__ResourceSet__Group__0 : rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 ;
    public final void rule__ResourceSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1325:1: ( rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1326:2: rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__02616);
            rule__ResourceSet__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__02619);
            rule__ResourceSet__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__0


    // $ANTLR start rule__ResourceSet__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1333:1: rule__ResourceSet__Group__0__Impl : ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) ;
    public final void rule__ResourceSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1337:1: ( ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1338:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1338:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1339:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1340:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1340:2: rule__ResourceSet__ModuleIdentifierAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl2646);
            rule__ResourceSet__ModuleIdentifierAssignment_0();
            _fsp--;


            }

             after(grammarAccess.getResourceSetAccess().getModuleIdentifierAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__0__Impl


    // $ANTLR start rule__ResourceSet__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1350:1: rule__ResourceSet__Group__1 : rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 ;
    public final void rule__ResourceSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1354:1: ( rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1355:2: rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__12676);
            rule__ResourceSet__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__12679);
            rule__ResourceSet__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__1


    // $ANTLR start rule__ResourceSet__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1362:1: rule__ResourceSet__Group__1__Impl : ( ( rule__ResourceSet__Group_1__0 )? ) ;
    public final void rule__ResourceSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1366:1: ( ( ( rule__ResourceSet__Group_1__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1367:1: ( ( rule__ResourceSet__Group_1__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1367:1: ( ( rule__ResourceSet__Group_1__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1368:1: ( rule__ResourceSet__Group_1__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1369:1: ( rule__ResourceSet__Group_1__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==23) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1369:2: rule__ResourceSet__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl2706);
                    rule__ResourceSet__Group_1__0();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getResourceSetAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__1__Impl


    // $ANTLR start rule__ResourceSet__Group__2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1379:1: rule__ResourceSet__Group__2 : rule__ResourceSet__Group__2__Impl ;
    public final void rule__ResourceSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1383:1: ( rule__ResourceSet__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1384:2: rule__ResourceSet__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__22737);
            rule__ResourceSet__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__2


    // $ANTLR start rule__ResourceSet__Group__2__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1390:1: rule__ResourceSet__Group__2__Impl : ( ( rule__ResourceSet__Group_2__0 )? ) ;
    public final void rule__ResourceSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1394:1: ( ( ( rule__ResourceSet__Group_2__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1395:1: ( ( rule__ResourceSet__Group_2__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1395:1: ( ( rule__ResourceSet__Group_2__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1396:1: ( rule__ResourceSet__Group_2__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1397:1: ( rule__ResourceSet__Group_2__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==24) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1397:2: rule__ResourceSet__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl2764);
                    rule__ResourceSet__Group_2__0();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getResourceSetAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group__2__Impl


    // $ANTLR start rule__ResourceSet__Group_1__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1413:1: rule__ResourceSet__Group_1__0 : rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 ;
    public final void rule__ResourceSet__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1417:1: ( rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1418:2: rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__02801);
            rule__ResourceSet__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__02804);
            rule__ResourceSet__Group_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_1__0


    // $ANTLR start rule__ResourceSet__Group_1__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1425:1: rule__ResourceSet__Group_1__0__Impl : ( 'include' ) ;
    public final void rule__ResourceSet__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1429:1: ( ( 'include' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1430:1: ( 'include' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1430:1: ( 'include' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1431:1: 'include'
            {
             before(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0()); 
            match(input,23,FOLLOW_23_in_rule__ResourceSet__Group_1__0__Impl2832); 
             after(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_1__0__Impl


    // $ANTLR start rule__ResourceSet__Group_1__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1444:1: rule__ResourceSet__Group_1__1 : rule__ResourceSet__Group_1__1__Impl ;
    public final void rule__ResourceSet__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1448:1: ( rule__ResourceSet__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1449:2: rule__ResourceSet__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12863);
            rule__ResourceSet__Group_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_1__1


    // $ANTLR start rule__ResourceSet__Group_1__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1455:1: rule__ResourceSet__Group_1__1__Impl : ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceSet__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1459:1: ( ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1460:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1460:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1461:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1462:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1462:2: rule__ResourceSet__IncludeResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2890);
            rule__ResourceSet__IncludeResourcesAssignment_1_1();
            _fsp--;


            }

             after(grammarAccess.getResourceSetAccess().getIncludeResourcesAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_1__1__Impl


    // $ANTLR start rule__ResourceSet__Group_2__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1476:1: rule__ResourceSet__Group_2__0 : rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 ;
    public final void rule__ResourceSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1480:1: ( rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1481:2: rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02924);
            rule__ResourceSet__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02927);
            rule__ResourceSet__Group_2__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_2__0


    // $ANTLR start rule__ResourceSet__Group_2__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1488:1: rule__ResourceSet__Group_2__0__Impl : ( 'exclude' ) ;
    public final void rule__ResourceSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1492:1: ( ( 'exclude' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1493:1: ( 'exclude' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1493:1: ( 'exclude' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1494:1: 'exclude'
            {
             before(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0()); 
            match(input,24,FOLLOW_24_in_rule__ResourceSet__Group_2__0__Impl2955); 
             after(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_2__0__Impl


    // $ANTLR start rule__ResourceSet__Group_2__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1507:1: rule__ResourceSet__Group_2__1 : rule__ResourceSet__Group_2__1__Impl ;
    public final void rule__ResourceSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1511:1: ( rule__ResourceSet__Group_2__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1512:2: rule__ResourceSet__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12986);
            rule__ResourceSet__Group_2__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_2__1


    // $ANTLR start rule__ResourceSet__Group_2__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1518:1: rule__ResourceSet__Group_2__1__Impl : ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) ;
    public final void rule__ResourceSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1522:1: ( ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1523:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1523:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1524:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesAssignment_2_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1525:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1525:2: rule__ResourceSet__ExcludeResourcesAssignment_2_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl3013);
            rule__ResourceSet__ExcludeResourcesAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getResourceSetAccess().getExcludeResourcesAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__Group_2__1__Impl


    // $ANTLR start rule__MODULEID__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1539:1: rule__MODULEID__Group__0 : rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 ;
    public final void rule__MODULEID__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1543:1: ( rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1544:2: rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__03047);
            rule__MODULEID__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__03050);
            rule__MODULEID__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group__0


    // $ANTLR start rule__MODULEID__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1551:1: rule__MODULEID__Group__0__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1555:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1556:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1556:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1557:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl3077); 
             after(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group__0__Impl


    // $ANTLR start rule__MODULEID__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1568:1: rule__MODULEID__Group__1 : rule__MODULEID__Group__1__Impl ;
    public final void rule__MODULEID__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1572:1: ( rule__MODULEID__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1573:2: rule__MODULEID__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__13106);
            rule__MODULEID__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group__1


    // $ANTLR start rule__MODULEID__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1579:1: rule__MODULEID__Group__1__Impl : ( ( rule__MODULEID__Group_1__0 )* ) ;
    public final void rule__MODULEID__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1583:1: ( ( ( rule__MODULEID__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1584:1: ( ( rule__MODULEID__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1584:1: ( ( rule__MODULEID__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1585:1: ( rule__MODULEID__Group_1__0 )*
            {
             before(grammarAccess.getMODULEIDAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1586:1: ( rule__MODULEID__Group_1__0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==25) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1586:2: rule__MODULEID__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl3133);
            	    rule__MODULEID__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getMODULEIDAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group__1__Impl


    // $ANTLR start rule__MODULEID__Group_1__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1600:1: rule__MODULEID__Group_1__0 : rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 ;
    public final void rule__MODULEID__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1604:1: ( rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1605:2: rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__03168);
            rule__MODULEID__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__03171);
            rule__MODULEID__Group_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group_1__0


    // $ANTLR start rule__MODULEID__Group_1__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1612:1: rule__MODULEID__Group_1__0__Impl : ( '.' ) ;
    public final void rule__MODULEID__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1616:1: ( ( '.' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1617:1: ( '.' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1617:1: ( '.' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1618:1: '.'
            {
             before(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0()); 
            match(input,25,FOLLOW_25_in_rule__MODULEID__Group_1__0__Impl3199); 
             after(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group_1__0__Impl


    // $ANTLR start rule__MODULEID__Group_1__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1631:1: rule__MODULEID__Group_1__1 : rule__MODULEID__Group_1__1__Impl ;
    public final void rule__MODULEID__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1635:1: ( rule__MODULEID__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1636:2: rule__MODULEID__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__13230);
            rule__MODULEID__Group_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group_1__1


    // $ANTLR start rule__MODULEID__Group_1__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1642:1: rule__MODULEID__Group_1__1__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1646:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1647:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1647:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1648:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl3257); 
             after(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MODULEID__Group_1__1__Impl


    // $ANTLR start rule__ModuleIdentifier__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1663:1: rule__ModuleIdentifier__Group__0 : rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 ;
    public final void rule__ModuleIdentifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1667:1: ( rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1668:2: rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__03290);
            rule__ModuleIdentifier__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__03293);
            rule__ModuleIdentifier__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__Group__0


    // $ANTLR start rule__ModuleIdentifier__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1675:1: rule__ModuleIdentifier__Group__0__Impl : ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) ;
    public final void rule__ModuleIdentifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1679:1: ( ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1680:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1680:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1681:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1682:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1682:2: rule__ModuleIdentifier__ModulenameAssignment_0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl3320);
            rule__ModuleIdentifier__ModulenameAssignment_0();
            _fsp--;


            }

             after(grammarAccess.getModuleIdentifierAccess().getModulenameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__Group__0__Impl


    // $ANTLR start rule__ModuleIdentifier__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1692:1: rule__ModuleIdentifier__Group__1 : rule__ModuleIdentifier__Group__1__Impl ;
    public final void rule__ModuleIdentifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1696:1: ( rule__ModuleIdentifier__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1697:2: rule__ModuleIdentifier__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__13350);
            rule__ModuleIdentifier__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__Group__1


    // $ANTLR start rule__ModuleIdentifier__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1703:1: rule__ModuleIdentifier__Group__1__Impl : ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) ;
    public final void rule__ModuleIdentifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1707:1: ( ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1708:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1708:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1709:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1710:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1710:2: rule__ModuleIdentifier__VersionAssignment_1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl3377);
            rule__ModuleIdentifier__VersionAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getModuleIdentifierAccess().getVersionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__Group__1__Impl


    // $ANTLR start rule__ResourceList__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1724:1: rule__ResourceList__Group__0 : rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 ;
    public final void rule__ResourceList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1728:1: ( rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1729:2: rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__03411);
            rule__ResourceList__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__03414);
            rule__ResourceList__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group__0


    // $ANTLR start rule__ResourceList__Group__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1736:1: rule__ResourceList__Group__0__Impl : ( ( rule__ResourceList__ResourcesAssignment_0 ) ) ;
    public final void rule__ResourceList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1740:1: ( ( ( rule__ResourceList__ResourcesAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1741:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1741:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1742:1: ( rule__ResourceList__ResourcesAssignment_0 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1743:1: ( rule__ResourceList__ResourcesAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1743:2: rule__ResourceList__ResourcesAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl3441);
            rule__ResourceList__ResourcesAssignment_0();
            _fsp--;


            }

             after(grammarAccess.getResourceListAccess().getResourcesAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group__0__Impl


    // $ANTLR start rule__ResourceList__Group__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1753:1: rule__ResourceList__Group__1 : rule__ResourceList__Group__1__Impl ;
    public final void rule__ResourceList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1757:1: ( rule__ResourceList__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1758:2: rule__ResourceList__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__13471);
            rule__ResourceList__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group__1


    // $ANTLR start rule__ResourceList__Group__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1764:1: rule__ResourceList__Group__1__Impl : ( ( rule__ResourceList__Group_1__0 )* ) ;
    public final void rule__ResourceList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1768:1: ( ( ( rule__ResourceList__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1769:1: ( ( rule__ResourceList__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1769:1: ( ( rule__ResourceList__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1770:1: ( rule__ResourceList__Group_1__0 )*
            {
             before(grammarAccess.getResourceListAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1771:1: ( rule__ResourceList__Group_1__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==16) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1771:2: rule__ResourceList__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl3498);
            	    rule__ResourceList__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getResourceListAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group__1__Impl


    // $ANTLR start rule__ResourceList__Group_1__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1785:1: rule__ResourceList__Group_1__0 : rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 ;
    public final void rule__ResourceList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1789:1: ( rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1790:2: rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__03533);
            rule__ResourceList__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__03536);
            rule__ResourceList__Group_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group_1__0


    // $ANTLR start rule__ResourceList__Group_1__0__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1797:1: rule__ResourceList__Group_1__0__Impl : ( ',' ) ;
    public final void rule__ResourceList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1801:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1802:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1802:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1803:1: ','
            {
             before(grammarAccess.getResourceListAccess().getCommaKeyword_1_0()); 
            match(input,16,FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl3564); 
             after(grammarAccess.getResourceListAccess().getCommaKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group_1__0__Impl


    // $ANTLR start rule__ResourceList__Group_1__1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1816:1: rule__ResourceList__Group_1__1 : rule__ResourceList__Group_1__1__Impl ;
    public final void rule__ResourceList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1820:1: ( rule__ResourceList__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1821:2: rule__ResourceList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__13595);
            rule__ResourceList__Group_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group_1__1


    // $ANTLR start rule__ResourceList__Group_1__1__Impl
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1827:1: rule__ResourceList__Group_1__1__Impl : ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1831:1: ( ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1832:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1832:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1833:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1834:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1834:2: rule__ResourceList__ResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl3622);
            rule__ResourceList__ResourcesAssignment_1_1();
            _fsp--;


            }

             after(grammarAccess.getResourceListAccess().getResourcesAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__Group_1__1__Impl


    // $ANTLR start rule__TransformationModel__TransformationsAssignment
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1849:1: rule__TransformationModel__TransformationsAssignment : ( ruleTransformation ) ;
    public final void rule__TransformationModel__TransformationsAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1853:1: ( ( ruleTransformation ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1854:1: ( ruleTransformation )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1854:1: ( ruleTransformation )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1855:1: ruleTransformation
            {
             before(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment3661);
            ruleTransformation();
            _fsp--;

             after(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TransformationModel__TransformationsAssignment


    // $ANTLR start rule__RemoveFrom__ResourceSetAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1864:1: rule__RemoveFrom__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__RemoveFrom__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1868:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1869:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1869:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1870:1: ruleResourceSet
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_13692);
            ruleResourceSet();
            _fsp--;

             after(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__RemoveFrom__ResourceSetAssignment_1


    // $ANTLR start rule__EmbedInto__HostModuleAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1879:1: rule__EmbedInto__HostModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__HostModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1883:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1884:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1884:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1885:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_13723);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__HostModuleAssignment_1


    // $ANTLR start rule__EmbedInto__ModulesAssignment_3
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1894:1: rule__EmbedInto__ModulesAssignment_3 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1898:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1899:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1899:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1900:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_33754);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__ModulesAssignment_3


    // $ANTLR start rule__EmbedInto__ModulesAssignment_4_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1909:1: rule__EmbedInto__ModulesAssignment_4_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1913:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1914:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1914:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1915:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_13785);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EmbedInto__ModulesAssignment_4_1


    // $ANTLR start rule__CreateModule__ModuleAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1924:1: rule__CreateModule__ModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__CreateModule__ModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1928:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1929:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1929:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1930:1: ruleModuleIdentifier
            {
             before(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_13816);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__ModuleAssignment_1


    // $ANTLR start rule__CreateModule__LayerAssignment_2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1939:1: rule__CreateModule__LayerAssignment_2 : ( ruleLayer ) ;
    public final void rule__CreateModule__LayerAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1943:1: ( ( ruleLayer ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1944:1: ( ruleLayer )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1944:1: ( ruleLayer )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1945:1: ruleLayer
            {
             before(grammarAccess.getCreateModuleAccess().getLayerLayerParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleLayer_in_rule__CreateModule__LayerAssignment_23847);
            ruleLayer();
            _fsp--;

             after(grammarAccess.getCreateModuleAccess().getLayerLayerParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__LayerAssignment_2


    // $ANTLR start rule__CreateModule__FromAssignment_3
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1954:1: rule__CreateModule__FromAssignment_3 : ( ruleFrom ) ;
    public final void rule__CreateModule__FromAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1958:1: ( ( ruleFrom ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1959:1: ( ruleFrom )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1959:1: ( ruleFrom )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1960:1: ruleFrom
            {
             before(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_33878);
            ruleFrom();
            _fsp--;

             after(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__CreateModule__FromAssignment_3


    // $ANTLR start rule__ClassifyModules__ModulesAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1969:1: rule__ClassifyModules__ModulesAssignment_1 : ( RULE_STRING ) ;
    public final void rule__ClassifyModules__ModulesAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1973:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1974:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1974:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1975:1: RULE_STRING
            {
             before(grammarAccess.getClassifyModulesAccess().getModulesSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ClassifyModules__ModulesAssignment_13909); 
             after(grammarAccess.getClassifyModulesAccess().getModulesSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__ModulesAssignment_1


    // $ANTLR start rule__ClassifyModules__ExcludedModulesAssignment_2_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1984:1: rule__ClassifyModules__ExcludedModulesAssignment_2_1 : ( RULE_STRING ) ;
    public final void rule__ClassifyModules__ExcludedModulesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1988:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1989:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1989:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1990:1: RULE_STRING
            {
             before(grammarAccess.getClassifyModulesAccess().getExcludedModulesSTRINGTerminalRuleCall_2_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ClassifyModules__ExcludedModulesAssignment_2_13940); 
             after(grammarAccess.getClassifyModulesAccess().getExcludedModulesSTRINGTerminalRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__ExcludedModulesAssignment_2_1


    // $ANTLR start rule__ClassifyModules__ClassificationAssignment_4
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1999:1: rule__ClassifyModules__ClassificationAssignment_4 : ( RULE_STRING ) ;
    public final void rule__ClassifyModules__ClassificationAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2003:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2004:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2004:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2005:1: RULE_STRING
            {
             before(grammarAccess.getClassifyModulesAccess().getClassificationSTRINGTerminalRuleCall_4_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ClassifyModules__ClassificationAssignment_43971); 
             after(grammarAccess.getClassifyModulesAccess().getClassificationSTRINGTerminalRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ClassifyModules__ClassificationAssignment_4


    // $ANTLR start rule__Layer__LayerAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2014:1: rule__Layer__LayerAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Layer__LayerAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2018:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2019:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2019:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2020:1: RULE_STRING
            {
             before(grammarAccess.getLayerAccess().getLayerSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Layer__LayerAssignment_14002); 
             after(grammarAccess.getLayerAccess().getLayerSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Layer__LayerAssignment_1


    // $ANTLR start rule__From__ResourceSetAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2029:1: rule__From__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__From__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2033:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2034:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2034:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2035:1: ruleResourceSet
            {
             before(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_14033);
            ruleResourceSet();
            _fsp--;

             after(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__From__ResourceSetAssignment_1


    // $ANTLR start rule__ResourceSet__ModuleIdentifierAssignment_0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2044:1: rule__ResourceSet__ModuleIdentifierAssignment_0 : ( ruleModuleIdentifier ) ;
    public final void rule__ResourceSet__ModuleIdentifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2048:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2049:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2049:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2050:1: ruleModuleIdentifier
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_04064);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__ModuleIdentifierAssignment_0


    // $ANTLR start rule__ResourceSet__IncludeResourcesAssignment_1_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2059:1: rule__ResourceSet__IncludeResourcesAssignment_1_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__IncludeResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2063:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2064:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2064:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2065:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_14095);
            ruleResourceList();
            _fsp--;

             after(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__IncludeResourcesAssignment_1_1


    // $ANTLR start rule__ResourceSet__ExcludeResourcesAssignment_2_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2074:1: rule__ResourceSet__ExcludeResourcesAssignment_2_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__ExcludeResourcesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2078:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2079:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2079:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2080:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_14126);
            ruleResourceList();
            _fsp--;

             after(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceSet__ExcludeResourcesAssignment_2_1


    // $ANTLR start rule__ModuleIdentifier__ModulenameAssignment_0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2089:1: rule__ModuleIdentifier__ModulenameAssignment_0 : ( ruleMODULEID ) ;
    public final void rule__ModuleIdentifier__ModulenameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2093:1: ( ( ruleMODULEID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2094:1: ( ruleMODULEID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2094:1: ( ruleMODULEID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2095:1: ruleMODULEID
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_04157);
            ruleMODULEID();
            _fsp--;

             after(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__ModulenameAssignment_0


    // $ANTLR start rule__ModuleIdentifier__VersionAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2104:1: rule__ModuleIdentifier__VersionAssignment_1 : ( RULE_STRING ) ;
    public final void rule__ModuleIdentifier__VersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2108:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2109:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2109:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2110:1: RULE_STRING
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_14188); 
             after(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ModuleIdentifier__VersionAssignment_1


    // $ANTLR start rule__ResourceList__ResourcesAssignment_0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2119:1: rule__ResourceList__ResourcesAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2123:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2124:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2124:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2125:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_04219); 
             after(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__ResourcesAssignment_0


    // $ANTLR start rule__ResourceList__ResourcesAssignment_1_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2134:1: rule__ResourceList__ResourcesAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2138:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2139:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2139:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2140:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_14250); 
             after(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ResourceList__ResourcesAssignment_1_1


 

    public static final BitSet FOLLOW_ruleTransformationModel_in_entryRuleTransformationModel61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransformationModel68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TransformationModel__TransformationsAssignment_in_ruleTransformationModel94 = new BitSet(new long[]{0x0000000000065002L});
    public static final BitSet FOLLOW_ruleTransformation_in_entryRuleTransformation122 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransformation129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transformation__Alternatives_in_ruleTransformation155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_entryRuleRemoveFrom182 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRemoveFrom189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__0_in_ruleRemoveFrom215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_entryRuleEmbedInto242 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEmbedInto249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__0_in_ruleEmbedInto275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_entryRuleCreateModule302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCreateModule309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__0_in_ruleCreateModule335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClassifyModules_in_entryRuleClassifyModules362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleClassifyModules369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__0_in_ruleClassifyModules395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLayer_in_entryRuleLayer422 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLayer429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__0_in_ruleLayer455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_entryRuleFrom482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFrom489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0_in_ruleFrom515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_entryRuleResourceSet542 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceSet549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_entryRuleMODULEID602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMODULEID609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier662 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleIdentifier669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_entryRuleResourceList722 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceList729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClassifyModules_in_rule__Transformation__Alternatives842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0872 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1934 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl1022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__01059 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__01062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__EmbedInto__Group__0__Impl1090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__11121 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__11124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21181 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31243 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41303 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1333 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01435 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01558 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__CreateModule__Group__0__Impl1589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11620 = new BitSet(new long[]{0x0000000000602000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21680 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__LayerAssignment_2_in_rule__CreateModule__Group__2__Impl1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31741 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__4_in_rule__CreateModule__Group__31744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__FromAssignment_3_in_rule__CreateModule__Group__3__Impl1771 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__4__Impl_in_rule__CreateModule__Group__41802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__CreateModule__Group__4__Impl1830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__0__Impl_in_rule__ClassifyModules__Group__01871 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__1_in_rule__ClassifyModules__Group__01874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__ClassifyModules__Group__0__Impl1902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__1__Impl_in_rule__ClassifyModules__Group__11933 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__2_in_rule__ClassifyModules__Group__11936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__ModulesAssignment_1_in_rule__ClassifyModules__Group__1__Impl1963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__2__Impl_in_rule__ClassifyModules__Group__21993 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__3_in_rule__ClassifyModules__Group__21996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group_2__0_in_rule__ClassifyModules__Group__2__Impl2023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__3__Impl_in_rule__ClassifyModules__Group__32054 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__4_in_rule__ClassifyModules__Group__32057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__ClassifyModules__Group__3__Impl2085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__4__Impl_in_rule__ClassifyModules__Group__42116 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__5_in_rule__ClassifyModules__Group__42119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__ClassificationAssignment_4_in_rule__ClassifyModules__Group__4__Impl2146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group__5__Impl_in_rule__ClassifyModules__Group__52176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__ClassifyModules__Group__5__Impl2204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group_2__0__Impl_in_rule__ClassifyModules__Group_2__02247 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group_2__1_in_rule__ClassifyModules__Group_2__02250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__ClassifyModules__Group_2__0__Impl2278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__Group_2__1__Impl_in_rule__ClassifyModules__Group_2__12309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ClassifyModules__ExcludedModulesAssignment_2_1_in_rule__ClassifyModules__Group_2__1__Impl2336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__0__Impl_in_rule__Layer__Group__02370 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Layer__Group__1_in_rule__Layer__Group__02373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Layer__Group__0__Impl2401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__1__Impl_in_rule__Layer__Group__12432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__LayerAssignment_1_in_rule__Layer__Group__1__Impl2459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__02493 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__From__Group__1_in_rule__From__Group__02496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__From__Group__0__Impl2524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__12555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl2582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__02616 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__02619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl2646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__12676 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__12679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl2706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__22737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl2764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__02801 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__02804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__ResourceSet__Group_1__0__Impl2832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02924 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__ResourceSet__Group_2__0__Impl2955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl3013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__03047 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__03050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl3077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__13106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl3133 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__03168 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__03171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__MODULEID__Group_1__0__Impl3199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__13230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl3257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__03290 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__03293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl3320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__13350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl3377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__03411 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__03414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl3441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__13471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl3498 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__03533 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__03536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl3564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__13595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl3622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment3661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_13692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_13723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_33754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_13785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_13816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLayer_in_rule__CreateModule__LayerAssignment_23847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_33878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ClassifyModules__ModulesAssignment_13909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ClassifyModules__ExcludedModulesAssignment_2_13940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ClassifyModules__ClassificationAssignment_43971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Layer__LayerAssignment_14002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_14033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_04064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_14095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_14126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_04157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_14188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_04219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_14250 = new BitSet(new long[]{0x0000000000000002L});

}