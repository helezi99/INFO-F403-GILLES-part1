import java.util.regex.PatternSyntaxException;

%%// Options of the scanner

%class LexicalAnalyzer // Name
%unicode               // Use unicode
%line                  // Enable line counting
%column                // Enable column counting
%type Symbol           // Tell JFlex to use the Symbol class
%standalone            // Standalone scanner
%function nextToken    // Name of the function that will be called in the parser and return the next token
%eofval{ // value of eof (handle end of file)
    return new Symbol(LexicalUnit.EOS, yyline, yycolumn); 
%eofval}

// States (YYINITIAL is the default state, SHORTCOMMENTS and LONGCOMMENTS are used for comments)
%xstate YYINITIAL, SHORTCOMMENTS, LONGCOMMENTS 

// ERE
ProgName              = [A-Z][A-Za-z_]* // String of letters and _ starting with a capital letter
VarName               = [a-z][A-Za-z0-9]* // String of letters and digits starting with a lowercase letter
Number                = [0-9]+ // String of digits only
WhiteSpace            = (" "|"\t"|"\r"|"\n")+ // White space, tab, newline

%% //Identification of tokens

// Comments tokens
<SHORTCOMMENTS>{
    // End of short comments
    "\n"              { yybegin(YYINITIAL); } // Defines the end of a short comment
    .                 { } // Ignore characters in comments 
}

<LONGCOMMENTS>{
    // End of long comments
    "!!"              { yybegin(YYINITIAL); } // Defines the end of a long comment
    <<EOF>>           { throw new PatternSyntaxException("Unexpected end of file, comment not closed", yytext(), (int) yychar);} // End of file too early
    .                 { } // Ignore characters in comments
}

// Gilles keywords
<YYINITIAL>{
    "$"               { yybegin(SHORTCOMMENTS); } // Begin of short comments
    "!!"              { yybegin(LONGCOMMENTS); } // Begin of long comments
    "LET"             { return new Symbol(LexicalUnit.LET, yyline, yycolumn, yytext()); }
    "BE"              { return new Symbol(LexicalUnit.BE, yyline, yycolumn, yytext()); }
    "END"             { return new Symbol(LexicalUnit.END, yyline, yycolumn, yytext()); }
    ":"               { return new Symbol(LexicalUnit.COLUMN, yyline, yycolumn, yytext()); }
    "="               { return new Symbol(LexicalUnit.ASSIGN, yyline, yycolumn, yytext()); }
    "("               { return new Symbol(LexicalUnit.LPAREN, yyline, yycolumn, yytext()); }
    ")"               { return new Symbol(LexicalUnit.RPAREN, yyline, yycolumn, yytext()); }
    "-"               { return new Symbol(LexicalUnit.MINUS, yyline, yycolumn, yytext()); }
    "+"               { return new Symbol(LexicalUnit.PLUS, yyline, yycolumn, yytext()); }
    "*"               { return new Symbol(LexicalUnit.TIMES, yyline, yycolumn, yytext()); }
    "/"               { return new Symbol(LexicalUnit.DIVIDE, yyline, yycolumn, yytext()); }
    "IF"              { return new Symbol(LexicalUnit.IF, yyline, yycolumn, yytext()); }
    "THEN"            { return new Symbol(LexicalUnit.THEN, yyline, yycolumn, yytext()); }
    "ELSE"            { return new Symbol(LexicalUnit.ELSE, yyline, yycolumn, yytext()); }
    "{"               { return new Symbol(LexicalUnit.LBRACK, yyline, yycolumn, yytext()); }
    "}"               { return new Symbol(LexicalUnit.RBRACK, yyline, yycolumn, yytext()); }
    "->"              { return new Symbol(LexicalUnit.IMPLIES, yyline, yycolumn, yytext()); }
    "|"               { return new Symbol(LexicalUnit.PIPE, yyline, yycolumn, yytext()); }
    "=="              { return new Symbol(LexicalUnit.EQUAL, yyline, yycolumn, yytext()); }
    "<="              { return new Symbol(LexicalUnit.SMALEQ, yyline, yycolumn, yytext()); }
    "<"               { return new Symbol(LexicalUnit.SMALLER, yyline, yycolumn, yytext()); }
    "WHILE"           { return new Symbol(LexicalUnit.WHILE, yyline, yycolumn, yytext()); }
    "REPEAT"          { return new Symbol(LexicalUnit.REPEAT, yyline, yycolumn, yytext()); }
    "OUT"             { return new Symbol(LexicalUnit.OUTPUT, yyline, yycolumn, yytext()); }
    "IN"              { return new Symbol(LexicalUnit.INPUT, yyline, yycolumn, yytext()); }
    {ProgName}        { return new Symbol(LexicalUnit.PROGNAME, yyline, yycolumn, yytext()); } 
    {VarName}         { return new Symbol(LexicalUnit.VARNAME, yyline, yycolumn, yytext()); }
    {Number}          { return new Symbol(LexicalUnit.NUMBER, yyline, yycolumn, Integer.valueOf(yytext())); } 
    {WhiteSpace}      { } // Ignore white spaces, tabs, newlines
    .                 { throw new PatternSyntaxException("Unrecognized character", yytext(), (int) yychar);} // Handle unmatched symbols
}
