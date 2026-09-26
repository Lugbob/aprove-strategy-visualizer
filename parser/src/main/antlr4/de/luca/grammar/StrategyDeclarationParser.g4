parser grammar StrategyDeclarationParser;


options {
    tokenVocab = StrategyDeclarationLexer;
}

program
: (declaration)+ EOF
;


//declaration/namePart/qualifiedName falls declares im falschen File, was manchmal vorkommt
declaration
    : DESCRIPTION* DECLARE namePart EQ qualifiedName (DEFAULTS parameterBlock)?
    ;

qualifiedName
    : namePart (DOT namePart)*
    ;

namePart
    : IDENT
    ;

parameterBlock
: LSQBRA parameterContent? RSQBRA
;

parameterContent
    : (parameterBlock | ~(LSQBRA | RSQBRA))+
    ;
