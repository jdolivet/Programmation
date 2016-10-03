# -*- coding: utf-8 -*-
low = balance / 12.0
high = (balance * (1 + annualInterestRate / 12.0) ** 12) / 12.0
result = (high + low) / 2.0
total = balance
while (high - low >= 0.01):
    balance = total
    for i in range(12):
        balance = (balance - result) * (1 + annualInterestRate / 12.0)
    if balance < 0:
        high = result
        result = (high + low) / 2.0
    else:
        low = result
        result = (high + low) / 2.0
print("Lowest Payment: "+str(round(result, 2)))
