# -*- coding: utf-8 -*-


# une fonction utilitaire qui ajoute les '*'
# autour d'un caractère lorsque 'same' est faux
def outline(char, same=True):
    return char if same else "*{}*".format(char)


# la fonction d'insertion la plus simple possible
def insertion_cost(base):
    return 1


# la fonction de substitution la plus simple possible
def substitution_cost(base1, base2):
    return 1 if base1 != base2 else 0


# un exemple de phase2
def phase2(adn1, adn2, costs):
    """
    À partir de deux brins d'ADN, et de leur tableau de coûts tels que
    calculé dans la première phase, on retourne deux chaines destinées
    à être affichées une au dessus de l'autre pour visualiser les différences

    Les insertions sont remplacées par le caractère -, et les substitutions
    sont affichées entourées du caractère *
    """
    i = len(adn1)
    j = len(adn2)
    # les résultats, mais sous forme de listes de chaines, et à l'envers
    r1 = []
    r2 = []
    # le parcours à proprement parler
    # on ne s'arrête que quand i==0 ET j==0
    while i > 0 or j > 0:
        # la valeur courante
        c = costs[i][j]
        # si on est au bord, les formules en i-1 ou j-1
        # ne vont pas faire ce qu'on veut, il faut traiter
        # ces cas à part
        if i == 0:                  # bord = insertion
            r1.append("-")
            j -= 1
            r2.append(adn2[j])
        elif j == 0:                # bord = insertion
            i -= 1
            r1.append(adn1[i])
            r2.append("-")
        # dans le milieu du tableau on regarde de quelle direction nous vient le minimum
        elif c == costs[i-1][j-1] + substitution_cost(adn1[i-1], adn2[j-1]):  # substitution
            # s'agit-t-il d'une vraie substitution ? 
            same = adn1[i-1] == adn2[j-1]
            i -= 1
            r1.append(outline(adn1[i], same))
            j -= 1
            r2.append(outline(adn2[j], same))
        elif c == costs[i][j-1] + insertion_cost(adn2[j-1]):    # insertion
            r1.append('-')
            j -= 1
            r2.append(adn2[j])
        elif c == costs[i-1][j] + insertion_cost(adn1[i-1]):    # insertion
            i -= 1
            r1.append(adn1[i])
            r2.append('-')
    # à ce stade il nous reste à retourner les listes, et les transformer en chaines
    s1 = "".join(r1[::-1])
    s2 = "".join(r2[::-1])
    return s1, s2
    # à ce stade il nous reste à retourner les listes, et les transformer en chaines
    s1 = "".join(reversed(r1))
    s2 = "".join(reversed(r2))
    return s1, s2
