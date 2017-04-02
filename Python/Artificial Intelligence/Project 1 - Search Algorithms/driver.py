# -*- coding: utf-8 -*-
"""
Created on Thu Jan 26 10:06:43 2017

@author: Johann Dolivet

Résolution de puzzle.
    http://mypuzzle.org/sliding
Différentes méthodes d'exploration d'arbre.
    BFS : Breadth-First Search
    DFS : Depth-First Search
    AST : A-Star Search
    IDA : IDA-Star Search
Les noeuds sont les états des puzzle (ce sont des tuples)
Les arêtes sont les déplacements de la case vide, dans cet ordre :
    Up, Down, Left, Right
On part d'un état initial et on s'arrête lorsque l'on est à l'état final 
    Etat final : puzzle dans l'ordre (donc tuple trié!)
"""

import sys
import math
import time
import resource


#==============================================================================
# Utilitaires
#==============================================================================

listMove = ['Up', 'Down', 'Left', 'Right']


def sizeBoard(board):   # taille du puzzle
    return int(math.sqrt(len(board)))


def neighbors(board):   # voisinage jouable pour un puzzle UDLR
    listBoard = list(board)
    listNeighbors = []
    size = sizeBoard(listBoard)
    position = listBoard.index(0)
    col = position % size
    ligne = position // size
    if (ligne > 0):
        newBoard = listBoard[:]
        temp = newBoard[position]
        newBoard[position] = newBoard[position - size]
        newBoard[position - size] = temp
        listNeighbors.append(tuple(newBoard))
    else:
        listNeighbors.append(())
    if (ligne < size - 1):
        newBoard = listBoard[:]
        temp = newBoard[position]
        newBoard[position] = newBoard[position + size]
        newBoard[position + size] = temp
        listNeighbors.append(tuple(newBoard))
    else:
        listNeighbors.append(())
    if (col > 0):
        newBoard = listBoard[:]
        temp = newBoard[position]
        newBoard[position] = newBoard[position - 1]
        newBoard[position - 1] = temp
        listNeighbors.append(tuple(newBoard))
    else:
        listNeighbors.append(())
    if (col < size - 1):
        newBoard = listBoard[:]
        temp = newBoard[position]
        newBoard[position] = newBoard[position + 1]
        newBoard[position + 1] = temp
        listNeighbors.append(tuple(newBoard))
    else:
        listNeighbors.append(())
    return listNeighbors


def distManhattan(board):   # Total distance of Manhattan
    size = sizeBoard(board)
    longueur = len(board)
    somme = 0
    for i in range(1, longueur):
        # coordonnées si à la bonne place
        colFin = i % size
        ligneFin = i // size
        # coordonnées actuelles
        position = board.index(i)
        col = position % size
        ligne = position // size
        somme += (abs(col - colFin) + abs(ligne - ligneFin))
    return somme


def backtrace(dico, start, end):    # retrouve le chemin
    parent = dico[end][0]
    path = [dico[end][1]]
    while parent != start:
        path.append(dico[parent][1])
        parent = dico[parent][0]
    path.reverse()
    return path


#==============================================================================
# Breadth-First Search
#==============================================================================

def bfs(board):     # Breadth-First Search
    # Paramètres pour le fichier d'export et pour la mesure du temps
    start_time = time.time()
    fichierManip = open("output.txt", 'w')
    # Définition de la cible
    listBoard = list(board)
    finalBoard = listBoard[:]
    finalBoard.sort()
    finalBoard = tuple(finalBoard)
    # La frontière est une queue
    frontier = [(board, 0)]    # traces (puzzle, profondeur)
    # Dictionnaire pour garder la trace des mouvements et reconstruire le chemin
    pathTrack = {}
    pathTrack[board] = ((), 'start')
    # Ensemble Frontière U Visités    
    checkSet = set()
    # Initialisation des paramètres d'exploration
    maxFringeSize = 0
    maxDeepSize = 0
    nodeExplored = 0

    while (len(frontier) != 0):
        # Mise à jour de la taille max de la frontière
        if len(frontier) > maxFringeSize:
            maxFringeSize = len(frontier)
        # Suivie du board et de la profondeur
        currentState = frontier.pop(0)
        currentBoard = currentState[0]
        currentLevel = currentState[1]
        # Si noeud final alors fini
        if (currentBoard == finalBoard):
            path = backtrace(pathTrack, board, finalBoard)
            fichierManip.write('path_to_goal: ' + str(path) + '\n')
            fichierManip.write('cost_of_path: ' + str(len(path)) + '\n')
            fichierManip.write('nodes_expanded: ' + str(nodeExplored) + '\n')
            fichierManip.write('fringe_size: ' + str(len(frontier)) + '\n')
            fichierManip.write('max_fringe_size: ' + str(maxFringeSize) + '\n')
            fichierManip.write('search_depth: ' + str(len(path)) + '\n')
            fichierManip.write('max_search_depth: ' + str(maxDeepSize) + '\n')
            fichierManip.write('running_time: ' + str(round(time.time() - start_time, 8)) + '\n')
            fichierManip.write('max_ram_usage: ' + str(round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8)) + '\n')
