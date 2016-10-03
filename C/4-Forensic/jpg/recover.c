/**
*CS-50 : Introduction to Computer Science
*Problem Set 4
*Recovers JPEGs from a forensic image.
*Made by Johann Dolivet
*Abril 2015
*Filename : recover.c
**/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

typedef uint8_t BYTE;

int main(int argc, char* argv[])
{
    
    // ensure proper usage
    if (argc != 1)
    {
        printf("Usage: ./recover\n");
        return 1;
    }

    // open input file 
    FILE* file = fopen("card.raw", "r");
    if (file == NULL)
    {
        printf("Could not open the file.\n");
        return 2;
    }    
            
    // the buffer
    BYTE buffer[512];
    
    // the counter of photos
    int counter = 0;    
    char title[10];
    
    // because we need it!
    FILE* img;
    
    // to start the process of writing
    int start = 0;
    
    while (fread(&buffer, 512, 1, file) != 0)
    {            
        if ((buffer[0] == 0xff) && (buffer[1] == 0xd8) && (buffer[2] == 0xff) && ((buffer[3] == 0xe0) || (buffer[3] == 0xe1)))
        {
            // close the old outfile
            if (start == 1)
            {
                fclose(img);
            }
                        
            // name of picture
            sprintf(title, "%03d.jpg", counter);
            counter += 1;
            
            // open new file
            img = fopen(title, "a");
            if (img == NULL)
            {
                fclose(img);
                printf("Could not create img.\n");
                return 3;
            }      
            
            // start the process
            start = 1;                 
        }
        // write in it
        if (start == 1)
        {
            fwrite(&buffer, 512, 1, img);       
        }
    }

    // close outfile
    if (start == 1)
    {
        fclose(img);
    }
    
    // close infile    
    fclose(file);

    // that's all
    return 0;    
    
}
