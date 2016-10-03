# -*- coding: utf-8 -*-
result = 0
total = balance
while (balance >= 0):
    balance = total
    for i in range(12):
        balance = (balance - result) * (1 + annualInterestRate / 12.0)
    result += 10
print("Lowest Payment: "+str(result - 10))
