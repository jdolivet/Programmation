# -*- coding: utf-8 -*-
"""
Created on Wed Mar 15 09:53:17 2017

@author: Johann Dolivet

Constraint Satisfaction Problems

Implementing the AC-3 and backtracking algorithms to solve Sudoku puzzles.

Board
    
    lines           |       columns         |       regions
0 0 0 0 0 0 0 0 0   |   0 1 2 3 4 5 6 7 8   |    0 0 0 3 3 3 6 6 6 
1 1 1 1 1 1 1 1 1   |   0 1 2 3 4 5 6 7 8   |    0 0 0 3 3 3 6 6 6  
2 2 2 2 2 2 2 2 2   |   0 1 2 3 4 5 6 7 8   |    0 0 0 3 3 3 6 6 6  
3 3 3 3 3 3 3 3 3   |   0 1 2 3 4 5 6 7 8   |    1 1 1 4 4 4 7 7 7  
4 4 4 4 4 4 4 4 4   |   0 1 2 3 4 5 6 7 8   |    1 1 1 4 4 4 7 7 7  
5 5 5 5 5 5 5 5 5   |   0 1 2 3 4 5 6 7 8   |    1 1 1 4 4 4 7 7 7  
6 6 6 6 6 6 6 6 6   |   0 1 2 3 4 5 6 7 8   |    2 2 2 5 5 5 8 8 8  
7 7 7 7 7 7 7 7 7   |   0 1 2 3 4 5 6 7 8   |    2 2 2 5 5 5 8 8 8  
8 8 8 8 8 8 8 8 8   |   0 1 2 3 4 5 6 7 8   |    2 2 2 5 5 5 8 8 8  

Variable : (i,j ) position on the board
    Less than 81 : all the empty cells
        on the line i
        on the column j
        on the region (i//3)+3*(j//3)
Domain : list of values [1,2,3,4,5,6,7,8,9]
Constraints : 
    Each line must have different values
    Each column must have different values
    Each region must have different values
"""

import sys


#==============================================================================
# Take the string and prepare the board and the variables with domains
#   datas : dict {(i, j) : []} 
#       keys : variables        (the empty cells)
#       value : list of values  (the possible values)
#==============================================================================

