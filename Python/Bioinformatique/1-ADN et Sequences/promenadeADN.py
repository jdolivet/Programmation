# -*- coding: utf-8 -*-
"""
Promenade le long de l'ADN
"""


import matplotlib.pyplot as pyplot


def path_x_y(adn):
    """
    algorithme qui calcule les deux chemins en x et y
    en partant et en se deplaçant le long de la chaine
    """
    # dictionnaire pour associer un deplacement a chaque nucleotide
    moves = {'C': [1, 0], 'A': [0, 1], 'G': [-1, 0], 'T': [0, -1]}
    # initialise les résultats
    path_x, path_y = [], []
    # on commence au centre
    x, y = 0, 0
    # le point de départ fait partie du chemin
    path_x.append(x)
    path_y.append(y)

    # pour tout l'ADN
    for nucleotide in adn:
        # quel deplacement faut-il faire
        delta_x, delta_y = moves[nucleotide]
        # on l'applique
        x += delta_x
        y += delta_y
        # on range le point courant
        # dans les listes resultat
        path_x.append(x)
        path_y.append(y)
    return path_x, path_y


def walk(adn):
    print("longueur de la séquence d'entrée : " + str(len(adn)))
    X, Y = path_x_y(adn)
    pyplot.plot(X, Y)
    pyplot.show()


exemple = open("Borrelia.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

walk(adn)
