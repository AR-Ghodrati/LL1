/* scanner for Example Solution 

Usage : 
1 - lex Lex.lex
2- gcc lex.yy.c
3- ./a.out

*/

%{
/* need this for the call to atof() below */
#include <math.h>
#include <string.h>
#include "t.tab.h"
%}

DIGIT    [0-9]
ID       [a-zA-z][a-zA-Z0-9]*{1,8}

%%

{ID}       {
                if(strlen(yytext) <= 8)
                printf( "An identifier: %s\n", yytext );
                else
                printf( "An Error Happened(Long Identifier): %s\n", yytext );
           }

%%


int yywrap(){} 

int main(int argc, char **argv) 
{  
// Explanation: 
// yywrap() - wraps the above rule section 
/* yyin - takes the file pointer  
          which contains the input*/
/* yylex() - this is the main flex function 
          which runs the Rule Section*/
// yytext is the text in the buffer 
  
// Uncomment the lines below  
// to take input from file 
// FILE *fp; 
// char filename[50]; 
// printf("Enter the filename: \n"); 
// scanf("%s",filename); 
// fp = fopen(filename,"r"); 
// yyin = fp; 
  
yylex(); 
return 0; 
}