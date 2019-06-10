%{
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include "grammar.tab.h"
%}

DIGIT    [0-9]
ID       [a-zA-z][a-zA-Z0-9]*

%%

"if"      return T_IF;
"then"    return T_THEN;
"while"   return T_WHILE;
"do"      return T_DO;
"not"     return T_NOT;
"isZero?" return T_ZERO;


"++"   return T_PP;
"--"   return T_MM;
"=>"   return T_GRATER;


"ε"    return T_LAMBDA;
"{"    return T_OPEN_BRACKET;
"}"    return T_CLOSE_BRACKET;
";"    return T_SEMICOLEM;


{ID}+  return T_IDENTIFIER;

[\t]   {}
[\n]   return 0;


%%

int yywrap(){}