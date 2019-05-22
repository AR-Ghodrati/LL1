%{
    #define YYSTYPE double
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    
    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    
    void yyerror(const char* msg) {
        fprintf(stderr, "%s\n", msg);
    }

%}

%token DIGIT
%token ID


%%

%%

int main() {
    yyin = stdin;
    do {
        yyparse();
    } while(!feof(yyin));
    return 0;
}
