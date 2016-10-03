# -*- coding: utf-8 -*-
"""
Promenade le long de l'ADN : réglage de la fenêtre
"""


import matplotlib.pyplot as pyplot


def path_x_y_abridged(adn, pas):
    """
    un algorithme qui calcule les deux chemins en x et y
    en se deplaçant le long de la chaine, et en ne retenant
    que les points qui correspondent à un indice multiple de 'pas'
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
    for index, nucleotide in enumerate(adn):
        # quel deplacement faut-il faire
        delta_x, delta_y = moves[nucleotide]
        # on l'applique
        x += delta_x
        y += delta_y
        # on ne le range que si l'indice
        # est un multiple de pas
        if index % pas == 0:
            path_x.append(x)
            path_y.append(y)
    return path_x, path_y


def walk_abridged(adn, pas):
    """
    on appelle cette fois le nouvel algorithme
    et on n'oublie pas de lui passer le pas
    """
    X, Y = path_x_y_abridged(adn, pas)
    # on peut maintenant dessiner
    pyplot.plot(X, Y)
    pyplot.show()


exemple = open("Burgdorferi.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

walk_abridged(adn, 10)
