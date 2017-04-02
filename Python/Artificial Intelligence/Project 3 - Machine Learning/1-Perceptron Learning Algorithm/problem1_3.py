# -*- coding: utf-8 -*-
"""
Created on Mon Feb 27 13:21:57 2017

@author: Johann Dolivet

Algorithm of classification
    Perceptron
"""

import sys
#import matplotlib.pyplot as plt


#==============================================================================
# Récupération des données depuis un fichier
#==============================================================================

def recupSet(file):
    fichierManip = open(file, 'r')
    lignes = fichierManip.readlines()
    fichierManip.close()
    datas = []
#    print("Datas : \n")
    for ligne in lignes:               
        stringData = ligne[:-1]
        stringData = stringData.split(',')
        data = ((int(stringData[0]), int(stringData[1])), int(stringData[2]))
        datas.append(data)
#    for data in datas:
#        print(data)
#    print('\n')
    return tuple(datas)
    
#==============================================================================
# Algorithme de Perceptron
#==============================================================================

def perceptron(file, datas):    
    fichierResult = open(file, 'w')
    bNew, w1New, w2New = 0, 0, 0 
    convergence = False
#    i = 1   # just to count the iterations
    while convergence == False: 
        b, w1, w2 = bNew, w1New, w2New   
#        print("\niteration n°", i)
        x1List, x2List = [], []
        for data in datas:
            x = data[0]
            y = data[1]            
            x1, x2 = x[0], x[1]
#            # plot the points
#            x1List.append(x1)
#            x2List.append(x2)
#            if y == 1:
#                plt.plot(x1, x2, 'or')
#            else:
#                plt.plot(x1, x2, 'ob')
                
            if (b + w1 * x1 + w2 * x2 > 0):
                f = 1
            else:
                f = -1
            if f * y < 0:                
                bNew = b + y * 1               
                w1New = w1 + y * x1
                w2New = w2 + y * x2 
#        # plot the line
#        print("Equation of the line :", w1New, "x +", w2New, "y +", bNew, "= 0")
#        x1Min, x1Max = min(x1List), max(x1List)
#        x2Min, x2Max = min(x2List), max(x2List)
#        if w2New != 0:
#            plt.plot([x1Min, x1Max], 
#                     [((-w1New * x1Min - bNew) / w2New), 
#                      ((-w1New * x1Max - bNew) / w2New)], 'g-')
#        else:
#            plt.plot([(-bNew / w1New), (-bNew / w1New)], 
#                     [x2Min, x2Max], 'g-')
#        plt.show()
        # test if it's done
        if bNew == b and w1New == w1 and w2New == w2:
            convergence = True
        fichierResult.write(str(w1New) + ',' + str(w2New) + ',' + str(bNew) + '\n')
#        i += 1
    fichierResult.close()
            
## Tests
#exemple = recupSet("input1.csv")
#perceptron("output.csv", exemple)

#==============================================================================
# Utilitaires pour exploiter le programme
#==============================================================================

def main():
    inputFile = sys.argv[1]
    exemple = recupSet(inputFile)    
    outputFile = sys.argv[2]
    perceptron(outputFile, exemple)  
    

if (__name__ == '__main__'):
    main()