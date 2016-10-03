# -*- coding: utf-8 -*-
"""
Recherche du codon start
"""


# rappelons quel est le codon START
start_codon = "ATG"


# la fonction utilisée dans la recherche de régions codantes
def next_start_codon(adn, start):
    """
    localise le prochain START en commençant à
    l'index start et sur la même phase
    renvoie None s'il n'y en a plus
    """
    # on commence à l'indice en question
    index = start
    # tant qu'on trouve un START
    while True:
        # on cherche un START à partir de la position
        index = adn.find(start_codon, index)
        # il n'y a plus rien à chercher
        if index == -1:
            return None
        # si on n'est pas sur la même phase que `indice`
        # on ignore cet endroit
        if (index-start) % 3 != 0:
            # dans ce cas il faut incrémenter sinon
            # on reste sur place
            index += 3
            # et on recherche plus loin
            continue
        # sinon, il y a un match sur la bonne phase
        return index
    # si on est ici c'est qu'il n'y a plus rien à trouver
    return None

# test de la fonction

exemple = open("Borrelia.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()


for phase in 0, 1, 2:
    print("PHASE " + str(phase))
    next = phase
    while next is not None:
        next = next_start_codon(adn, next)
        if next is not None:
            print("trouvé à l'indice " + str(next) + " " +
                  str(adn[next:next+3]))
            next += 3