#            print('path_to_goal: ', path)
#            print('cost_of_path: ', len(path))
#            print('nodes_expanded: ', nodeExplored)
#            print('fringe_size: ', len(frontier))
#            print('max_fringe_size: ', maxFringeSize)
#            print('search_depth: ', len(path))
#            print('max_search_depth: ', maxDeepSize)
#            print('running_time: ', round(time.time() - start_time, 8))
#            print('max_ram_usage: ', round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8))
            fichierManip.close()
            return True
        # Sinon incrémentation du nb de noeuds visités et ajout à l'ensemble
        nodeExplored += 1
        checkSet.add(currentBoard)
        # Exploration au niveau inférieur
        for i in range(4):      # Les 4 directions possibles
            neighbor = neighbors(currentBoard)[i]
            if (len(neighbor) != 0):
                move = listMove[i]
                if neighbor not in checkSet:
                    frontier.append((neighbor, currentLevel + 1))
                    checkSet.add(neighbor)
                    # On garde trace du parent pour reconstruire le chemin
                    pathTrack[neighbor] = (currentBoard, move)
                    # Mise à jour de le profondeur maximale
                    if currentLevel + 1 > maxDeepSize:
                        maxDeepSize = currentLevel + 1

    fichierManip.close()
    return False


#==============================================================================
# Depth-First Search
#==============================================================================

def dfs(board):     # Depth-First Search
    # Paramètres pour le fichier d'export et pour la mesure du temps
    start_time = time.time()
    fichierManip = open("output.txt", 'w')
    # Définition de la cible
    listBoard = list(board)
    finalBoard = listBoard[:]
    finalBoard.sort()
    finalBoard = tuple(finalBoard)
    # La frontière est une pile
    frontier = [(board, 0)]    # traces (puzzle, profondeur)
    # Dictionnaire pour garder la trace des mouvements et reconstruire le chemin
    pathTrack = {}
    pathTrack[board] = ((), 'start')
    # Ensemble Frontière U Visités    
    checkSet = set()
    # Initialisation des paramètres d'exploration
    maxFringeSize = 0
    maxDeepSize = 0
    nodeExplored = 0

    while (len(frontier) != 0):
        # Mise à jour de la taille max de la frontière
        if len(frontier) > maxFringeSize:
            maxFringeSize = len(frontier)
        # Suivie du board et de la profondeur
        currentState = frontier.pop()
        currentBoard = currentState[0]
        currentLevel = currentState[1]
        # Si noeud final alors fini
        if (currentBoard == finalBoard):
            path = backtrace(pathTrack, board, finalBoard)
            fichierManip.write('path_to_goal: ' + str(path) + '\n')
            fichierManip.write('cost_of_path: ' + str(len(path)) + '\n')
            fichierManip.write('nodes_expanded: ' + str(nodeExplored) + '\n')
            fichierManip.write('fringe_size: ' + str(len(frontier)) + '\n')
            fichierManip.write('max_fringe_size: ' + str(maxFringeSize) + '\n')
            fichierManip.write('search_depth: ' + str(len(path)) + '\n')
            fichierManip.write('max_search_depth: ' + str(maxDeepSize) + '\n')
            fichierManip.write('running_time: ' + str(round(time.time() - start_time, 8)) + '\n')
            fichierManip.write('max_ram_usage: ' + str(round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8)) + '\n')
