
%{
#include <stdio.h>
#include <stdlib.h>
extern int yylex();
extern int yyparse();
extern FILE* yyin;
void yyerror(const char* s);
%}

%token T_IF T_THEN T_WHILE T_DO T_NOT T_IDENTIFIER T_CONSTANT
%token T_ZERO T_PP T_MM T_LAMBDA T_GRATER
%token T_OPEN_BRACKET T_CLOSE_BRACKET

%start Program

%%

Program : Statement ;

Statement :
	T_IF Expression T_THEN Block       //{ $$ = if $2 then $4 }
	 | T_WHILE Expression T_DO Block   //{ $$ = while $2 do $4 }
	 | Expression 			  // { $$ = $1 }
	 ;

Expression :
	  Term T_GRATER T_IDENTIFIER  //{ $$ = $1 => identifier }
	  | T_ZERO Term 	     // { $$ = isZero? $2 }
	  | T_NOT Expression	     // { $$ = not $2 }
	  | T_PP T_IDENTIFIER        // { $$ = ++ identifier }
	  | T_MM T_IDENTIFIER        // { $$ = -- identifier }
	  ;

Term :
	T_IDENTIFIER                  //{ $$ = identifier }
	| T_CONSTANT 		     // { $$ = constant }
	;

Block :
	Statement   //{ $$ = $1 }
	| T_OPEN_BRACKET Statements T_CLOSE_BRACKET  // { $$ = { $2 } }
	;

Statements :
	Statement Statements //{ $$ = $1 $2 }
	| T_LAMBDA // { $$ = $1 }
	;

%%

int main() {
	yyin = stdin;
	do {
		yyparse();
	} while(!feof(yyin));
	return 0;
}
void yyerror(const char* s) {
	fprintf(stderr, "Parse error: %s\n", s);
	exit(1);
}
