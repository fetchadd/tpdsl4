grammar VecMath;

prog    : stat+ ;
stat    : ID '=' expr    // (= ID expr)
        | 'print' expr   // (print expr)
        ;

expr    : multExpr (op='+' multExpr)* ;
multExpr: primary (op=('*'|'.') primary)* ;
primary : INT
        | ID
        | '[' expr (',' expr)* ']'
        ;

ID : 'a'..'z'+ ;
INT: '0'..'9'+ ;
WS: (' '|'\r'|'\n')+ -> skip ;