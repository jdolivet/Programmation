# python 2
#
# Homework 4, Problem 2
# Caesar Sorting
#
# Name: Johann
#
#

def rot(c, n):
    """ rotates c, a single character, forward by n spots in the alphabet"""
    if 'a' <= c <= 'z':
      return chr((((ord(c)-97)+n)%26)+97)
    elif 'A' <= c <= 'Z':
      return chr((((ord(c)-65)+n)%26)+65)
    else:
      return c
#print rot('%',2)

def encipher(S, n):
    """return a new string in which the letters in S have been "rotated" 
    by n characters forward in the alphabet, wrapping around as needed"""
    if S == '':
        return ''
    else:
        return rot(S[0], n) + encipher(S[1:], n)
#print encipher('This is a string!', 1)

# table of probabilities for each letter
def letProb(c):
  """ if c is the space character or an alphabetic character,
    we return its monogram probability (for english),
    otherwise we return 1.0 We ignore capitalization.
    Adapted from
    http://www.cs.chalmers.se/Cs/Grundutb/Kurser/krypto/en_stat.html
  """
  if c == ' ': return 0.1904
  if c == 'e' or c == 'E': return 0.1017
  if c == 't' or c == 'T': return 0.0737
  if c == 'a' or c == 'A': return 0.0661
  if c == 'o' or c == 'O': return 0.0610
  if c == 'i' or c == 'I': return 0.0562
  if c == 'n' or c == 'N': return 0.0557
  if c == 'h' or c == 'H': return 0.0542
  if c == 's' or c == 'S': return 0.0508
  if c == 'r' or c == 'R': return 0.0458
  if c == 'd' or c == 'D': return 0.0369
  if c == 'l' or c == 'L': return 0.0325
  if c == 'u' or c == 'U': return 0.0228
  if c == 'm' or c == 'M': return 0.0205
  if c == 'c' or c == 'C': return 0.0192
  if c == 'w' or c == 'W': return 0.0190
  if c == 'f' or c == 'F': return 0.0175
  if c == 'y' or c == 'Y': return 0.0165
  if c == 'g' or c == 'G': return 0.0161
  if c == 'p' or c == 'P': return 0.0131
  if c == 'b' or c == 'B': return 0.0115
  if c == 'v' or c == 'V': return 0.0088
  if c == 'k' or c == 'K': return 0.0066
  if c == 'x' or c == 'X': return 0.0014
  if c == 'j' or c == 'J': return 0.0008
  if c == 'q' or c == 'Q': return 0.0008
  if c == 'z' or c == 'Z': return 0.0005
  return 1.0
  
def score(S):
    """ return the score of the string S based upoen the table above"""
    return sum([letProb(c) for c in S])

def decipher(S):
    """return, to the best of its ability, the original English string, 
    which will be some rotation (possibly 0) of the input S."""
    L = [encipher(S,n) for n in range(26)]
    LoL = [score(x) for x in L]
    return L[LoL.index(max(LoL))]
#print decipher('Bzdrzq bhogdq? H oqdedq Bzdrzq rzkzc.')
#print decipher('Hu lkbjhapvu pz doha ylthpuz hmaly dl mvynla '\
#       'lclyfaopun dl ohcl slhyulk.')
#print decipher('Onyx balks')

def blsort(L):
    """take in a list binary list L and should return a list 
    with the same elements as L, but in ascending order"""
    nb0 = sum([i==0 for i in L])
    return [0]*nb0 + [1]*(len(L)-nb0)
#print blsort([1, 0, 1])
#print blsort([1, 0, 1, 0, 1, 0, 1])

def gensort(L , newL=[]):
    """return a list with the same elements as L, but in ascending order"""
    if L == []:
        return newL
    else:
        newL = newL + [min(L)]
        L.remove(min(L))
        return gensort(L,newL)
#print gensort([42, 1, 3.14])
#print gensort([7, 9, 4, 3, 0, 5, 2, 6, 1, 8])

def removeString(S, c):
    """ return the string without the char i"""
    newS=''
    justOne=0
    for letter in S:
      if justOne==0 and c == letter:
        newS=newS + ''
        justOne += 1
      else:
        newS = newS + letter
    return newS
#print removeString('diner', 'p')

def jscore(S, T, somme = 0):
    """ return the the number of characters in S that are shared by T. 
    Repeated letters are counted multiple times, 
    as long as they appear multiple times in both strings"""
    if S == '':
      return somme
    else:
      if S[0] in T:
        somme += 1
      return jscore(S[1:],removeString(T,S[0]),somme)
#print jscore('diner', 'syrup')
#print jscore('geese', 'elate')
#print jscore('gattaca', 'aggtccaggcgc')
#print jscore('gattaca', '') 

def exact_change(target_amount, L):
  """return True if it's possible to create target_amount by adding up some-or-all of the values in L. 
  It should return False if it's not possible to create target_amount by adding up some-or-all of the values in L"""
  if target_amount == 0:
    return True
  if L == []:
    return False
  else:
    useIt = exact_change(target_amount-L[0], L[1:])
    loseIt = exact_change(target_amount, L[1:])
    return useIt or loseIt
#print exact_change(42, [25, 1, 25, 10, 5, 1])
#print exact_change(42, [25, 1, 25, 10, 5])
#print exact_change(42, [23, 1, 23, 100])
#print exact_change(42, [23, 17, 2, 100])
#print exact_change(42, [25, 16, 2, 15])
#print exact_change(0, [4, 5, 6])
#print exact_change(-47, [4, 5, 6])
#print exact_change(0, [])
#print exact_change(42, [])

def LCS(S, T , sol=''):
  """return the longest common subsequence (LCS) that S and T share."""
  if S == '' or T=='':
    return sol
  else:
    if S[0] == T[0]:
      sol=sol+S[0]
      return LCS(S[1:],T[1:],sol)
    else:
      result1 = LCS(S[1:],T,sol)
      result2= LCS(S,T[1:],sol)
      if len(result1)>len(result2):
        return result1
      else:
        return result2
#print LCS('human', 'chimp')
#print LCS('gattaca', 'tacgaacta') 
#print LCS('wow', 'whew')
#print LCS('', 'whew')
#print LCS('abcdefgh', 'efghabcd') 
