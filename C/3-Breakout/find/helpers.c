/**
 * helpers.c
 *
 * Computer Science 50
 * Problem Set 3
 *
 * Helper functions for Problem Set 3.
 */
       
#include <cs50.h>

#include "helpers.h"

/**
 * Returns true if value is in array of n values, else false.
 */
bool search(int value, int values[], int n)
{
    // Implement a searching algorithm : binary search (iterative vesion)
    int min = 0;
    int max = n - 1;
    while (max >= min)
    {
        int mid = (max + min) / 2;
        if (values[mid] == value)
        {
            return true;
        }
        else
        {
            if (values[mid] < value)
            {
                min = mid + 1;
            }
            else
            {
                max = mid - 1;
            }
        }
    }
    return false;  
}

/**
 * Sorts array of n values.
 */
void sort(int values[], int n)
{
    // Implement an O(n^2) sorting algorithm : selection sort
    for (int i = 0; i < n - 1; i++)
    {
        int min = i;
        for (int j = i + 1; j < n; j++)
        {
            if (values[j] < values[min])
            {
                min = j;
            }            
        } 
        if (i != min)
        {
            int temp = values[i];
            values[i] = values[min];
            values[min] = temp;
        }
    }      
    return;
}
