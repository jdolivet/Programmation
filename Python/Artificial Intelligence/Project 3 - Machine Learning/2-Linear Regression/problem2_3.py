# -*- coding: utf-8 -*-
"""
Created on Mon Feb 27 23:43:25 2017

@author: Johann Dolivet
Algorithm of Linear Regression
    with gradient descent
"""

import sys
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np


#==============================================================================
# Récupération des données depuis un fichier
#==============================================================================

def recupSet(file):
    fichierManip = open(file, 'r')
    lignes = fichierManip.readlines()
    fichierManip.close()
    datas = []
    for ligne in lignes:               
        stringData = ligne[:-1]
        stringData = stringData.split(',')
        data = [float(stringData[0]), float(stringData[1]), float(stringData[2])]
        datas.append(data)
    return datas
 
#==============================================================================
# Traitement des données : normalisation des deux parametres : age and weight
#==============================================================================

def normalize(datas):
    # average of features : age and weight
    sumAge = 0
    sumWeight = 0    
    size = len(datas)    
    for data in datas:
        sumAge += data[0]
        sumWeight += data[1]
    meanAge = sumAge / size
    meanWeight = sumWeight / size
    # standard deviation of features : age and weight
    sumDevAge = 0
    sumDevWeight = 0
    for data in datas:
        sumDevAge += (data[0] - meanAge) ** 2
        sumDevWeight += (data[1] - meanWeight) ** 2
    stdDevAge = (sumDevAge / size) ** 0.5
    stdDevWeight = (sumDevWeight / size) ** 0.5
    # normalize the datas
    for data in datas:
        data[0] = (data[0] - meanAge) / stdDevAge
        data[1] = (data[1] - meanWeight) / stdDevWeight
    #prepare X et Y
    dataX = []
    dataY = []
    for data in datas:
        dataX.append([1, data[0], data[1]])
        dataY.append(data[2])
    return (dataX, dataY)
 
## Test normalize  
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple) 
#print(x)
#print(y)


#==============================================================================
# Algorithme de Regression linéaire
#==============================================================================

def regression(dataX, dataY, alpha, iteration):    
    size = len(dataX)    # nb of datas
    # initialize the betas to 0
    betas = [0, 0, 0]
    # iterate to get better (parameter nbIter on the beginning)
    for j in range(iteration):
#        fig = plt.figure()
#        ax = fig.add_subplot(111, projection='3d')
        risk = 0    
        gradb0 = 0
        gradb1 = 0
        gradb2 = 0
        
#        xList = []
#        yList = []
        for i in range(size):            
            
#            x, y, z = dataX[i][1], dataX[i][2], dataY[i]
#            xList.append(x)
#            yList.append(y)
#            ax.scatter(x, y, z)
            
            risk += (betas[0] + betas[1] * dataX[i][1] + 
                        betas[2] * dataX[i][2] - dataY[i]) ** 2
            gradb0 += (betas[0] + betas[1] * dataX[i][1] + 
                        betas[2] * dataX[i][2] - dataY[i]) 
            gradb1 += (betas[0] + betas[1] * dataX[i][1] + 
                        betas[2] * dataX[i][2] - dataY[i]) * dataX[i][1]
            gradb2 += (betas[0] + betas[1] * dataX[i][1] + 
                        betas[2] * dataX[i][2] - dataY[i]) * dataX[i][2]
        risk = risk / (2 * size)
        betas[0] -= (alpha / size) * gradb0
        betas[1] -= (alpha / size) * gradb1
        betas[2] -= (alpha / size) * gradb2
        
#        print("\nItéreation n°", j + 1, "\nAlpha =  ", alpha, "\nError =", risk, 
#                  "\nSurface : z =", betas[0], "+", betas[1], "x +", betas[2], "y")
#        xMin, xMax = min(xList), max(xList)
#        yMin, yMax = min(yList), max(yList)
#        X, Y = np.arange(xMin, xMax, 0.5), np.arange(yMin, yMax, 0.5)
#        X, Y = np.meshgrid(X, Y)
#        Z = betas[0] + betas[1] * X + betas[2] * Y
#        ax.plot_surface(X, Y, Z, color = 'green', linewidth=0, shade=False)
#        plt.show()
    return (risk, betas)

