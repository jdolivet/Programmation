# -*- coding: utf-8 -*-
"""
Algorithme de Needleman et Wunsch (version itérative)
"""


from step1 import *
from step2 import *


def needleman_wunsch(adn1, adn2):
    # on calcule les coûts avec la phase1
    costs = phase1(adn1, adn2)
    # on calcule la distance
    d = costs[-1][-1]
    # on passe à la phase 2
    s1, s2 = phase2(adn1, adn2, costs)
    # on affiche le résultat
    print("distance = " + str(d))
    print(s1)
    print(s2)

# test 1
a1 = "ACTG"
a2 = "ACTC"
print("a1 : " + a1)
print("a2 : " + a2)
needleman_wunsch(a1, a2)

print("")
# test 2
b1 = "ACGTAGC"
b2 = "ACTGTAGC"
print("b1 : " + b1)
print("b2 : " + b2)
needleman_wunsch(b1, b2)

print("")
# test 3
c1 = "ACTGCCAAC"
c2 = "ACTGCGCAAC"
print("c1 : " + c1)
print("c2 : " + c2)
needleman_wunsch(c1, c2)

print("")
# test 4
d1 = "ACCTCTGTATCTATTCGGCATCGATCAT"
d2 = "ACCTCGTGTATCTCTTCGGCATCATCAT"
print("d1 : " + d1)
print("d2 : " + d2)
needleman_wunsch(d1, d2)
