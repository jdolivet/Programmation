# python 2
#
# Homework 4, Problem 1
# "Lights On!"
#
# Name: Johann
#
#

import time           # provides time.sleep(0.5)
from random import *  # provides choice([0,1]), etc.


def mutate(i, oldL):
    """ Accepts an index (i) and an old list (oldL).
        mutate returns the ith element of a NEW list!
        * Note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve.
    """
    new_ith_element = 1 + oldL[i]
    return new_ith_element
    
def mutate0(i, oldL):
    """ Accepts an index (i) and an old list (oldL).
        mutate returns the ith element of a NEW list!
        * Note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve.
    """
    new_ith_element =  2 * oldL[i]
    return new_ith_element

def mutate1(i, oldL):
    """ Accepts an index (i) and an old list (oldL).
        mutate returns the ith element of a NEW list!
        * Note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve.
    """
    new_ith_element = oldL[i] ** 2
    return new_ith_element

def mutate2(i, oldL):
    """ Accepts an index (i) and an old list (oldL).
        mutate returns the ith element of a NEW list!
        * Note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve.
    """
    new_ith_element = oldL[(i-1) % len(oldL)]
    return new_ith_element


def mutate3(i, oldL):
    """ Accepts an index (i) and an old list (oldL).
        mutate returns the ith element of a NEW list!
        * Note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve.
    """
    new_ith_element = choice([0, 1])
    return new_ith_element

def allOnes(L):
    """ returns True if all of L's elements are 1 and returns False otherwise
    input : list of numbers
    """
    if L == []:
      return True
    else:
      return (L[0] == 1) and allOnes(L[1:])
#print allOnes([1, 1, 1])
#print allOnes([])
#print allOnes([0, 0, 2, 2])
#print allOnes([1, 1, 0])

def mutate4(i, oldL, user=0):
    """ takes as input an index (i) and an old list (oldL)
        mutate returns the ith element of a NEW list!
        * note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve
    """
    if i == user:
        new_ith_element = 1        # this makes the game easy!
    else:
        new_ith_element = oldL[i] # the new is the same as the old
    return new_ith_element
    
def mutate5(i, oldL, user=0):
    """ takes as input an index (i) and an old list (oldL)
        mutate returns the ith element of a NEW list!
        * note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve
    """
    if i == user:
        new_ith_element = (oldL[i] + 1) % 2        # this makes the game easy!
    else:
        new_ith_element = oldL[i] # the new is the same as the old
    return new_ith_element
    
def mutate6(i, oldL, user=0):
    """ takes as input an index (i) and an old list (oldL)
        mutate returns the ith element of a NEW list!
        * note that mutate returns ONLY the ith element
          mutate thus needs to be called many times in evolve
    """
    if i == user or i == user - 1 or i == user + 1:
        new_ith_element = (oldL[i] + 1) % 2 
    else:
        new_ith_element = oldL[i] # the new is the same as the old
    return new_ith_element

def randBL(N):
    """return random binary list
    input : integer
    """
    return [choice([0,1]) for i in range(N)]
#print randBL(5)

def evolve(oldL, curgen = 0):
    """ This function should evolve oldL (a list)
        it starts at curgen, the "current" generation
        and it ends at generation #5 (for now)
        
        It works by calling mutate at each index i.
    """
    print oldL,                    # print the old list, L
    print "  (gen: " + str(curgen) + ")"  # and its gen.
    #time.sleep(0.05)
    
    if allOnes(oldL) == True:  # we're done!
        return curgen       
    else:
        user = choice(range(len(oldL)))
        newL = [mutate6(i,oldL,int(user)) for i in range(len(oldL))]
        return evolve(newL, curgen + 1)
#evolve([1,2,3], 0)
#evolve([1,2,3,42])
#print evolve([0,0,0,0,1])
#print evolve([0,1,0,1])

#I think it would take around 32 steps for five element because the prob of all one is (1/2)^5

#evolve([0,0,0,0,0,0,0,0])
#evolve(randBL(5))
