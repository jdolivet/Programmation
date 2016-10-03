# -*- coding: utf-8 -*-


# la fonction d'insertion la plus simple possible
def insertion_cost(base):
    return 1


# la fonction de substitution la plus simple possible
def substitution_cost(base1, base2):
    return 1 if base1 != base2 else 0


# initialisation de la structure
def init_costs(len1, len2):
    """
    Initialise un tableau de len1 + 1 listes
    de chacune len2 + 1 éléments
    initialisés à 0
    """
    return [[0 for j in range(len2 + 1)] for i in range(len1 + 1)]


def phase1(adn1, adn2):
    """
    Première phase de Needleman et Wunsch itératif
    Élabore itérativement le tableau des coûts
      par un parcours en diagonale
    On obtient un tableau de taille
      [len(adn1) + 1] x [len(adn2) + 1]
    Renvoie le tableau en valeur
    """
    # initialisations
    len1 = len(adn1)
    len2 = len(adn2)
    # le tableau est initialisé à zéro
    costs = init_costs(len1, len2)

    # le parcours en diagonale - cf ci-dessus
    for c in range(len1 + len2 + 1):
        for i in range(c + 1):
            # on déduit j de c et i
            j = c - i
            # on ne considère que ceux qui tombent dans le rectangle
            if 0 <= i <= len1 and 0 <= j <= len2:
                if i == 0 and j == 0:  # le coin en haut a gauche
                    costs[i][j] = 0
                elif j == 0:           # sur un bord : insertion
                    costs[i][j] = costs[i-1][j] + insertion_cost(adn1[i-1])
                elif i == 0:           # l'autre bord : insertion
                    costs[i][j] = costs[i][j-1] + insertion_cost(adn2[j-1])
                else:                  # au milieu
                    costs[i][j] = min(
                        # substitution
                        costs[i-1][j-1] + substitution_cost(adn1[i-1], adn2[j-1]),
                        # insertion
                        costs[i][j-1] + insertion_cost(adn2[j-1]),
                        # insertion
                        costs[i-1][j] + insertion_cost(adn1[i-1]))
    # on renvoie le résultat
    return costs


# et donc du coup on peut définir
def distance(adn1, adn2):
    return phase1(adn1, adn2)[-1][-1]
