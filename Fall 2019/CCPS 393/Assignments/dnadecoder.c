#include <stdio.h>
#define INDEX_SIZE 4

// dnadecoder.c
// Kasaine Kipusi
// Student ID: 500936268
// Assignment #2: DNA Decoder
// CCPS 393 Section 310
// Instructor: L. Hiraki
// Purpose: Use a provided file of DNA bases to decode into amino acids.
// Purpose: Display the decoded amino acids and their total, as well as the number of bases processed.

int main(int argc, char *argv[])
{
    // The indexes of the 3-d array will be used to
    // represent the 4 possible bases of an amino acid.
    // Key: 0 = T, 1 = C, 2 = A, 3 = G
    char codonArray[INDEX_SIZE][INDEX_SIZE][INDEX_SIZE];

    codonArray[0][0][0] = 'F', codonArray[0][0][1] = 'F', codonArray[0][0][2] = 'L', codonArray[0][0][3] = 'L';
    codonArray[1][0][0] = 'L', codonArray[1][0][1] = 'L', codonArray[1][0][2] = 'L', codonArray[1][0][3] = 'L';
    codonArray[2][0][0] = 'I', codonArray[2][0][1] = 'I', codonArray[2][0][2] = 'I', codonArray[2][0][3] = 'M';
    codonArray[3][0][0] = 'V', codonArray[3][0][1] = 'V', codonArray[3][0][2] = 'V', codonArray[3][0][3] = 'V';

    codonArray[0][1][0] = 'S', codonArray[0][1][1] = 'S', codonArray[0][1][2] = 'S', codonArray[0][1][3] = 'S';
    codonArray[1][1][0] = 'P', codonArray[1][1][1] = 'P', codonArray[1][1][2] = 'P', codonArray[1][1][3] = 'P';
    codonArray[2][1][0] = 'T', codonArray[2][1][1] = 'T', codonArray[2][1][2] = 'T', codonArray[2][1][3] = 'T';
    codonArray[3][1][0] = 'A', codonArray[3][1][1] = 'A', codonArray[3][1][2] = 'A', codonArray[3][1][3] = 'A';

    codonArray[0][2][0] = 'Y', codonArray[0][2][1] = 'Y', codonArray[0][2][2] = '*', codonArray[0][2][3] = '*';
    codonArray[1][2][0] = 'H', codonArray[1][2][1] = 'H', codonArray[1][2][2] = 'Q', codonArray[1][2][3] = 'Q';
    codonArray[2][2][0] = 'N', codonArray[2][2][1] = 'N', codonArray[2][2][2] = 'K', codonArray[2][2][3] = 'K';
    codonArray[3][2][0] = 'D', codonArray[3][2][1] = 'D', codonArray[3][2][2] = 'E', codonArray[3][2][3] = 'E';

    codonArray[0][3][0] = 'C', codonArray[0][3][1] = 'C', codonArray[0][3][2] = '*', codonArray[0][3][3] = 'W';
    codonArray[1][3][0] = 'R', codonArray[1][3][1] = 'R', codonArray[1][3][2] = 'R', codonArray[1][3][3] = 'R';
    codonArray[2][3][0] = 'S', codonArray[2][3][1] = 'S', codonArray[2][3][2] = 'R', codonArray[2][3][3] = 'R';
    codonArray[3][3][0] = 'G', codonArray[3][3][1] = 'G', codonArray[3][3][2] = 'G', codonArray[3][3][3] = 'G';

    // Handling incorrect arguments
    if (argc < 2)
    {
        printf("Error - No file specified. You must provide a file to decode.\n");
    }
    if (argc > 2)
    {
        printf("Error - Too many files specified. You can only decode one file at a time.");
    }

    // Decode if the correct number of arguments have been given
    if (argc == 2)
    {
        FILE *dnaFile;
        int totalBases = 0, totalAminos = 0, baseTrioCount = 0;
        int baseTrio[3]; // This array will match to the codonArray
        char base;

        dnaFile = fopen(argv[1], "r");

        while (fscanf(dnaFile, "%c", &base) != EOF)
        {
            // The switch statement will only update the baseTrio array
            // if @base matches an amino base character.
            // Each time a base is found, baseTrioCount is updated.
            switch (base)
            {
            case 'T':
            case 't':
                baseTrio[baseTrioCount] = 0;
                baseTrioCount++;
                totalBases++;
                break;

            case 'C':
            case 'c':
                baseTrio[baseTrioCount] = 1;
                baseTrioCount++;
                totalBases++;
                break;

            case 'A':
            case 'a':
                baseTrio[baseTrioCount] = 2;
                baseTrioCount++;
                totalBases++;
                break;

            case 'G':
            case 'g':
                baseTrio[baseTrioCount] = 3;
                baseTrioCount++;
                totalBases++;
                break;
            }

            // After a base is processed, check if 3 have been found.
            // If so, display the corresponding amino acid, reset the
            // baseTrioCount and increment totalAminos.
            if (baseTrioCount == 3)
            {
                printf("%c", codonArray[baseTrio[0]][baseTrio[1]][baseTrio[2]]);
                baseTrioCount = 0;
                totalAminos++;
            }
        }
        fclose(dnaFile);

        printf("\nTotal number bases processed: %d\n", totalBases);
        printf("Total number of amino acids decoded: %d\n", totalAminos);
    }

    return (0);
}