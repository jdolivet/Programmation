# -*- coding: utf-8 -*-
"""
Calcul des fréquences des 4 bases
"""


def count_bases(adn):
    """
    retourne 5 valeurs:
    * la longueur totale de la chaîne
    * le nombre de 'A'
    * le nombre de 'C'
    * le nombre de 'G'
    * le nombre de 'T'
    """
    nbA = nbC = nbG = nbT = nbTotal = 0
    for nucleotide in adn:
        if nucleotide == 'A':
            nbA += 1
        elif nucleotide == 'C':
            nbC += 1
        elif nucleotide == 'G':
            nbG += 1
        elif nucleotide == 'T':
            nbT += 1
        nbTotal += 1
    return (nbTotal, nbA, nbC, nbG, nbT)


def display(comptage):
    """
    affiche le résultat de count_bases
    """
    # on extrait les 5 informations qui nous viennent de count_bases
    nbTotal, nbA, nbC, nbG, nbT = comptage
    # et on les affiche
    print("Longueur de la séquence : " + str(nbTotal))
    print("A = {:.2%}".format(nbA/nbTotal))
    print("C = {:.2%}".format(nbC/nbTotal))
    print("G = {:.2%}".format(nbG/nbTotal))
    print("T = {:.2%}".format(nbT/nbTotal))
    # si on veut afficher également les proportions de GC par rapport à TA
    print("CG = {:.2%}".format((nbC+nbG)/nbTotal))
    print("TA = {:.2%}".format((nbT+nbA)/nbTotal))


brin = "TATCCTGACTGGACGACAACGACGCAAT"
comptage = count_bases(brin)
display(comptage)
