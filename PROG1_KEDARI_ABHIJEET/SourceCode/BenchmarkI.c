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

//This array will hold per second executed  number of instructions so that we can keep the log of their values

long catlog[4];

//This function is for performing Intergers operation to calculate the CPU performance

void* threadOperation(void *arg)
{
	int count= * (int *)arg;
	
	int i;
	
	//for(i=0;i<100000;i++)
	while(1)
		{
		int addition = 4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2+4+2;
		catlog[count]=catlog[count]+20;
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
	fp=fopen("log_for_Experiment_Integer.txt", "a+");
	
	int no_of_threads=atoi(argv[1]);
	pthread_t tid[no_of_threads];
	int i,j,k,l,join_ret,thread_ret;
	int p[4];
	l=0;
	
	// Creating different number of threads depends on command line argument
	
	for(k=0;k<4;k++)
	{
		catlog[k]=0;
		p[k]=k;
	}
	
	clock_t t1,t2,t3,t4;
	clock_t totaltime;
	//double flops_time=0;
	double flops_Count;
	float gflops_Count,flops_time;

	t1=clock();
	
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,threadOperation,&p[i]);
	}
	fprintf(fp,"---------------------------------------------------------\n");	
	
	// Here will calculate the per seconds number of operations performed by all threads combined
	// and will track this till 600 seoinds and after that will kill the running thread processess.
	
	while(1)
	{
		t2=clock();
		if((t2-t1)/CLOCKS_PER_SEC>=1)
		{
			t1=clock();
			l++;

			catlog[0]=catlog[0]+catlog[1]+catlog[2]+catlog[3];
			
			fprintf(fp, "%ld\n",catlog[0] / 1000000000);
			catlog[0]=0;
			catlog[1]=0;
			catlog[2]=0;
			catlog[3]=0;
		}
		if(l==600)
		{
			for(i=0;i<no_of_threads;i++)
				{
					pthread_kill(&(tid[i]),1);
				}
			break;
		}
	}
	
    fclose(fp);
    return 0;


}
