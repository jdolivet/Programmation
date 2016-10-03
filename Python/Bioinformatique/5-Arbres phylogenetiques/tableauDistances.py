# -*- coding: utf-8 -*-
"""
Construction d'un tableau de distance
"""


from algoNeedlemanWunschIter import *


def all_distances(filename):
    """
    Lit le fichier d'entrée, qui doit contenir une séquence ADN par ligne

    Retourne en valeur:
    * une liste des séquences d'entrée
    * un dictionnaire indexé sur les couples d'indices,
    et qui contient la distance associée à ce couple d'entrées
    """
    # on commence par lire le fichier et ranger toutes les entrées dans un seul tableau
    adns = []
    distances = {}
    entry = open(filename, 'r')
    for line in entry:
        adns.append(line.strip())
    entry.close()
    for i, adn1 in enumerate(adns):
        for j in range(i):
            distances[(i, j)] = distance(adns[i], adns[j])
    return adns, distances


def get_distance(d, i, j):
    return 0 if i == j \
        else d[(i, j)] if i > j \
        else d[(j, i)]

# on affiche sur 4 caractères
space = 4 * " "
formatr = "{:4}"
formatl = "{:<4}"


# on affiche un tableau propre
def pretty_distances(filename):
    adns, distances = all_distances(filename)
    for line in adns:
        print(line)
    l = len(adns)
    print("")
    # première ligne
    print(space + "".join([formatr.format(i) for i in range(l)]))
    # pour chaque ligne
    for i in range(l):
        print(formatl.format(i) +
              "".join([formatr.format(get_distance(distances, i, j))
                   for j in range(l)]))

pretty_distances("data_species.txt")
