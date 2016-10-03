/**
*CS-50 : Introduction to Computer Science
*Problem Set 2
*Vigenere Cipher
*Made by Johann Dolivet
*March 2015
*Filename : vigenere.c
**/

#include <cs50.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>


int main(int argc, string argv[])
{
    // test if there's an argument
    if (argc != 2)
    {
        printf("Need an argument\n");
        return 1;
    }

    // Convert the argument in string
    string key = argv[1];
    
    // Test the key
    for (int i = 0, n = strlen(key); i < n; i++)    
    {
        if (!isalpha(key[i]))
        {
            printf("Must be alphabetic characters\n");
            return 1;        
        } 
    }
          
    // enter the message
    string message = GetString();
    if (message != NULL)
    {
        // counter for the key
        int j = 0;
        
        for (int i = 0, n = strlen(message); i < n; i++)
        {
            // majuscule
            int input = message[i];
            int input_key = tolower(key[j % strlen(key)]);
            if ((input > 64) && (input < 91))
            {
                input = ((input - 65 + (input_key - 97)) % 26) + 65;
                j += 1;                    
            }
            // minuscule
            if ((input > 96) && (input < 123))
            {
                input = ((input - 97 + (input_key - 97)) % 26) + 97; 
                j += 1;                   
            }
            char letter = input;
            printf("%c",letter);              
        }
        printf("\n");              
    }
    return 0;
}
