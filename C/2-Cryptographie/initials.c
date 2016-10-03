/**
*CS-50 : Introduction to Computer Science
*Problem Set 2
*Return the initials of the name
*Made by Johann Dolivet
*March 2015
*Filename : initials.c
**/

#include <cs50.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main(void)
{
    // enter the name
    string name = GetString();
    
    if (name != NULL)
    {
        for (int i = 0, n = strlen(name); i < n; i++)
        {
            if (i == 0 || name[i - 1] == 32)
            {
                printf("%c", toupper(name[i]));
            }
        } 
        printf("\n");
    }
}
