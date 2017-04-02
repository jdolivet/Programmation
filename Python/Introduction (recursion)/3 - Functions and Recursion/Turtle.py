# python 2
#
# Homework 3, Problem 1
#
# name: Johann

from turtle import *
import time

def poly(n, N):
    """ draws n sides of an N-sided regular polygon """
    if n == 0:
        return None
    else:
        forward( 50 )   # 50 is hard-coded at the moment...
        left( 360.0/N )
        poly( n-1, N )
#poly(7,7)

def spiral(initialLength, angle, multiplier):
    """ draws spiral """
    if (initialLength < 1) or (initialLength > 500):
        return None
    else:
        forward(initialLength)   
        left(angle)
        spiral(initialLength * multiplier, angle, multiplier)
#spiral( 100, 90, 0.9 )

def chai(size):
    """ our chai function! """
    if (size<9): 
        return None
    else:
        forward(size)
        left(90)
        forward(size/2.0)
        right(90)
        chai(size/2)
        right(90)
        forward(size)
        left(90)
        chai(size/2)
        left(90)
        forward(size/2.0)
        right(90)
        backward(size)
#chai(100)

def svtree(trunklength, levels):
    """ the svtree function! """
    if (levels < 1): 
        return None
    else:
        forward(trunklength)
        left(45)
        svtree(trunklength/2, levels-1)
        right(90)
        svtree(trunklength/2, levels-1)
        left(45)
        backward(trunklength)
#svtree(100,5)

def flakeside(sidelength, levels):
    """ flakeside function"""
    if levels == 0:
      forward(sidelength)
      return None
    else :
      flakeside(sidelength/3, levels-1)
      right(60)
      flakeside(sidelength/3, levels-1)
      left(120)
      flakeside(sidelength/3, levels-1)
      right(60)
      flakeside(sidelength/3, levels-1)
#flakeside(100,2)

def snowflake(sidelength, levels):
    """ fractal snowflake function
          sidelength: pixels in the largest-scale triangle side
          levels: the number of recursive levels in each side
    """
    flakeside( sidelength, levels )
    left(120)
    flakeside( sidelength, levels )
    left(120)
    flakeside( sidelength, levels )
    left(120)
#snowflake(100,1)
