
// To Build

// 1- bison -d  grammar.y
// 2- flex lex.l
// 3- clang lex.yy.c grammar.tab.c
// 4- ./a.out -> Test It !



%{
#include <stdio.h>
#include <stdlib.h>
extern int yylex();
extern int yyparse();
void yyerror(const char* s);
%}

%token T_IF T_THEN T_WHILE T_DO T_NOT T_IDENTIFIER T_CONSTANT
%token T_ZERO T_PP T_MM T_LAMBDA T_GRATER
%token T_OPEN_BRACKET T_CLOSE_BRACKET
%token T_SEMICOLEM

%start Program

%%

Program : Statement ;

Statement :
	T_IF Expression T_THEN Block       { printf("T_IF Expression T_THEN Block\n"); }
	 | T_WHILE Expression T_DO Block   { printf("T_WHILE Expression T_DO Block\n"); }
	 | Expression T_SEMICOLEM	    { printf("Expression T_SEMICOLEM\n"); }
	 ;

Expression :
	  Term T_GRATER T_IDENTIFIER  { printf(" Term T_GRATER T_IDENTIFIER\n"); }
	  | T_ZERO Term 	      { printf("T_ZERO Term\n"); }
	  | T_NOT Expression	      { printf("T_NOT Expression\n"); }
	  | T_PP T_IDENTIFIER         { printf("T_PP T_IDENTIFIER\n"); }
	  | T_MM T_IDENTIFIER         { printf("T_MM T_IDENTIFIER\n"); }
	  ;

Term :
	T_IDENTIFIER                  { printf("T_IDENTIFIER\n"); }
	| T_CONSTANT 		      { printf("T_CONSTANT\n"); }
	;

Block :
	Statement    { printf("Statement\n"); }
	| T_OPEN_BRACKET Statements T_CLOSE_BRACKET   { printf("T_OPEN_BRACKET Statements T_CLOSE_BRACKET\n"); }
	;

Statements :
	Statement Statements { printf("Statement Statements\n"); }
	| T_LAMBDA           { printf("T_LAMBDA\n"); }
	;

%%

int main() {
        printf("Started the parsing\n");
	yyparse();
	printf("Accepted !! -> Parse Compeleted!!\n");
	return 0;
}
void yyerror(const char* s) {
	fprintf(stderr, "Rejected !! -> Parse error: %s\n", s);
	exit(1);
}