# paste your code here
#
# Your name:
# Date: 
# Here is a short python program... try it out!
# 


import time          # includes a library named time
import random        # a random library


name = raw_input('Hi... what is your name? ')   
print

if name == 'Alex' or name == 'Colleen':
    print 'I\'m "offline." Try later.'

elif name == 'Zach':                            
    print 'Zach Quinto!?!'
    time.sleep(1.0)             
    print 'No?'
    time.sleep(1.0)
    print 'Meh.'

else:                   
    print 'Welcome,', name
    my_choice = random.choice(['rock', 'paper', 'scissors'])
    print 'By the way, I choose', my_choice
    print
