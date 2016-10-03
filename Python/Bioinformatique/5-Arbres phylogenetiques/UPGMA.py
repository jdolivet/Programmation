# -*- coding: utf-8 -*-
"""
Algorithme UPGMA (Unweighted Pair Group Method with Arithmetic Mean)
"""


from distance import *


def get_distance(distances, sp1, sp2):
    """
    Cherche la distance entre les espèces sp1 et sp2
    dans la table distances
    """
    if (sp1, sp2) in distances:
        return distances[(sp1, sp2)]
    # sinon il doit y être dans l'autre sens
    else:
        return distances[(sp2, sp1)]
    # En principe, si notre algorithme est correct on n'a pas
    # besoin de considérer d'autre cas comme sp1 == sp2


def minimal_couple(distances, species):
    """
    Recherche parmi tous les couples de species x species
    avec sp1 != sp2
    celui qui correspond à la distance minimale dans distances

    Retourne le couple en question - et pas la distance
    car on n'en a pas besoin
    """
    # initialisations
    # le couple résultat
    couple = None
    # la valeur minimale rencontrée jusqu'ici
    minimum = None
    # on balaie tous les couples
    for sp1 in species:
        for sp2 in species:
            # on ne considère que les couples qui sont dans `distances`
            # de cette façon
            # (*) on évite le cas où sp1 == sp2, et
            # (*) on ne traite qu'une fois chaque paire
            if (sp1, sp2) not in distances:
                continue
            # si minimum est None, on traite notre premier couple
            if minimum is None:
                minimum = get_distance(distances, sp1, sp2)
                couple = sp1, sp2
            # sinon, on mémorise ce couple si sa distance est
            # inférieure au minimum courant
            else:
                candidate = get_distance(distances, sp1, sp2)
                if candidate < minimum:
                    minimum = candidate
                    couple = sp1, sp2
    # on n'oublie pas de retourner le résultat
    return couple


def UPGMA(filename, verbose=False):
    """
    Lit un fichier contenant sur chaque ligne
    un nom d'espèce et un ADN

    Le paramètre optionnel verbose permet de déclencher
    des impressions qui illustrent la progression de l'algorithme

    Calcule le tableau des distances,
    puis implémente l'algorithme UPGMA

    Renvoie l'arbre de filiation sous forme d'un tuple
    de tuples / et noms d'espèces
    """
    native_species = {}
    # lire le fichier
    entry = open(filename, 'r')
    for line in entry:
        # on coupe la ligne pour trouver le nom et la séquence
        name, adn = line.split()
        # on mémorise dans native_species
        native_species[name] = adn
    entry.close()
    # on calcule le tableau des distances
    distances = {}
    # essentiellement comme on l'a fait dans la séquence passée
    for sp1, adn1 in native_species.items():
        for sp2, adn2 in native_species.items():
            # on ignore les couples diagonaux
            if sp1 == sp2:
                continue
            # la seule astuce est de regarder si le couple
            # symétrique est déjà présent dans distances
            # auquel cas on ignore aussi
            if (sp2, sp1) in distances:
                continue
            distances[sp1, sp2] = distance(adn1, adn2)
    # la liste des clés de départ
    species = list(native_species.keys())
    # bavardage
    if verbose:
        print(str(10*'+') + ' Initial distances')
        print(distances)
    # c'est ici que se passe la partie intéressante
    # on s'attend à ce que species se réduise jusqu'à ne contenir plus que
    # une unique espèce synthétique qui décrit l'arbre de filiation
    while len(species) > 1:
        # on cherche le couple d'espèces les plus proches
        closer1, closer2 = minimal_couple(distances, species)
        # on peut les enlever de notre radar
        species.remove(closer1)
        species.remove(closer2)
        # et en faire une espèce synthétique
        new_species = closer1, closer2
        # il faut recalculer les distances entre cette nouvelle
        # espèce et les espèces encore en lice
        # cf le transparent :
        # dist(F,C),A = (dist F,A + dist C,A) / 2
        for sp in species:
            distances[(sp, new_species)] = \
              (get_distance(distances, closer1, sp) +
               get_distance(distances, closer2, sp)) / 2
        # on peut maintenant ajouter la nouvelle espèce
        # pour le tour suivant
        species.append(new_species)
        # bavardage
        if verbose:
            print(str(10*'=') + " species = " + str(species))
            print(distances)
    # le résultat est le seul élément de species
    return species[0]


resCourt = UPGMA("data_named_species.txt")
print(resCourt)
#resLong = UPGMA("data-named-species.txt", True)
#print(resLong)
