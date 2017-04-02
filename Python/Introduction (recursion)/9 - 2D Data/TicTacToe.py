# python 2
#
# Homework 9, Problem 2
#
# Name:
#

# here is a function for printing 2d arrays
# (lists-of-lists) of data

def print2d( A ):
    """ print2d prints a 2d array, A
        as rows and columns
        input: A, a 2d list of lists
        output: None (no return value)
    """
    NR = len(A)
    NC = len(A[0])

    for r in range(NR): # NR = =numrows
        for c in range(NC):  # NC == numcols
            print A[r][c],
        print('')

    return None  # this is implied anyway,
    # when no return statement is present
# some tests for print2d
#A = [ ['X',' ','O'], ['O','X','O'] ]
#print("2-row, 3-col A is")
#print2d(A)

#A = [ ['X','O'], [' ','X'], ['O','O'], ['O','X'] ]
#print("4-row, 2-col A is")
#print2d(A)


# create a 2d array from a 1d string
def createA( NR, NC, s ):
    """ returns a 2d array with
        NR rows (numrows) and
        NC cols (numcols)
        using the data from s: across the
        first row, then the second, etc.
        We'll only test it with enough data!
    """
    A = []
    for r in range(NR):
        newrow = []
        for c in range(NC):
            newrow += [ s[0] ] # add that char
            s = s[1:]   # get rid of that first char
        A += [newrow]
    return A

# a couple of tests for createA:
#A = [ ['X',' ','O'], ['O','X','O'] ]
#newA = createA( 2, 3, 'X OOXO')
#print("Is newA == A? Should be True:", newA == A)

#A = [ ['X','O'], [' ','X'], ['O','O'], ['O','X'] ]
#newA = createA( 4, 2, 'XO XOOOX')
#print("Is newA == A? Should be True:", newA == A)

def inarow_3east(ch, r_start, c_start, A):
  """This should start from r_start and c_start 
  and check for three-in-a-row eastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - 1:
    return False
  if c_start < 0 or c_start > len(A[0]) - 3:
    return False
  compt = 0
  if A[r_start][c_start] == ch and A[r_start][c_start+1] == ch and A[r_start][c_start+2] == ch:
    return True
  return False
# tests of inarow_3east
#A = createA(3, 4, 'XXOXXXOOOOOO')
#print2d(A)
#print("inarow_3east('X',0,0,A): False ==", inarow_3east('X',0,0,A))
#print("inarow_3east('O',2,1,A):  True ==", inarow_3east('O',2,1,A))
#print("inarow_3east('X',2,1,A): False ==", inarow_3east('X',2,1,A))
#print("inarow_3east('O',2,2,A): False ==", inarow_3east('O',2,2,A))

def inarow_3south( ch, r_start, c_start, A ):
  """This should start from r_start and c_start 
  and check for three-in-a-row southward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - 3:
    return False
  if c_start < 0 or c_start > len(A[0]) - 1:
    return False
  compt = 0
  if A[r_start][c_start] == ch and A[r_start+1][c_start] == ch and A[r_start+2][c_start] == ch:
    return True
  return False
# tests of inarow_3south
#A = createA( 4, 4, 'XXOXXXOXXOO OOOX')
#print2d(A)
#print "inarow_3south('X',0,0,A):    True ==", inarow_3south('X',0,0,A)
#print "inarow_3south('O',2,2,A):   False ==", inarow_3south('O',2,2,A)
#print "inarow_3south('X',1,3,A):   False ==", inarow_3south('X',1,3,A)
#print "inarow_3south('O',42,42,A): False ==", inarow_3south('O',42,42,A)

def inarow_3se( ch, r_start, c_start, A ):
  """This should start from r_start and c_start 
  and check for three-in-a-row southeastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - 3:
    return False
  if c_start < 0 or c_start > len(A[0]) - 3:
    return False
  compt = 0
  if A[r_start][c_start] == ch and A[r_start+1][c_start+1] == ch and A[r_start+2][c_start+2] == ch:
    return True
  return False
# tests of inarow_3se
#A = createA( 4, 4, 'XOOXXXOXX XOOOOX')
#print2d(A)
#print "inarow_3se('X',1,1,A):  True ==", inarow_3se('X',1,1,A)
#print "inarow_3se('X',1,0,A): False ==", inarow_3se('X',1,0,A)
#print "inarow_3se('O',0,1,A):  True ==", inarow_3se('O',0,1,A)
#print "inarow_3se('X',2,2,A): False ==", inarow_3se('X',2,2,A)

def inarow_3ne( ch, r_start, c_start, A ):
  """This should start from r_start and c_start 
  and check for three-in-a-row northeastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 2 or r_start > len(A) - 1:
    return False
  if c_start < 0 or c_start > len(A[0]) - 3:
    return False
  compt = 0
  if A[r_start][c_start] == ch and A[r_start-1][c_start+1] == ch and A[r_start-2][c_start+2] == ch:
    return True
  return False
