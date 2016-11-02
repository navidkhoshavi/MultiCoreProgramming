#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>


int A[2][3],B[3][4],C[2][4];

void *Matrix_Mult1()
{
   int i = 0;
   int j = 0;
   int k = 0;
       for(i = 0; i < 1; i++)
           for( j = 0; j < 4; j++)
               for( k = 0; k < 3; k++)
                   C[i][j] +=  A[i][k] * B[k][j];
}


void *Matrix_Mult2()
{
   int i = 0;
   int j = 0;
   int k = 0;
       for(i = 1; i < 2; i++)
           for( j = 0; j < 4; j++)
               for( k = 0; k < 3; k++)
                   C[i][j] +=  A[i][k] * B[k][j];
}


int main(int argc, char *argv[])
{

   int x,y;
   pthread_t thread1, thread2;
   A[0][0]=1;A[0][1]=3;A[0][2]=4;A[1][0]=2;A[1][1]=0;A[1][2]=1;
   B[0][0]=1;B[0][1]=2;B[0][2]=3;B[0][3]=1;B[1][0]=2;B[1][1]=2;
   B[1][2]=2;B[1][3]=2;B[2][0]=3;B[2][1]=2;B[2][2]=1;B[2][3]=4;

   pthread_create(&thread1, NULL, Matrix_Mult1, NULL);
   pthread_create(&thread2, NULL, Matrix_Mult2, NULL);
   pthread_join(thread1,NULL);
   pthread_join(thread2,NULL);

   for(x = 0; x < 2; x++)
    {
        for(y = 0; y < 4; y++)
            printf("%4d", C[x][y]);
        putchar('\n');
    }

   return EXIT_SUCCESS;
}
