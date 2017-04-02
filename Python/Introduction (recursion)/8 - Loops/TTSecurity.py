# python 2
#
# Homework 8, Problem 3
# Loops!
#
# Name: Johann
#

def menu():
    """ a function that simply prints the menu """
    print("")
    print("(0) Input a new list")
    print("(1) Print the current list")
    print("(2) Find the average price")
    print("(3) Find the standard deviation")
    print("(4) Find the min and its day")
    print("(5) Find the max and its day")
    print("(6) Your TT investment plan")
    print("(9) Quit")
    print("")


def main():
    """ the main user-interaction loop """

    L = [20,10,30] # an initial list

    while True:   # the user-interaction loop
        menu()
        uc = raw_input( "Enter your choice: " )
        if uc == '9': # we want to quit
            print("See you!")
            break
        elif uc == '0': # we want to enter a new list
            numString = raw_input("Enter a new list of prices: ")
            L = makeList(numString)
        elif uc == '1': # we want to enter a new list
            printList(L)
        elif uc == '2': 
            print("The average price is " + str(averagePrice(L)))
        elif uc == '3': 
            print("The st. deviation is " + str(standardDev(L)))
        elif uc == '4':
          print("The min is " + str(minDay(L)[0]) + " on day " + str(minDay(L)[1]))
        elif uc == '5': 
          print("The max is " + str(maxDay(L)[0]) + " on day " + str(maxDay(L)[1]))
        elif uc == '6':
          print("Your TTS investment strategy is to")
          print("\nBuy on " + str(minDay(L)[1]) + " at price " + str(minDay(L)[0]))
          print("Sell on " + str(maxDay(L)[1]) + " at price " + str(maxDay(L)[0]))
          print("\nFor a total profit of " + str(TTPlan(L)[2]) )

        else:
            print("That's not on the menu!")

    print("")
    print("I knew you were going to quit!")

def makeList(numString):
    numString = numString.replace('[', '')
    numString = numString.replace(']', '')
    numList = numString.split(',')
    L = []
    for x in numList:
        L.append(float(x.strip()))
    return L
    
def printList(L):
  """ print """
  print("Day Price")
  print("--- -----")
  taille = len(L)
  for i in range(taille):
    print(" " + str(i) + "" + str("%7.2f" % L[i]))

def averagePrice(L):
  """ avg """
  somme = 0.0
  taille = len(L)
  for e in L:
    somme += e
  return somme/taille

def standardDev(L):
  """ std dev """
  somme = 0.0
  taille = len(L)
  avg = averagePrice(L)
  for e in L:
    somme += (e - avg) ** 2
  return (somme/taille) ** 0.5
  
def minDay(L):
  """ min"""
  mini = L[0]
  pos = 0
  taille = len(L)
  for i in range(1, taille):
    if L[i] < mini:
      mini = L[i]
      pos = i
  return [mini, pos]
  
def maxDay(L):
  """ max"""
  maxi = L[0]
  pos = 0
  taille = len(L)
  for i in range(1, taille):
    if L[i] > maxi:
      maxi = L[i]
      pos = i
  return [maxi, pos]

def TTPlan(L):
  """ plan """
  return [minDay(L)[1], maxDay(L)[1], maxDay(L)[0]-minDay(L)[0]]


#main()
