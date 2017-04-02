# python 2
#
# Homework 3, Problem 1.5
#
# Name: Johann
#
# Date: 22/08/2016
# Overall comments:
#

#
# mylen example from class
#
def mylen( s ):
    """ mylen outputs the length of s
              input: s, which can be a string or list

    """
    if s == '' or s == []:   # if empty string or empty list
        return 0
    else:
        return 1 + mylen( s[1:] )
        
def mult(n, m):
    """ mult returns the product of its two inputs
            inputs: n and m are both integers
            output: the result upon multiplying n and m
    """
    if n == 0:
        return 0
    elif n > 0:
        return m + mult(n-1,m)
    elif n <= 0 and m > 0:
        return -(m+mult(-1-n,m))
    else:
        return (m-mult(1-n,m))
#
# Tests
#
print "mult(6,7)    42 ==", mult(6,7)
print "mult(6,-7)  -42 ==", mult(6,-7)
print "mult(-6,7)  -42 ==", mult(-6,7)
print "mult(-6,-7)  42 ==", mult(-6,-7)
print "mult(6,0)     0 ==", mult(6,0)
print "mult(0,7)     0 ==", mult(0,7)
print "mult(0,0)     0 ==", mult(0,0)

def dot(L, K):
    """ dot returns the dot product of the lists L and K
            inputs: L and K are both lists
            output: float
    """
    if L == [] or K == [] or len(K)!=len(L):
        return 0.0
    else:
        return (L[0]*K[0]+dot(L[1:],K[1:]))
#
# Tests
#
print "dot( [5,3], [6,4] )     42.0 ==", dot( [5,3], [6,4] ) 
print "dot( [1,2,3,4], [10,100,1000,10000] )  43210.0 ==", dot( [1,2,3,4], [10,100,1000,10000] ) 
print "dot( [5,3], [6] )        0.0 ==", dot( [5,3], [6] ) 
print "dot( [], [6] )           0.0 ==", dot( [], [6] ) 
print "dot( [], [] )            0.0 ==", dot( [], [] ) 

def ind(e, L):
    """ ind returns the index at which e is first found in L
            inputs: L is a list e is an element
            output: integer
    """
    if e not in L:
        return len(L)
    elif L[0] == e or L[0] == '':
        return 0
    else:
        return 1 + ind(e,L[1:])
#
# Tests
#
print "ind( 42, [ 55, 77, 42, 12, 42, 100 ])  2 ==", ind( 42, [ 55, 77, 42, 12, 42, 100 ])
print "ind(42, range(0,100))                  42 ==", ind(42, range(0,100))
print "ind('hi', [ 'hello', 42, True ])       3 ==", ind('hi', [ 'hello', 42, True ])
print "ind('hi', [ 'well', 'hi', 'there' ])   1 ==", ind('hi', [ 'well', 'hi', 'there' ])
print "ind('i', 'team')                       4 ==", ind('i', 'team')
print "ind(' ', 'outer exploration')          5 ==", ind(' ', 'outer exploration')

def letterScore(let):
    """ letterScore returns the value of that character as a scrabble tile
            input: caracter
            output: integer
    """
    if let in 'aeilnorstu':
        return 1
    elif let in 'dg':
        return 2
    elif let in 'bcmp':
        return 3
    elif let in 'fhvwy':
        return 4
    elif let in 'k':
        return 5
    elif let in 'jx':
        return 8
    elif let in 'qz':
        return 10
    else:
        return 0
        
def scrabbleScore(S):
    """ scrabbleScore returns scrabble score of that string
            inputs: string
            output: integer
    """
    if S == '':
        return 0
    else:
        return letterScore(S[0]) + scrabbleScore(S[1:])
#
# Tests
#
print "scrabbleScore('quetzal'):  25 ==", scrabbleScore('quetzal')
print "scrabbleScore('jonquil'):  23 ==", scrabbleScore('jonquil')
print "scrabbleScore('syzygy'):   25 ==", scrabbleScore('syzygy')
print "scrabbleScore('abcdefghijklmnopqrstuvwxyz'):  87 ==", scrabbleScore('abcdefghijklmnopqrstuvwxyz')
print "scrabbleScore('?!@#$%^&*()'):  0 ==", scrabbleScore('?!@#$%^&*()')
print "scrabbleScore(''):          0 ==", scrabbleScore('')

def one_dna_to_rna(c):
    """ converts a single-character c from DNA
        nucleotide to complementary RNA nucleotide """
    if c == 'A': return 'U'
    elif c == 'C': return 'G'
    elif c == 'G': return 'C'
    elif c == 'T': return 'A'
    else: return ''

def transcribe(S):
    """ transcribe returns the messenger RNA that would be produced from the string S
            inputs: string
            output: string
    """
    if S == '':
        return ''
    else:
        return one_dna_to_rna(S[0]) + transcribe(S[1:])
        
#
# Tests
#
print "transcribe('ACGT TGCA'):  'UGCAACGU' ==", transcribe('ACGT TGCA')
print "transcribe('GATTACA'):     'CUAAUGU' ==", transcribe('GATTACA')
print "transcribe('cs5') :               '' ==", transcribe('cs5')
print "transcribe('') :                  '' ==", transcribe('')
