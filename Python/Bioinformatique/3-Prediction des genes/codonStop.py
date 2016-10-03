# -*- coding: utf-8 -*-
"""
Recherche du premier codon stop
"""
import re
# on rappelle la définition de re_stop
# pour chercher 'TAA' ou 'TAG' ou 'TGA', on utilise le ou logique |
re_stop = re.compile("TAA|TAG|TGA")


# la fonction utilisée dans la recherche de régions codantes
def next_stop_codon(adn, start):
    """
    localise l'un des prochains STOP en commençant à
    l'index start et sur la même phase
    renvoie None s'il n'y en a plus
    """
    # on commence à l'indice en question
    index = start
    # tant qu'on trouve un STOP
    while True:
        # on cherche un STOP
        # à partir de la position courante
        match = re_stop.search(adn, index)
        # il n'y a plus rien à chercher
        if match is None:
            return None
        # si on n'est pas sur la même phase que `start`
        # on ignore cet endroit
        index = match.start()
        if (index-start) % 3 != 0:
            index += 3
            continue
        # sinon, il y a un match sur la bonne phase
        return index
    # si on est ici c'est qu'il n'y a plus rien à trouver
    return None


exemple = open("Borrelia.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()

for phase in 0, 1, 2:
    print("PHASE", phase)
    next = phase
    while next is not None:
        next = next_stop_codon(adn, next)
        if next is not None:
            print("trouvé à l'indice", next, adn[next:next+3])
            next += 3
