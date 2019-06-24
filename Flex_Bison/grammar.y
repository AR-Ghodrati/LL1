
// To Build

// 1- bison -d  grammar.y
// 2- flex lex.l
// 3- clang lex.yy.c grammar.tab.c
// 4- ./a.out -> Test It !



%{
    
#define CYN  "\x1B[36m"
#define RED  "\x1B[31m"
#define GRN  "\x1B[32m"
#define YEL  "\x1B[35m"


#include <stdio.h>
#include <stdlib.h>
extern int yylex();
extern int yyparse();
void yyerror(const char* s);
%}

%token T_IF T_THEN T_WHILE T_DO T_NOT T_IDENTIFIER T_CONSTANT
%token T_ZERO T_PP T_MM T_GRATER
%token T_OPEN_BRACKET T_CLOSE_BRACKET
%token T_SEMICOLEM

%start Program

%%

Program : Statement ;

Statement :
	T_IF Expression T_THEN Block       { printf("%sT_IF Expression T_THEN Block\n",YEL); }
	 | T_WHILE Expression T_DO Block   { printf("%sT_WHILE Expression T_DO Block\n",YEL); }
	 | Expression T_SEMICOLEM	    { printf("%sExpression T_SEMICOLEM\n",YEL); }
	 ;

Expression :
	  Term T_GRATER T_IDENTIFIER  { printf("%sTerm T_GRATER T_IDENTIFIER\n",YEL); }
	  | T_ZERO Term 	      { printf("%sT_ZERO Term\n",YEL); }
	  | T_NOT Expression	      { printf("%sT_NOT Expression\n",YEL); }
	  | T_PP T_IDENTIFIER         { printf("%sT_PP T_IDENTIFIER\n",YEL); }
	  | T_MM T_IDENTIFIER         { printf("%sT_MM T_IDENTIFIER\n",YEL); }
	  ;

Term :
	T_IDENTIFIER                  { printf("%sT_IDENTIFIER\n",YEL); }
	| T_CONSTANT 		      { printf("%sT_CONSTANT\n",YEL); }
	;

Block :
	Statement    { printf("%sStatement\n",YEL); }
	| T_OPEN_BRACKET Statements T_CLOSE_BRACKET   { printf("%sT_OPEN_BRACKET Statements T_CLOSE_BRACKET\n",YEL); }
	;

Statements :
	Statement Statements { printf("%sStatement Statements\n",YEL); }
	| /* LAMBDA */           { printf("%sT_LAMBDA\n",YEL); }
	;

%%

int main() {
    
    while(1){
       printf("%sEnter Template for parsing : \n",CYN);
	   int res = yyparse();
        if(res == 0) // Accepted
	       printf("%s Accepted !!\n",GRN);
    }
	return 0;
}
void yyerror(const char* s) {
	fprintf(stderr, "%s Rejected !! -> Parse error: %s\n",RED,s);
	//exit(1);
}