# tests of inarow_3ne
#A = createA( 4, 4, 'XOXXXXOXXOXOOOOX')
#print2d(A)
#print "inarow_3ne('X',2,0,A):  True ==", inarow_3ne('X',2,0,A)
#print "inarow_3ne('O',3,0,A):  True ==", inarow_3ne('O',3,0,A)
#print "inarow_3ne('O',3,1,A): False ==", inarow_3ne('O',3,1,A)
#print "inarow_3ne('X',3,3,A): False ==", inarow_3ne('X',3,3,A)

def inarow_Neast(ch, r_start, c_start, A, N):
  """This should start from r_start and c_start 
  and check for N-in-a-row eastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - 1:
    return False
  if c_start < 0 or c_start > len(A[0]) - N:
    return False
  compt = 0
  test = (A[r_start][c_start] == ch)
  for j in range(1, N):
    test = test and (A[r_start][c_start + j] == ch)
  if test == True:
    return True
  return False
# tests of inarow_Neast
#A = createA( 5, 5, 'XXOXXXOOOOOOXXXX XXXOOOOO')
# print2d(A)
#print "inarow_Neast('O',1,1,A,4):  True ==", inarow_Neast('O',1,1,A,4)
#print "inarow_Neast('O',1,3,A,2):  True ==", inarow_Neast('O',1,3,A,2)
#print "inarow_Neast('X',3,2,A,4): False ==", inarow_Neast('X',3,2,A,4)
#print "inarow_Neast('O',4,0,A,5):  True ==", inarow_Neast('O',4,0,A,5)

def inarow_Nsouth(ch, r_start, c_start, A, N):
  """This should start from r_start and c_start 
  and check for N-in-a-row southward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - N:
    return False
  if c_start < 0 or c_start > len(A[0]) - 1:
    return False
  compt = 0
  test = (A[r_start][c_start] == ch)
  for j in range(1, N):
    test = test and (A[r_start + j][c_start] == ch)
  if test == True:
    return True
  return False
# tests of inarow_Nsouth
#A = createA( 5, 5, 'XXOXXXOOOOOOXXXXOXXXOOOXO')
#print2d(A)
#print "inarow_Nsouth('X',0,0,A,5): False ==", inarow_Nsouth('X',0,0,A,5)
#print "inarow_Nsouth('O',1,1,A,4):  True ==", inarow_Nsouth('O',1,1,A,4)
#print "inarow_Nsouth('O',0,1,A,6): False ==", inarow_Nsouth('O',0,1,A,6)
#print "inarow_Nsouth('X',4,3,A,1):  True ==", inarow_Nsouth('X',4,3,A,1)

def inarow_Nse(ch, r_start, c_start, A, N):
  """This should start from r_start and c_start 
  and check for N-in-a-row southeastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < 0 or r_start > len(A) - N:
    return False
  if c_start < 0 or c_start > len(A[0]) - N:
    return False
  compt = 0
  test = (A[r_start][c_start] == ch)
  for j in range(1, N):
    test = test and (A[r_start + j][c_start + j] == ch)
  if test == True:
    return True
  return False
# tests of inarow_Nse
#A = createA( 5, 5, 'XOO XXXOXOOOXXXXOXXXOOOXX' )
# print2d(A)
#print "inarow_Nse('X',1,1,A,4):  True ==", inarow_Nse('X',1,1,A,4)
#print "inarow_Nse('O',0,1,A,3): False ==", inarow_Nse('O',0,1,A,3)
#print "inarow_Nse('O',0,1,A,2):  True ==", inarow_Nse('O',0,1,A,2)
#print "inarow_Nse('X',3,0,A,2): False ==", inarow_Nse('X',3,0,A,2)

def inarow_Nne(ch, r_start, c_start, A, N):
  """This should start from r_start and c_start 
  and check for N-in-a-row northeastward of element ch, 
  returning True or False, as appropriate"""
  if r_start < (N - 1) or r_start > len(A) - 1:
    return False
  if c_start < 0 or c_start > len(A[0]) - N:
    return False
  compt = 0
  test = (A[r_start][c_start] == ch)
  for j in range(1, N):
    test = test and (A[r_start - j][c_start + j] == ch)
  if test == True:
    return True
  return False
# tests of inarow_Nne
#A = createA( 5, 5, 'XOO XXXOXOOOXOXXXOXXXOOXX' )
#print2d(A)
#print "inarow_Nne('X',4,0,A,5):  True ==", inarow_Nne('X',4,0,A,5)
#print "inarow_Nne('O',4,1,A,4):  True ==", inarow_Nne('O',4,1,A,4)
#print "inarow_Nne('O',2,0,A,2): False ==", inarow_Nne('O',2,0,A,2)
#print "inarow_Nne('X',0,3,A,1): False ==", inarow_Nne('X',0,3,A,1)
