#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void Move(int count, int start, int finish, int temp);

int main(int argc, char *argv[]) {
  int i,height;

  /*check for valid input*/
  if (argc != 2 )  {
     fprintf(stderr,"Usage: %s <height (integer)> \n",argv[0]);
     exit(1);
  }
  for (i=0;i<strlen(argv[1]);i++) 
   if (!isdigit(argv[1][i])) {
     fprintf(stderr,"Usage: %s <height (integer)> \n",argv[0]);
     exit(1);
      }
  height=atoi(argv[1]);
  Move(height,1,3,2);
  exit(0);
}

void Move(int count, int start, int finish, int temp) {
  if(count >0) {
    Move(count-1,start,temp,finish);
    printf("Move a disk from %6d to %6d\n",start,finish);
    Move(count-1,temp,finish,start);
    }
}
