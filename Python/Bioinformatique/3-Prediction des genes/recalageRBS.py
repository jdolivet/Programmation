# -*- coding: utf-8 -*-
"""
Recalage par recherche des RBS
"""


from codonStop import next_stop_codon
from codonStart import next_start_codon
from rechercheGenePhase import coding_regions_one_phase


# avec à nouveau une longueur minimale de 300 par défaut
# et en utilisant le recalage RBS
def coding_regions_one_phase_rbs(adn, phase, rbs, minimal_length=300):
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
        # regardons si on peut trouver un RBS dans la region codante
        next_rbs = adn.find(rbs, start)
        # est-ce bien dans la region ?
        if start <= next_rbs <= stop2:
            # le RBS est bien dans la région:
            # on recale le debut de la région codante comme
            # le prochain START à droite du RBS
            start = next_start_codon(adn, next_rbs)
        if stop2 - start < minimal_length:
            # si la region est trop petite, on l'ignore
            pass
        else:
            # cette fois on a trouvé un gène, on l'ajoute dans les résultats
            genes.append([start, stop2])
        # on peut passer à l'ORF suivant
        stop1 = stop2


# on teste la fonction avec l'ADN de burgdorferi en comparant avec l'autre méthode
exemple = open("EscheriaColi.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

# avec recalage
rbs_coli = 'AGGAGG'
for phase in 0, 1, 2:
    algo_rbs = coding_regions_one_phase_rbs(adn, phase, rbs_coli)
    # calculons la taille moyenne
    how_many = len(algo_rbs)
    total_length = sum(stop-start for start, stop in algo_rbs)
    average_size = total_length / how_many
    print("L'algorithme RBS trouve {} regions, taille moyenne = {:.02f} sur la phase {}".format(how_many, average_size, phase))

# sans recalage
print("")
print("Sans utiliser le recalage :")
for phase in 0, 1, 2:
    algo_simple = coding_regions_one_phase(adn, phase)
    # calculons la taille moyenne
    how_many = len(algo_simple)
    total_length = sum(stop-start for start, stop in algo_simple)
    average_size = total_length / how_many
    print("L'algorithme simple trouve {} regions, taille moyenne = {:.02f} sur la phase {}".format(how_many, average_size, phase))