#            print('path_to_goal: ', path)
#            print('cost_of_path: ', len(path))
#            print('nodes_expanded: ', nodeExplored)
#            print('fringe_size: ', len(frontier))
#            print('max_fringe_size: ', maxFringeSize)
#            print('search_depth: ', len(path))
#            print('max_search_depth: ', maxDeepSize)
#            print('running_time: ', round(time.time() - start_time, 8))
#            print('max_ram_usage: ', round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8))
            fichierManip.close()
            return True
        # Sinon incrémentation du nb de noeuds visités et ajout à l'ensemble
        nodeExplored += 1
        checkSet.add(currentBoard)
        # Exploration au niveau inférieur
        for i in range(3,-1,-1):      # Les 4 directions possibles : on empile à l'envers!!
            neighbor = neighbors(currentBoard)[i]
            if (len(neighbor) != 0):
                move = listMove[i]
                if neighbor not in checkSet:
                    frontier.append((neighbor, currentLevel + 1))
                    checkSet.add(neighbor)
                    # On garde trace du parent pour reconstruire le chemin
                    pathTrack[neighbor] = (currentBoard, move)
                    # Mise à jour de la profondeur maximale
                    if currentLevel + 1 > maxDeepSize:
                        maxDeepSize = currentLevel + 1

    fichierManip.close()
    return False


#==============================================================================
# A-Star Search
#==============================================================================

def ast(board):     # A-Star Search
    # Paramètres pour le fichier d'export et pour la mesure du temps
    start_time = time.time()
    fichierManip = open("output.txt", 'w')
    # Définition de la cible
    listBoard = list(board)
    finalBoard = listBoard[:]
    finalBoard.sort()
    finalBoard = tuple(finalBoard)
    # La frontière est une file prioritaire
    initCost = distManhattan(board)
    frontier = [(board, 0, initCost)]    # traces (puzzle, profondeur, h(n))
    # Dictionnaire pour garder la trace des mouvements et reconstruire le chemin
    pathTrack = {}
    pathTrack[board] = ((), 'start')
    # Ensemble Frontière U Visités
    checkSet = set()
    # Ensemble frontiere
    fringe = set()
    # Initialisation des paramètres d'exploration
    maxFringeSize = 0
    maxDeepSize = 0
    nodeExplored = 0

    while (len(frontier) != 0):
        # Mise à jour de la taille max de la frontière
        if len(frontier) > maxFringeSize:
            maxFringeSize = len(frontier)
        # Suivie du board et de la profondeur
        currentState = frontier.pop(0)
        currentBoard = currentState[0]
        fringe.discard(currentBoard)
        currentLevel = currentState[1]
        # Si noeud final alors fini
        if (currentBoard == finalBoard):
            path = backtrace(pathTrack, board, finalBoard)
            fichierManip.write('path_to_goal: ' + str(path) + '\n')
            fichierManip.write('cost_of_path: ' + str(len(path)) + '\n')
            fichierManip.write('nodes_expanded: ' + str(nodeExplored) + '\n')
            fichierManip.write('fringe_size: ' + str(len(frontier)) + '\n')
            fichierManip.write('max_fringe_size: ' + str(maxFringeSize) + '\n')
            fichierManip.write('search_depth: ' + str(len(path)) + '\n')
            fichierManip.write('max_search_depth: ' + str(maxDeepSize) + '\n')
            fichierManip.write('running_time: ' + str(round(time.time() - start_time, 8)) + '\n')
            fichierManip.write('max_ram_usage: ' + str(round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8)) + '\n')
