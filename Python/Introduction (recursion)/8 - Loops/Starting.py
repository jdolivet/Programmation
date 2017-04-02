# python 2
#
# Homework 8, Problem 1
# Loops!
#
# Name: Johann
#

import random


def power(b,p):
    """ loop-based power function 
        input: b:any numeric value (base), p:any nonnegative integer (power)
        output: the value of b**p
    """
    result = 1                 # starting value - like a base case
    for x in range(p):         # loop from 0 to p-1, inclusive
        result = result * b    # update the result by mult. by b
    return result              # notice this is AFTER the loop!
    
# tests: run by pressing the Run button above
#print "power(2,5): should be 32 == ", power(2,5)
#print "power(5,2): should be 25 == ", power(5,2)
#print "power(42,0): should be 1 == ", power(42,0)
#print "power(0,42): should be 0 == ", power(0,42)
#print "power(0,0): should be 1 == ", power(0,0)


def summedOdds( L ):
    """ loop-based function to return a numeric list, summed
        (sum is built-in, so we're using a different name)
        input: L, a list of integers
        output: the sum of all of the odd elements in L using a loop
    """
    result = 0
    for e in L:
      if e % 2 == 1:
        result = result + e  # or result += e
    return result

# tests!
#print "summedOdds( [4,5,6] ): should be 5 == ", summedOdds([4,5,6])
#print "summedOdds( range(3,10) ): should be 24 == ", summedOdds(range(3,10))

def uniq(L):
    """ returns whether all elements in L are unique
        input: L, a list of any elements
        output: True, if all elements in L are unique,
             or False, if there is any repeated element
    """
    if len(L) == 0:
        return True
    elif L[0] in L[1:]:
        return False
    else:
        return uniq( L[1:] ) # recursion is OK, too!

def untilARepeat(high):
    """ return the number of guesses needed until it gets a repeat
    """
    L = []
    numguesses = 0    # we just made one, above
    while uniq(L) == True:
        guess = random.choice(range(0, high))
        L = L + [guess]  
        numguesses += 1  # add one to our number of guesses
    return numguesses

#print(untilARepeat(365))
