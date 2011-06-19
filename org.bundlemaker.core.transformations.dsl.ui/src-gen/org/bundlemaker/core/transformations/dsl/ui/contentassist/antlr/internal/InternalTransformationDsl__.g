lexer grammar InternalTransformationDsl;
@header {
package org.bundlemaker.core.transformations.dsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T12 : 'remove-from' ;
T13 : ';' ;
T14 : 'embed-into' ;
T15 : 'modules' ;
T16 : ',' ;
T17 : 'create-module' ;
T18 : 'classify-modules' ;
T19 : 'as' ;
T20 : 'but not ' ;
T21 : 'layer' ;
T22 : 'from' ;
T23 : 'include' ;
T24 : 'exclude' ;
T25 : '.' ;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2150
RULE_BMID : '^'? ('a'..'z'|'A'..'Z'|'_'|'-') ('a'..'z'|'A'..'Z'|'_'|'-'|'0'..'9')*;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2152
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2154
RULE_INT : ('0'..'9')+;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2156
RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2158
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2160
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2162
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl.ui/src-gen/org/bundlemaker/core/transformations/dsl/ui/contentassist/antlr/internal/InternalTransformationDsl.g" 2164
RULE_ANY_OTHER : .;


