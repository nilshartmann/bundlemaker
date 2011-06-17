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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_BMID", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'remove-from'", "';'", "'embed-into'", "'modules'", "','", "'create-module'", "'layer'", "'from'", "'include'", "'exclude'", "'.'"
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

                if ( (LA1_0==12||LA1_0==14||LA1_0==17) ) {
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


    // $ANTLR start entryRuleLayer
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:201:1: entryRuleLayer : ruleLayer EOF ;
    public final void entryRuleLayer() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:202:1: ( ruleLayer EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:203:1: ruleLayer EOF
            {
             before(grammarAccess.getLayerRule()); 
            pushFollow(FOLLOW_ruleLayer_in_entryRuleLayer362);
            ruleLayer();
            _fsp--;

             after(grammarAccess.getLayerRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLayer369); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:210:1: ruleLayer : ( ( rule__Layer__Group__0 ) ) ;
    public final void ruleLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:214:2: ( ( ( rule__Layer__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__Layer__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__Layer__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:216:1: ( rule__Layer__Group__0 )
            {
             before(grammarAccess.getLayerAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:1: ( rule__Layer__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:2: rule__Layer__Group__0
            {
            pushFollow(FOLLOW_rule__Layer__Group__0_in_ruleLayer395);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:229:1: entryRuleFrom : ruleFrom EOF ;
    public final void entryRuleFrom() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:230:1: ( ruleFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:231:1: ruleFrom EOF
            {
             before(grammarAccess.getFromRule()); 
            pushFollow(FOLLOW_ruleFrom_in_entryRuleFrom422);
            ruleFrom();
            _fsp--;

             after(grammarAccess.getFromRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFrom429); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:238:1: ruleFrom : ( ( rule__From__Group__0 ) ) ;
    public final void ruleFrom() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:242:2: ( ( ( rule__From__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__From__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__From__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:244:1: ( rule__From__Group__0 )
            {
             before(grammarAccess.getFromAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:1: ( rule__From__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:2: rule__From__Group__0
            {
            pushFollow(FOLLOW_rule__From__Group__0_in_ruleFrom455);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:257:1: entryRuleResourceSet : ruleResourceSet EOF ;
    public final void entryRuleResourceSet() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:258:1: ( ruleResourceSet EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:259:1: ruleResourceSet EOF
            {
             before(grammarAccess.getResourceSetRule()); 
            pushFollow(FOLLOW_ruleResourceSet_in_entryRuleResourceSet482);
            ruleResourceSet();
            _fsp--;

             after(grammarAccess.getResourceSetRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceSet489); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:266:1: ruleResourceSet : ( ( rule__ResourceSet__Group__0 ) ) ;
    public final void ruleResourceSet() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:270:2: ( ( ( rule__ResourceSet__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__ResourceSet__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__ResourceSet__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:272:1: ( rule__ResourceSet__Group__0 )
            {
             before(grammarAccess.getResourceSetAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:1: ( rule__ResourceSet__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:2: rule__ResourceSet__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet515);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:285:1: entryRuleMODULEID : ruleMODULEID EOF ;
    public final void entryRuleMODULEID() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:286:1: ( ruleMODULEID EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:287:1: ruleMODULEID EOF
            {
             before(grammarAccess.getMODULEIDRule()); 
            pushFollow(FOLLOW_ruleMODULEID_in_entryRuleMODULEID542);
            ruleMODULEID();
            _fsp--;

             after(grammarAccess.getMODULEIDRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMODULEID549); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:294:1: ruleMODULEID : ( ( rule__MODULEID__Group__0 ) ) ;
    public final void ruleMODULEID() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:298:2: ( ( ( rule__MODULEID__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__MODULEID__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__MODULEID__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:300:1: ( rule__MODULEID__Group__0 )
            {
             before(grammarAccess.getMODULEIDAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:1: ( rule__MODULEID__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:2: rule__MODULEID__Group__0
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID575);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:313:1: entryRuleModuleIdentifier : ruleModuleIdentifier EOF ;
    public final void entryRuleModuleIdentifier() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:314:1: ( ruleModuleIdentifier EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:315:1: ruleModuleIdentifier EOF
            {
             before(grammarAccess.getModuleIdentifierRule()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier602);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getModuleIdentifierRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleIdentifier609); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:322:1: ruleModuleIdentifier : ( ( rule__ModuleIdentifier__Group__0 ) ) ;
    public final void ruleModuleIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:326:2: ( ( ( rule__ModuleIdentifier__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:328:1: ( rule__ModuleIdentifier__Group__0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:1: ( rule__ModuleIdentifier__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:2: rule__ModuleIdentifier__Group__0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier635);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:341:1: entryRuleResourceList : ruleResourceList EOF ;
    public final void entryRuleResourceList() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:342:1: ( ruleResourceList EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:343:1: ruleResourceList EOF
            {
             before(grammarAccess.getResourceListRule()); 
            pushFollow(FOLLOW_ruleResourceList_in_entryRuleResourceList662);
            ruleResourceList();
            _fsp--;

             after(grammarAccess.getResourceListRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceList669); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:350:1: ruleResourceList : ( ( rule__ResourceList__Group__0 ) ) ;
    public final void ruleResourceList() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:354:2: ( ( ( rule__ResourceList__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:355:1: ( ( rule__ResourceList__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:355:1: ( ( rule__ResourceList__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:356:1: ( rule__ResourceList__Group__0 )
            {
             before(grammarAccess.getResourceListAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:357:1: ( rule__ResourceList__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:357:2: rule__ResourceList__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList695);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:369:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) );
    public final void rule__Transformation__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:373:1: ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) )
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
                    new NoViableAltException("369:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) );", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:374:1: ( ruleRemoveFrom )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:374:1: ( ruleRemoveFrom )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:375:1: ruleRemoveFrom
                    {
                     before(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives731);
                    ruleRemoveFrom();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:380:6: ( ruleEmbedInto )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:380:6: ( ruleEmbedInto )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:381:1: ruleEmbedInto
                    {
                     before(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives748);
                    ruleEmbedInto();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:386:6: ( ruleCreateModule )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:386:6: ( ruleCreateModule )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:387:1: ruleCreateModule
                    {
                     before(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2()); 
                    pushFollow(FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives765);
                    ruleCreateModule();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2()); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:399:1: rule__RemoveFrom__Group__0 : rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 ;
    public final void rule__RemoveFrom__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:403:1: ( rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:404:2: rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0795);
            rule__RemoveFrom__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0798);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:411:1: rule__RemoveFrom__Group__0__Impl : ( 'remove-from' ) ;
    public final void rule__RemoveFrom__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:415:1: ( ( 'remove-from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:416:1: ( 'remove-from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:416:1: ( 'remove-from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:417:1: 'remove-from'
            {
             before(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl826); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:430:1: rule__RemoveFrom__Group__1 : rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 ;
    public final void rule__RemoveFrom__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:434:1: ( rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:435:2: rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1857);
            rule__RemoveFrom__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1860);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:442:1: rule__RemoveFrom__Group__1__Impl : ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) ;
    public final void rule__RemoveFrom__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:446:1: ( ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:447:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:447:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:448:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:449:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:449:2: rule__RemoveFrom__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl887);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:459:1: rule__RemoveFrom__Group__2 : rule__RemoveFrom__Group__2__Impl ;
    public final void rule__RemoveFrom__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:463:1: ( rule__RemoveFrom__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:464:2: rule__RemoveFrom__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2917);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:470:1: rule__RemoveFrom__Group__2__Impl : ( ';' ) ;
    public final void rule__RemoveFrom__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:474:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:475:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:475:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:476:1: ';'
            {
             before(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2()); 
            match(input,13,FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl945); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:495:1: rule__EmbedInto__Group__0 : rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 ;
    public final void rule__EmbedInto__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:499:1: ( rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:500:2: rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__0982);
            rule__EmbedInto__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__0985);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:507:1: rule__EmbedInto__Group__0__Impl : ( 'embed-into' ) ;
    public final void rule__EmbedInto__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:511:1: ( ( 'embed-into' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:512:1: ( 'embed-into' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:512:1: ( 'embed-into' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:513:1: 'embed-into'
            {
             before(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__EmbedInto__Group__0__Impl1013); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:526:1: rule__EmbedInto__Group__1 : rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 ;
    public final void rule__EmbedInto__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:530:1: ( rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:531:2: rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__11044);
            rule__EmbedInto__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__11047);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:538:1: rule__EmbedInto__Group__1__Impl : ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) ;
    public final void rule__EmbedInto__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:542:1: ( ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:543:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:543:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:544:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:545:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:545:2: rule__EmbedInto__HostModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1074);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:555:1: rule__EmbedInto__Group__2 : rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 ;
    public final void rule__EmbedInto__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:559:1: ( rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:560:2: rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21104);
            rule__EmbedInto__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21107);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:567:1: rule__EmbedInto__Group__2__Impl : ( 'modules' ) ;
    public final void rule__EmbedInto__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:571:1: ( ( 'modules' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:572:1: ( 'modules' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:572:1: ( 'modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:573:1: 'modules'
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2()); 
            match(input,15,FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1135); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:586:1: rule__EmbedInto__Group__3 : rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 ;
    public final void rule__EmbedInto__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:590:1: ( rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:591:2: rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31166);
            rule__EmbedInto__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31169);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:598:1: rule__EmbedInto__Group__3__Impl : ( ( rule__EmbedInto__ModulesAssignment_3 ) ) ;
    public final void rule__EmbedInto__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:602:1: ( ( ( rule__EmbedInto__ModulesAssignment_3 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:603:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:603:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:604:1: ( rule__EmbedInto__ModulesAssignment_3 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_3()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:605:1: ( rule__EmbedInto__ModulesAssignment_3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:605:2: rule__EmbedInto__ModulesAssignment_3
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1196);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:615:1: rule__EmbedInto__Group__4 : rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 ;
    public final void rule__EmbedInto__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:619:1: ( rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:620:2: rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41226);
            rule__EmbedInto__Group__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41229);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:627:1: rule__EmbedInto__Group__4__Impl : ( ( rule__EmbedInto__Group_4__0 )* ) ;
    public final void rule__EmbedInto__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:631:1: ( ( ( rule__EmbedInto__Group_4__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:632:1: ( ( rule__EmbedInto__Group_4__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:632:1: ( ( rule__EmbedInto__Group_4__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:633:1: ( rule__EmbedInto__Group_4__0 )*
            {
             before(grammarAccess.getEmbedIntoAccess().getGroup_4()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:634:1: ( rule__EmbedInto__Group_4__0 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:634:2: rule__EmbedInto__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1256);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:644:1: rule__EmbedInto__Group__5 : rule__EmbedInto__Group__5__Impl ;
    public final void rule__EmbedInto__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:648:1: ( rule__EmbedInto__Group__5__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:649:2: rule__EmbedInto__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51287);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:655:1: rule__EmbedInto__Group__5__Impl : ( ';' ) ;
    public final void rule__EmbedInto__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:659:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:660:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:660:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:661:1: ';'
            {
             before(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5()); 
            match(input,13,FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1315); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:686:1: rule__EmbedInto__Group_4__0 : rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 ;
    public final void rule__EmbedInto__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:690:1: ( rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:691:2: rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01358);
            rule__EmbedInto__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01361);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:698:1: rule__EmbedInto__Group_4__0__Impl : ( ',' ) ;
    public final void rule__EmbedInto__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:702:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:703:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:703:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:704:1: ','
            {
             before(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0()); 
            match(input,16,FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1389); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:717:1: rule__EmbedInto__Group_4__1 : rule__EmbedInto__Group_4__1__Impl ;
    public final void rule__EmbedInto__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:721:1: ( rule__EmbedInto__Group_4__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:722:2: rule__EmbedInto__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11420);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:728:1: rule__EmbedInto__Group_4__1__Impl : ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) ;
    public final void rule__EmbedInto__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:732:1: ( ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:733:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:733:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:734:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_4_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:735:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:735:2: rule__EmbedInto__ModulesAssignment_4_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1447);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:749:1: rule__CreateModule__Group__0 : rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 ;
    public final void rule__CreateModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:753:1: ( rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:754:2: rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01481);
            rule__CreateModule__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01484);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:761:1: rule__CreateModule__Group__0__Impl : ( 'create-module' ) ;
    public final void rule__CreateModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:765:1: ( ( 'create-module' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:766:1: ( 'create-module' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:766:1: ( 'create-module' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:767:1: 'create-module'
            {
             before(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__CreateModule__Group__0__Impl1512); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:780:1: rule__CreateModule__Group__1 : rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 ;
    public final void rule__CreateModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:784:1: ( rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:785:2: rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11543);
            rule__CreateModule__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11546);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:792:1: rule__CreateModule__Group__1__Impl : ( ( rule__CreateModule__ModuleAssignment_1 ) ) ;
    public final void rule__CreateModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:796:1: ( ( ( rule__CreateModule__ModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:797:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:797:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:798:1: ( rule__CreateModule__ModuleAssignment_1 )
            {
             before(grammarAccess.getCreateModuleAccess().getModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:799:1: ( rule__CreateModule__ModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:799:2: rule__CreateModule__ModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1573);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:809:1: rule__CreateModule__Group__2 : rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 ;
    public final void rule__CreateModule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:813:1: ( rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:814:2: rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21603);
            rule__CreateModule__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21606);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:821:1: rule__CreateModule__Group__2__Impl : ( ( rule__CreateModule__LayerAssignment_2 )? ) ;
    public final void rule__CreateModule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:825:1: ( ( ( rule__CreateModule__LayerAssignment_2 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:826:1: ( ( rule__CreateModule__LayerAssignment_2 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:826:1: ( ( rule__CreateModule__LayerAssignment_2 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:827:1: ( rule__CreateModule__LayerAssignment_2 )?
            {
             before(grammarAccess.getCreateModuleAccess().getLayerAssignment_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:828:1: ( rule__CreateModule__LayerAssignment_2 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==18) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:828:2: rule__CreateModule__LayerAssignment_2
                    {
                    pushFollow(FOLLOW_rule__CreateModule__LayerAssignment_2_in_rule__CreateModule__Group__2__Impl1633);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:838:1: rule__CreateModule__Group__3 : rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4 ;
    public final void rule__CreateModule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:842:1: ( rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:843:2: rule__CreateModule__Group__3__Impl rule__CreateModule__Group__4
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31664);
            rule__CreateModule__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__4_in_rule__CreateModule__Group__31667);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:850:1: rule__CreateModule__Group__3__Impl : ( ( rule__CreateModule__FromAssignment_3 )* ) ;
    public final void rule__CreateModule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:854:1: ( ( ( rule__CreateModule__FromAssignment_3 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:855:1: ( ( rule__CreateModule__FromAssignment_3 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:855:1: ( ( rule__CreateModule__FromAssignment_3 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:856:1: ( rule__CreateModule__FromAssignment_3 )*
            {
             before(grammarAccess.getCreateModuleAccess().getFromAssignment_3()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:857:1: ( rule__CreateModule__FromAssignment_3 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==19) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:857:2: rule__CreateModule__FromAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__CreateModule__FromAssignment_3_in_rule__CreateModule__Group__3__Impl1694);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:867:1: rule__CreateModule__Group__4 : rule__CreateModule__Group__4__Impl ;
    public final void rule__CreateModule__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:871:1: ( rule__CreateModule__Group__4__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:872:2: rule__CreateModule__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__4__Impl_in_rule__CreateModule__Group__41725);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:878:1: rule__CreateModule__Group__4__Impl : ( ';' ) ;
    public final void rule__CreateModule__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:882:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:883:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:883:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:884:1: ';'
            {
             before(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_4()); 
            match(input,13,FOLLOW_13_in_rule__CreateModule__Group__4__Impl1753); 
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


    // $ANTLR start rule__Layer__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:907:1: rule__Layer__Group__0 : rule__Layer__Group__0__Impl rule__Layer__Group__1 ;
    public final void rule__Layer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:911:1: ( rule__Layer__Group__0__Impl rule__Layer__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:912:2: rule__Layer__Group__0__Impl rule__Layer__Group__1
            {
            pushFollow(FOLLOW_rule__Layer__Group__0__Impl_in_rule__Layer__Group__01794);
            rule__Layer__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Layer__Group__1_in_rule__Layer__Group__01797);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:919:1: rule__Layer__Group__0__Impl : ( 'layer' ) ;
    public final void rule__Layer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:923:1: ( ( 'layer' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:924:1: ( 'layer' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:924:1: ( 'layer' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:925:1: 'layer'
            {
             before(grammarAccess.getLayerAccess().getLayerKeyword_0()); 
            match(input,18,FOLLOW_18_in_rule__Layer__Group__0__Impl1825); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:938:1: rule__Layer__Group__1 : rule__Layer__Group__1__Impl ;
    public final void rule__Layer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:942:1: ( rule__Layer__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:943:2: rule__Layer__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Layer__Group__1__Impl_in_rule__Layer__Group__11856);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:949:1: rule__Layer__Group__1__Impl : ( ( rule__Layer__LayerAssignment_1 ) ) ;
    public final void rule__Layer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:953:1: ( ( ( rule__Layer__LayerAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:954:1: ( ( rule__Layer__LayerAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:954:1: ( ( rule__Layer__LayerAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:955:1: ( rule__Layer__LayerAssignment_1 )
            {
             before(grammarAccess.getLayerAccess().getLayerAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:956:1: ( rule__Layer__LayerAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:956:2: rule__Layer__LayerAssignment_1
            {
            pushFollow(FOLLOW_rule__Layer__LayerAssignment_1_in_rule__Layer__Group__1__Impl1883);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:970:1: rule__From__Group__0 : rule__From__Group__0__Impl rule__From__Group__1 ;
    public final void rule__From__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:974:1: ( rule__From__Group__0__Impl rule__From__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:975:2: rule__From__Group__0__Impl rule__From__Group__1
            {
            pushFollow(FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__01917);
            rule__From__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__From__Group__1_in_rule__From__Group__01920);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:982:1: rule__From__Group__0__Impl : ( 'from' ) ;
    public final void rule__From__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:986:1: ( ( 'from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:987:1: ( 'from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:987:1: ( 'from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:988:1: 'from'
            {
             before(grammarAccess.getFromAccess().getFromKeyword_0()); 
            match(input,19,FOLLOW_19_in_rule__From__Group__0__Impl1948); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1001:1: rule__From__Group__1 : rule__From__Group__1__Impl ;
    public final void rule__From__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1005:1: ( rule__From__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1006:2: rule__From__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__11979);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1012:1: rule__From__Group__1__Impl : ( ( rule__From__ResourceSetAssignment_1 ) ) ;
    public final void rule__From__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1016:1: ( ( ( rule__From__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1017:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1017:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1018:1: ( rule__From__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1019:1: ( rule__From__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1019:2: rule__From__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl2006);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1033:1: rule__ResourceSet__Group__0 : rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 ;
    public final void rule__ResourceSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1037:1: ( rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1038:2: rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__02040);
            rule__ResourceSet__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__02043);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1045:1: rule__ResourceSet__Group__0__Impl : ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) ;
    public final void rule__ResourceSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1049:1: ( ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1050:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1050:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1051:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1052:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1052:2: rule__ResourceSet__ModuleIdentifierAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl2070);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1062:1: rule__ResourceSet__Group__1 : rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 ;
    public final void rule__ResourceSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1066:1: ( rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1067:2: rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__12100);
            rule__ResourceSet__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__12103);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1074:1: rule__ResourceSet__Group__1__Impl : ( ( rule__ResourceSet__Group_1__0 )? ) ;
    public final void rule__ResourceSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1078:1: ( ( ( rule__ResourceSet__Group_1__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1079:1: ( ( rule__ResourceSet__Group_1__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1079:1: ( ( rule__ResourceSet__Group_1__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1080:1: ( rule__ResourceSet__Group_1__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1081:1: ( rule__ResourceSet__Group_1__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==20) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1081:2: rule__ResourceSet__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl2130);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1091:1: rule__ResourceSet__Group__2 : rule__ResourceSet__Group__2__Impl ;
    public final void rule__ResourceSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1095:1: ( rule__ResourceSet__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1096:2: rule__ResourceSet__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__22161);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1102:1: rule__ResourceSet__Group__2__Impl : ( ( rule__ResourceSet__Group_2__0 )? ) ;
    public final void rule__ResourceSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1106:1: ( ( ( rule__ResourceSet__Group_2__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1107:1: ( ( rule__ResourceSet__Group_2__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1107:1: ( ( rule__ResourceSet__Group_2__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1108:1: ( rule__ResourceSet__Group_2__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1109:1: ( rule__ResourceSet__Group_2__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==21) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1109:2: rule__ResourceSet__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl2188);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1125:1: rule__ResourceSet__Group_1__0 : rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 ;
    public final void rule__ResourceSet__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1129:1: ( rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1130:2: rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__02225);
            rule__ResourceSet__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__02228);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1137:1: rule__ResourceSet__Group_1__0__Impl : ( 'include' ) ;
    public final void rule__ResourceSet__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1141:1: ( ( 'include' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1142:1: ( 'include' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1142:1: ( 'include' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1143:1: 'include'
            {
             before(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0()); 
            match(input,20,FOLLOW_20_in_rule__ResourceSet__Group_1__0__Impl2256); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1156:1: rule__ResourceSet__Group_1__1 : rule__ResourceSet__Group_1__1__Impl ;
    public final void rule__ResourceSet__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1160:1: ( rule__ResourceSet__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1161:2: rule__ResourceSet__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12287);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1167:1: rule__ResourceSet__Group_1__1__Impl : ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceSet__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1171:1: ( ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1172:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1172:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1173:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1174:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1174:2: rule__ResourceSet__IncludeResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2314);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1188:1: rule__ResourceSet__Group_2__0 : rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 ;
    public final void rule__ResourceSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1192:1: ( rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1193:2: rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02348);
            rule__ResourceSet__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02351);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1200:1: rule__ResourceSet__Group_2__0__Impl : ( 'exclude' ) ;
    public final void rule__ResourceSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1204:1: ( ( 'exclude' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1205:1: ( 'exclude' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1205:1: ( 'exclude' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1206:1: 'exclude'
            {
             before(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0()); 
            match(input,21,FOLLOW_21_in_rule__ResourceSet__Group_2__0__Impl2379); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1219:1: rule__ResourceSet__Group_2__1 : rule__ResourceSet__Group_2__1__Impl ;
    public final void rule__ResourceSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1223:1: ( rule__ResourceSet__Group_2__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1224:2: rule__ResourceSet__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12410);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1230:1: rule__ResourceSet__Group_2__1__Impl : ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) ;
    public final void rule__ResourceSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1234:1: ( ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1235:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1235:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1236:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesAssignment_2_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1237:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1237:2: rule__ResourceSet__ExcludeResourcesAssignment_2_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl2437);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1251:1: rule__MODULEID__Group__0 : rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 ;
    public final void rule__MODULEID__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1255:1: ( rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1256:2: rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__02471);
            rule__MODULEID__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__02474);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1263:1: rule__MODULEID__Group__0__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1267:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1268:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1268:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1269:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl2501); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1280:1: rule__MODULEID__Group__1 : rule__MODULEID__Group__1__Impl ;
    public final void rule__MODULEID__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1284:1: ( rule__MODULEID__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1285:2: rule__MODULEID__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__12530);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1291:1: rule__MODULEID__Group__1__Impl : ( ( rule__MODULEID__Group_1__0 )* ) ;
    public final void rule__MODULEID__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1295:1: ( ( ( rule__MODULEID__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1296:1: ( ( rule__MODULEID__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1296:1: ( ( rule__MODULEID__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1297:1: ( rule__MODULEID__Group_1__0 )*
            {
             before(grammarAccess.getMODULEIDAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1298:1: ( rule__MODULEID__Group_1__0 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==22) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1298:2: rule__MODULEID__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl2557);
            	    rule__MODULEID__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1312:1: rule__MODULEID__Group_1__0 : rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 ;
    public final void rule__MODULEID__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1316:1: ( rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1317:2: rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__02592);
            rule__MODULEID__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__02595);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1324:1: rule__MODULEID__Group_1__0__Impl : ( '.' ) ;
    public final void rule__MODULEID__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1328:1: ( ( '.' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1329:1: ( '.' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1329:1: ( '.' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1330:1: '.'
            {
             before(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0()); 
            match(input,22,FOLLOW_22_in_rule__MODULEID__Group_1__0__Impl2623); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1343:1: rule__MODULEID__Group_1__1 : rule__MODULEID__Group_1__1__Impl ;
    public final void rule__MODULEID__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1347:1: ( rule__MODULEID__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1348:2: rule__MODULEID__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__12654);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1354:1: rule__MODULEID__Group_1__1__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1358:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1359:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1359:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1360:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl2681); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1375:1: rule__ModuleIdentifier__Group__0 : rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 ;
    public final void rule__ModuleIdentifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1379:1: ( rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1380:2: rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__02714);
            rule__ModuleIdentifier__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__02717);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1387:1: rule__ModuleIdentifier__Group__0__Impl : ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) ;
    public final void rule__ModuleIdentifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1391:1: ( ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1392:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1392:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1393:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1394:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1394:2: rule__ModuleIdentifier__ModulenameAssignment_0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl2744);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1404:1: rule__ModuleIdentifier__Group__1 : rule__ModuleIdentifier__Group__1__Impl ;
    public final void rule__ModuleIdentifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1408:1: ( rule__ModuleIdentifier__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1409:2: rule__ModuleIdentifier__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__12774);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1415:1: rule__ModuleIdentifier__Group__1__Impl : ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) ;
    public final void rule__ModuleIdentifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1419:1: ( ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1420:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1420:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1421:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1422:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1422:2: rule__ModuleIdentifier__VersionAssignment_1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl2801);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1436:1: rule__ResourceList__Group__0 : rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 ;
    public final void rule__ResourceList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1440:1: ( rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1441:2: rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__02835);
            rule__ResourceList__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__02838);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1448:1: rule__ResourceList__Group__0__Impl : ( ( rule__ResourceList__ResourcesAssignment_0 ) ) ;
    public final void rule__ResourceList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1452:1: ( ( ( rule__ResourceList__ResourcesAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1453:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1453:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1454:1: ( rule__ResourceList__ResourcesAssignment_0 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1455:1: ( rule__ResourceList__ResourcesAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1455:2: rule__ResourceList__ResourcesAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl2865);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1465:1: rule__ResourceList__Group__1 : rule__ResourceList__Group__1__Impl ;
    public final void rule__ResourceList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1469:1: ( rule__ResourceList__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1470:2: rule__ResourceList__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__12895);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1476:1: rule__ResourceList__Group__1__Impl : ( ( rule__ResourceList__Group_1__0 )* ) ;
    public final void rule__ResourceList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1480:1: ( ( ( rule__ResourceList__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1481:1: ( ( rule__ResourceList__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1481:1: ( ( rule__ResourceList__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1482:1: ( rule__ResourceList__Group_1__0 )*
            {
             before(grammarAccess.getResourceListAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1483:1: ( rule__ResourceList__Group_1__0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==16) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1483:2: rule__ResourceList__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl2922);
            	    rule__ResourceList__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1497:1: rule__ResourceList__Group_1__0 : rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 ;
    public final void rule__ResourceList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1501:1: ( rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1502:2: rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__02957);
            rule__ResourceList__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__02960);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1509:1: rule__ResourceList__Group_1__0__Impl : ( ',' ) ;
    public final void rule__ResourceList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1513:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1514:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1514:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1515:1: ','
            {
             before(grammarAccess.getResourceListAccess().getCommaKeyword_1_0()); 
            match(input,16,FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl2988); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1528:1: rule__ResourceList__Group_1__1 : rule__ResourceList__Group_1__1__Impl ;
    public final void rule__ResourceList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1532:1: ( rule__ResourceList__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1533:2: rule__ResourceList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__13019);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1539:1: rule__ResourceList__Group_1__1__Impl : ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1543:1: ( ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1544:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1544:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1545:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1546:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1546:2: rule__ResourceList__ResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl3046);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1561:1: rule__TransformationModel__TransformationsAssignment : ( ruleTransformation ) ;
    public final void rule__TransformationModel__TransformationsAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1565:1: ( ( ruleTransformation ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1566:1: ( ruleTransformation )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1566:1: ( ruleTransformation )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1567:1: ruleTransformation
            {
             before(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment3085);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1576:1: rule__RemoveFrom__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__RemoveFrom__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1580:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1581:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1581:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1582:1: ruleResourceSet
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_13116);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1591:1: rule__EmbedInto__HostModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__HostModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1595:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1596:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1596:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1597:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_13147);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1606:1: rule__EmbedInto__ModulesAssignment_3 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1610:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1611:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1611:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1612:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_33178);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1621:1: rule__EmbedInto__ModulesAssignment_4_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1625:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1626:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1626:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1627:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_13209);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1636:1: rule__CreateModule__ModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__CreateModule__ModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1640:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1641:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1641:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1642:1: ruleModuleIdentifier
            {
             before(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_13240);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1651:1: rule__CreateModule__LayerAssignment_2 : ( ruleLayer ) ;
    public final void rule__CreateModule__LayerAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1655:1: ( ( ruleLayer ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1656:1: ( ruleLayer )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1656:1: ( ruleLayer )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1657:1: ruleLayer
            {
             before(grammarAccess.getCreateModuleAccess().getLayerLayerParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleLayer_in_rule__CreateModule__LayerAssignment_23271);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1666:1: rule__CreateModule__FromAssignment_3 : ( ruleFrom ) ;
    public final void rule__CreateModule__FromAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1670:1: ( ( ruleFrom ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1671:1: ( ruleFrom )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1671:1: ( ruleFrom )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1672:1: ruleFrom
            {
             before(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_33302);
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


    // $ANTLR start rule__Layer__LayerAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1681:1: rule__Layer__LayerAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Layer__LayerAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1685:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1686:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1686:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1687:1: RULE_STRING
            {
             before(grammarAccess.getLayerAccess().getLayerSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Layer__LayerAssignment_13333); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1696:1: rule__From__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__From__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1700:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1701:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1701:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1702:1: ruleResourceSet
            {
             before(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_13364);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1711:1: rule__ResourceSet__ModuleIdentifierAssignment_0 : ( ruleModuleIdentifier ) ;
    public final void rule__ResourceSet__ModuleIdentifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1715:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1716:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1716:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1717:1: ruleModuleIdentifier
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_03395);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1726:1: rule__ResourceSet__IncludeResourcesAssignment_1_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__IncludeResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1730:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1731:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1731:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1732:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_13426);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1741:1: rule__ResourceSet__ExcludeResourcesAssignment_2_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__ExcludeResourcesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1745:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1746:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1746:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1747:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_13457);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1756:1: rule__ModuleIdentifier__ModulenameAssignment_0 : ( ruleMODULEID ) ;
    public final void rule__ModuleIdentifier__ModulenameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1760:1: ( ( ruleMODULEID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1761:1: ( ruleMODULEID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1761:1: ( ruleMODULEID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1762:1: ruleMODULEID
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_03488);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1771:1: rule__ModuleIdentifier__VersionAssignment_1 : ( RULE_STRING ) ;
    public final void rule__ModuleIdentifier__VersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1775:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1776:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1776:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1777:1: RULE_STRING
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_13519); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1786:1: rule__ResourceList__ResourcesAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1790:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1791:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1791:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1792:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_03550); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1801:1: rule__ResourceList__ResourcesAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1805:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1806:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1806:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1807:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_13581); 
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
    public static final BitSet FOLLOW_rule__TransformationModel__TransformationsAssignment_in_ruleTransformationModel94 = new BitSet(new long[]{0x0000000000025002L});
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
    public static final BitSet FOLLOW_ruleLayer_in_entryRuleLayer362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLayer369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__0_in_ruleLayer395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_entryRuleFrom422 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFrom429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0_in_ruleFrom455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_entryRuleResourceSet482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceSet489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_entryRuleMODULEID542 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMODULEID549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleIdentifier609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_entryRuleResourceList662 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceList669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0795 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1857 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__0982 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__0985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__EmbedInto__Group__0__Impl1013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__11044 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__11047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21104 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31166 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41226 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1256 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01358 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01481 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__CreateModule__Group__0__Impl1512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11543 = new BitSet(new long[]{0x00000000000C2000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21603 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__LayerAssignment_2_in_rule__CreateModule__Group__2__Impl1633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31664 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__4_in_rule__CreateModule__Group__31667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__FromAssignment_3_in_rule__CreateModule__Group__3__Impl1694 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__4__Impl_in_rule__CreateModule__Group__41725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__CreateModule__Group__4__Impl1753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__0__Impl_in_rule__Layer__Group__01794 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Layer__Group__1_in_rule__Layer__Group__01797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Layer__Group__0__Impl1825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__Group__1__Impl_in_rule__Layer__Group__11856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Layer__LayerAssignment_1_in_rule__Layer__Group__1__Impl1883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__01917 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__From__Group__1_in_rule__From__Group__01920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__From__Group__0__Impl1948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__11979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl2006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__02040 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__02043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl2070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__12100 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__12103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl2130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__22161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl2188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__02225 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__02228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__ResourceSet__Group_1__0__Impl2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02348 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__ResourceSet__Group_2__0__Impl2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl2437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__02471 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__02474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl2501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__12530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl2557 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__02592 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__02595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MODULEID__Group_1__0__Impl2623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__12654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl2681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__02714 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__02717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__12774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl2801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__02835 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__02838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl2865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__12895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl2922 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__02957 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__02960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl2988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__13019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl3046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment3085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_13116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_13147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_33178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_13209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_13240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLayer_in_rule__CreateModule__LayerAssignment_23271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_33302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Layer__LayerAssignment_13333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_13364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_03395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_13426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_13457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_03488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_13519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_03550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_13581 = new BitSet(new long[]{0x0000000000000002L});

}