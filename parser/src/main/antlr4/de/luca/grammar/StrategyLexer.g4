lexer grammar StrategyLexer;

REPEAT
    : 'RepeatS'
    | 'Repeat'
    ;

MAYBE
    : 'Maybe'
    ;

FIRST
    : 'First'
    ;

DELAY
    : 'AnyDelay'
    | 'Delay'
    ;

IF
    : 'If'
    ;

COMBINE
    : 'CombineParallel'
    | 'CombineSequential'
    | 'Combine'
    ;

TIMER
    : 'WallTimer'
    | 'Timer'
    ;

ANYK
    : 'AnyK' //Maybe needed
    ;


ANY
    : 'Any'
    ;

SOLVE
    : 'Solve'
    ;

PROVE
    : 'Prove'
    ;

DISPROVE
    : 'DISPROVE'
    ;

ITERATE
    : 'IterateS'
    | 'Iterate'
    ;
EQ
    : '='
    ;

DOT
    : '.'
    ;

DECLARE //Für eine Zeile (Benutzung in falscher Datei)
: 'declare'
;

COLON
    : ':'
    ;

SEMICOLON
    : ';'
    ;

COMMA
    : ','
    ;

LBRA
    : '('
    ;

RBRA
    : ')'
    ;

LSQBRA
    : '['
    ;

RSQBRA
    : ']'
    ;

STAR
    : '*'
    ;

NUMBER
    : '-'? [0-9]+
    ;

DESCRIPTION
    : '#@description' ~[\r\n]*
    ;
    
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

LCNAME
    : [a-z] [a-zA-Z0-9_]*
    ;

UCNAME
    : [A-Z] [a-zA-Z0-9_]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

STRING
: '"' ~["\r\n]* '"'
;

BACKSLASH
: '\\' -> skip
;