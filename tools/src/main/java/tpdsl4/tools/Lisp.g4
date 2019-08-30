grammar Lisp;

@header {
package tpdsl4.tools;
}

list: '(' (ATOMIC|LITERAL|list)+ ')';

LITERAL: '\''(~[']*)'\''|'"'(~[']*)'"';
// 如果')' 在ATOMIC中，解析的时候会报错: line x:xx extraneous input '<EOF>' xxxx
ATOMIC: ~[ \r\t\n()]+;
WS: [ \r\t\n]+ -> skip;