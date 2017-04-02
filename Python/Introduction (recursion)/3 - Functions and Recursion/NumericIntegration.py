# python 2
#
# Homework 3, Problem 3
# List comprehensions!
#
# Name: Johann
#

# this gives us functions like sin and cos
from math import *

# two more functions (not in the math library above)
def dbl(x):
    """ doubler!  input: x, a number """
    return 2*x

def sq(x):
    """ squarer!  input: x, a number """
    return x**2

# examples for getting used to list comprehensions
def lc_mult(N):
    """ this example takes in an int N
        and returns a list of integers
        from 0 to N-1, **each multiplied by 2**
    """
    return [2*x for x in range(N)]

def lc_idiv(N):
    """ this example takes in an int N
        and returns a list of integers
        from 0 to N-1, **each divided by 2**
        WARNING: this is INTEGER division...!
    """
    return [float(x/2) for x in range(N)]

def lc_fdiv(N):
    """ this example takes in an int N
        and returns a list of integers
        from 0 to N-1, **each divided by 2**
        NOTE: this is floating-point division...!
    """
    return [float(x)/2 for x in range(N)]

# Here is where your functions start for the homework:

def unitfracs(N):
    """  it returns a list of evenly-spaced left-hand endpoints (fractions) in the unit interval [0,1]
    """
    return [float(x)/N for x in range(N)]
#print unitfracs(10)

def scaledfracs(low,hi,N):
    """ creates N left endpoints uniformly through the interval [low,hi]
    """
    return [low + (hi-low) * x for x in unitfracs(N)]
#print scaledfracs(10, 30, 5)

def sqfracs(low,hi,N):
    """ similar to scaledfracs except that each value is squared
    """
    return [x * x for x in scaledfracs(low,hi,N)]
#print sqfracs(0,10,5)

def f_of_fracs(f,low,hi,N):
    """ takes a function as its first input
    """
    return [f(x) for x in scaledfracs(low,hi,N)]
#print f_of_fracs(sq, 4, 10, 6)

def integrate(f,low,hi,N):
    """ integrate returns an estimate of the definite integral
     of the function f (the first input)
     with lower limit low (the second input)
     and upper limit hi (the third input)
     where N steps are taken (the fourth input)

    integrate simply returns the sum of the areas of rectangles
    under f, drawn at the left endpoints of N uniform steps
    from low to hi
    """
    L=f_of_fracs(f,low,hi,N)
    return sum(L) * float((hi-low*1.0)/N)
#print integrate(dbl, 0, 10, 1000)

"""
Q1. 

Because the area is always lowest : this is a growing function
"""
def c(x):
    """ c is a semicircular function of radius two """
    return (4-x**2)**0.5
#print integrate(c,0,2,2000)
"""
Q1. 
integrate(c,0,2,2)=3.73205080757
integrate(c,0,2,20)=3.22846487975
integrate(c,0,2,200)=3.15117694484
integrate(c,0,2,2000)=3.14257950591
The value will be pi (because this is the area of half disk)
"""
