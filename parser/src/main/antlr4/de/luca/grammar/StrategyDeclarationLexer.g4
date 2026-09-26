lexer grammar StrategyDeclarationLexer;

DECLARE
    : 'declare'
    ;

DEFAULTS
    : 'defaults'
    ;

EQ
    : '='
    ;

DOT
    : '.'
    ;

COMMA
    : ','
    ;

LSQBRA
    : '['
    ;

RSQBRA
    : ']'
    ;

DESCRIPTION
    : '#@description' ~[\r\n]*
    ;

COMMENT
    : '#' ~[\r\n]* -> skip
    ;

STRING
    : '"' (~["\\] | '\\' .)* '"'
    ;

NUMBER
    : '-'? [0-9]+
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

IDENT
    : [A-Z] [a-zA-Z0-9_]*
    | [a-z] [a-zA-Z0-9_]*
    ;