# python 2
#
# Homework 5, Problem 2
#
# Name: Johann
#

def numToBaseB(N, B):
  """takes as input a non-negative integer N and a base B (between 2 and 10 inclusive); 
  it should returns a string representing the number N in base B."""
  if N == 0:
    return ''
  else:
    return numToBaseB((N // B), B) + str(N % B)
#print(numToBaseB(42, 8))
#print(numToBaseB(42, 5))
#print(numToBaseB(42, 10))
#print(numToBaseB(42, 2))
#print(numToBaseB(4, 2))
#print(numToBaseB(0, 4))

def baseBToNum(S, B):
  """ input a string S and a base B where S represents a number in base B 
  and B is between 2 and 10 inclusive. baseBToNum should  then 
  return an integer in base 10 representing the same number as S."""
  if S == '':
    return 0
  else:
    return baseBToNum(S[:-1], B) * B + int(S[-1]) % B
#print(baseBToNum('222', 4))
#print(baseBToNum("101010", 2))
#print(baseBToNum("101010", 3))
#print(baseBToNum("101010", 10))
#print(baseBToNum("", 10))

def baseToBase(B1,B2,s_in_B1):
  """a base B1, a base B2 (both of which are between 2 and 10, inclusive) 
  and s_in_B1, which is a string representing a number in base B1.
  return a string representing the same number in base B2."""
  solDec = baseBToNum(s_in_B1, B1)
  return numToBaseB(solDec, B2)
#print(baseToBase(2, 10, "11"))
#print(baseToBase(10, 2, "3"))
#print(baseToBase(3, 5, "11"))
#print(baseToBase(2, 5, '1001001010'))

def add(S,T):
  """takes two binary strings S and T as input 
  and returns their sum, also in binary."""
  somme = baseBToNum(S, 2)+baseBToNum(T, 2)
  return numToBaseB(somme, 2)
#print(add("11", "1"))
#print(add("11100", "11110"))

def addB(S, T, C = '0'):
  """takes two binary strings S and T as input 
  and returns their sum, also in binary."""
  taille = max(len(S), len(T))
  S = '0' * (taille - len(S)) + S
  T = '0' * (taille - len(T)) + T
  if S == '' and  T == '' and C == '1':
    return '1'
  elif S == '' and  T == '' and C == '0':
    return ''
  else:
    somme = (int(S[-1]) + int(T[-1]) + int(C))
    return addB(S[:-1], T[:-1], str(somme // 2)) + str(somme % 2)
#print(addB("11100", "11110"))
#print(addB("10101", "10101"))
#print(addB("11", "1"))
#print(addB("11", "100"))

def code(c, N):
  """return the RLE code of length N <=64 of the caractere 'c'"""
  bin = numToBaseB(N, 2)
  taille = len(bin)
  bin = '0' * (7 - taille) + bin
  return c + bin
#print(code('0',52))

def compress(S, sol = ''):
  """takes a binary string S of length less than or equal to 64 as input 
  and returns another binary string as output."""
  if S == '':
    return sol
  else:
    i = 0
    l = len(S)
    while i < min(64, l) and (S[i]==S[0]):
      i = i + 1
    sol = sol + code(S[0], i)
    return compress(S[i:],sol)
#print(compress(64*'0'))
#print(compress('11111'))
#print(compress('0'*16 + '1'*16 + '0'*16 + '1'*16))

def uncompress(S, sol = ''):
  """ "inverts" or "undoes" the compressing in your compress function."""
  if S == '':
    return sol
  else:
    bit = S[0]
    size = baseBToNum(S[1:8],2)
    sol = sol + bit * size
    return uncompress(S[8:], sol)
#print(uncompress('10000101'))
#print(uncompress('00010000100100000001000010010000'))
