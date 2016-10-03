/**
*CS-50 : Introduction to Computer Science
*Problem Set 1
*Mario steps : constructs the number of steps you want
*Made by Johann Dolivet
*February 2015
*Filename : mario.c
*/

#include <cs50.h>
#include <stdio.h>

int main(void)
{
    int n;
    do
    {
        printf("Height: ");
        n = GetInt();
    }
    while (n < 0 || n > 23);
    
    int i, j;
    // Generated the number of steps
    for(i = 0; i < n; i++)          
    {
    
        // Generated each step
        for(j = 0; j <= n; j++)           
        {
            if (j < n - i - 1)
            {
                printf(" ");
            }
            else
            {
                printf("#");
            }        
        }
        printf("\n"); 
    }
}
