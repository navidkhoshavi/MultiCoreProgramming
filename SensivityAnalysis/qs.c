#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int a[5],right,left,pivot,right1,left1,pivot1,right2,left2,pivot2;

void *quicksort1()
{
	int b;
	pivot1 = a[left1];
	while (left1 < right1)
	{
		while (a[left1] < pivot1) left1++;
		while (a[right1] > pivot1) right1--;
		if (left1 < right1)  {b = a[left1]; a[left1] = a[right1]; a[right1] = b;}
	}
}


void *quicksort2()
{
	int b;
	pivot2 = a[left2];
	while (left2 < right2)
	{
		while (a[left2] < pivot2) left2++;
		while (a[right2] > pivot2) right2--;
		if (left2 < right2)  {b = a[left2]; a[left2] = a[right2]; a[right2] = b;}
	}
}


int main(int argc, char *argv[])
{
	pthread_t thread1, thread2;
	int i,b;
	a[0] = 5;a[1] = 2;a[2] = 9;a[3] = 8;a[4] = 3;
	left = 0;
	right = 4;
	pivot = a[left];
	while (left < right)
	{
		while (a[left] < pivot) left++;
		while (a[right] > pivot) right--;
		if (left < right)  {b = a[left]; a[left] = a[right]; a[right] = b;}
	}
	pivot = right;

	left1 = 0;
	right1 = pivot - 1;
	left2 = pivot + 1;
	right2 = 4;

	pthread_create(&thread1, NULL, quicksort1, NULL);
        pthread_create(&thread2, NULL, quicksort2, NULL);
        pthread_join(thread1,NULL);
        pthread_join(thread2,NULL);

	for (i=0; i<5; ++i) printf("%4d",a[i]);
	putchar('\n');

	return EXIT_SUCCESS;
}
