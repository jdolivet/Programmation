# -*- coding: utf-8 -*-
"""
Created on Mon Mar 27 18:51:59 2017

@author: Johann Dolivet

Natural Language Processing : Sentiment analysis

The goal is to predict if a critic for a movie is positive or negative.

Datas :
    25000 critics (text with label 1 if positive and 0 if negative)
        are used to train the model.
    25000 critics (text without labels) are used to test the model.

Model :
    The Stochastic Gradient Descent Classifier is used.
    
Libraries : pandas to use dataframes, read csv, transform in csv
            sklearn to transfrom in vectors and to use SGD Classifier
"""
import os
import re
import pandas as pd
from sklearn.linear_model import SGDClassifier
from sklearn.feature_extraction.text import TfidfVectorizer

# paths for the datas
train_path = "public/aclImdb/train/"    # files are under this directory
test_path = "public/imdb_te.csv"        # test data for grade evaluation

# parameter for the fitting :
MinDf = 2       # discard words appearing in less than MinDf documents
MaxDf = 0.9     # discard words appearing in more than MaxDf of the documents

#==============================================================================
# Process the datas
#   convert the datas in a csv (in lowercase without stopwords)
#==============================================================================

def stop_words(fileName):
    result = []
    fichierManip = open(fileName, 'r')
    for line in fichierManip:
        result.append(line[:-1])
    fichierManip.close()
    return result

def imdb_data_preprocess(inpath, name="imdb_tr.csv"):
    chemin = inpath
    stopList = stop_words("stopwords.en.txt")
    negatif = os.path.join(chemin,"neg")
    positif = os.path.join(chemin,"pos")
    signe = [positif, negatif]
    pattern = re.compile('[^a-z]')
    listText, listPol = [],[]
    for elt in signe:
        listeFile = os.listdir(elt)  
        for file in listeFile:    
            sample = open(elt+'/'+file,'r')
            for line in sample:
                line = line.replace('<br /><br />', ' ').lower()
                liste = pattern.split(line)
                liste = [elt for elt in liste if (elt not in stopList and len(elt) > 1)]
                line = ' '.join(liste)
                listText.append(line)
            if elt == positif:
                listPol.append(1)
            else:
                listPol.append(0)
            sample.close()
    df = pd.DataFrame({'text' : listText, 'polarity' : listPol})
    df = df[['text','polarity']]
    df.to_csv(name)    
#imdb_data_preprocess(train_path)
    
def imdb_data_preprocessTest(inpath, name="imdb_te.csv"):
    stopList = stop_words("stopwords.en.txt")
    datas = pd.read_csv(inpath, encoding='latin1')
    listCritiques = []
    pattern = re.compile('[^a-z]')
    for idx, row in datas.iterrows():
        texte = row[1].replace('<br /><br />', ' ').lower()    
        liste = pattern.split(texte)
        liste = [elt for elt in liste if (elt not in stopList and len(elt) > 1)]
        line = ' '.join(liste)
        listCritiques.append(line)    
    df = pd.DataFrame({'text' : listCritiques})   
    df.to_csv(name)
#imdb_data_preprocessTest(test_path)
   
#==============================================================================
# Unigram method
#   input : dataframes for train and test
#       training :  vector for X (n samples of list of words)
#                   vector for y (labels)
#       testing :   vector for X (n samples of list of words)
#   write the result (prediction for testing dataframe) in a csv file
#==============================================================================

def unigram(dataTrain, dataTest):
    vectorizer = TfidfVectorizer(min_df=MinDf, max_df = MaxDf)
    train = dataTrain['text']
    test = dataTest['text']
    label = dataTrain['polarity']
    trainX = vectorizer.fit_transform(train)
    testX = vectorizer.transform(test)   
    clf = SGDClassifier(loss="hinge", penalty="l1")
    clf.fit(trainX, label)
    result = clf.predict(testX)
    fichier = open("unigram.output.txt", 'w')
    for elt in result:
        fichier.write(str(elt) + '\n')
    fichier.close()
#dataTrain = pd.read_csv(trainFile, encoding='latin1')
#dataTest = pd.read_csv(testFile, encoding='latin1')
#unigram(dataTrain, dataTest)
   
