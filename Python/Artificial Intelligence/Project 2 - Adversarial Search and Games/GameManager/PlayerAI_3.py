# -*- coding: utf-8 -*-
"""
Created on Thu Feb  9 18:21:19 2017

@author: Johann Dolivet

Adversarial Search and Games

2048 : http://gabrielecirulli.github.io/2048

Minimax : 
    maximize utility : gradient of the grid
Alpha Beta Pruning
"""

from random import randint
from BaseAI_3 import BaseAI
import time


# Limitations for execution
testDepth = 5
timeLimit = 0.1
allowance = 0.05
 
 
#==============================================================================
# heuristic : snake gradient 
# gradient (must be maximal) : 
#   the tiles should be directed to the right bottom or the right top
#   use weight depending on the position in the matrix
#   max of 2 possibilities : both to the right side
#==============================================================================
 
snake = [[2**3,2**4,2**11,2**12],
         [2**2,2**5,2**10,2**14],
         [2**1,2**6,2**9 ,2**17],
         [2**0,2**7,2**8 ,2**21]] 
 
def heuristic(grid):  
    maxValue = grid.getMaxTile()
    nbFree = len(grid.getAvailableCells())
    liste = grid.map    
    snakeTest1 = 0
    snakeTest2 = 0
    for i in range(grid.size):
        for j in range(grid.size):
            snakeTest1 += liste[i][j] * snake[i][j]
            snakeTest2 += liste[i][j] * snake[3 - i][j]
    evaluation = max(snakeTest1, snakeTest2)
    return evaluation
    
    
#==============================================================================
# Algorithme Minimax avec alpha beta pruning
#==============================================================================
    
def minimize(grid, depth, startTime, alpha, beta):    
    currenTime = time.clock()
    if grid.canMove() == False or testDepth == depth\
       or (currenTime - startTime >=  1.35 * timeLimit):
        utility = heuristic(grid) 
        return (None, utility)
        
    minChild = None
    minUtility = 2 ** 1000
        
    possibleCells = grid.getAvailableCells()
    # on prevoit le futur coup de l'ordinateur (juste pour la valeur 2)
    for move in possibleCells:
        childGrid = grid.clone()
        childGrid.setCellValue(move, 2)
        utility = maximize(childGrid, depth + 1, startTime, alpha, beta)[1]
        if utility < minUtility:
            minUtility = utility
            minChild = childGrid   
        if minUtility <= alpha:
            break
        if minUtility < beta:
            beta = minUtility   
          
    return (minChild, minUtility)
    
def maximize(grid, depth, startTime, alpha, beta):
    currenTime = time.clock()
    if grid.canMove() == False or testDepth == depth\
       or (currenTime - startTime >= 1.35 * timeLimit):
        utility = heuristic(grid)
        return (None, utility)

    maxChild = None
    maxUtility = -2 ** 1000
    
    children = grid.getAvailableMoves()

    for child in children:
        childGrid = grid.clone()
        childGrid.move(child)
        utility = minimize(childGrid, depth + 1, startTime, alpha, beta)[1]
        if utility > maxUtility:
            maxChild = child
            maxUtility = utility  
        if maxUtility >= beta:
            break
        if maxUtility > alpha:
            alpha = maxUtility
    return (maxChild, maxUtility)        
    
def decision(grid, maxDepth):
    startTime = time.clock()
    bestGrid = maximize(grid, 0, startTime, -2**1000, 2**1000)[0]
    return bestGrid   
 
 
#==============================================================================
#  Classe joueur qui invoque l'algo Minimax
#==============================================================================
 
class PlayerAI(BaseAI):    
    
    def getMove(self, grid):
        moves = grid.getAvailableMoves()
        optiGrid = decision(grid, testDepth)
        bestMove = moves.index(optiGrid)
        return moves[bestMove] if moves else None
       
       