#            print('path_to_goal: ', path)
#            print('cost_of_path: ', len(path))
#            print('nodes_expanded: ', nodeExplored)
#            print('fringe_size: ', len(frontier))
#            print('max_fringe_size: ', maxFringeSize)
#            print('search_depth: ', len(path))
#            print('max_search_depth: ', maxDeepSize)
#            print('running_time: ', round(time.time() - start_time, 8))
#            print('max_ram_usage: ', round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8))
            fichierManip.close()
            return True
        # Sinon incrémentation du nb de noeuds visités et ajout à l'ensemble
        nodeExplored += 1
        checkSet.add(currentBoard)
        # Exploration au niveau inférieur
        for i in range(4):      # Les 4 directions possibles : on empile à l'envers!!
            neighbor = neighbors(currentBoard)[i]
            if (len(neighbor) != 0):
                move = listMove[i]
                if neighbor not in checkSet:
                    # On place le board à la bonne place dans la file : la liste est donc triée
                    cost = currentLevel + 1 + distManhattan(neighbor)
                    taille = len(frontier)
                    index = 0
                    for i in range(taille):
                        if frontier[i][2] <= cost:
                            index += 1
                        else:
                            break
                    frontier.insert(index, (neighbor, currentLevel + 1, cost))
                    checkSet.add(neighbor)                    
                    fringe.add(neighbor)
                    # On garde trace du parent pour reconstruire le chemin
                    pathTrack[neighbor] = (currentBoard, move)
                    # Mise à jour de le profondeur maximale
                    if currentLevel + 1 > maxDeepSize:
                        maxDeepSize = currentLevel + 1
                else:
                    if neighbor in fringe:      # On actualise la position du puzzle dans la file               
                        cost = currentLevel + 1 + distManhattan(neighbor)
                        taille = len(frontier)
                        # parcours la frontiere pour enlever l'ancienne position
                        # et detecter la position bien ordonnée
                        newIdx = 0
                        for i in range(taille):
                            if frontier[i][0] == neighbor:
                                oldIdx = i
                            if frontier[i][2] <= cost:
                                newIdx += 1
                        # si oldIdx > new idx alors on enleve l'ancien puis on insere le nouveau
                        if oldIdx > newIdx:
                            oldPos = frontier[oldIdx]
                            frontier.remove(oldPos)
                            frontier.insert(newIdx, (neighbor, currentLevel + 1, cost))
                        
    fichierManip.close()
    return False


#==============================================================================
# IDA-Star Search
#==============================================================================

def dls(board, bound):     # Depth Limited Search
    # Définition de la cible
    listBoard = list(board)
    finalBoard = listBoard[:]
    finalBoard.sort()
    finalBoard = tuple(finalBoard)
    # La frontière est une pile
    frontier = [(board, 0)]    # traces (puzzle, profondeur)
    # Dictionnaire pour garder la trace des mouvements et reconstruire le chemin
    pathTrack = {}
    pathTrack[board] = ((), 'start', 0)     # pour remonter le chemin
    # Ensemble Frontière U Visités
    checkSet = set()
    # Initialisation des paramètres d'exploration
    maxFringeSize = 0
    maxDeepSize = 0
    explored = set()
    minCost = 0
    while (len(frontier) != 0):
        # Mise à jour de la taille max de la frontière
        if len(frontier) > maxFringeSize:
            maxFringeSize = len(frontier)
        # Suivie du board et de la profondeur
        currentState = frontier.pop()
        currentBoard = currentState[0]
        currentLevel = currentState[1]        
        # Si noeud final alors fini
        if (currentBoard == finalBoard):
            path = backtrace(pathTrack, board, finalBoard)
            return (True, path, explored, maxFringeSize, maxDeepSize, frontier)
        # Sinon incrémentation du nb de noeuds visités et ajout à l'ensemble
        explored.add(currentBoard)
        checkSet.add(currentBoard)
        # Exploration au niveau inférieur
        for i in range(3,-1,-1):      # Les 4 directions possibles : on empile à l'envers!!
            neighbor = neighbors(currentBoard)[i]
            if (len(neighbor) != 0):
                move = listMove[i]
                cost = currentLevel + 1 + distManhattan(neighbor)
                if (neighbor not in checkSet):
                    if (cost <= bound): # On ajoute à la frontière si < limite
                        frontier.append((neighbor, currentLevel + 1))
                        checkSet.add(neighbor)
                        # On garde trace du parent pour reconstruire le chemin
                        pathTrack[neighbor] = (currentBoard, move, currentLevel + 1)
                        # Mise à jour de le profondeur maximale
                        if currentLevel + 1 > maxDeepSize:
                            maxDeepSize = currentLevel + 1
                    else:   # Sinon on garde le minimum des valeurs qui dépassent
                        if minCost == 0:
                            minCost = cost
                        else:
                            if cost < minCost:
                                minCost = cost
                else:   # on verifie si on est délà passé par ici par un chemin plus court
                    oldLevel = pathTrack[neighbor][2]
                    if currentLevel + 1 < oldLevel:
                        frontier.append((neighbor, currentLevel + 1))
                        pathTrack[neighbor] = (currentBoard, move, currentLevel + 1)
    return (False, minCost, explored, maxFringeSize, maxDeepSize)


