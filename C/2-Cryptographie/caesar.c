/**
*CS-50 : Introduction to Computer Science
*Problem Set 2
*Caesar Cipher
*Made by Johann Dolivet
*March 2015
*Filename : caesar.c
**/

#include <cs50.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


int main(int argc, string argv[])
{
    // test if there's an argument
    if (argc != 2)
    {
        printf("Need an argument");
        return 1;
    }

    // Convert the argument in integer
    int key = atoi(argv[1]);
    
    // test if the key is positive
    if (key < 0)
    {
        printf("Key must be a non-negative integer.");
        return 1;
    }
       
    // enter the message
    string message = GetString();
    if (message != NULL)
    {
        for (int i = 0, n = strlen(message); i < n; i++)
        {
            // majuscule
            int input = message[i];
            if ((input > 64) && (input < 91))
            {
                input = ((input - 65 + key) % 26) + 65;                    
            }
            // minuscule
            if ((input > 96) && (input < 123))
            {
                input = ((input - 97 + key) % 26) + 97;                    
            }
            char letter = input;
            printf("%c",letter);              
        }
        printf("\n");              
    }
    return 0;
}
