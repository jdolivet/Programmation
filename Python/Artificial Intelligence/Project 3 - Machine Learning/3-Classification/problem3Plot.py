# -*- coding: utf-8 -*-
"""
Created on Wed Mar  1 22:24:58 2017

@author: Johann Dolivet

Représente les données et les prédictions de classification
pour chaque méthode :
    SVM with Linear Kernel  
    SVM with Polynomial Kernel
    SVM with RBF Kernel 
    Logistic Regression 
    k-Nearest Neighbors 
    Decision Trees 
    Random Forest 
"""

from itertools import product

import numpy as np
import matplotlib.pyplot as plt

from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier

# Loading some example data
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
    return data, target
    
data, target = recupSet("input3.csv")
X = data
y = target

# Training classifiers
clf1 = SVC(kernel='linear', C = 0.1, probability=True)
clf2 = SVC(kernel='poly',C = 0.1, degree = 5, gamma = 1, probability=True)
clf3 = SVC(kernel='rbf',C = 100, gamma = 1, probability=True)
clf4 = LogisticRegression(C = 0.1)
clf5 = KNeighborsClassifier(leaf_size = 5, n_neighbors = 5)
clf6 = DecisionTreeClassifier(max_depth = 6, min_samples_split = 2)
clf7 = RandomForestClassifier(max_depth = 22, min_samples_split = 3)



clf1.fit(X, y)
clf2.fit(X, y)
clf3.fit(X, y)
clf4.fit(X, y)
clf5.fit(X, y)
clf6.fit(X, y)
clf7.fit(X, y)


# Plotting decision regions
x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.1),
                     np.arange(y_min, y_max, 0.1))

f, axarr = plt.subplots(3,3, sharex='col', sharey='row', figsize=(20, 16))

for idx, clf, tt in zip(product([0, 1, 2], [0, 1, 2]),
                        [clf1, clf2, clf3, clf4, clf5, clf6, clf7],
                        ['SVM with Linear Kernel (C=0.1)', 
                        'SVM with Polynomial Kernel (C=0.1, degree=5, gamma = 1)', 
                        'SVM with RBF Kernel (C=100, gamma = 1)',
                        'Logistic Regression (C = 0.1)',
                        'k-Nearest Neighbors (leaf_size = 5, n_neighbors = 5)',
                        'Decision Trees (max_depth = 6, min_samples_split = 2)',
                        'Random Forest (max_depth = 22, min_samples_split = 3)']):

    Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)

    axarr[idx[0], idx[1]].contourf(xx, yy, Z, alpha=0.4)
    axarr[idx[0], idx[1]].scatter(X[:, 0], X[:, 1], c=y, alpha=0.8)
    axarr[idx[0], idx[1]].set_title(tt)

plt.show()