def prepareDatas(sudokuString):
    sudokuLignes = [[0 for i in range(9)] for j in range(9)]
    sudokuColonnes = [[0 for i in range(9)] for j in range(9)]
    sudokuRegions = [[] for j in range(9)]
    sudoku = []
    variables = []
    taille = len(sudokuString)
    for k in range(taille):
        sudoku.append(int(sudokuString[k]))
        i = k // 9
        j = k % 9
        sudokuLignes[i][j] = int(sudokuString[k])   
        if sudokuLignes[i][j] == 0:
            variables.append((i,j))
        sudokuColonnes[j][i] = int(sudokuString[k]) 
        ii = i // 3
        jj = j // 3
        sudokuRegions[ii+3*jj].append(int(sudokuString[k])) 
    taille = len(sudoku)
    for i in range(taille):
        if i % 9 == 0:
            print()
        print(sudoku[i], end=' ')
    print('\n')  
    datas = {}
    for var in variables:
        variable = var
        domaine = []
        for i in range(1, 10):
            ligne, colonne = variable
            region = (ligne // 3) + 3 * (colonne // 3)
            if (i not in sudokuLignes[ligne]) and \
                (i not in sudokuColonnes[colonne]) and \
                (i not in sudokuRegions[region]):
                domaine.append(i)
        datas[variable] = domaine
    return datas, sudoku
  
#==============================================================================
#  Preprocessing of the datas : find arc and check consistancy : AC-3
#==============================================================================

def prepareArcs(datas):
    listArcs = []
    for var1 in datas:
        for var2 in datas:
            if var1 != var2:
                if (var1[0] == var2[0]) or (var1[1] == var2[1]) or \
                    ((var1[0]//3)+3*(var1[1]//3) == (var2[0]//3)+3*(var2[1]//3)):
                    listArcs.append((var1, var2))
    return listArcs


def AC3(dataVars, datasArcs):
    datas = dataVars
    queue = datasArcs[:]
    while(len(queue) > 0):
        arc = queue.pop(0)
        var1 = arc[0]
        var2 = arc[1]
        if revise(datas, var1, var2):
            domaine1 = datas[var1]
            if len(domaine1) == 0:
                return False, {}
            for arcs in datasArcs:
                if arcs[0] == var1:
                    if arcs[1] != var2:
                        queue.append((arcs[1], arcs[0]))
    return True, datas
            
def revise(dataVars, var1, var2):
    revised = False
    domaine1 = dataVars[var1]
    domaine2 = dataVars[var2]
    for value1 in domaine1:
        # constraint : no value in damaine 2 that allow to satisfy value1!=value2
        # the only way is if there is just the value 1 in domaine2!
        if (len(domaine2) == 1) and (value1 == domaine2[0]):
            domaine1.remove(value1)
            revised = True
    return revised
    
#==============================================================================
#   Useful methods for the search : 
#       sort the datas by values
#       return the position with less values : Minimum Remaining Values
#       check if the move is correct
#       check if a sudoku is solved : just to check the sol at the end
#==============================================================================

def sortDatas(datas):
    # sort the keys of the datas and return the sorted list of keys
    usefulVars = list(datas.keys())
    taille = len(usefulVars)
    for i in range(1, taille):
        j = i
        while j > 0 and len(datas[usefulVars[j]]) < len(datas[usefulVars[j-1]]):
            usefulVars[j], usefulVars[j-1] = usefulVars[j-1], usefulVars[j]
            j -= 1
    return usefulVars
    
def MRV(assignment, dataKeys):
    # return the next with fewer values
    for var in dataKeys:
        if var not in assignment.keys():
            return var
    return False
    
def isConsistantListe(liste):
    newList = []
    for elt in liste:
        if elt != 0:
            newList.append(elt)
    if len(newList) == len(set(newList)):
        return True
    else:
        return False
    
def isConsistant(sudoku, assignment, variable, value):    
    sudokuTest = sudoku[:]
    for position in assignment.keys():
        ligne, colonne = position
        valeur = assignment[position]
        sudokuTest[9 * ligne + colonne] = valeur    
    ligne = variable[0]
    colonne = variable[1]
    sudokuTest[9 * ligne + colonne] = value        
    sudokuLignes = [[0 for i in range(9)] for j in range(9)]
    sudokuColonnes = [[0 for i in range(9)] for j in range(9)]
    sudokuRegions = [[] for j in range(9)]
    taille = len(sudokuTest)
    for k in range(taille):
        i = k // 9
        j = k % 9
        sudokuLignes[i][j] = sudokuTest[k]   
        sudokuColonnes[j][i] = sudokuTest[k]
        ii = i // 3
        jj = j // 3
        sudokuRegions[ii+3*jj].append(sudokuTest[k]) 
    for ligne in sudokuLignes:
        if not isConsistantListe(ligne):
            return False
    for colonne in sudokuColonnes:
        if not isConsistantListe(colonne):
            return False
    for region in sudokuRegions:
        if not isConsistantListe(region):
            return False
    return True

def isSolved(sudoku, assignment):    
    sudokuTest = sudoku[:]
    for position in assignment.keys():
        ligne, colonne = position
        valeur = assignment[position]
        sudokuTest[9 * ligne + colonne] = valeur        
    testSet = set([1,2,3,4,5,6,7,8,9])
    sudokuLignes = [[0 for i in range(9)] for j in range(9)]
    sudokuColonnes = [[0 for i in range(9)] for j in range(9)]
    sudokuRegions = [[] for j in range(9)]
    taille = len(sudokuTest)
    for k in range(taille):
        i = k // 9
        j = k % 9
        sudokuLignes[i][j] = sudokuTest[k]   
        sudokuColonnes[j][i] = sudokuTest[k]
        ii = i // 3
        jj = j // 3
        sudokuRegions[ii+3*jj].append(sudokuTest[k]) 
    for ligne in sudokuLignes:
        if testSet.intersection(set(ligne)) != testSet:
            return False
    for colonne in sudokuColonnes:
        if testSet.intersection(set(colonne)) != testSet:
            return False
    for region in sudokuRegions:
        if testSet.intersection(set(colonne)) != testSet:
            return False 
    return True
   
#==============================================================================
# Forward checking : inference methods : useful to know if a move is possible
#   checking the consequences of the move : all the values that we'll be define
#==============================================================================

def forwardChecking(sudoku, assignment, datas, variable, value):
    # copy the assignment to check if it's possible or not
    assignmentCheck = {}
    for var in assignment.keys():
        assignmentCheck[var] = assignment[var]
    inference = {}
    # copy the datas we need like this we can modify
    newDatas = {}
    for var in datas.keys():
        if var not in assignment.keys():
            # if already done move to inference
            if len(datas[var]) == 1:
                inference[var] = datas[var][0]
            else:
                newDatas[var] = datas[var][:]
    candidates = [(variable, value)]
    while len(candidates) > 0:
        currentVar = candidates.pop()
        position, valeur = currentVar
        # take the neighbors of the var
        neighbors = []
        for var in newDatas.keys():
            if (var[0] == position[0]) or (var[1] == position[1]) or \
                ((var[0]//3)+3*(var[1]//3) == (position[0]//3)+3*(position[1]//3)):
                neighbors.append(var)
        # remove the valeur in neighborhood
        for neighbor in neighbors:
            if valeur in newDatas[neighbor]:
                newDatas[neighbor].remove(valeur)
            # check if it's done
            if len(newDatas[neighbor]) == 1:
                newValeur = newDatas[neighbor][0]
                if isConsistant(sudoku, assignmentCheck, neighbor, newValeur):
                    inference[neighbor] = newValeur
                    assignmentCheck[neighbor] = newValeur
                else:
                    return False
                candidates.append((neighbor, newValeur))
                del newDatas[neighbor]
    return inference
               
#==============================================================================
# Search of the solution : this is a Depth First Search
#==============================================================================

def backtrackingSearch(sudoku, datas):
    dataKeys = sortDatas(datas)
    return backtrack({}, sudoku, datas, dataKeys)

def backtrack(assignment, sudoku, datas, dataKeys):
    if len(assignment) == len(datas):
        return assignment
    variable = MRV(assignment, dataKeys)
    values = datas[variable]
    for value in values:
        if isConsistant(sudoku, assignment, variable, value):
            assignment[variable] = value
            # check the consequences of the value
            inferences = forwardChecking(sudoku, assignment, datas, variable, value)
            if inferences is not False:
                for var in inferences.keys():
                    assignment[var] = inferences[var]
                # if good we're going deeper
                result = backtrack(assignment, sudoku, datas, dataKeys)
                if result is not False:
                    return result
            # else remove the move
            del assignment[variable]
            if inferences is not False:
                for var in inferences.keys():
                    del assignment[var]
    return False
    
#==============================================================================
# Solver of the sudoku
#==============================================================================
    
def solver(inputString):
    firstDatas, sudoku = prepareDatas(inputString)
    arcs = prepareArcs(firstDatas)
    if AC3(firstDatas, arcs)[0]:
        datas = AC3(firstDatas, arcs)[1]
    solution = backtrackingSearch(sudoku, datas)
    if solution is not  False:
        # Check if the solution is good
        if isSolved(sudoku, solution):
            print("Sudoku solved!")
        else:
            print("Wrong solution, we have a problem!!")
    else:
        print("Sudoku unsolved, we have a big problem!")
    # Conctruct the grid
    for position in solution.keys():
        ligne = position[0]
        colonne = position[1]
        valeur = solution[position]
        sudoku[9 * ligne + colonne] = valeur
    return sudoku

#==============================================================================
# Tests
#==============================================================================

## Basic tests
#inputString1 = "003020600900305001001806400008102900700000008006708200002609500800203009005010300"
#inputString2 = "000260701680070090190004500820100040004602900050003028009300074040050036703018000"
#inputString3 = "000100702030950000001002003590000301020000070703000098800200100000085060605009000"
#inputString4 = "094000130000000000000076002080010000032000000000200060000050400000008007006304008"
#inputString5 = "090060000004000006003000942000200000086000200007081694700008000009510000050000073"
#solution = solver(inputString5)   
#taille = len(solution)
#for i in range(taille):
#    if i % 9 == 0:
#        print()
#    print(str(solution[i]), end=' ')
#print('\n\n')  


# Big tests
fichierIn = open("sudokus_start.txt", 'r')
j = 1
for line in fichierIn:
    inputString = line[:-1]
    print("Sudoku n°" + str(j))    
    solution = solver(inputString)
    print("Solution : ")     
    taille = len(solution)
    for i in range(taille):
        if i % 9 == 0:
            print()
        print(str(solution[i]), end=' ')
    print('\n\n')  
    j += 1
fichierIn.close()

#==============================================================================
# Utilitaires pour exploiter le programme
#==============================================================================

#def main():
#    inputString = sys.argv[1]
#    solution = solver(inputString)    
#    fichierManip = open("output.txt", 'w')
#    for elt in solution:
#        fichierManip.write(str(elt))
#    fichierManip.close()
#    
#    
#if (__name__ == '__main__'):
#    main()
