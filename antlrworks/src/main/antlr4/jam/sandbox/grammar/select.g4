grammar select;

/*
 * Lexer rules
 */

ID: ('a'..'z' | 'A' .. 'Z' | '_' ) ('a'..'z' | 'A' .. 'Z' | '_' | '0'..'9')+ ;
COMMA: ',';

NEWLINE : '\r' ? '\n' -> skip;
WS      : (' ' | '\t' | '\n' | '\r')+ -> skip;

/*
 * parser rules
 */

selectItem : ID;
selectExpression: selectItem ( COMMA selectItem )* ;

