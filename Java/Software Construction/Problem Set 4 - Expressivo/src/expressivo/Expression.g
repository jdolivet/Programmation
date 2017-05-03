/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * root is the root non terminal node
 * sum, product and primitive are the non terminal nodes
 * number and variable are the terminal nodes
 */
root ::= sum;
@skip whitespace{
	sum ::=  product ('+' product)* ;
	product ::= primitive ('*' primitive)*;
	primitive ::= number | variable | '(' sum ')';
}

whitespace ::= [ ]+;
number ::= ([0-9]+)? ('.' [0-9]+)?;
variable ::= [a-zA-Z]+;