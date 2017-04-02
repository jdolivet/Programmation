# python 2
#
# Homework 8, Problem 2
# Loops!
#
# Name: Johann
#

import random
import math

def dartThrow():
  """Generate random x and y coordinates between -1.0 and 1.0
  return True if the dart hit the target
  return False if the dart did not hit the target
  """
  x = random.uniform(-1.0, 1.0)
  y = random.uniform(-1.0, 1.0)
  if (x ** 2 + y ** 2) ** 0.5 <= 1.0:
    return True
  else:
    return False
#print(dartThrow())


def forPi(n):
  """ return the resulting estimate of pi """
  numthrows = 0
  numhits = 0
  for i in range(n):
    numthrows += 1
    if dartThrow() == True:
      numhits += 1
    estPi = 4.0 * numhits / numthrows
    print(str(numhits) +" hits out of " + str(numthrows) + " throws so that pi is " + str(estPi))
  return 4.0 * numhits / numthrows
#print(forPi(10))

def whilePi(maxerror):
  """return the number of darts thrown in order to reach the desired input accuracy"""
  numthrows = 0
  numhits = 0
  estPi = 0.0
  while abs(estPi - math.pi) > maxerror :
    numthrows += 1
    if dartThrow() == True:
      numhits += 1
    estPi = 4.0 * numhits / numthrows
    print(str(numhits) +" hits out of " + str(numthrows) + " throws so that pi is " + str(estPi))
  return numthrows
#print(whilePi(0.1))
