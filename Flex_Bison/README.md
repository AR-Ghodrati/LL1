
# Flex And Bison

#### Flex Tokens Included :

```
ID       [a-zA-z][a-zA-Z0-9]*

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

{ID}+  return T_IDENTIFIER;
```


#### Bison Rules Included :

````
Program : Statement ;

Statement :
	T_IF Expression T_THEN Block     
	 | T_WHILE Expression T_DO Block 
	 | Expression 		
	 ;

Expression :
	  Term T_GRATER T_IDENTIFIER  
	  | T_ZERO Term 	   
	  | T_NOT Expression	   
	  | T_PP T_IDENTIFIER      
	  | T_MM T_IDENTIFIER     
	  ;

Term :
	T_IDENTIFIER       
	| T_CONSTANT 		 
	;

Block :
	Statement  
	| T_OPEN_BRACKET Statements T_CLOSE_BRACKET 
	;

Statements :
	Statement Statements 
	| T_LAMBDA 
	;
````