# -*- coding: utf-8 -*-
"""
Recherche d'un gène
"""


from codonStop import next_stop_codon
from codonStart import next_start_codon


# recherche de gènes sur une phase seulement
# avec par défaut une longueur minimale de 300
def coding_regions_one_phase(adn, phase, minimal_length=300):
    # on initialise index à la phase; avec next_start_codon
    # et next_stop_codon on reste toujours sur la même phase
    index = phase
    # les résultats sont retournés sous la forme d'une liste
    # de couples [start_gene, stop_gene]
    genes = []
    # on calcule stop1 qui désigne pour nous le stop "de gauche"
    # à ce stade, c'est le premier STOP à partir de la phase
    stop1 = next_stop_codon(adn, index)
    # s'il n'y a pas du tout de stop dans toute la séquence
    # on a terminé
    if not stop1:
        return genes
    # la boucle principale
    while True:
        # on cherche le premier STOP à partir de stop1
        # pour trouver le stop "de droite"
        stop2 = next_stop_codon(adn, stop1+3)
        # s'il n'y a plus de stop, on a fini
        if not stop2:
            return genes
        # mais il faut qu'il soit assez loin
        if stop2 - stop1 < minimal_length:
            # c'est trop court, on saute ce fragment
            stop1 = stop2
            continue
        # à ce stade on a trouvé un ORF, reste à trouver le bon start
        start = next_start_codon(adn, stop1)
        # s'il n'y en a pas: c'est qu'on ne trouvera plus rien
        # et donc on a fini
        if not start:
            return genes
        if stop2 - start < minimal_length:
            # si la region est trop petite, on l'ignore
            pass
        else:
            # cette fois on a trouvé un gène, on l'ajoute dans les résultats
            genes.append([start, stop2])
        # on peut passer à l'ORF suivant
        stop1 = stop2


# on teste la fonction avec l'ADN de burgdorferi
exemple = open("Subtilis.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

genes = coding_regions_one_phase(adn, 0)
print("On a trouvé {} gènes sur la phase 0".format(len(genes)))
