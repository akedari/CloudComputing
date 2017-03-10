/*
 ============================================================================
 Name        : Programming.c
 Author      : Abhijeet
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include<stdio.h>
#include<string.h>
#include<pthread.h>
#include<stdlib.h>
#include<unistd.h>
#include<time.h>

//This function is for performing Intergers operation to calculate the CPU performance 
void* threadOperation(void *arg)
{

	int i;
	for(i=0;i<1000000000;++i)
		{
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
			4+2;
	
		}
return NULL;
}

// Return the time difference between two clocks

double timediff(clock_t t1, clock_t t2) {
    double elapsed;
    elapsed = ((double)t2 - t1) / CLOCKS_PER_SEC;
    return elapsed;
}

int main(int argc, char *argv[]) {

	FILE *fp;
	fp=fopen("log_for_Integer.txt", "a+");
	
	int no_of_threads=atoi(argv[1]);
	pthread_t tid[no_of_threads];
	int i,j,join_ret,thread_ret;
	
	clock_t t1,t2;
	double iops_Count,elapsed;
	float giops_Count;

	t1=clock();
	
	// Creating different number of threads depends on command line argument
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,threadOperation,NULL);
	}
	
	// Join the thread so that main program execution will be in waiting state  
	for(j=0;j<no_of_threads;j++)
	{
		pthread_join(tid[j],NULL);
	}
	t2=clock();

	elapsed= timediff(t1,t2);
	//printf("elapsed: %ld ms\n", elapsed);
	
	iops_Count = no_of_threads*20*(1000000000/ elapsed);
	giops_Count = iops_Count / 1000000000;
	fprintf(fp, "GIOPS: %f\n",giops_Count);
	//printf("\nGFLOPS = %f\n", giops_Count);

    fclose(fp);
    return 0;


}
