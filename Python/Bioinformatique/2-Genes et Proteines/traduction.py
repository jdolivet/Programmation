# -*- coding: utf-8 -*-
"""
Traduction
"""
import transcription


# La correspondance codon -> acide aminé
lookup_table = {
    'UUU': 'F', 'UCU': 'S', 'UAU': 'Y', 'UGU': 'C',
    'UUC': 'F', 'UCC': 'S', 'UAC': 'Y', 'UGC': 'C',
    'UUA': 'L', 'UCA': 'S', 'UAA': '#', 'UGA': '#',
    'UUG': 'L', 'UCG': 'S', 'UAG': '#', 'UGG': 'W',
    'CUU': 'L', 'CCU': 'P', 'CAU': 'H', 'CGU': 'R',
    'CUC': 'L', 'CCC': 'P', 'CAC': 'H', 'CGC': 'R',
    'CUA': 'L', 'CCA': 'P', 'CAA': 'Q', 'CGA': 'R',
    'CUG': 'L', 'CCG': 'P', 'CAG': 'Q', 'CGG': 'R',
    'AUU': 'I', 'ACU': 'T', 'AAU': 'N', 'AGU': 'S',
    'AUC': 'I', 'ACC': 'T', 'AAC': 'N', 'AGC': 'S',
    'AUA': 'I', 'ACA': 'T', 'AAA': 'K', 'AGA': 'R',
    'AUG': 'M', 'ACG': 'T', 'AAG': 'K', 'AGG': 'R',
    'GUU': 'V', 'GCU': 'A', 'GAU': 'D', 'GGU': 'G',
    'GUC': 'V', 'GCC': 'A', 'GAC': 'D', 'GGC': 'G',
    'GUA': 'V', 'GCA': 'A', 'GAA': 'E', 'GGA': 'G',
    'GUG': 'V', 'GCG': 'A', 'GAG': 'E', 'GGG': 'G',
}


def translate_arn_to_amino_acids(arn, phase=0):
    """
    Traduction d'un brin d'ARN en une chaine encodant
    les acides aminés correspondants
    L'ARN en entrée est découpé en groupes de 3
    en partant de l'indice depart; à nouveau
    les lettres superflues en fin de chaine sont ignorées
    """
    # initialisation: la variable qui indique le début d'un groupe de 3
    offset = phase
    # on stocke la longueur de l'arn dans une variable
    # pour ne pas avoir à le recalculer à chaque passage dans la boucle
    longueur = len(arn)
    # initialisation de la variable qui contiendra le résultat
    resultat = ""
    # la boucle principale
    while offset <= longueur - 3:
        # le groupe de 3 est obtenu par slicing
        codon = arn[offset:offset+3]
        # on utilise += pour ajouter à la fin de la chaine resultat
        resultat += lookup_table[codon]
        # à la prochaine itération on veut passer au groupe suivant
        offset += 3
    return resultat

# un utilitaire pour afficher les acides aminés
noms_acides_amines = {
    'A': ('Ala', 'Alanine'),
    'R': ('Arg', 'Arginine'),
    'N': ('Asn', 'Asparagine'),
    'D': ('Asp', 'Aspartic acid'),
    'C': ('Cys', 'Cysteine'),
    'E': ('Glu', 'Glutamic acid'),
    'Q': ('Gln', 'Glutamine'),
    'G': ('Gly', 'Glycine'),
    'H': ('His', 'Histidine'),
    'I': ('Ile', 'Isoleucine'),
    'L': ('Leu', 'Leucine'),
    'K': ('Lys', 'Lysine'),
    'M': ('Met', 'Methionine'),
    'F': ('Phe', 'Phenylalanine'),
    'P': ('Pro', 'Proline'),
    'S': ('Ser', 'Serine'),
    'T': ('Thr', 'Threonine'),
    'W': ('Trp', 'Tryptophan'),
    'Y': ('Tyr', 'Tyrosine'),
    'V': ('Val', 'Valine'),
    # on ajoute une entrée pour les '#' qu'ajoute notre traduction
    '#': ('Stp', 'STOP'),
}


def affichage_amines(acides_amines):
    for indice, lettre in enumerate(acides_amines):
        if indice == len(acides_amines):
            break
        court, long = noms_acides_amines[lettre]
        print("{:03d}:{} [{}] -> {}".format(indice, lettre, court, long))


exemple = open("Borrelia.txt", 'r')
adn = exemple.read()
adn = adn[:-1]      # On enleve le dernier element : \n
exemple.close()
arn = translate_adn_to_arn(adn)

acides = translate_arn_to_amino_acids(arn)
# on peut alors voir le résultat
affichage_amines(acides)
