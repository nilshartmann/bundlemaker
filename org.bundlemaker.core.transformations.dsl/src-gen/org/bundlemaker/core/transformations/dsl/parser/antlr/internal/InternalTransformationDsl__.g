lexer grammar InternalTransformationDsl;
@header {
package org.bundlemaker.core.transformations.dsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T12 : 'remove-from' ;
T13 : ';' ;
T14 : 'embed-into' ;
T15 : 'modules' ;
T16 : ',' ;
T17 : 'create-module' ;
T18 : 'from' ;
T19 : 'include' ;
T20 : 'exclude' ;
T21 : '.' ;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 763
RULE_BMID : '^'? ('a'..'z'|'A'..'Z'|'_'|'-') ('a'..'z'|'A'..'Z'|'_'|'-'|'0'..'9')*;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 765
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 767
RULE_INT : ('0'..'9')+;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 769
RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 771
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 773
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 775
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../org.bundlemaker.core.transformations.dsl/src-gen/org/bundlemaker/core/transformations/dsl/parser/antlr/internal/InternalTransformationDsl.g" 777
RULE_ANY_OTHER : .;


