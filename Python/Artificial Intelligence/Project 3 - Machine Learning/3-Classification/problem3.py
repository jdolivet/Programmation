# -*- coding: utf-8 -*-
"""
Created on Tue Feb 28 22:43:03 2017

@author: Johann Dolivet

Classification en utilisant plusiers méthodes :
    SVM with Linear Kernel  
    SVM with Polynomial Kernel
    SVM with RBF Kernel 
    Logistic Regression 
    k-Nearest Neighbors 
    Decision Trees 
    Random Forest 
"""


import numpy as np
import matplotlib.pyplot as plt
from sklearn import svm
from sklearn.model_selection import cross_val_score, train_test_split, GridSearchCV
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier

#==============================================================================
# Récupération des données depuis un fichier
#==============================================================================

#def recupSet(file):
#    fichierManip = open(file, 'r')
#    lignes = fichierManip.readlines()
#    fichierManip.close()
#    datas = []
#    size = len(lignes)
#    for i in range(1, size):              
#        stringData = lignes[i]    
#        stringData = stringData.split(',')
#        data = [(float(stringData[0]), float(stringData[1])), int(stringData[2])]
#        datas.append(data)
#    return datas
#    
#    

def recupSet(file):
    fichierManip = open(file)
    fichierManip.readline()
    datas = np.loadtxt(fichierManip, delimiter=",")
    fichierManip.close()
     # plot the points
    for data in datas:
        x1 = data[0]
        x2 = data[1]
        y = data[2]            
        if y == 1:
            plt.plot(x1, x2, 'or')
        else:
            plt.plot(x1, x2, 'ob')
    plt.show()
    data = datas[:, 0:2]  # select columns 0 and 1 for datas
    target = datas[:, 2]  # select column 2 to target
    # prepare the sets : Training set : 60% and Testing set : 40%
    data_train, data_test, target_train, target_test = \
            train_test_split(data, target, test_size=0.4, stratify = target)
    return data_train, data_test, target_train, target_test
    
data_train, data_test, target_train, target_test = recupSet("input3.csv")


def SVMLinear(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nSVM with Linear Kernel")
    parameters =  {'C': [0.1, 0.5, 1, 5, 10, 50, 100]}
    svr = svm.SVC(kernel='linear')
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestC = clf1.best_params_['C']
    # then use it to the estimation
    clf = svm.SVC(kernel='linear', C = bestC)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
    
#SVMLinear(data_train, data_test, target_train, target_test)

def SVMPolynomial(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nSVM with Polynomial Kernel")
    parameters =  {'C': [0.1, 1, 3], "degree" : [4, 5, 6], "gamma" : [0.1, 1]}
    svr = svm.SVC(kernel='poly')
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestC = clf1.best_params_['C']
    bestDegree = clf1.best_params_['degree']
    bestGamma = clf1.best_params_['gamma']
    # then use it to the estimation
    clf = svm.SVC(kernel='poly', C = bestC, degree = bestDegree, gamma = bestGamma)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
#SVMPolynomial(data_train, data_test, target_train, target_test)


def SVMRBF(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nSVM with RBF Kernel")
    parameters =  {'C': [0.1, 0.5, 1, 5, 10, 50, 100], "gamma" : [0.1, 0.5, 1, 3, 6, 10]}
    svr = svm.SVC(kernel='rbf')
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestC = clf1.best_params_['C']
    bestGamma = clf1.best_params_['gamma']
    # then use it to the estimation
    clf = svm.SVC(kernel='rbf', C = bestC, gamma = bestGamma)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
#SVMRBF(data_train, data_test, target_train, target_test)


def LogReg(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nLogistic Regression")
    parameters =  {'C':[0.1, 0.5, 1, 5, 10, 50, 100]}
    svr = LogisticRegression()
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestC = clf1.best_params_['C']
    # then use it to the estimation
    clf = LogisticRegression(C = bestC)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
#LogReg(data_train, data_test, target_train, target_test)


def KNN(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nk-Nearest Neighbors")
    p1 = [i for i in range(1, 51)]
    p2 = [i for i in range(5, 61, 5)]
    parameters =  {'n_neighbors' : p1, 'leaf_size' : p2 }
    svr = KNeighborsClassifier()
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestN = clf1.best_params_['n_neighbors']
    bestS = clf1.best_params_['leaf_size']
    # then use it to the estimation
    clf = KNeighborsClassifier(n_neighbors = bestN, leaf_size = bestS)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
#KNN(data_train, data_test, target_train, target_test)


def decisiontTree(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nDecision Trees")
    p1 = [i for i in range(1, 51)]
    p2 = [i for i in range(2, 11)]
    parameters =  {'max_depth' : p1, 'min_samples_split' : p2 }
    svr = DecisionTreeClassifier()
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestD = clf1.best_params_['max_depth']
    bestS = clf1.best_params_['min_samples_split']
    # then use it to the estimation
    clf = DecisionTreeClassifier(max_depth = bestD, min_samples_split = bestS)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
#decisiontTree(data_train, data_test, target_train, target_test)


def randomForest(data_train, data_test, target_train, target_test):
    # first choose the best parameter
    print("\nRandom Forest")
    p1 = [i for i in range(1, 51)]
    p2 = [i for i in range(2, 11)]
    parameters =  {'max_depth' : p1, 'min_samples_split' : p2 }
    svr = RandomForestClassifier()
    clf1 = GridSearchCV(svr, parameters, cv=5, scoring='accuracy')
    clf1.fit(data_train, target_train)
    print(clf1.cv_results_)
    print(clf1.best_params_)
    bestD = clf1.best_params_['max_depth']
    bestS = clf1.best_params_['min_samples_split']
    # then use it to the estimation
    clf = RandomForestClassifier(max_depth = bestD, min_samples_split = bestS)
    print("Training :")
    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
    print(scoreTrain)
    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
    print("Testing :")
    scoreTest = cross_val_score(clf, data_test, target_test, cv = 5)
    print(scoreTest)
    print("Test score: %0.8f (+/- %0.8f)" % (scoreTest.mean(), scoreTest.std() * 2))
   
randomForest(data_train, data_test, target_train, target_test)

#listC = [0.1, 0.5, 1, 5, 10, 50, 100]
#print("Training :")
#for C in listC:
#    print("pour C = ", C)
#    clf = svm.SVC(kernel='linear', C = C)
#    scoreTrain = cross_val_score(clf, data_train, target_train, cv = 5)
#    print("Best score: %0.8f (+/- %0.8f)" % (scoreTrain.mean(), scoreTrain.std() * 2))
