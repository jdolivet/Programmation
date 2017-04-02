# python 2
#
# Homework 9, Problem 1
# Game of Life
#
# Name: Johann
#

import random

def createOneRow(width):
    """ returns one row of zeros of width "width"...  
         You should use this in your
         createBoard(width, height) function """
    row = []
    for col in range(width):
        row += [0]
    return row
    
def createBoard(width, height):
    """ returns a 2d array with "height" rows and "width" cols """
    A = []
    for row in range(height):
        A += [createOneRow(width)] 
    return A
#print(createBoard(5, 3))

def printBoard(A):
    for row in A:
        line = ''
        for col in row:
            line += str(col)
        print line
#printBoard(createBoard(5, 3))

def diagonalize(width, height):
    """ creates an empty board and then modifies it
        so that it has a diagonal strip of "on" cells.
    """
    A = createBoard(width, height)
    for row in range(height):
        for col in range(width):
            if row == col:
                A[row][col] = 1
            else:
                A[row][col] = 0
    return A
#printBoard(diagonalize(7, 6))

def innerCells(w, h):
    """returns a 2d array that has all live cells with the value of 1 
    except for a one-cell-wide border of empty cells (with the value of 0) 
    around the edge of the 2d array."""
    A = createBoard(w, h)
    for row in range(h):
        for col in range(w):
            if row == 0 or col == 0 or row  == len(A) - 1 or col == len(A[0]) - 1:
                A[row][col] = 0
            else:
                A[row][col] = 1
    return A
#printBoard(innerCells(3, 7))

def randomCells(w, h):
    """returns an array of randomly-assigned 1's and 0's except that the outer edge of the array is still completely empty (all 0's) as in the case of innerCells"""
    A = createBoard(w, h)
    for row in range(h):
        for col in range(w):
            if row == 0 or col == 0 or row  == len(A) - 1 or col == len(A[0]) - 1:
                A[row][col] = 0
            else:
                A[row][col] = random.choice([0, 1])
    return A
#printBoard(randomCells(6, 5))

def copy(A):
  """make a deep copy of the 2d array A"""
  height = len(A)
  width = len(A[0])
  newA = [[0 for i in range(width)] for j in range(height)]
  for row in range(height):
    for col in range(width):
      newA[row][col] = A[row][col]
  return newA
#A = createBoard(4,3)
#printBoard(A)
#newA = copy(A)
#printBoard(newA)
#A[1][1] = 1
#printBoard(A)
#printBoard(newA)

def innerReverse(A):
  """takes an old 2d array (or "generation") 
  and then creates a new generation of the same shape and size
  the new generation should be the "opposite" of A's cells 
  everywhere except on the outer edge"""
  height = len(A)
  width = len(A[0])
  newA = [[0 for i in range(width)] for j in range(height)]
  for row in range(1, height - 1):
    for col in range(1, width - 1):
      newA[row][col] = (A[row][col] + 1) % 2
  return newA
#A = randomCells(8, 8)
#printBoard(A)
#print("")
#A2 = innerReverse(A)
#printBoard(A2)

def countNeighbors(row, col, A):
  """return the number of live neighbors for a cell 
  in the board A at a particular row and col."""
  compt = 0
  for i in [row - 1, row, row + 1]:
    for j in [col - 1, col, col + 1]:
      if i != row or j != col:
        if i>=0 and i<len(A) and j>=0 and j<len(A[0]):
          compt += A[i][j]
  return compt
#A = [ [0,0,0,0],
#      [0,0,1,0],
#      [0,0,1,0],
#      [0,0,1,0],
#      [0,0,0,0]]
#printBoard(A)
#print(countNeighbors(2,1,A))
#print(countNeighbors(2,2,A))
#print(countNeighbors(0,1,A))
#print(countNeighbors(1,0,A))
#print(countNeighbors(4,3,A))
#print(countNeighbors(3,3,A))

def next_life_generation(A):
  """should accept a 2D array A, representing the "old" generation of cells, 
  and it should return the next generation of cells, each either 0 or 1, 
  based on John Conway's rules for the Game of Life"""
  height = len(A)
  width = len(A[0])
  newA = [[0 for i in range(width)] for j in range(height)]
  for row in range(1, height - 1):
    for col in range(1, width - 1):
      neighbors = countNeighbors(row, col, A)
      if neighbors < 2:
        newA[row][col] = 0
      elif neighbors > 3:
        newA[row][col] = 0
      elif neighbors == 3:
        newA[row][col] = 1
      else:
        newA[row][col] = A[row][col]
  return newA
#A = [ [0,0,0,0,0],
#      [0,0,1,0,0],
#      [0,0,1,0,0],
#      [0,0,1,0,0],
#      [0,0,0,0,0]]
#printBoard(A)
#print('')
#A2 = next_life_generation(A)
#printBoard(A2)
#print('')
#A3 = next_life_generation(A2)
#printBoard(A3)
