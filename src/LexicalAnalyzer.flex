%%
%class LexicalAnalyzer
%unicode
%line
%standalone
%column
// the return type of the lexical analyzer
%type Symbol 
%function nextSymbol 


%xstate YYINITIAL, SHORT_COMMENT, LONG_COMMENT

PROGNAME = [A-Z][a-zA-Z]*[_][a-zA-Z]*
VARNAME = [a-z][a-zA-Z0-9]*
NUMBER = [1-9][0-9]*|0
WHITESPACE = [ \t\r\n]+ 

// check if we end the file in the Long_Comment state. if not we simply return a Symbol with LexicalUnit.EOS
%eofval{
    if (yystate() == LONG_COMMENT) {
        System.out.println("no closing comment token found");
        System.exit(1);
    }
    return new Symbol(LexicalUnit.EOS, yyline, yycolumn);
%eofval}
%%


// initial state
<YYINITIAL> {    
    {WHITESPACE} { }
    {PROGNAME} {return new Symbol(LexicalUnit.PROGNAME, yyline, yycolumn, yytext());}
    {VARNAME} {return new Symbol(LexicalUnit.VARNAME, yyline, yycolumn, yytext());}
    {NUMBER} {return new Symbol(LexicalUnit.NUMBER, yyline, yycolumn, yytext());}
    "LET" {return new Symbol(LexicalUnit.LET, yyline, yycolumn, yytext());} 
    "BE" {return new Symbol(LexicalUnit.BE, yyline, yycolumn, yytext());}
    "END" {return new Symbol(LexicalUnit.END, yyline, yycolumn, yytext());}
    "IN" {return new Symbol(LexicalUnit.INPUT, yyline, yycolumn, yytext());}
    "OUT" {return new Symbol(LexicalUnit.OUTPUT, yyline, yycolumn, yytext());}
    "REPEAT" {return new Symbol(LexicalUnit.REPEAT, yyline, yycolumn, yytext());}        
    "WHILE" {return new Symbol(LexicalUnit.WHILE, yyline, yycolumn, yytext());}
    "IF" {return new Symbol(LexicalUnit.IF, yyline, yycolumn, yytext());}
    "THEN" {return new Symbol(LexicalUnit.THEN, yyline, yycolumn, yytext());}
    "ELSE" {return new Symbol(LexicalUnit.ELSE, yyline, yycolumn, yytext());}
    "->" {return new Symbol(LexicalUnit.IMPLIES, yyline, yycolumn, yytext());}
    "|" {return new Symbol(LexicalUnit.PIPE, yyline, yycolumn, yytext());}
    "==" {return new Symbol(LexicalUnit.EQUAL, yyline, yycolumn, yytext());}
    "(" {return new Symbol(LexicalUnit.LPAREN, yyline, yycolumn, yytext());}
    ")" {return new Symbol(LexicalUnit.RPAREN, yyline, yycolumn, yytext());}
    ":" {return new Symbol(LexicalUnit.COLUMN, yyline, yycolumn, yytext());}
    "<" {return new Symbol(LexicalUnit.SMALLER, yyline, yycolumn, yytext());}
    "=" {return new Symbol(LexicalUnit.ASSIGN, yyline, yycolumn, yytext());}
    "<=" {return new Symbol(LexicalUnit.SMALEQ, yyline, yycolumn, yytext());}
    "{" {return new Symbol(LexicalUnit.LBRACK, yyline, yycolumn, yytext());}
    "}" {return new Symbol(LexicalUnit.RBRACK, yyline, yycolumn, yytext());}
    "*" {return new Symbol(LexicalUnit.TIMES, yyline, yycolumn, yytext());}
    "/" {return new Symbol(LexicalUnit.DIVIDE, yyline, yycolumn, yytext());}
    "+" {return new Symbol(LexicalUnit.PLUS, yyline, yycolumn, yytext());}
    "-" {return new Symbol(LexicalUnit.MINUS, yyline, yycolumn, yytext());}
    "!!" {yybegin(LONG_COMMENT);} // switch to long comment state
    "$" {yybegin(SHORT_COMMENT);} // switch short comment state
    . {System.out.println("Unrecognized symbol: " + yytext());}


    
}
// comment states
<SHORT_COMMENT> {
    \n {yybegin(YYINITIAL);} // switch back to initial state if we encounter a newline 
    . { } // ignore everything else
}
<LONG_COMMENT> {
    "!!" {yybegin(YYINITIAL);} // switch back to initial state if we encounter a closing comment token
    \n { } // ignore newlines
    . { } // ignore everything else
    
}



