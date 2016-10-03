/**
*CS-50 : Introduction to Computer Science
*Problem Set 4
*Resize a picture
*Made by Johann Dolivet build with copy.c
*Abril 2015
*Filename : resize.c
**/

#include <stdio.h>
#include <stdlib.h>

#include "bmp.h"

int main(int argc, char* argv[])
{
    // ensure proper usage
    if (argc != 4)
    {
        printf("Usage: ./resize factor infile outfile\n");
        return 1;
    }

    // remember filenames
    int factor = atoi(argv[1]);
    char* infile = argv[2];
    char* outfile = argv[3];
    
    // check the value of factor
    if (factor < 1 || factor > 100)
    {
        printf("n, the resize factor, must satisfy 0 < n <= 100.");
        return 3;
    }

    // open input file 
    FILE* inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        printf("Could not open %s.\n", infile);
        return 2;
    }

    // open output file
    FILE* outptr = fopen(outfile, "w");
    if (outptr == NULL)
    {
        fclose(inptr);
        fprintf(stderr, "Could not create %s.\n", outfile);
        return 3;
    }

    // read infile's BITMAPFILEHEADER
    BITMAPFILEHEADER bf;
    fread(&bf, sizeof(BITMAPFILEHEADER), 1, inptr);

    // read infile's BITMAPINFOHEADER
    BITMAPINFOHEADER bi;
    fread(&bi, sizeof(BITMAPINFOHEADER), 1, inptr);

    // ensure infile is (likely) a 24-bit uncompressed BMP 4.0
    if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 || 
        bi.biBitCount != 24 || bi.biCompression != 0)
    {
        fclose(outptr);
        fclose(inptr);
        fprintf(stderr, "Unsupported file format.\n");
        return 4;
    }

    // keep values of the input file
    DWORD old_SizeImage;
    LONG old_Height;
    LONG old_Width;
    old_SizeImage = bi.biSizeImage;
    old_Height = bi.biHeight;
    old_Width = bi.biWidth;

    // determine padding for scanlines
    int old_padding = (4 - (old_Width * sizeof(RGBTRIPLE)) % 4) % 4;
    
    // calculate outfile's BITMAPINFOHEADER
    bi.biHeight = bi.biHeight * factor;
    bi.biWidth = bi.biWidth * factor;
    
    // determine padding for adding
    int padding =  (4 - (bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4;    
    
    // calculate more outfile's BITMAPINFOHEADER   
    bi.biSizeImage = abs(bi.biHeight) * (bi.biWidth * 3 + padding);
    
    // calculate outfile's BITMAPFILEHEADER
    bf.bfSize = bi.biSizeImage + 54;
    
    // write outfile's BITMAPFILEHEADER    
    fwrite(&bf, sizeof(BITMAPFILEHEADER), 1, outptr);    
   
    // write outfile's BITMAPINFOHEADER
    fwrite(&bi, sizeof(BITMAPINFOHEADER), 1, outptr);


    // iterate over infile's scanlines
    for (int i = 0, biHeight = abs(bi.biHeight); i < biHeight; i = i + 1)
    {       
        // iterate over pixels in scanline
        for (int j = 0; j < old_Width; j++)
        {
            // temporary storage
            RGBTRIPLE triple;

            // read RGB triple from infile
            fread(&triple, sizeof(RGBTRIPLE), 1, inptr);

            // write RGB triple to outfile
            for (int l = 0; l < factor; l++)   
            {
                fwrite(&triple, sizeof(RGBTRIPLE), 1, outptr);                    
            }         
        }

        // skip over padding, if any
        fseek(inptr, old_padding, SEEK_CUR);

        // then add it back (to demonstrate how)
        for (int k = 0; k < padding; k++)
        {
            fputc(0x00, outptr);
        }
        if ( (i + 1) % factor != 0)
        {
            fseek(inptr, -(old_Width * 3 + old_padding), SEEK_CUR);
        }
    }

    // close infile
    fclose(inptr);

    // close outfile
    fclose(outptr);

    // that's all folks
    return 0;
}
