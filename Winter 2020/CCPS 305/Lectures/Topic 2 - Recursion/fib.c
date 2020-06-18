#include <stdio.h>
//fibonacci

int fib(int x) {
   if (x==0) return 0;
   if (x==1) return 1;
   return fib(x-1) + fib(x-2);
}


int main (void) {
  printf("fib(6) is %d\n",fib(6));
  printf("fib(60) is ");
  fflush(stdout);
  printf("%d\n",fib(60));
}
