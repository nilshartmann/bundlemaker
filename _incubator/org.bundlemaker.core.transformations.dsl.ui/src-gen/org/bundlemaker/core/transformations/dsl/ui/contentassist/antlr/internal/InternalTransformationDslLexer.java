package org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTransformationDslLexer extends Lexer {
    public static final int RULE_BMID=4;
    public static final int RULE_ID=6;
    public static final int RULE_ANY_OTHER=11;
    public static final int T25=25;
    public static final int Tokens=26;
    public static final int T24=24;
    public static final int EOF=-1;
    public static final int RULE_SL_COMMENT=9;
    public static final int T23=23;
    public static final int T22=22;
    public static final int T21=21;
    public static final int T20=20;
    public static final int RULE_ML_COMMENT=8;
    public static final int RULE_STRING=5;
    public static final int RULE_INT=7;
    public static final int T12=12;
    public static final int T13=13;
    public static final int T14=14;
    public static final int RULE_WS=10;
    public static final int T15=15;
    public static final int T16=16;
    public static final int T17=17;
    public static final int T18=18;
    public static final int T19=19;
    public InternalTransformationDslLexer() {;} 
    public InternalTransformationDslLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g"; }

    // $ANTLR start T12
    public final void mT12() throws RecognitionException {
        try {
            int _type = T12;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:10:5: ( 'remove-from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:10:7: 'remove-from'
            {
            match("remove-from"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T12

    // $ANTLR start T13
    public final void mT13() throws RecognitionException {
        try {
            int _type = T13;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:11:5: ( ';' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:11:7: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T13

    // $ANTLR start T14
    public final void mT14() throws RecognitionException {
        try {
            int _type = T14;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:12:5: ( 'embed-into' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:12:7: 'embed-into'
            {
            match("embed-into"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T14

    // $ANTLR start T15
    public final void mT15() throws RecognitionException {
        try {
            int _type = T15;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:13:5: ( 'modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:13:7: 'modules'
            {
            match("modules"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T15

    // $ANTLR start T16
    public final void mT16() throws RecognitionException {
        try {
            int _type = T16;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:14:5: ( ',' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:14:7: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T16

    // $ANTLR start T17
    public final void mT17() throws RecognitionException {
        try {
            int _type = T17;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:15:5: ( 'create-module' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:15:7: 'create-module'
            {
            match("create-module"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T17

    // $ANTLR start T18
    public final void mT18() throws RecognitionException {
        try {
            int _type = T18;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:16:5: ( 'classify-modules' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:16:7: 'classify-modules'
            {
            match("classify-modules"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T18

    // $ANTLR start T19
    public final void mT19() throws RecognitionException {
        try {
            int _type = T19;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:17:5: ( 'as' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:17:7: 'as'
            {
            match("as"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T19

    // $ANTLR start T20
    public final void mT20() throws RecognitionException {
        try {
            int _type = T20;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:18:5: ( 'but not ' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:18:7: 'but not '
            {
            match("but not "); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T20

    // $ANTLR start T21
    public final void mT21() throws RecognitionException {
        try {
            int _type = T21;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:19:5: ( 'layer' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:19:7: 'layer'
            {
            match("layer"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T21

    // $ANTLR start T22
    public final void mT22() throws RecognitionException {
        try {
            int _type = T22;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:20:5: ( 'from' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:20:7: 'from'
            {
            match("from"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T22

    // $ANTLR start T23
    public final void mT23() throws RecognitionException {
        try {
            int _type = T23;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:21:5: ( 'include' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:21:7: 'include'
            {
            match("include"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T23

    // $ANTLR start T24
    public final void mT24() throws RecognitionException {
        try {
            int _type = T24;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:22:5: ( 'exclude' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:22:7: 'exclude'
            {
            match("exclude"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T24

    // $ANTLR start T25
    public final void mT25() throws RecognitionException {
        try {
            int _type = T25;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:23:5: ( '.' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:23:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T25

    // $ANTLR start RULE_BMID
    public final void mRULE_BMID() throws RecognitionException {
        try {
            int _type = RULE_BMID;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2150:11: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '0' .. '9' )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2150:13: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '0' .. '9' )*
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2150:13: ( '^' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='^') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2150:13: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( input.LA(1)=='-'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2150:46: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='-'||(LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_BMID

    // $ANTLR start RULE_ID
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2152:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2152:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2152:11: ( '^' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='^') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2152:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2152:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ID

    // $ANTLR start RULE_INT
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2154:10: ( ( '0' .. '9' )+ )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2154:12: ( '0' .. '9' )+
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2154:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2154:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_INT

    // $ANTLR start RULE_STRING
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:13: ( ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\"') ) {
                alt8=1;
            }
            else if ( (LA8_0=='\'') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2156:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:16: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:20: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\\') ) {
                            alt6=1;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='!')||(LA6_0>='#' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFE')) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:21: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:62: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:82: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:87: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0>='\u0000' && LA7_0<='&')||(LA7_0>='(' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFE')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:88: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2156:129: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_STRING

    // $ANTLR start RULE_ML_COMMENT
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2158:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2158:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2158:24: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='.')||(LA9_1>='0' && LA9_1<='\uFFFE')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<=')')||(LA9_0>='+' && LA9_0<='\uFFFE')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2158:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("*/"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ML_COMMENT

    // $ANTLR start RULE_SL_COMMENT
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFE')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:40: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:41: ( '\\r' )? '\\n'
                    {
                    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:41: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2160:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_SL_COMMENT

    // $ANTLR start RULE_WS
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2162:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2162:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2162:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\t' && LA13_0<='\n')||LA13_0=='\r'||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_WS

    // $ANTLR start RULE_ANY_OTHER
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2164:16: ( . )
            // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:2164:18: .
            {
            matchAny(); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ANY_OTHER

    public void mTokens() throws RecognitionException {
        // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:8: ( T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | RULE_BMID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt14=22;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:10: T12
                {
                mT12(); 

                }
                break;
            case 2 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:14: T13
                {
                mT13(); 

                }
                break;
            case 3 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:18: T14
                {
                mT14(); 

                }
                break;
            case 4 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:22: T15
                {
                mT15(); 

                }
                break;
            case 5 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:26: T16
                {
                mT16(); 

                }
                break;
            case 6 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:30: T17
                {
                mT17(); 

                }
                break;
            case 7 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:34: T18
                {
                mT18(); 

                }
                break;
            case 8 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:38: T19
                {
                mT19(); 

                }
                break;
            case 9 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:42: T20
                {
                mT20(); 

                }
                break;
            case 10 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:46: T21
                {
                mT21(); 

                }
                break;
            case 11 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:50: T22
                {
                mT22(); 

                }
                break;
            case 12 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:54: T23
                {
                mT23(); 

                }
                break;
            case 13 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:58: T24
                {
                mT24(); 

                }
                break;
            case 14 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:62: T25
                {
                mT25(); 

                }
                break;
            case 15 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:66: RULE_BMID
                {
                mRULE_BMID(); 

                }
                break;
            case 16 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:76: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 17 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:84: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 18 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:93: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 19 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:105: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 20 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:121: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 21 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:137: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 22 :
                // ../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g:1:145: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA14_eotS =
        "\1\uffff\1\30\1\uffff\2\30\1\uffff\6\30\1\uffff\1\25\1\30\2\uffff"+
        "\3\25\2\uffff\2\30\2\uffff\3\30\1\uffff\2\30\1\62\4\30\1\uffff\1"+
        "\30\5\uffff\6\30\1\uffff\12\30\1\uffff\1\30\1\110\7\30\1\120\1\uffff"+
        "\7\30\1\uffff\3\30\1\133\1\134\2\30\1\137\2\30\2\uffff\2\30\1\uffff"+
        "\5\30\1\151\2\30\1\154\1\uffff\2\30\1\uffff\3\30\1\162\1\30\1\uffff"+
        "\1\30\1\165\1\uffff";
    static final String DFA14_eofS =
        "\166\uffff";
    static final String DFA14_minS =
        "\1\0\1\60\1\uffff\2\60\1\uffff\6\60\1\uffff\1\55\1\60\2\uffff\2"+
        "\0\1\52\2\uffff\2\60\2\uffff\3\60\1\uffff\2\60\1\55\4\60\1\uffff"+
        "\1\60\5\uffff\6\60\1\uffff\1\40\11\60\1\uffff\1\60\1\55\2\60\1\55"+
        "\4\60\1\55\1\uffff\1\60\1\55\1\151\3\60\1\55\1\uffff\1\60\1\146"+
        "\1\156\2\55\1\60\1\155\1\55\1\162\1\164\2\uffff\1\55\1\157\1\uffff"+
        "\2\157\1\155\1\144\1\155\1\55\1\157\1\165\1\55\1\uffff\1\144\1\154"+
        "\1\uffff\1\165\1\145\1\154\1\55\1\145\1\uffff\1\163\1\55\1\uffff";
    static final String DFA14_maxS =
        "\1\ufffe\1\172\1\uffff\2\172\1\uffff\6\172\1\uffff\2\172\2\uffff"+
        "\2\ufffe\1\57\2\uffff\2\172\2\uffff\3\172\1\uffff\7\172\1\uffff"+
        "\1\172\5\uffff\6\172\1\uffff\12\172\1\uffff\12\172\1\uffff\2\172"+
        "\1\151\4\172\1\uffff\1\172\1\146\1\156\3\172\1\155\1\172\1\162\1"+
        "\164\2\uffff\1\172\1\157\1\uffff\2\157\1\155\1\144\1\155\1\172\1"+
        "\157\1\165\1\172\1\uffff\1\144\1\154\1\uffff\1\165\1\145\1\154\1"+
        "\172\1\145\1\uffff\1\163\1\172\1\uffff";
    static final String DFA14_acceptS =
        "\2\uffff\1\2\2\uffff\1\5\6\uffff\1\16\2\uffff\1\17\1\21\3\uffff"+
        "\1\25\1\26\2\uffff\1\17\1\2\3\uffff\1\5\7\uffff\1\16\1\uffff\1\21"+
        "\1\22\1\23\1\24\1\25\6\uffff\1\10\12\uffff\1\11\12\uffff\1\13\7"+
        "\uffff\1\12\12\uffff\1\15\1\4\2\uffff\1\14\11\uffff\1\3\2\uffff"+
        "\1\1\5\uffff\1\6\2\uffff\1\7";
    static final String DFA14_specialS =
        "\166\uffff}>";
    static final String[] DFA14_transitionS = {
            "\11\25\2\24\2\25\1\24\22\25\1\24\1\25\1\21\4\25\1\22\4\25\1"+
            "\5\1\17\1\14\1\23\12\20\1\25\1\2\5\25\32\16\3\25\1\15\1\16\1"+
            "\25\1\7\1\10\1\6\1\16\1\3\1\12\2\16\1\13\2\16\1\11\1\4\4\16"+
            "\1\1\10\16\uff84\25",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\26\25\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\14\27\1\32\12\27\1"+
            "\33\2\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\16\27\1\34\13\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\13\27\1\36\5\27\1"+
            "\37\10\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\22\27\1\40\7\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\24\27\1\41\5\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\1\42\31\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\21\27\1\43\10\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\15\27\1\44\14\27",
            "",
            "\1\30\23\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "\uffff\50",
            "\uffff\50",
            "\1\51\4\uffff\1\52",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\14\27\1\54\15\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\1\27\1\55\30\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\2\27\1\56\27\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\3\27\1\57\26\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\1\60\31\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\61\25\27",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\23\27\1\63\6\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\30\27\1\64\1\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\16\27\1\65\13\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\2\27\1\66\27\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\16\27\1\67\13\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\70\25\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\13\27\1\71\16\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\24\27\1\72\5\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\22\27\1\73\7\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\1\74\31\27",
            "",
            "\1\75\17\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\76\25\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\14\27\1\77\15\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\13\27\1\100\16\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\25\27\1\101\4\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\3\27\1\102\26\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\24\27\1\103\5\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\13\27\1\104\16\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\22\27\1\105\7\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\23\27\1\106\6\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\21\27\1\107\10\27",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\24\27\1\111\5\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\112\25\27",
            "\1\113\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\3\27\1\114\26\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\115\25\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\10\27\1\116\21\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\117\25\27",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\3\27\1\121\26\27",
            "\1\122\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\123",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\124\25\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\22\27\1\125\7\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\5\27\1\126\24\27",
            "\1\127\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\4\27\1\130\25\27",
            "\1\131",
            "\1\132",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\30\27\1\135\1\27",
            "\1\136",
            "\1\30\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\140",
            "\1\141",
            "",
            "",
            "\1\142\2\uffff\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\143",
            "",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30",
            "\1\152",
            "\1\153",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30",
            "",
            "\1\155",
            "\1\156",
            "",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30",
            "\1\163",
            "",
            "\1\164",
            "\1\30\2\uffff\12\30\7\uffff\32\30\4\uffff\1\30\1\uffff\32\30",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | RULE_BMID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
    }
 

}