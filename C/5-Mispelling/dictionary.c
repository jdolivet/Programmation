/****************************************************************************
 * dictionary.c
 *
 * Computer Science 50
 * Problem Set 5
 *
 * Implements a dictionary's functionality with trie structure
 ***************************************************************************/

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "dictionary.h"

// structure of theo trie's node
typedef struct node
{
    bool is_word;
    struct node* children[27];
}
node;
// create the root of the trie
node* root; 

// counter of words
int number_words = 0;

/**
 * Returns true if word is in dictionary else false.
 */
bool check(const char* word)
{
    // Intialize the search in the root
    node* checked_word = root;
    
    // position of the letter in the word (array)
    int counter = 0;
    
    // explore the dictionary and test the existence of the word
    while(word[counter] != '\0')
    {
        char letter = word[counter];

        // Treating each letter independant of the case        
        if (word[counter] == '\'')
        {
            letter = (int) 'z' + 1;
        }        
        int index_node = tolower(letter) - (int) 'a';
        
        // If the node (of the letter) have children, continue ...
        if (checked_word->children[index_node] != NULL)
        {
            checked_word = checked_word->children[index_node];
            counter++;        
        }
        else
        {
            return false;
        }
    }
     
    // checking if it's a valid word
    if (checked_word->is_word == true)
    {
        return true;
    }
    else
    {
        return false;
    }
}   


/**
 * Loads dictionary into memory.  Returns true if successful else false.
 */
bool load(const char* dictionary)
{
    // open the file
    FILE *dict = fopen(dictionary, "r");    
    if (dict == NULL)
    {
        return false;
    }
    
    // starting with the root node
    root = calloc(1, sizeof(node));
    
    // define new : the new node which be created or used
    node* new;
    
    // start to read the file : it will be used to stop the while loop        
    int end_loop = fgetc(dict);    
    
    // each loop will insert a new word
    while(end_loop != EOF)
    {
        // start from the root
        new = root;
        
        // insert the new word in the structure
        for(int counter = end_loop; counter != '\n'; counter = fgetc(dict))
        {
            
            // Treating each letter independant of the case             
            if (counter == '\'')
            {
                counter = (int) 'z' + 1;
            }            
            int position = counter - (int) 'a';
            
            // if next node don't exist create it. Either go deeper.. 
            if (new->children[position] == NULL)
            {
                new->children[position] = calloc(1, sizeof(node));
                new = new->children[position];
            }
            else
            {
                new = new->children[position];
            }
        }       
        // notify the end of the existent word
        new->is_word = true;
        number_words++;
        
        // go ahead to the next letter of the file
        end_loop = fgetc(dict);        
    }
    
    // close the file
    fclose(dict);    
    return true;
}

/**
 * Returns number of words in dictionary if loaded else 0 if not yet loaded.
 */
unsigned int size(void)
{
    // using the function load()
    return number_words;
}

/**
 * Unloads dictionary from memory.  Returns true if successful else false.
 */

// create the recursive function starting from the parameter
void free_node(node* liberted)
{
    for(int i = 0; i < 27; i++)
    {
        if (liberted->children[i] != NULL)
        {
            free_node(liberted->children[i]);
        }
    }
    free(liberted);
}

bool unload(void)
{
    // from the root, use the recursive funcion to free all the childrens
    for(int i = 0; i < 27; i++)
    {
        if (root->children[i] != NULL)
        {
            free_node(root->children[i]);
        }
    }
    // now can free the root
    free(root);
    return true;
}


