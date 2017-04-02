# python 2
#
# Homework 10, Problem 2
# Name: Johann
#

class Board:
    """ a datatype representing a C4 board
        with an arbitrary number of rows and cols
    """
    
    def __init__( self, width, height ):
        """ the constructor for objects of type Board """
        self.width = width
        self.height = height
        W = self.width
        H = self.height
        self.data = [ [' ']*W for row in range(H) ]
        # we do not need to return inside a constructor!
        
    def __repr__(self):
        """ this method returns a string representation
            for an object of type Board
        """
        H = self.height
        W = self.width
        s = ''   # the string to return
        for row in range(0,H):
            s += '|'   
            for col in range(0,W):
                s += self.data[row][col] + '|'
            s += '\n'

        s += (2*W+1) * '-'    # bottom of the board
        s += '\n'
        for col in range(0,W):
          s += ' ' + str(col)
        return s       # the board is complete, return it
        
    def addMove(self, col, ox):
      """the first input col represents the index of the column to which the checker will be added; 
      the second input ox will be a 1-character string representing the checker to add to the board"""
      row = self.height - 1
      while self.data[row][col] != ' ':
        row -= 1
      self.data[row][col] = ox
        
    def clear(self):
      """ clear the board"""
      H = self.height
      W = self.width
      for row in range(0,H):
          for col in range(0,W):
              self.data[row][col] = ' '
              
    def setBoard( self, moveString ):
      """ takes in a string of columns and places
            alternating checkers in those columns,
            starting with 'X'
            
            For example, call b.setBoard('012345')
            to see 'X's and 'O's alternate on the
            bottom row, or b.setBoard('000000') to
            see them alternate in the left column.
            moveString must be a string of integers
      """
      nextCh = 'X'   # start by playing 'X'
      for colString in moveString:
          col = int(colString)
          if 0 <= col <= self.width:
              self.addMove(col, nextCh)
          if nextCh == 'X': nextCh = 'O'
          else: nextCh = 'X'
          
    def allowsMove(self, c):
      """return True if the calling object (of type Board) 
      does allow a move into column c. 
      It returns False if column c is not a legal column 
      number for the calling object. 
      It also returns False if column c is full."""
      if c < 0 or c >= self.width:
        return False
      elif self.data[0][c] != ' ':
        return False
      else:
        return True
        
    def isFull(self):
      """return True if the calling object (of type Board) 
      is completely full of checkers. 
      It should return False otherwise."""
      H = self.height
      W = self.width
      for row in range(0,H):
          for col in range(0,W):
              if self.data[row][col] == ' ':
                return False
      return True
              
    def delMove(self, c):
      """do the opposite of addMove. 
      It should remove the top checker from the column c. 
      If the column is empty, then delMove should do nothing. """
      row = 0
      while row < self.height - 1 and self.data[row][c] == ' ':
        row += 1
      self.data[row][c] = ' '
      
    def winsFor(self, ox):
      """input ox is a 1-character checker: either 'X' or 'O'. 
      It should return True if there are four checkers 
      of type ox in a row on the board. 
      It should return False othwerwise."""
      H = self.height
      W = self.width
      D = self.data
      # check for horizontal wins 
      for row in range(0, H):
          for col in range(0, W - 3):
              if D[row][col] == ox and \
                 D[row][col+1] == ox and \
                 D[row][col+2] == ox and \
                 D[row][col+3] == ox:
                  return True
      # check for verticaltal wins 
      for row in range(0, H - 3):
          for col in range(0, W):
              if D[row][col] == ox and \
                 D[row+1][col] == ox and \
                 D[row+2][col] == ox and \
                 D[row+3][col] == ox:
                  return True
      # check for diagonal wins 
      for row in range(0,H - 3):
          for col in range(0, W - 3):
              if D[row][col] == ox and \
                 D[row+1][col+1] == ox and \
                 D[row+2][col+2] == ox and \
                 D[row+3][col+3] == ox:
                  return True
      # check for diagonal wins 
      for row in range(3, H):
          for col in range(0, W - 3):
              if D[row][col] == ox and \
                 D[row-1][col+1] == ox and \
                 D[row-2][col+2] == ox and \
                 D[row-3][col+3] == ox:
                  return True
      return False
      
    def hostGame(self):
      """ host a game of connect four
       it should alternate turns between 'X' (who will always go first) 
       and 'O' (who will always go second). 
       It should ask the user (with the input function) 
       to select a column number for each move."""
      player = ['X', 'O']
      nbPlayer = 0
      cur = player[nbPlayer]
      print "Welcome to Connect Four!\n"
      while self.winsFor('X') == False and self.winsFor('O') == False and self.isFull() == False:
        print ""
        print self
        users_col = -1
        while self.allowsMove(users_col) == False:
          users_col = int(input(cur + " choose a column: "))
        self.addMove(users_col, cur)
        nbPlayer = (nbPlayer + 1) % 2
        cur = player[nbPlayer]
      print ""
      if self.winsFor('X') == True:
        print "X wins : Congratulations!"
      elif self.winsFor('O') == True:
        print "O wins : Congratulations!"
      else:
        print "This is a Tie!"
      print ""
      print self

#print(Board(7,6))

##b = Board(7,6)
##b.addMove(0, 'X')
##b.addMove(0, 'O')
##b.addMove(0, 'X')
##b.addMove(3, 'O')
##b.addMove(4, 'O')  # cheating by letting O go again!
##b.addMove(5, 'O')
##b.addMove(6, 'O')
##print b
##b.clear()
##print b
##b.setBoard('012345')
##print b

##b = Board(2,2)
##print b
##b.addMove(0, 'X')
##b.addMove(0, 'O')
##print b
##print(b.allowsMove(-1))
##print(b.allowsMove(0))
##print(b.allowsMove(1))
##print(b.allowsMove(2))

##b = Board(2,2)
##print(b.isFull())
##b.setBoard('0011')
##print(b)
##print(b.isFull())

##b = Board(2,2)
##b.setBoard('0011')
##print b
##b.delMove(1)
##print b
##b.delMove(1)
##print b
##b.delMove(1)
##print b
##b.delMove(0)
##print b

##b = Board(7,6)
##b.setBoard( '00102030' )
##print b
##print b.winsFor('X')
##print b.winsFor('O')
##b = Board(7,6)
##b.setBoard( '00102030' )
##print b
##print b.winsFor('X')
##print b.winsFor('O')
##b = Board(7,6)
##b.setBoard( '23344545515' )
##print b
##print b.winsFor('X')  # diagonal
##print b.winsFor('O')

#b = Board(7,6)
#b.hostGame()

