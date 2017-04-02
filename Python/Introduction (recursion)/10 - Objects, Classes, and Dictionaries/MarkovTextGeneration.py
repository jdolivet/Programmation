# python 2
#
# Homework 10, Problem 3
# Name: Johann
#
import random

def createDictionary(filename):
    """ takes in a string, the name of a text file containing 
    some sample text. 
    It should return a dictionary whose 
    keys are words encountered in the text file and whose 
    entries are a list of words that may legally follow the key word """
    f = open( filename )
    text = f.read()
    f.close()
    
    LoW = text.split()   
    taille = len(LoW)
    d = {}
    for w in LoW:
      if w[-1] not in ".!?":
        d[w] = []
    d['$']=[]
    for i in range(taille):
      if i == 0 or LoW[i-1][-1] in ".!?":
        d['$'] += [LoW[i]]
      else:
        d[LoW[i-1]] += [LoW[i]]
    return d

#print createDictionary('t.txt')
#print createDictionary('a.txt')

def generateText(d, n):
  """take in a dictionary of word transitions d 
  (generated in your createDictionary function, above) 
  and a positive integer, n. 
  Then, generateText should return a string of n words."""
  w = random.choice(d['$'])
  s = w
  for i in range(1, n):
    if w[-1] in ".!?":
      w = random.choice(d['$'])
      s += ' ' + w
    else:
      w = random.choice(d[w])
      s += ' ' + w
  return s
 
#d = createDictionary('t.txt')
#print generateText(d, 20)
