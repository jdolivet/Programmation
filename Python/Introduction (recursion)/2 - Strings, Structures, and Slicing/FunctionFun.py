# python 2
#
# Homework 2, Problem 2
#
# Name: Johann
# Date: 21/08/2016
#

def dbl(x):
    """  output: dbl returns twice its input
         input x: a number (int or float)
         Spam is great, and dbl("spam") is better!
    """
    return 2*x

def tpl(x):
    """ output: tpl returns thrice its input
         input x: a number (int or float)
    """
    return 3*x


def sq(x):
    """ output: sq returns the square its input
         input x: a number (int or float)
    """
    return x*x


def interp(low,hi,fraction):
    """ output: interp returns the floating-point value that is fraction of the way between low and hi.
         input low,hi,fraction: numbers (int or float)
    """
    return low + (hi-low) * fraction

def checkends(s):
    """ output: checkends returns True if the first character in s is the same as the last character in s. It returns False otherwise.
         input s: string
    """
    return s[0]==s[-1]

def flipside(s):
    """ output: flipside returns a string whose first half is s's second half and whose second half is s's first half.
         input s: string
    """
    l = len(s)/2
    return s[l:]+s[:l]

def convertFromSeconds(s):
    """ output: convertFromSeconds returns a list L of four nonnegative integers that represents that number of seconds in more conventional units of time.
         input s: non negative integer
    """
    sec = s%60
    m = s/60
    min = m%60
    h = m/60
    hour = h%24
    d=h/24
    return [d,hour,min,sec]

def front3(str):
    """ output: front3 returns a new string which is 3 copies of the front.
         input str: string
    """
    return 3*str[:3]
