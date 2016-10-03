# -*- coding: utf-8 -*-

# le nucléotide complémentaire
complement = {'A': 'T', 'C': 'G', 'G': 'C', 'T': 'A'}


def reverse_complement(adn):
    """
    Calcule la séquence complémentaire (A->T, etc...) et
    inversée (les premiers sont les derniers)
    d'un brin d'ADN
    """
    # la liste des nucléotides complémentaires
    liste = [complement[nucleo] for nucleo in adn]
    # la même liste mais inversée
    liste = liste[::-1]
    # il ne reste plus qu'à retransformer en chaine
    return "".join(liste)
