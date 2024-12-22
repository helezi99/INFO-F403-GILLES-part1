%{
package com.example; // Replace with your actual package name
%}

%class LexicalAnalyzer
%unicode
%cup

%%

\s+              { /* Ignore whitespace */ }
[a-zA-Z_][a-zA-Z_0-9]*  { return new java_cup.runtime.Symbol(1, yytext()); }
[0-9]+           { return new java_cup.runtime.Symbol(2, yytext()); }
<<EOF>>          { return new java_cup.runtime.Symbol(0); }
