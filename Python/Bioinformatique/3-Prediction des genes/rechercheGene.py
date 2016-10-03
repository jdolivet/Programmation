# -*- coding: utf-8 -*-
"""
Recherche de tous les gènes
"""

from rechercheGenePhase import coding_regions_one_phase
from complement import reverse_complement


# les gènes trouvés sur les 6 phases
def all_coding_regions(adn):
    inverse = reverse_complement(adn)
    genes = []
    for sujet in adn, inverse:
        for phase in 0, 1, 2:
            for start, end in coding_regions_one_phase(sujet, phase):
                # c'est ici qu'on extrait le gène in extenso
                genes.append(sujet[start:end])
    return genes

# on teste la fonction avec l'ADN de burgdorferi
exemple = open("Subtilis.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

# sur les 6 phases on obtient maintenant
genes = all_coding_regions(adn)
print("Sur les 6 phases on a trouvé {} gènes".format(len(genes)))
