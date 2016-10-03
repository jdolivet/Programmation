# -*- coding: utf-8 -*-
s = 0
for i in range(12):
    print("Month: " + str(i + 1))
    print("Minimum monthly payment: " +
          str(round(monthlyPaymentRate * balance, 2)))
    s += monthlyPaymentRate * balance
    balance = (balance - monthlyPaymentRate * balance) * \
        (1 + annualInterestRate / 12.0)
    print("Remaining balance: " + str(round(balance, 2)))
print("Total paid: " + str(round(s, 2)))
print("Remaining balance: " + str(round(balance, 2)))
