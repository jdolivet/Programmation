# paste your code here
# Name: 
# Date:
#
#


import random

user = raw_input("Choose your weapon: ")
comp = random.choice( ['rock','paper','scissors'] )

print 'the user (you) chose', user
print 'the comp (I) chose', comp

if user == 'rock':
  if comp == 'paper':
    print 'I win!'
  if comp == 'rock':
    print 'Tie.'
  if comp == 'scissors':
    print 'You win!'
    
if user == 'paper':
  if comp == 'paper':
    print 'Tie'
  if comp == 'rock':
    print 'You win!'
  if comp == 'scissors':
    print 'I win!'

if user == 'scissors':
  if comp == 'paper':
    print 'You win!'
  if comp == 'rock':
    print 'I win'
  if comp == 'scissors':
    print 'Tie.'
