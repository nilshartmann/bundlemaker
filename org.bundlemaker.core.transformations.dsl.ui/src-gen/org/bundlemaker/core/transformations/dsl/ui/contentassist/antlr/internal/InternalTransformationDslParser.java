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


    // $ANTLR start entryRuleFrom
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:201:1: entryRuleFrom : ruleFrom EOF ;
    public final void entryRuleFrom() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:202:1: ( ruleFrom EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:203:1: ruleFrom EOF
            {
             before(grammarAccess.getFromRule()); 
            pushFollow(FOLLOW_ruleFrom_in_entryRuleFrom362);
            ruleFrom();
            _fsp--;

             after(grammarAccess.getFromRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFrom369); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:210:1: ruleFrom : ( ( rule__From__Group__0 ) ) ;
    public final void ruleFrom() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:214:2: ( ( ( rule__From__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__From__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:215:1: ( ( rule__From__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:216:1: ( rule__From__Group__0 )
            {
             before(grammarAccess.getFromAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:1: ( rule__From__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:217:2: rule__From__Group__0
            {
            pushFollow(FOLLOW_rule__From__Group__0_in_ruleFrom395);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:229:1: entryRuleResourceSet : ruleResourceSet EOF ;
    public final void entryRuleResourceSet() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:230:1: ( ruleResourceSet EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:231:1: ruleResourceSet EOF
            {
             before(grammarAccess.getResourceSetRule()); 
            pushFollow(FOLLOW_ruleResourceSet_in_entryRuleResourceSet422);
            ruleResourceSet();
            _fsp--;

             after(grammarAccess.getResourceSetRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceSet429); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:238:1: ruleResourceSet : ( ( rule__ResourceSet__Group__0 ) ) ;
    public final void ruleResourceSet() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:242:2: ( ( ( rule__ResourceSet__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__ResourceSet__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:243:1: ( ( rule__ResourceSet__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:244:1: ( rule__ResourceSet__Group__0 )
            {
             before(grammarAccess.getResourceSetAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:1: ( rule__ResourceSet__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:245:2: rule__ResourceSet__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet455);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:257:1: entryRuleMODULEID : ruleMODULEID EOF ;
    public final void entryRuleMODULEID() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:258:1: ( ruleMODULEID EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:259:1: ruleMODULEID EOF
            {
             before(grammarAccess.getMODULEIDRule()); 
            pushFollow(FOLLOW_ruleMODULEID_in_entryRuleMODULEID482);
            ruleMODULEID();
            _fsp--;

             after(grammarAccess.getMODULEIDRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMODULEID489); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:266:1: ruleMODULEID : ( ( rule__MODULEID__Group__0 ) ) ;
    public final void ruleMODULEID() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:270:2: ( ( ( rule__MODULEID__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__MODULEID__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:271:1: ( ( rule__MODULEID__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:272:1: ( rule__MODULEID__Group__0 )
            {
             before(grammarAccess.getMODULEIDAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:1: ( rule__MODULEID__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:273:2: rule__MODULEID__Group__0
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID515);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:285:1: entryRuleModuleIdentifier : ruleModuleIdentifier EOF ;
    public final void entryRuleModuleIdentifier() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:286:1: ( ruleModuleIdentifier EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:287:1: ruleModuleIdentifier EOF
            {
             before(grammarAccess.getModuleIdentifierRule()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier542);
            ruleModuleIdentifier();
            _fsp--;

             after(grammarAccess.getModuleIdentifierRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleIdentifier549); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:294:1: ruleModuleIdentifier : ( ( rule__ModuleIdentifier__Group__0 ) ) ;
    public final void ruleModuleIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:298:2: ( ( ( rule__ModuleIdentifier__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:299:1: ( ( rule__ModuleIdentifier__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:300:1: ( rule__ModuleIdentifier__Group__0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:1: ( rule__ModuleIdentifier__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:301:2: rule__ModuleIdentifier__Group__0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier575);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:313:1: entryRuleResourceList : ruleResourceList EOF ;
    public final void entryRuleResourceList() throws RecognitionException {
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:314:1: ( ruleResourceList EOF )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:315:1: ruleResourceList EOF
            {
             before(grammarAccess.getResourceListRule()); 
            pushFollow(FOLLOW_ruleResourceList_in_entryRuleResourceList602);
            ruleResourceList();
            _fsp--;

             after(grammarAccess.getResourceListRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleResourceList609); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:322:1: ruleResourceList : ( ( rule__ResourceList__Group__0 ) ) ;
    public final void ruleResourceList() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:326:2: ( ( ( rule__ResourceList__Group__0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__ResourceList__Group__0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:327:1: ( ( rule__ResourceList__Group__0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:328:1: ( rule__ResourceList__Group__0 )
            {
             before(grammarAccess.getResourceListAccess().getGroup()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:1: ( rule__ResourceList__Group__0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:329:2: rule__ResourceList__Group__0
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList635);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:341:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) );
    public final void rule__Transformation__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:345:1: ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) )
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
                    new NoViableAltException("341:1: rule__Transformation__Alternatives : ( ( ruleRemoveFrom ) | ( ruleEmbedInto ) | ( ruleCreateModule ) );", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:346:1: ( ruleRemoveFrom )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:346:1: ( ruleRemoveFrom )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:347:1: ruleRemoveFrom
                    {
                     before(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives671);
                    ruleRemoveFrom();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getRemoveFromParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:352:6: ( ruleEmbedInto )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:352:6: ( ruleEmbedInto )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:353:1: ruleEmbedInto
                    {
                     before(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives688);
                    ruleEmbedInto();
                    _fsp--;

                     after(grammarAccess.getTransformationAccess().getEmbedIntoParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:358:6: ( ruleCreateModule )
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:358:6: ( ruleCreateModule )
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:359:1: ruleCreateModule
                    {
                     before(grammarAccess.getTransformationAccess().getCreateModuleParserRuleCall_2()); 
                    pushFollow(FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives705);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:371:1: rule__RemoveFrom__Group__0 : rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 ;
    public final void rule__RemoveFrom__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:375:1: ( rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:376:2: rule__RemoveFrom__Group__0__Impl rule__RemoveFrom__Group__1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0735);
            rule__RemoveFrom__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0738);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:383:1: rule__RemoveFrom__Group__0__Impl : ( 'remove-from' ) ;
    public final void rule__RemoveFrom__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:387:1: ( ( 'remove-from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:388:1: ( 'remove-from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:388:1: ( 'remove-from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:389:1: 'remove-from'
            {
             before(grammarAccess.getRemoveFromAccess().getRemoveFromKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl766); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:402:1: rule__RemoveFrom__Group__1 : rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 ;
    public final void rule__RemoveFrom__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:406:1: ( rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:407:2: rule__RemoveFrom__Group__1__Impl rule__RemoveFrom__Group__2
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1797);
            rule__RemoveFrom__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1800);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:414:1: rule__RemoveFrom__Group__1__Impl : ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) ;
    public final void rule__RemoveFrom__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:418:1: ( ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:419:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:419:1: ( ( rule__RemoveFrom__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:420:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:421:1: ( rule__RemoveFrom__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:421:2: rule__RemoveFrom__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl827);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:431:1: rule__RemoveFrom__Group__2 : rule__RemoveFrom__Group__2__Impl ;
    public final void rule__RemoveFrom__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:435:1: ( rule__RemoveFrom__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:436:2: rule__RemoveFrom__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2857);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:442:1: rule__RemoveFrom__Group__2__Impl : ( ';' ) ;
    public final void rule__RemoveFrom__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:446:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:447:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:447:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:448:1: ';'
            {
             before(grammarAccess.getRemoveFromAccess().getSemicolonKeyword_2()); 
            match(input,13,FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl885); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:467:1: rule__EmbedInto__Group__0 : rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 ;
    public final void rule__EmbedInto__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:471:1: ( rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:472:2: rule__EmbedInto__Group__0__Impl rule__EmbedInto__Group__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__0922);
            rule__EmbedInto__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__0925);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:479:1: rule__EmbedInto__Group__0__Impl : ( 'embed-into' ) ;
    public final void rule__EmbedInto__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:483:1: ( ( 'embed-into' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:484:1: ( 'embed-into' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:484:1: ( 'embed-into' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:485:1: 'embed-into'
            {
             before(grammarAccess.getEmbedIntoAccess().getEmbedIntoKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__EmbedInto__Group__0__Impl953); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:498:1: rule__EmbedInto__Group__1 : rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 ;
    public final void rule__EmbedInto__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:502:1: ( rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:503:2: rule__EmbedInto__Group__1__Impl rule__EmbedInto__Group__2
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__1984);
            rule__EmbedInto__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__1987);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:510:1: rule__EmbedInto__Group__1__Impl : ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) ;
    public final void rule__EmbedInto__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:514:1: ( ( ( rule__EmbedInto__HostModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:515:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:515:1: ( ( rule__EmbedInto__HostModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:516:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:517:1: ( rule__EmbedInto__HostModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:517:2: rule__EmbedInto__HostModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1014);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:527:1: rule__EmbedInto__Group__2 : rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 ;
    public final void rule__EmbedInto__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:531:1: ( rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:532:2: rule__EmbedInto__Group__2__Impl rule__EmbedInto__Group__3
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21044);
            rule__EmbedInto__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21047);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:539:1: rule__EmbedInto__Group__2__Impl : ( 'modules' ) ;
    public final void rule__EmbedInto__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:543:1: ( ( 'modules' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:544:1: ( 'modules' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:544:1: ( 'modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:545:1: 'modules'
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesKeyword_2()); 
            match(input,15,FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1075); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:558:1: rule__EmbedInto__Group__3 : rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 ;
    public final void rule__EmbedInto__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:562:1: ( rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:563:2: rule__EmbedInto__Group__3__Impl rule__EmbedInto__Group__4
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31106);
            rule__EmbedInto__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31109);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:570:1: rule__EmbedInto__Group__3__Impl : ( ( rule__EmbedInto__ModulesAssignment_3 ) ) ;
    public final void rule__EmbedInto__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:574:1: ( ( ( rule__EmbedInto__ModulesAssignment_3 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:575:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:575:1: ( ( rule__EmbedInto__ModulesAssignment_3 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:576:1: ( rule__EmbedInto__ModulesAssignment_3 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_3()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:577:1: ( rule__EmbedInto__ModulesAssignment_3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:577:2: rule__EmbedInto__ModulesAssignment_3
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1136);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:587:1: rule__EmbedInto__Group__4 : rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 ;
    public final void rule__EmbedInto__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:591:1: ( rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:592:2: rule__EmbedInto__Group__4__Impl rule__EmbedInto__Group__5
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41166);
            rule__EmbedInto__Group__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41169);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:599:1: rule__EmbedInto__Group__4__Impl : ( ( rule__EmbedInto__Group_4__0 )* ) ;
    public final void rule__EmbedInto__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:603:1: ( ( ( rule__EmbedInto__Group_4__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:604:1: ( ( rule__EmbedInto__Group_4__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:604:1: ( ( rule__EmbedInto__Group_4__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:605:1: ( rule__EmbedInto__Group_4__0 )*
            {
             before(grammarAccess.getEmbedIntoAccess().getGroup_4()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:606:1: ( rule__EmbedInto__Group_4__0 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:606:2: rule__EmbedInto__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1196);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:616:1: rule__EmbedInto__Group__5 : rule__EmbedInto__Group__5__Impl ;
    public final void rule__EmbedInto__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:620:1: ( rule__EmbedInto__Group__5__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:621:2: rule__EmbedInto__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51227);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:627:1: rule__EmbedInto__Group__5__Impl : ( ';' ) ;
    public final void rule__EmbedInto__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:631:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:632:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:632:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:633:1: ';'
            {
             before(grammarAccess.getEmbedIntoAccess().getSemicolonKeyword_5()); 
            match(input,13,FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1255); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:658:1: rule__EmbedInto__Group_4__0 : rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 ;
    public final void rule__EmbedInto__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:662:1: ( rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:663:2: rule__EmbedInto__Group_4__0__Impl rule__EmbedInto__Group_4__1
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01298);
            rule__EmbedInto__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01301);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:670:1: rule__EmbedInto__Group_4__0__Impl : ( ',' ) ;
    public final void rule__EmbedInto__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:674:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:675:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:675:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:676:1: ','
            {
             before(grammarAccess.getEmbedIntoAccess().getCommaKeyword_4_0()); 
            match(input,16,FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1329); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:689:1: rule__EmbedInto__Group_4__1 : rule__EmbedInto__Group_4__1__Impl ;
    public final void rule__EmbedInto__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:693:1: ( rule__EmbedInto__Group_4__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:694:2: rule__EmbedInto__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11360);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:700:1: rule__EmbedInto__Group_4__1__Impl : ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) ;
    public final void rule__EmbedInto__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:704:1: ( ( ( rule__EmbedInto__ModulesAssignment_4_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:705:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:705:1: ( ( rule__EmbedInto__ModulesAssignment_4_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:706:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesAssignment_4_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:707:1: ( rule__EmbedInto__ModulesAssignment_4_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:707:2: rule__EmbedInto__ModulesAssignment_4_1
            {
            pushFollow(FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1387);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:721:1: rule__CreateModule__Group__0 : rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 ;
    public final void rule__CreateModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:725:1: ( rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:726:2: rule__CreateModule__Group__0__Impl rule__CreateModule__Group__1
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01421);
            rule__CreateModule__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01424);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:733:1: rule__CreateModule__Group__0__Impl : ( 'create-module' ) ;
    public final void rule__CreateModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:737:1: ( ( 'create-module' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:738:1: ( 'create-module' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:738:1: ( 'create-module' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:739:1: 'create-module'
            {
             before(grammarAccess.getCreateModuleAccess().getCreateModuleKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__CreateModule__Group__0__Impl1452); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:752:1: rule__CreateModule__Group__1 : rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 ;
    public final void rule__CreateModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:756:1: ( rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:757:2: rule__CreateModule__Group__1__Impl rule__CreateModule__Group__2
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11483);
            rule__CreateModule__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11486);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:764:1: rule__CreateModule__Group__1__Impl : ( ( rule__CreateModule__ModuleAssignment_1 ) ) ;
    public final void rule__CreateModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:768:1: ( ( ( rule__CreateModule__ModuleAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:769:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:769:1: ( ( rule__CreateModule__ModuleAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:770:1: ( rule__CreateModule__ModuleAssignment_1 )
            {
             before(grammarAccess.getCreateModuleAccess().getModuleAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:771:1: ( rule__CreateModule__ModuleAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:771:2: rule__CreateModule__ModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1513);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:781:1: rule__CreateModule__Group__2 : rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 ;
    public final void rule__CreateModule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:785:1: ( rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:786:2: rule__CreateModule__Group__2__Impl rule__CreateModule__Group__3
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21543);
            rule__CreateModule__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21546);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:793:1: rule__CreateModule__Group__2__Impl : ( ( rule__CreateModule__FromAssignment_2 )* ) ;
    public final void rule__CreateModule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:797:1: ( ( ( rule__CreateModule__FromAssignment_2 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:798:1: ( ( rule__CreateModule__FromAssignment_2 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:798:1: ( ( rule__CreateModule__FromAssignment_2 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:799:1: ( rule__CreateModule__FromAssignment_2 )*
            {
             before(grammarAccess.getCreateModuleAccess().getFromAssignment_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:800:1: ( rule__CreateModule__FromAssignment_2 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==18) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:800:2: rule__CreateModule__FromAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__CreateModule__FromAssignment_2_in_rule__CreateModule__Group__2__Impl1573);
            	    rule__CreateModule__FromAssignment_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

             after(grammarAccess.getCreateModuleAccess().getFromAssignment_2()); 

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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:810:1: rule__CreateModule__Group__3 : rule__CreateModule__Group__3__Impl ;
    public final void rule__CreateModule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:814:1: ( rule__CreateModule__Group__3__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:815:2: rule__CreateModule__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31604);
            rule__CreateModule__Group__3__Impl();
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:821:1: rule__CreateModule__Group__3__Impl : ( ';' ) ;
    public final void rule__CreateModule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:825:1: ( ( ';' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:826:1: ( ';' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:826:1: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:827:1: ';'
            {
             before(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_3()); 
            match(input,13,FOLLOW_13_in_rule__CreateModule__Group__3__Impl1632); 
             after(grammarAccess.getCreateModuleAccess().getSemicolonKeyword_3()); 

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


    // $ANTLR start rule__From__Group__0
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:848:1: rule__From__Group__0 : rule__From__Group__0__Impl rule__From__Group__1 ;
    public final void rule__From__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:852:1: ( rule__From__Group__0__Impl rule__From__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:853:2: rule__From__Group__0__Impl rule__From__Group__1
            {
            pushFollow(FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__01671);
            rule__From__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__From__Group__1_in_rule__From__Group__01674);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:860:1: rule__From__Group__0__Impl : ( 'from' ) ;
    public final void rule__From__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:864:1: ( ( 'from' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:865:1: ( 'from' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:865:1: ( 'from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:866:1: 'from'
            {
             before(grammarAccess.getFromAccess().getFromKeyword_0()); 
            match(input,18,FOLLOW_18_in_rule__From__Group__0__Impl1702); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:879:1: rule__From__Group__1 : rule__From__Group__1__Impl ;
    public final void rule__From__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:883:1: ( rule__From__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:884:2: rule__From__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__11733);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:890:1: rule__From__Group__1__Impl : ( ( rule__From__ResourceSetAssignment_1 ) ) ;
    public final void rule__From__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:894:1: ( ( ( rule__From__ResourceSetAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:895:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:895:1: ( ( rule__From__ResourceSetAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:896:1: ( rule__From__ResourceSetAssignment_1 )
            {
             before(grammarAccess.getFromAccess().getResourceSetAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:897:1: ( rule__From__ResourceSetAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:897:2: rule__From__ResourceSetAssignment_1
            {
            pushFollow(FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl1760);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:911:1: rule__ResourceSet__Group__0 : rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 ;
    public final void rule__ResourceSet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:915:1: ( rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:916:2: rule__ResourceSet__Group__0__Impl rule__ResourceSet__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__01794);
            rule__ResourceSet__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__01797);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:923:1: rule__ResourceSet__Group__0__Impl : ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) ;
    public final void rule__ResourceSet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:927:1: ( ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:928:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:928:1: ( ( rule__ResourceSet__ModuleIdentifierAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:929:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:930:1: ( rule__ResourceSet__ModuleIdentifierAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:930:2: rule__ResourceSet__ModuleIdentifierAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl1824);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:940:1: rule__ResourceSet__Group__1 : rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 ;
    public final void rule__ResourceSet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:944:1: ( rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:945:2: rule__ResourceSet__Group__1__Impl rule__ResourceSet__Group__2
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__11854);
            rule__ResourceSet__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__11857);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:952:1: rule__ResourceSet__Group__1__Impl : ( ( rule__ResourceSet__Group_1__0 )? ) ;
    public final void rule__ResourceSet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:956:1: ( ( ( rule__ResourceSet__Group_1__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:957:1: ( ( rule__ResourceSet__Group_1__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:957:1: ( ( rule__ResourceSet__Group_1__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:958:1: ( rule__ResourceSet__Group_1__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:959:1: ( rule__ResourceSet__Group_1__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:959:2: rule__ResourceSet__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl1884);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:969:1: rule__ResourceSet__Group__2 : rule__ResourceSet__Group__2__Impl ;
    public final void rule__ResourceSet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:973:1: ( rule__ResourceSet__Group__2__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:974:2: rule__ResourceSet__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__21915);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:980:1: rule__ResourceSet__Group__2__Impl : ( ( rule__ResourceSet__Group_2__0 )? ) ;
    public final void rule__ResourceSet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:984:1: ( ( ( rule__ResourceSet__Group_2__0 )? ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:985:1: ( ( rule__ResourceSet__Group_2__0 )? )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:985:1: ( ( rule__ResourceSet__Group_2__0 )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:986:1: ( rule__ResourceSet__Group_2__0 )?
            {
             before(grammarAccess.getResourceSetAccess().getGroup_2()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:987:1: ( rule__ResourceSet__Group_2__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==20) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:987:2: rule__ResourceSet__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl1942);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1003:1: rule__ResourceSet__Group_1__0 : rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 ;
    public final void rule__ResourceSet__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1007:1: ( rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1008:2: rule__ResourceSet__Group_1__0__Impl rule__ResourceSet__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__01979);
            rule__ResourceSet__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__01982);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1015:1: rule__ResourceSet__Group_1__0__Impl : ( 'include' ) ;
    public final void rule__ResourceSet__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1019:1: ( ( 'include' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1020:1: ( 'include' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1020:1: ( 'include' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1021:1: 'include'
            {
             before(grammarAccess.getResourceSetAccess().getIncludeKeyword_1_0()); 
            match(input,19,FOLLOW_19_in_rule__ResourceSet__Group_1__0__Impl2010); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1034:1: rule__ResourceSet__Group_1__1 : rule__ResourceSet__Group_1__1__Impl ;
    public final void rule__ResourceSet__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1038:1: ( rule__ResourceSet__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1039:2: rule__ResourceSet__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12041);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1045:1: rule__ResourceSet__Group_1__1__Impl : ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceSet__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1049:1: ( ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1050:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1050:1: ( ( rule__ResourceSet__IncludeResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1051:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1052:1: ( rule__ResourceSet__IncludeResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1052:2: rule__ResourceSet__IncludeResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2068);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1066:1: rule__ResourceSet__Group_2__0 : rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 ;
    public final void rule__ResourceSet__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1070:1: ( rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1071:2: rule__ResourceSet__Group_2__0__Impl rule__ResourceSet__Group_2__1
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02102);
            rule__ResourceSet__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02105);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1078:1: rule__ResourceSet__Group_2__0__Impl : ( 'exclude' ) ;
    public final void rule__ResourceSet__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1082:1: ( ( 'exclude' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1083:1: ( 'exclude' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1083:1: ( 'exclude' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1084:1: 'exclude'
            {
             before(grammarAccess.getResourceSetAccess().getExcludeKeyword_2_0()); 
            match(input,20,FOLLOW_20_in_rule__ResourceSet__Group_2__0__Impl2133); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1097:1: rule__ResourceSet__Group_2__1 : rule__ResourceSet__Group_2__1__Impl ;
    public final void rule__ResourceSet__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1101:1: ( rule__ResourceSet__Group_2__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1102:2: rule__ResourceSet__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12164);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1108:1: rule__ResourceSet__Group_2__1__Impl : ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) ;
    public final void rule__ResourceSet__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1112:1: ( ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1113:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1113:1: ( ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1114:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesAssignment_2_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1115:1: ( rule__ResourceSet__ExcludeResourcesAssignment_2_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1115:2: rule__ResourceSet__ExcludeResourcesAssignment_2_1
            {
            pushFollow(FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl2191);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1129:1: rule__MODULEID__Group__0 : rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 ;
    public final void rule__MODULEID__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1133:1: ( rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1134:2: rule__MODULEID__Group__0__Impl rule__MODULEID__Group__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__02225);
            rule__MODULEID__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__02228);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1141:1: rule__MODULEID__Group__0__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1145:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1146:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1146:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1147:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_0()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl2255); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1158:1: rule__MODULEID__Group__1 : rule__MODULEID__Group__1__Impl ;
    public final void rule__MODULEID__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1162:1: ( rule__MODULEID__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1163:2: rule__MODULEID__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__12284);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1169:1: rule__MODULEID__Group__1__Impl : ( ( rule__MODULEID__Group_1__0 )* ) ;
    public final void rule__MODULEID__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1173:1: ( ( ( rule__MODULEID__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1174:1: ( ( rule__MODULEID__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1174:1: ( ( rule__MODULEID__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1175:1: ( rule__MODULEID__Group_1__0 )*
            {
             before(grammarAccess.getMODULEIDAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1176:1: ( rule__MODULEID__Group_1__0 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==21) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1176:2: rule__MODULEID__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl2311);
            	    rule__MODULEID__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1190:1: rule__MODULEID__Group_1__0 : rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 ;
    public final void rule__MODULEID__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1194:1: ( rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1195:2: rule__MODULEID__Group_1__0__Impl rule__MODULEID__Group_1__1
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__02346);
            rule__MODULEID__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__02349);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1202:1: rule__MODULEID__Group_1__0__Impl : ( '.' ) ;
    public final void rule__MODULEID__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1206:1: ( ( '.' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1207:1: ( '.' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1207:1: ( '.' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1208:1: '.'
            {
             before(grammarAccess.getMODULEIDAccess().getFullStopKeyword_1_0()); 
            match(input,21,FOLLOW_21_in_rule__MODULEID__Group_1__0__Impl2377); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1221:1: rule__MODULEID__Group_1__1 : rule__MODULEID__Group_1__1__Impl ;
    public final void rule__MODULEID__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1225:1: ( rule__MODULEID__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1226:2: rule__MODULEID__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__12408);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1232:1: rule__MODULEID__Group_1__1__Impl : ( RULE_BMID ) ;
    public final void rule__MODULEID__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1236:1: ( ( RULE_BMID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1237:1: ( RULE_BMID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1237:1: ( RULE_BMID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1238:1: RULE_BMID
            {
             before(grammarAccess.getMODULEIDAccess().getBMIDTerminalRuleCall_1_1()); 
            match(input,RULE_BMID,FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl2435); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1253:1: rule__ModuleIdentifier__Group__0 : rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 ;
    public final void rule__ModuleIdentifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1257:1: ( rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1258:2: rule__ModuleIdentifier__Group__0__Impl rule__ModuleIdentifier__Group__1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__02468);
            rule__ModuleIdentifier__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__02471);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1265:1: rule__ModuleIdentifier__Group__0__Impl : ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) ;
    public final void rule__ModuleIdentifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1269:1: ( ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1270:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1270:1: ( ( rule__ModuleIdentifier__ModulenameAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1271:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1272:1: ( rule__ModuleIdentifier__ModulenameAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1272:2: rule__ModuleIdentifier__ModulenameAssignment_0
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl2498);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1282:1: rule__ModuleIdentifier__Group__1 : rule__ModuleIdentifier__Group__1__Impl ;
    public final void rule__ModuleIdentifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1286:1: ( rule__ModuleIdentifier__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1287:2: rule__ModuleIdentifier__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__12528);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1293:1: rule__ModuleIdentifier__Group__1__Impl : ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) ;
    public final void rule__ModuleIdentifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1297:1: ( ( ( rule__ModuleIdentifier__VersionAssignment_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1298:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1298:1: ( ( rule__ModuleIdentifier__VersionAssignment_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1299:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionAssignment_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1300:1: ( rule__ModuleIdentifier__VersionAssignment_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1300:2: rule__ModuleIdentifier__VersionAssignment_1
            {
            pushFollow(FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl2555);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1314:1: rule__ResourceList__Group__0 : rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 ;
    public final void rule__ResourceList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1318:1: ( rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1319:2: rule__ResourceList__Group__0__Impl rule__ResourceList__Group__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__02589);
            rule__ResourceList__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__02592);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1326:1: rule__ResourceList__Group__0__Impl : ( ( rule__ResourceList__ResourcesAssignment_0 ) ) ;
    public final void rule__ResourceList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1330:1: ( ( ( rule__ResourceList__ResourcesAssignment_0 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1331:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1331:1: ( ( rule__ResourceList__ResourcesAssignment_0 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1332:1: ( rule__ResourceList__ResourcesAssignment_0 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_0()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1333:1: ( rule__ResourceList__ResourcesAssignment_0 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1333:2: rule__ResourceList__ResourcesAssignment_0
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl2619);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1343:1: rule__ResourceList__Group__1 : rule__ResourceList__Group__1__Impl ;
    public final void rule__ResourceList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1347:1: ( rule__ResourceList__Group__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1348:2: rule__ResourceList__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__12649);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1354:1: rule__ResourceList__Group__1__Impl : ( ( rule__ResourceList__Group_1__0 )* ) ;
    public final void rule__ResourceList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1358:1: ( ( ( rule__ResourceList__Group_1__0 )* ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1359:1: ( ( rule__ResourceList__Group_1__0 )* )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1359:1: ( ( rule__ResourceList__Group_1__0 )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1360:1: ( rule__ResourceList__Group_1__0 )*
            {
             before(grammarAccess.getResourceListAccess().getGroup_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1361:1: ( rule__ResourceList__Group_1__0 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==16) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1361:2: rule__ResourceList__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl2676);
            	    rule__ResourceList__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1375:1: rule__ResourceList__Group_1__0 : rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 ;
    public final void rule__ResourceList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1379:1: ( rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1380:2: rule__ResourceList__Group_1__0__Impl rule__ResourceList__Group_1__1
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__02711);
            rule__ResourceList__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__02714);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1387:1: rule__ResourceList__Group_1__0__Impl : ( ',' ) ;
    public final void rule__ResourceList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1391:1: ( ( ',' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1392:1: ( ',' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1392:1: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1393:1: ','
            {
             before(grammarAccess.getResourceListAccess().getCommaKeyword_1_0()); 
            match(input,16,FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl2742); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1406:1: rule__ResourceList__Group_1__1 : rule__ResourceList__Group_1__1__Impl ;
    public final void rule__ResourceList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1410:1: ( rule__ResourceList__Group_1__1__Impl )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1411:2: rule__ResourceList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__12773);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1417:1: rule__ResourceList__Group_1__1__Impl : ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) ;
    public final void rule__ResourceList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1421:1: ( ( ( rule__ResourceList__ResourcesAssignment_1_1 ) ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1422:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1422:1: ( ( rule__ResourceList__ResourcesAssignment_1_1 ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1423:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            {
             before(grammarAccess.getResourceListAccess().getResourcesAssignment_1_1()); 
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1424:1: ( rule__ResourceList__ResourcesAssignment_1_1 )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1424:2: rule__ResourceList__ResourcesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl2800);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1439:1: rule__TransformationModel__TransformationsAssignment : ( ruleTransformation ) ;
    public final void rule__TransformationModel__TransformationsAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1443:1: ( ( ruleTransformation ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1444:1: ( ruleTransformation )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1444:1: ( ruleTransformation )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1445:1: ruleTransformation
            {
             before(grammarAccess.getTransformationModelAccess().getTransformationsTransformationParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment2839);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1454:1: rule__RemoveFrom__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__RemoveFrom__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1458:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1459:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1459:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1460:1: ruleResourceSet
            {
             before(grammarAccess.getRemoveFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_12870);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1469:1: rule__EmbedInto__HostModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__HostModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1473:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1474:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1474:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1475:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getHostModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_12901);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1484:1: rule__EmbedInto__ModulesAssignment_3 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1488:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1489:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1489:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1490:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_32932);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1499:1: rule__EmbedInto__ModulesAssignment_4_1 : ( ruleModuleIdentifier ) ;
    public final void rule__EmbedInto__ModulesAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1503:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1504:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1504:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1505:1: ruleModuleIdentifier
            {
             before(grammarAccess.getEmbedIntoAccess().getModulesModuleIdentifierParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_12963);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1514:1: rule__CreateModule__ModuleAssignment_1 : ( ruleModuleIdentifier ) ;
    public final void rule__CreateModule__ModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1518:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1519:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1519:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1520:1: ruleModuleIdentifier
            {
             before(grammarAccess.getCreateModuleAccess().getModuleModuleIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_12994);
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


    // $ANTLR start rule__CreateModule__FromAssignment_2
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1529:1: rule__CreateModule__FromAssignment_2 : ( ruleFrom ) ;
    public final void rule__CreateModule__FromAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1533:1: ( ( ruleFrom ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1534:1: ( ruleFrom )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1534:1: ( ruleFrom )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1535:1: ruleFrom
            {
             before(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_23025);
            ruleFrom();
            _fsp--;

             after(grammarAccess.getCreateModuleAccess().getFromFromParserRuleCall_2_0()); 

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
    // $ANTLR end rule__CreateModule__FromAssignment_2


    // $ANTLR start rule__From__ResourceSetAssignment_1
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1544:1: rule__From__ResourceSetAssignment_1 : ( ruleResourceSet ) ;
    public final void rule__From__ResourceSetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1548:1: ( ( ruleResourceSet ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1549:1: ( ruleResourceSet )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1549:1: ( ruleResourceSet )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1550:1: ruleResourceSet
            {
             before(grammarAccess.getFromAccess().getResourceSetResourceSetParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_13056);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1559:1: rule__ResourceSet__ModuleIdentifierAssignment_0 : ( ruleModuleIdentifier ) ;
    public final void rule__ResourceSet__ModuleIdentifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1563:1: ( ( ruleModuleIdentifier ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1564:1: ( ruleModuleIdentifier )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1564:1: ( ruleModuleIdentifier )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1565:1: ruleModuleIdentifier
            {
             before(grammarAccess.getResourceSetAccess().getModuleIdentifierModuleIdentifierParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_03087);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1574:1: rule__ResourceSet__IncludeResourcesAssignment_1_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__IncludeResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1578:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1579:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1579:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1580:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getIncludeResourcesResourceListParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_13118);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1589:1: rule__ResourceSet__ExcludeResourcesAssignment_2_1 : ( ruleResourceList ) ;
    public final void rule__ResourceSet__ExcludeResourcesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1593:1: ( ( ruleResourceList ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1594:1: ( ruleResourceList )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1594:1: ( ruleResourceList )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1595:1: ruleResourceList
            {
             before(grammarAccess.getResourceSetAccess().getExcludeResourcesResourceListParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_13149);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1604:1: rule__ModuleIdentifier__ModulenameAssignment_0 : ( ruleMODULEID ) ;
    public final void rule__ModuleIdentifier__ModulenameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1608:1: ( ( ruleMODULEID ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1609:1: ( ruleMODULEID )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1609:1: ( ruleMODULEID )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1610:1: ruleMODULEID
            {
             before(grammarAccess.getModuleIdentifierAccess().getModulenameMODULEIDParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_03180);
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1619:1: rule__ModuleIdentifier__VersionAssignment_1 : ( RULE_STRING ) ;
    public final void rule__ModuleIdentifier__VersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1623:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1624:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1624:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1625:1: RULE_STRING
            {
             before(grammarAccess.getModuleIdentifierAccess().getVersionSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_13211); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1634:1: rule__ResourceList__ResourcesAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1638:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1639:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1639:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1640:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_03242); 
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
    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1649:1: rule__ResourceList__ResourcesAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ResourceList__ResourcesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1653:1: ( ( RULE_STRING ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1654:1: ( RULE_STRING )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1654:1: ( RULE_STRING )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1655:1: RULE_STRING
            {
             before(grammarAccess.getResourceListAccess().getResourcesSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_13273); 
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
    public static final BitSet FOLLOW_ruleFrom_in_entryRuleFrom362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFrom369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0_in_ruleFrom395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_entryRuleResourceSet422 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceSet429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0_in_ruleResourceSet455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_entryRuleMODULEID482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMODULEID489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0_in_ruleMODULEID515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_entryRuleModuleIdentifier542 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleIdentifier549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0_in_ruleModuleIdentifier575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_entryRuleResourceList602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleResourceList609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0_in_ruleResourceList635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRemoveFrom_in_rule__Transformation__Alternatives671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEmbedInto_in_rule__Transformation__Alternatives688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCreateModule_in_rule__Transformation__Alternatives705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__0__Impl_in_rule__RemoveFrom__Group__0735 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1_in_rule__RemoveFrom__Group__0738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__RemoveFrom__Group__0__Impl766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__1__Impl_in_rule__RemoveFrom__Group__1797 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2_in_rule__RemoveFrom__Group__1800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__ResourceSetAssignment_1_in_rule__RemoveFrom__Group__1__Impl827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RemoveFrom__Group__2__Impl_in_rule__RemoveFrom__Group__2857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__RemoveFrom__Group__2__Impl885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__0__Impl_in_rule__EmbedInto__Group__0922 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1_in_rule__EmbedInto__Group__0925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__EmbedInto__Group__0__Impl953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__1__Impl_in_rule__EmbedInto__Group__1984 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2_in_rule__EmbedInto__Group__1987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__HostModuleAssignment_1_in_rule__EmbedInto__Group__1__Impl1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__2__Impl_in_rule__EmbedInto__Group__21044 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3_in_rule__EmbedInto__Group__21047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__EmbedInto__Group__2__Impl1075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__3__Impl_in_rule__EmbedInto__Group__31106 = new BitSet(new long[]{0x0000000000012000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4_in_rule__EmbedInto__Group__31109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_3_in_rule__EmbedInto__Group__3__Impl1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__4__Impl_in_rule__EmbedInto__Group__41166 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5_in_rule__EmbedInto__Group__41169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0_in_rule__EmbedInto__Group__4__Impl1196 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group__5__Impl_in_rule__EmbedInto__Group__51227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__EmbedInto__Group__5__Impl1255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__0__Impl_in_rule__EmbedInto__Group_4__01298 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1_in_rule__EmbedInto__Group_4__01301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__EmbedInto__Group_4__0__Impl1329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__Group_4__1__Impl_in_rule__EmbedInto__Group_4__11360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EmbedInto__ModulesAssignment_4_1_in_rule__EmbedInto__Group_4__1__Impl1387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__0__Impl_in_rule__CreateModule__Group__01421 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1_in_rule__CreateModule__Group__01424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__CreateModule__Group__0__Impl1452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__1__Impl_in_rule__CreateModule__Group__11483 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2_in_rule__CreateModule__Group__11486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__ModuleAssignment_1_in_rule__CreateModule__Group__1__Impl1513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__2__Impl_in_rule__CreateModule__Group__21543 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3_in_rule__CreateModule__Group__21546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CreateModule__FromAssignment_2_in_rule__CreateModule__Group__2__Impl1573 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_rule__CreateModule__Group__3__Impl_in_rule__CreateModule__Group__31604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__CreateModule__Group__3__Impl1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__0__Impl_in_rule__From__Group__01671 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__From__Group__1_in_rule__From__Group__01674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__From__Group__0__Impl1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__Group__1__Impl_in_rule__From__Group__11733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__From__ResourceSetAssignment_1_in_rule__From__Group__1__Impl1760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__0__Impl_in_rule__ResourceSet__Group__01794 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1_in_rule__ResourceSet__Group__01797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ModuleIdentifierAssignment_0_in_rule__ResourceSet__Group__0__Impl1824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__1__Impl_in_rule__ResourceSet__Group__11854 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2_in_rule__ResourceSet__Group__11857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0_in_rule__ResourceSet__Group__1__Impl1884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group__2__Impl_in_rule__ResourceSet__Group__21915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0_in_rule__ResourceSet__Group__2__Impl1942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__0__Impl_in_rule__ResourceSet__Group_1__01979 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1_in_rule__ResourceSet__Group_1__01982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__ResourceSet__Group_1__0__Impl2010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_1__1__Impl_in_rule__ResourceSet__Group_1__12041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__IncludeResourcesAssignment_1_1_in_rule__ResourceSet__Group_1__1__Impl2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__0__Impl_in_rule__ResourceSet__Group_2__02102 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1_in_rule__ResourceSet__Group_2__02105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__ResourceSet__Group_2__0__Impl2133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__Group_2__1__Impl_in_rule__ResourceSet__Group_2__12164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceSet__ExcludeResourcesAssignment_2_1_in_rule__ResourceSet__Group_2__1__Impl2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__0__Impl_in_rule__MODULEID__Group__02225 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1_in_rule__MODULEID__Group__02228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group__0__Impl2255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group__1__Impl_in_rule__MODULEID__Group__12284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0_in_rule__MODULEID__Group__1__Impl2311 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__0__Impl_in_rule__MODULEID__Group_1__02346 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1_in_rule__MODULEID__Group_1__02349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__MODULEID__Group_1__0__Impl2377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MODULEID__Group_1__1__Impl_in_rule__MODULEID__Group_1__12408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BMID_in_rule__MODULEID__Group_1__1__Impl2435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__0__Impl_in_rule__ModuleIdentifier__Group__02468 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1_in_rule__ModuleIdentifier__Group__02471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__ModulenameAssignment_0_in_rule__ModuleIdentifier__Group__0__Impl2498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__Group__1__Impl_in_rule__ModuleIdentifier__Group__12528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleIdentifier__VersionAssignment_1_in_rule__ModuleIdentifier__Group__1__Impl2555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__0__Impl_in_rule__ResourceList__Group__02589 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1_in_rule__ResourceList__Group__02592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_0_in_rule__ResourceList__Group__0__Impl2619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group__1__Impl_in_rule__ResourceList__Group__12649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0_in_rule__ResourceList__Group__1__Impl2676 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__0__Impl_in_rule__ResourceList__Group_1__02711 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1_in_rule__ResourceList__Group_1__02714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__ResourceList__Group_1__0__Impl2742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__Group_1__1__Impl_in_rule__ResourceList__Group_1__12773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ResourceList__ResourcesAssignment_1_1_in_rule__ResourceList__Group_1__1__Impl2800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransformation_in_rule__TransformationModel__TransformationsAssignment2839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__RemoveFrom__ResourceSetAssignment_12870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__HostModuleAssignment_12901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_32932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__EmbedInto__ModulesAssignment_4_12963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__CreateModule__ModuleAssignment_12994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFrom_in_rule__CreateModule__FromAssignment_23025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceSet_in_rule__From__ResourceSetAssignment_13056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleIdentifier_in_rule__ResourceSet__ModuleIdentifierAssignment_03087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__IncludeResourcesAssignment_1_13118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleResourceList_in_rule__ResourceSet__ExcludeResourcesAssignment_2_13149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMODULEID_in_rule__ModuleIdentifier__ModulenameAssignment_03180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ModuleIdentifier__VersionAssignment_13211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_03242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__ResourceList__ResourcesAssignment_1_13273 = new BitSet(new long[]{0x0000000000000002L});

}