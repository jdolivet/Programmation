# -*- coding: utf-8 -*-
"""
Distance de Hamming
"""


# Calcul de la distance de Hamming
def hamming_distance(adn1, adn2, length=None):
    if length is None:
        length = min(len(adn1), len(adn2))
    distance = 0
    for i in range(length):
        if adn1[i] != adn2[i]:
            distance += 1
    return distance

# test 1
a1 = "ACCTCTGTATCTATTCGGCATCATCAT"
a2 = "ACCTCTGAATCTATTCGGGATCATCAT"
#            ^          ^
print("a1 : " + a1)
print("a2 : " + a2)
print("La distance de Hamming entre les 2 brins vaut : " +
      str(hamming_distance(a1, a2)))

print("")
# test 2
b1 = "ACCTCTGTATCTATTCGGGATCATCAT"
b2 = "ACCTCTGAATCTATCCGGGATCATGAT"
#            ^      ^         ^
print("b1 : " + b1)
print("b2 : " + b2)
print("La distance de Hamming entre les 2 brins vaut : " +
      str(hamming_distance(b1, b2)))