def ida(board):     # IDA-Star Search
    # Paramètres pour le fichier d'export et pour la mesure du temps
    start_time = time.time()
    fichierManip = open("output.txt", 'w')
    # Paramètres
    maxFringeSize = 0
    maxDeepSize = 0
    explored = set()
    cost = distManhattan(board)
    found = False
    while found is False:   # On itère tant qu'on n'a pas la solution
        result = dls(board, cost)
        # On teste pour savoir si c'est bon
        found = result[0]
        # Limite pour le prochain DLS
        cost = result[1]
        # On récupère les noeuds explorés
        iterExplored = result[2]
        explored = explored.union(iterExplored)
        # On affine les paramètres
        maxFringeIter = result[3]
        if maxFringeIter > maxFringeSize:
            maxFringeSize = maxFringeIter
        maxDeepIter = result[4]
        if maxDeepIter > maxDeepSize:
            maxDeepSize = maxDeepIter
    # On conclut
    path = result[1]
    nodeExplored = len(explored)
    frontier = result[5]
#    fichierManip.write('path_to_goal: ' + str(path) + '\n')
#    fichierManip.write('cost_of_path: ' + str(len(path)) + '\n')
#    fichierManip.write('nodes_expanded: ' + str(nodeExplored) + '\n')
#    fichierManip.write('fringe_size: ' + str(len(frontier)) + '\n')
#    fichierManip.write('max_fringe_size: ' + str(maxFringeSize) + '\n')
#    fichierManip.write('search_depth: ' + str(len(path)) + '\n')
#    fichierManip.write('max_search_depth: ' + str(maxDeepSize) + '\n')
#    fichierManip.write('running_time: ' + str(round(time.time() - start_time, 8)) + '\n')
#    fichierManip.write('max_ram_usage: ' + str(round(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss / 1000, 8)) + '\n')
    print('path_to_goal: ', path)
    print('cost_of_path: ', len(path))
    print('nodes_expanded: ', nodeExplored)
    print('fringe_size: ', len(frontier))
    print('max_fringe_size: ', maxFringeSize)
    print('search_depth: ', len(path))
    print('max_search_depth: ', maxDeepSize)
    print('running_time: ', round(time.time() - start_time, 8))
    fichierManip.close()


#==============================================================================
# Tests
#==============================================================================

#Tests :   
#print(sizeBoard([1,2,5,3,4,0,6,7,8]))
#print(coordonateEmpty([1,2,5,3,4,0,6,7,8],3))
#print(neighbors((1,2,5,3,4,0,6,7,8))
#print(neighbors((0,2,5,3,4,1,6,7,8))
#bfs((1,2,5,3,4,0,6,7,8))
#bfs((3,1,2,0,4,5,6,7,8))
#dfs((1,2,5,3,4,0,6,7,8))
#dfs((3,1,2,0,4,5,6,7,8))
#ast((1,2,5,3,4,0,6,7,8))
#ast((3,1,2,0,4,5,6,7,8))
#ast((7, 2, 4, 5, 0, 6, 8, 3, 1))
#ida((8,0,6,5,4,7,2,3,1))
#ida((1,5,0,3,2,7,4,6,8))    # should be in 12
#print(bfs([6,1,8,4,0,2,7,3,5]))


#==============================================================================
# Utilitaires pour exploiter le programme
#==============================================================================

def main():
    method = sys.argv[1]
    initialStringBoard = sys.argv[2].split(',')
    initialBoard = []
    for lettre in initialStringBoard:
        initialBoard.append(int(lettre))
    initialBoard = tuple(initialBoard)
    if method == 'bfs':
        bfs(initialBoard)
    elif method == 'dfs':
        dfs(initialBoard)
    elif method == 'ast':
        ast(initialBoard)
    elif method == 'ida':
        ida(initialBoard)
    else:
        fichierManip = open("output.txt", 'w')
        fichierManip.close()


if (__name__ == '__main__'):
    main()