## Tests regression
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple)  
#regression(x, y, 0.65, 100)


def representation(dataX, dataY, risk, betas):
    size = len(dataX)
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    xList = []
    yList = []
    for i in range(size):
        x, y, z = dataX[i][1], dataX[i][2], dataY[i]
        xList.append(x)
        yList.append(y)
        ax.scatter(x, y, z)
    xMin, xMax = min(xList), max(xList)
    yMin, yMax = min(yList), max(yList)
    X, Y = np.arange(xMin, xMax, 0.5), np.arange(yMin, yMax, 0.5)
    X, Y = np.meshgrid(X, Y)
    Z = betas[0] + betas[1] * X + betas[2] * Y
    ax.plot_surface(X, Y, Z, color = 'green', linewidth = 0, shade = False)
    ax.text2D(0.05, 0.95, "\nAlpha =  " + str(alpha) + "\nError =" + str(risk) +
        "\nSurface : z = " + str(round(betas[0], 5)) + " + " + str(round(betas[1], 5)) + " x + " +
        str(round(betas[2], 5)) + " y", transform=ax.transAxes)
    ax.set_xlabel('Age Normalized')
    ax.set_ylabel('Weight Normalized')
    ax.set_zlabel('Height')
    plt.show()

## Tests representation
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple)  
#alpha = 0.65
#resultats = regression(x, y, alpha, 100)
#representation(x, y, resultats[0], resultats[1])
   
   
# the parameters
alphas = [0.001, 0.005, 0.01, 0.05, 0.1, 0.5, 1, 5, 10]
nbIter = 100
goodAlpha = 0.65
goodIter = 300

def test(dataX, dataY, file):
    fichierResult = open(file, 'w')
    for alpha in alphas:
        #print("\nfor alpha = ", alpha)
        result = regression(dataX, dataY, alpha, nbIter)
        #print("resultats : ", result , '\n')
        fichierResult.write(str(alpha) + ',' + str(nbIter) + ',' + 
                    str(result[1][0]) + ',' + str(result[1][1]) + ',' +
                    str(result[1][2]) + '\n')
    result = regression(dataX, dataY, goodAlpha, goodIter)
    fichierResult.write(str(goodAlpha) + ',' + str(goodIter) + ',' + 
                    str(result[1][0]) + ',' + str(result[1][1]) + ',' +
                    str(result[1][2]) + '\n')
    fichierResult.close()
    
## Tests regression
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple)  
#test(x,y,"output.csv")


# Tests for the values of alpha     
def testAlphas(dataX, dataY):
    for i in range(5, 200, 5):
        print("\nfor alpha = ", i/100)
        result = regression(dataX, dataY, i/100, nbIter)
        print("resultats : ", result , '\n')
## Tests alphas
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple)  
#testAlphas(x, y)
# best choice : alpha = 0.65


#Test for the iterations
def testIter(dataX, dataY):
    for i in range(50, 500, 50):
        print("\nfor nbIter = ", i)
        result = regression(dataX, dataY, goodAlpha, i)
        print("resultats : ", result , '\n')
## Tests iterations
#exemple = recupSet("input2.csv")  
#x, y = normalize(exemple) 
#testIter(x,y)
# best choice : iter = 300


#==============================================================================
# Utilitaires pour exploiter le programme
#==============================================================================

def main():
    inputFile = sys.argv[1]
    exemple = recupSet(inputFile)    
    x, y = normalize(exemple) 
    outputFile = sys.argv[2]
    test(x, y, outputFile) 
    

if (__name__ == '__main__'):
    main()