#==============================================================================
# Bigram method
#   input : dataframes for train and test
#       training :  vector for X (n samples of list of words)
#                   vector for y (labels)
#       testing :   vector for X (n samples of list of words)
#   write the result (prediction for testing dataframe) in a csv file
#==============================================================================
   
def bigram(dataTrain, dataTest):
    vectorizer = TfidfVectorizer(min_df=MinDf, max_df = MaxDf, ngram_range=(1,2))
    train = dataTrain['text']
    test = dataTest['text']
    label = dataTrain['polarity']
    trainX = vectorizer.fit_transform(train)
    testX = vectorizer.transform(test)
    clf = SGDClassifier(loss="hinge", penalty="l1")
    clf.fit(trainX, label)
    result = clf.predict(testX)
    fichier = open("bigram.output.txt", 'w')
    for elt in result:
        fichier.write(str(elt) + '\n')
    fichier.close()
#dataTrain = pd.read_csv(trainFile, encoding='latin1')
#dataTest = pd.read_csv(testFile, encoding='latin1')    
#bigram(dataTrain, dataTest)
    
#==============================================================================
# Unigram with tf-idf method
#   input : dataframes for train and test
#       training :  vector for X (n samples of list of words)
#                   vector for y (labels)
#       testing :   vector for X (n samples of list of words)
#   write the result (prediction for testing dataframe) in a csv file
#==============================================================================

def unigramtfidf(dataTrain, dataTest):
    vectorizer = TfidfVectorizer(min_df=MinDf, max_df = MaxDf, sublinear_tf=True, use_idf=True)
    train = dataTrain['text']
    test = dataTest['text']
    label = dataTrain['polarity']
    trainX = vectorizer.fit_transform(train)
    testX = vectorizer.transform(test)   
    clf = SGDClassifier(loss="hinge", penalty="l1")
    clf.fit(trainX, label)
    result = clf.predict(testX)
    fichier = open("unigramtfidf.output.txt", 'w')
    for elt in result:
        fichier.write(str(elt) + '\n')
    fichier.close()
#dataTrain = pd.read_csv(trainFile, encoding='latin1')
#dataTest = pd.read_csv(testFile, encoding='latin1')
#unigramtfidf(dataTrain, dataTest)
   
#==============================================================================
# Bigram with tf-idf method
#   input : dataframes for train and test
#       training :  vector for X (n samples of list of words)
#                   vector for y (labels)
#       testing :   vector for X (n samples of list of words)
#   write the result (prediction for testing dataframe) in a csv file
#==============================================================================
   
def bigramtfidf(dataTrain, dataTest):
    vectorizer = TfidfVectorizer(min_df=MinDf, max_df = MaxDf, ngram_range=(1,2), sublinear_tf=True, use_idf=True)
    train = dataTrain['text']
    test = dataTest['text']
    label = dataTrain['polarity']
    trainX = vectorizer.fit_transform(train)
    testX = vectorizer.transform(test)
    clf = SGDClassifier(loss="hinge", penalty="l1")
    clf.fit(trainX, label)
    result = clf.predict(testX)
    fichier = open("bigramtfidf.output.txt", 'w')
    for elt in result:
        fichier.write(str(elt) + '\n')
    fichier.close()
#dataTrain = pd.read_csv(trainFile, encoding='latin1')
#dataTest = pd.read_csv(testFile, encoding='latin1')
#bigramtfidf(dataTrain, dataTest)

#==============================================================================
# The main function to compile everything
#==============================================================================

def main():
#    # construct the imdb_tr.csv
#    imdb_data_preprocess(train_path)
#    imdb_data_preprocessTest(test_path)
    dataTrain = pd.read_csv('imdb_tr.csv', encoding='latin1')
    dataTest = pd.read_csv('imdb_te.csv', encoding='latin1')
    unigram(dataTrain, dataTest)    
    bigram(dataTrain, dataTest)    
    unigramtfidf(dataTrain, dataTest)    
    bigramtfidf(dataTrain, dataTest)
    
    
if (__name__ == '__main__'):
    main()
