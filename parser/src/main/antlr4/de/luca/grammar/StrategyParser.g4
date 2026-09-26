parser grammar StrategyParser;

options {
    tokenVocab = StrategyLexer;
}

program
: (equation | declaration)+ EOF
;


//declaration/namePart/qualifiedName falls declares im falschen File, was manchmal vorkommt
declaration
    : DESCRIPTION* DECLARE namePart EQ qualifiedName (LCNAME parameterBlock)?
    ;

qualifiedName
    : namePart (DOT namePart)*
    ;

namePart
    : LCNAME
    | UCNAME
    | REPEAT
    | MAYBE
    | FIRST
    | DELAY
    | IF
    | COMBINE
    | TIMER
    | ANY
    | ANYK
    | SOLVE
    | PROVE
    | ITERATE
    ;

equation
: DESCRIPTION* LCNAME EQ strategyTerm
;

strategyTerm
: sequence
;

strategyTermList
: strategyTerm (COMMA strategyTerm)*
;

sequence
: strategyAtom ((COLON | SEMICOLON) strategyAtom)*
;

timer
: TIMER LBRA NUMBER COMMA strategyTerm RBRA
;

ifExpr
: IF parameterBlock (LBRA strategyTerm (COMMA strategyTerm)? RBRA)?
;

combine
: COMBINE LBRA strategyTermList RBRA
;

solveProve
: (SOLVE|PROVE|DISPROVE) LBRA strategyTerm RBRA
;

strategyAtom
: repeat
| iterate
| choice
| maybe
| processor
| timer
| ifExpr
| combine
| solveProve
| delay
| LCNAME //Für definierte Strategien
;

repeat
: REPEAT LBRA NUMBER COMMA upperBound COMMA strategyTerm RBRA
;

iterate
: ITERATE LBRA processor RBRA
;

upperBound
: NUMBER
| STAR
;

processor
: UCNAME parameterBlock?
;

parameterBlock
: LSQBRA parameterContent? RSQBRA
;

parameterContent
    : (parameterBlock | ~(LSQBRA | RSQBRA))+
    ;

choice
    : (ANY | FIRST) LBRA strategyTermList RBRA
    | ANYK LBRA NUMBER COMMA strategyTermList RBRA
    ;

maybe
    : MAYBE LBRA strategyTerm RBRA
    ;

delay
    : DELAY LBRA NUMBER COMMA strategyTermList RBRA
    ;