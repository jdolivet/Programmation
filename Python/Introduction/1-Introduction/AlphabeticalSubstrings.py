# -*- coding: utf-8 -*-
length = len(s)
len_max = 0
pos_max = 0
for i in range(length):
    count = 0
    pos = i
    j = i
    while j < length-1 and s[j] <= s[j + 1]:
        count += 1
        j += 1
    if count > len_max:
        len_max = count
        pos_max = pos
print("Longest substring in alphabetical order is: " + 
      str(s[pos_max:pos_max+len_max+1]))
