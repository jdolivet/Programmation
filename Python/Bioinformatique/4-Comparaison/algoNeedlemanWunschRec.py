# -*- coding: utf-8 -*-

"""
Algorithme de Needleman et Wunsch (version récursive)
"""


def needleman_wunsch_rec(adn1, adn2, i=None, j=None):
    """
    Calcule l'écart entre deux chaines d'ADN selon
    l'algorithme de Needleman et Wunsch
    en version récursive

    Utilise les fonctions
     * insertion_cost(base) et
     * substitution_cost(base1, base2)

    L'appelant en général ne spécifie pas i et j, qui sont utilisés
    lors des appels récursifs
    """
    # si on n'a pas précisé i ou j cela signifie
    # qu'on veut travailler sur toute la chaine
    i = i if i is not None else len(adn1)
    j = j if j is not None else len(adn2)

    # la condition d'arrêt
    # le bord supérieur
    if j == 0:
        return sum(insertion_cost(base) for base in adn1[:i])
    # le bord gauche
    elif i == 0:
        return sum(insertion_cost(base) for base in adn2[:j])

    # dans le cas général on peut appliquer la formule telle quelle
    return min(
        # substitution
        needleman_wunsch_rec(adn1, adn2, i-1, j-1) + substitution_cost(adn1[i-1], adn2[j-1]),
        # insertion
        needleman_wunsch_rec(adn1, adn2, i, j-1) + insertion_cost(adn2[j-1]),
        # insertion
        needleman_wunsch_rec(adn1, adn2, i-1, j) + insertion_cost(adn1[i-1]))


# la fonction d'insertion la plus simple possible
def insertion_cost(base):
    return 1


# la fonction de substitution la plus simple possible
def substitution_cost(base1, base2):
    return 1 if base1 != base2 else 0

# test 1
a1 = "ACTG"
a2 = "ACTC"
print("a1 : " + a1)
print("a2 : " + a2)
print("L'ecart entre les 2 brins vaut : " + str(needleman_wunsch_rec(a1, a2)))

print("")
# test 2
b1 = "ACGTAGC"
b2 = "ACTGTAGC"
print("b1 : " + b1)
print("b2 : " + b2)
print("L'ecart entre les 2 brins vaut : " + str(needleman_wunsch_rec(b1, b2)))
