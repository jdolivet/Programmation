# python 2
#
# Homework 11
# Name: Johann
#
import random

class Board:
    """ a datatype representing a C4 board
        with an arbitrary number of rows and cols
    """
    
    def __init__(self, width, height):
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
              
    def setBoard(self, moveString):
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
      
    def playGame(self, px, po):
      """ calls on the nextMove method in px and po, 
      which will be objects of type Player in order to play a game.
      handle the case in which either px or po is the string 'human' 
      instead of an object of type Player"""
      player = [px, po]
      nbPlayer = 1
      curStr = 'O'
      print "Welcome to Connect Four!\n"
      while self.winsFor(curStr) == False and self.isFull() == False:
        nbPlayer = (nbPlayer + 1) % 2
        cur = player[nbPlayer]
        if cur == 'human':
          if nbPlayer == 0:
            curStr = 'X'
          else:
            curStr = 'O'
        else:
          curStr = cur.ox
        print ""
        print self
        if cur == 'human':
          users_col = -1
          while self.allowsMove(users_col) == False:
            users_col = int(input(curStr + " (the user) choose a column: "))
          self.addMove(users_col, curStr)
        else:
          users_col = cur.nextMove(self)
          self.addMove(users_col, curStr)
          print curStr, "(the computer) played :", users_col
      print ""
      if self.winsFor(curStr) == True:
        print curStr + " wins : Congratulations!"
      else:
        print "This is a Tie!"
      print ""
      print self


class Player:
    """ an AI player for Connect Four """
    
    def __init__( self, ox, tbt, ply ):
        """ the constructor """
        self.ox = ox
        self.tbt = tbt
        self.ply = ply
        
    def __repr__( self ):
        """ creates an appropriate string """
        s = "Player for " + self.ox + "\n"
        s += "  with tiebreak type: " + self.tbt + "\n"
        s += "  and ply == " + str(self.ply) + "\n\n"
        return s
        
    def oppCh(self):
      """return the other kind of checker or playing piece"""
      if self.ox == 'X':
        return 'O'
      else:
        return 'X'
        
    def scoreBoard(self, b):
      """return a single float value representing the score of the input b, 
      which you may assume will be an object of type Board"""
      if b.winsFor(self.ox) == True:
        return 100.0
      elif b.winsFor(self.oppCh()) == True:
        return 0.0
      else:
        return 50.0
        
    def tiebreakMove(self, scores):
      """takes in scores : a nonempty list of floating-point numbers. 
      If there is only one highest score in that scores list, 
      this method should return its column number.
      If there is more than one highest score because of a tie, 
      this method should return the column number of the highest score 
      appropriate to the player's tiebreaking type."""
      maxi = max(scores)
      if scores.count(max) == 1:
        return scores.index(max)
      else:
        results = []
        for i in range(len(scores)):
          if scores[i] == maxi:
            results += [i]
        if self.tbt == 'LEFT':
          return results[0]
        elif self.tbt == 'RIGHT':
          return results[-1]
        else:
          return random.choice(results)
          
    def scoresFor(self, b):
      """return a list of scores, with the cth score 
      representing the "goodness" of the input board 
      after the player moves to column c. 
      And, "goodness" is measured by 
      what happens in the game after self.ply moves."""
      scores = [50]*b.width
      for col in range(b.width):
        #if col is full
        if b.allowsMove(col) == False:
          scores[col] = -1.0
        #if player already wins
        elif b.winsFor(self.ox) == True:
          scores[col] = 100.0
        #if opponent already wins
        elif b.winsFor(self.oppCh()) == True:
          scores[col] = 0.0
        #if no more anticipation
        elif self.ply == 0:
          scores[col] = 50.0
        #anticipation
        else:
          b.addMove(col, self.ox)
          newPlayer = Player(self.oppCh(), self.tbt, self.ply - 1)
          scoresOpp = newPlayer.scoresFor(b)
          scores[col] = 100.0 - max(scoresOpp)
          b.delMove(col)
      return scores
      
    def nextMove(self, b):
      """returns an integer-namely, the column number 
      that the calling object (of class Player) chooses to move to"""
      return self.tiebreakMove(self.scoresFor(b))

#px = Player('X', 'LEFT', 1)
#po = Player('O', 'LEFT', 1)
#b = Board(7,6)
#b.playGame(px, 'human')
