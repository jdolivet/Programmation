/**
*CS-50 : Introduction to Computer Science
*Problem Set 1
*Counting change : number of coins
*Made by Johann Dolivet
*February 2015
*Filename : greedy.c
*/

#include <cs50.h>
#include <math.h>  
#include <stdio.h>

int main(void)
{
    float sum_dollar;
    do    
    {
        printf("O hai! How much change is owed?\n");  
        sum_dollar = GetFloat();
    }
    while (sum_dollar < 0);
    
    int num_coins = 0;
    int sum_cents = round(100 * sum_dollar);
   
    //Numbers of quarters
    num_coins += sum_cents / 25;
    sum_cents %= 25;
    
    //Numbers of dimes
    num_coins += sum_cents / 10;
    sum_cents %= 10;
 
    //Numbers of nickels
    num_coins += sum_cents / 5;
    sum_cents %= 5;
     
    //Numbers of pennies
    num_coins += sum_cents / 1;
    sum_cents %= 1;
    
    //Result
    printf("%d\n",num_coins);

}
