# python 2
#
# Homework 5, Problem 1
#
# Name: Johann
#

def isOdd(n):
  """returns True if n is odd and False if n is even"""
  return (n % 2) == 1
#print(isOdd(42))
#print(isOdd(43))

def numToBinary(N):
  """ converting decimal numbers into binary form one bit at a time
  """
  if N == 0:
    return ''
  elif N % 2 == 1:
    return numToBinary(N // 2) + '1'
  else:
    return numToBinary(N // 2) + '0'
#print(numToBinary(5))
#print(numToBinary(12))

def binaryToNum(S):
  """ converting binary numbers (a string) into decimal form one bit at a time
  """
  if S == '':
    return 0
  elif S[-1] == '1':    # if the last digit is a '1'
    return  binaryToNum(S[:-1]) * 2 + 1
  else:                 # last digit must be '0'
    return  binaryToNum(S[:-1]) * 2 + 0
#print(binaryToNum('101010'))
#print(binaryToNum("100"))
#print(binaryToNum("1011"))
#print(binaryToNum("00001011"))
#print(binaryToNum(""))
#print(binaryToNum("0"))
#print(binaryToNum("1100100"))

def increment(S):
  """accepts an 8-character string S of 0's and 1's 
  and returns the next largest number in base 2"""
  nb = binaryToNum(S)
  string = numToBinary((nb + 1) % (2**8))
  l = len(string)
  return '0' * (8 - l) + string
#print(increment('00000000'))
#print(increment('00000001'))
#print(increment('00000111'))
#print(increment('11111111'))

def count(S, n):
  """accepts an 8-character binary input string 
  and then begins counts n times upward from S, printing as it goes."""
  if n == 0:
    print(S)
    return None
  else:
    print(S)
    return count(increment(S),n-1)
#count("00000000", 4)
#count("11111110", 5)

# in base 3, 42 = 1120

def numToTernary(N):
  """ converting decimal numbers into ternary form one bit at a time
  """
  if N == 0:
    return ''
  elif N % 3 == 0:
    return numToTernary(N // 3) + '0'
  elif N % 3 == 1:
    return numToTernary(N // 3) + '1'
  else:
    return numToTernary(N // 3) + '2'
#print(numToTernary(42))
#print(numToTernary(4242))

def ternaryToNum(S):
  """ converting ternary numbers (a string) into decimal form one bit at a time
  """
  if S == '':
    return 0
  elif S[-1] == '2':    # if the last digit is a '2'
    return  ternaryToNum(S[:-1]) * 3 + 2
  elif S[-1] == '1':    # if the last digit is a '1'
    return  ternaryToNum(S[:-1]) * 3 + 1
  else:                 # last digit must be '0'
    return  ternaryToNum(S[:-1]) * 3 + 0
#print(ternaryToNum('1120'))
#print(ternaryToNum('12211010'))

def balancedTernaryToNum(S):
  """return the decimal value equivalent 
  to the balanced ternary string S
  + (the plus sign) represents +1
  0 represents zero, as usual
  - (the minus sign) represents -1"""
  if S == '':
    return 0
  elif S[-1] == '+':    # if the last digit is a '+'
    return  balancedTernaryToNum(S[:-1]) * 3 + 1
  elif S[-1] == '0':    # if the last digit is a '0'
    return  balancedTernaryToNum(S[:-1]) * 3 + 0
  else:                 # last digit must be '-'
    return  balancedTernaryToNum(S[:-1]) * 3 - 1
#print(balancedTernaryToNum('+---0'))
#print(balancedTernaryToNum('++-0+'))

def numToBalancedTernary(N):
  """return a balanced ternary string 
  representing the value of the argument N
  + (the plus sign) represents +1
  0 represents zero, as usual
  - (the minus sign) represents -1"""
  if N == 0:
    return ''
  elif N % 3 == 0:
    return numToBalancedTernary(N // 3) + '0'
  elif N % 3 == 1:
    return numToBalancedTernary(N // 3) + '+'
  else:
    return numToBalancedTernary((N + 1) // 3) + '-'
#print(numToBalancedTernary(42))
#print(numToBalancedTernary(100))
