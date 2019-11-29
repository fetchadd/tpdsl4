grammar Cymbol;

@header {
package symtab.classes.Cymbol;
}


compilationUnit
    : (classDefinition | varDeclaration | methodDeclaration)+ EOF
    ;

classDefinition
    : 'class' ID superClass? '{' classMember+ '}' ';'
    ;

superClass
    : ':' 'public' ID
    ;

classMember
    : type ID ('=' expr)? ';'
    | methodDeclaration
    | 'public' ':'
    ;

methodDeclaration
    : type ID '(' formalParameters? ')' block
    ;

formalParameters
    : type ID (',' type ID)*
    ;

type: 'float'
    | 'int'
    | 'void'
    | ID
    ;

block
    : '{' statement* '}'
    ;

varDeclaration
    : type ID ('=' expr)? ';'
    ;

statement
    : block
    | varDeclaration
    | 'return' expr? ';'
    ;

exprList
    : expr (',' expr)*
    ;

expr: ID '(' exprList? ')'          # Call
    | expr '[' expr ']'             # Index
    | '-' expr                      # Negate
    | '!' expr                      # Not
    | expr '*' expr                 # Mult
    | expr ('+'|'-') expr           # AddSub
    | expr '==' expr                # Equal
    | ID                            # Var
    | INT                           # Int
    | '(' expr ')'                  # Parens
    | expr '.' ID                   # AccessFiled
    | expr '.' ID '(' exprList ')'  # AccessMethod
    ;


K_FLOAT : 'float';
K_INT   : 'int';
K_VOID  : 'void';
ID      : LETTER (LETTER | [0-9])* ;

fragment
LETTER : [a-zA-Z];
INT    : [0-9] +;
WS     : [ \t\n\r] + -> skip;

SL_COMMENT
    : '//' .*? '\n' -> skip
    ;
