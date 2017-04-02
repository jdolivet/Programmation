# python 2
#
# Homework 3, Problem 2
#
# Name: Johann
#

import random  

def rs():
    """ rs chooses a random step and returns it 
        note that a call to rs() requires parentheses
        inputs: none at all!
    """
    return random.choice([-1,1])
    
def rwpos(start, nsteps):
    """ rwpos returns the random walker's position
        inputs: integers
        output: integer
    """
    if nsteps == 0:
      return start
    else:
      start = start + rs()
      print 'start is', start
      return rwpos(start, nsteps-1)
#print(rwpos(40, 4))

def rwsteps(start, low, hi):
    """ rwsteps simulate a random walk
        hi >= start >= low
    """
    if start >= hi or start <= low:
      return 0
    else:
      print (low-1)*' '+'|'+(start-low-1)*' '+'S'+(hi-start-1)*' '+'|'
      newstart = start + rs()
      rest_of_steps = rwsteps(newstart, low, hi)
      return 1+rest_of_steps
#print rwsteps( 10, 5, 15 )

def rwposPlain(start, nsteps):
    """ rwpos returns the random walker's position
        inputs: integers
        output: integer
    """
    if nsteps == 0:
      return start
    else:
      start = start + rs()
      return rwposPlain(start, nsteps-1)

def ave_signed_displacement(numtrials):
  """return the average of the result
  """
  LC = [rwposPlain(0,100) for x in range(numtrials)]
  sum = 0
  for e in LC:
    sum=sum+e
  return float(sum)/len(LC)
  
def ave_squared_displacement(numtrials):
  """return the average of the square of the result
  """
  LC = [rwposPlain(0,100) for x in range(numtrials)]
  sum = 0
  for e in LC:
    sum=sum + e*e
  return float(sum)/len(LC)
  
#print ave_signed_displacement(142)
#print ave_squared_displacement(142)
