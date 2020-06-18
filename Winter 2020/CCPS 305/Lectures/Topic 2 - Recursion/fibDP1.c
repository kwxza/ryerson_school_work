#include <stdio.h>
#include <stdlib.h>

//fibonacci using Dynamic Programming (top-down)

int fib(int x) {

   static int saved[1000] = {0}; //For simplicity. Should use DMA
                                 //to allocate exactly (x+1) slots

   if (x==0) return 0;
   if (x==1) return 1;
   if ( saved[x] != 0 ) return saved[x];
   saved[x] =  fib(x-1) + fib(x-2);
   return saved[x];
}


int main (void) {
  printf("fib(6) is %d\n",fib(6));
  printf("fib(60) is ");
  fflush(stdout);
  printf("%d\n",fib(60));
}
