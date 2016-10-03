# -*- coding: utf-8 -*-
"""
Fenêtres glissantes et recouvrantes
"""


import matplotlib.pyplot as pyplot


def compter_c_g(adn, debut, fin):
    # les valeurs de retour
    c = g = 0
    # on ne fait le parcours que sur un morceau
    for nucleo in adn[debut:fin]:
        if nucleo == 'C':
            c += 1
        elif nucleo == 'G':
            g += 1
    # on retourne les deux résultats
    return c, g


def fenetre_x_y(adn, fenetre, recouvrement):
    """
   en entrée
      adn:          le brin d'ADN
      fenetre:      la largeur de la fenêtre
      recouvrement: de combien se recouvrent deux fenêtres consécutives
   en sortie
      X:            liste des abscisses - les multiples de (fenetre-recouvrement)
      Y:            liste des ordonnees - les valeurs de (G-C)/(G+C)
    """
    # la longueur de l'adn - pour ne pas avoir
    # à la recalculer à chaque boucle
    longueur = len(adn)
    # le début de la fenêtre
    debut = 0
    # les deux listes de résultats
    X = []
    Y = []
    while debut < longueur:
        # avec le slicing ce n'est pas grave si on déborde à droite
        c, g = compter_c_g (adn, debut, debut + fenetre)
        # dans tous les cas, en abscisse on prend debut
        x = debut
        # le cas pathologique où on n'a aucun C ou G
        if c == 0 and g == 0:
            y = 0.
        else:
            y = (g - c) / (g + c)
        # on range ce point dans les résultats
        X.append(x)
        Y.append(y)
        # on n'oublie pas de décaler la fenêtre 
        debut += (fenetre - recouvrement)
    # c'est fini on peut retourner le resultat
    return X, Y


def fenetre_glissante(adn, fenetre, recouvrement):
    X, Y = fenetre_x_y(adn, fenetre, recouvrement)
    pyplot.plot(X, Y)
    # on trace un trait correspondant à y=0 sur toute la largeur
    # qui est obtenue comme X[-1], c'est-à-dire le dernier élément de X
    pyplot.plot([0, X[-1]], [0, 0])
    pyplot.show()


exemple = open("Borrelia.txt",'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

fenetre_glissante(adn, 20, 5)
