
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

char buffer[1024*1024];
//char * source_buffer;
char * destination_buffer;
char * source_buffer; 


// this is buffered space we are using for reading and writing purpose form Disk and to disk 

// Return the time difference between two clocks	
long timediff(clock_t t1, clock_t t2) {
    long elapsed;
    elapsed = ((double)t2 - t1) / CLOCKS_PER_SEC * 1000;
    return elapsed;
}

// this is to Read+Write a byte data sequencially from Memory
void* readSeqByte(void *arg)
{
	int i;
	int source_buffer_index,destination_buffer_index;
	source_buffer_index=0;
	destination_buffer_index=0;
/*	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);*/

	for(i=0;i<100000000;i++) // 100mb data
	{
		memcpy(&destination_buffer[destination_buffer_index], &source_buffer[source_buffer_index], 1);
		source_buffer_index= source_buffer_index +1; 
		destination_buffer_index=destination_buffer_index+1;
		//source_buffer=source_buffer+1;
		//destination_buffer=destination_buffer+1;
	}
	return NULL;
}

// this is to Read+Write a Kilo byte data sequencially from Memory
void* readSeqKByte(void *arg)
{
	int i;
	int source_buffer_index,destination_buffer_index;
/*	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);*/
	source_buffer_index=0;
	destination_buffer_index=0;
	for(i=0;i<100000;i++) // 100mb data
	{
		memcpy(&destination_buffer[destination_buffer_index], &source_buffer[source_buffer_index], 1024);
		
		source_buffer_index= source_buffer_index +1024; 
		destination_buffer_index=destination_buffer_index+1024;
		//source_buffer=source_buffer+1024;
		//destination_buffer=destination_buffer+1024;
	}
	return NULL;
}

// this is to Read+Write a Mega byte data sequencially from Memory
void* readSeqMByte(void *arg)
{
	int i;
	int source_buffer_index,destination_buffer_index;
	source_buffer_index=0;
		destination_buffer_index=0;
/*	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);*/

	//printf("\nSizeof Source buffer = %lf\n",sizeof(source_buffer));

	for(i=0;i<100;i++) // 100mb data
	{
		memcpy(&destination_buffer[destination_buffer_index], &source_buffer[source_buffer_index], (1024*1024));
		source_buffer_index= source_buffer_index +(1024*1024); 
		destination_buffer_index=destination_buffer_index+(1024*1024);
		/*
		source_buffer=source_buffer+(1024*1024);
		destination_buffer=destination_buffer+(1024*1024);*/
	}
	return NULL;
}

// this is to Read+Write a byte data Randomly from Memory
void* readRanByte(void *arg)
{
	int i,randloc;
	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);

	for(i=0;i<100000000;i++) // 100mb data
	{
		randloc= rand() % (1024*1024*100);
		memcpy(&destination_buffer[randloc], &source_buffer[randloc], 1);
	}
	return NULL;
}

// this is to Read+Write a Kilo byte data Randomly from Memory
void* readRanKByte(void *arg)
{
	int i,randloc;
/*	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);*/

	for(i=0;i<100000;i++) // 100mb data
	{
		randloc= (rand() % ((1024*1024*100)-1024));
		memcpy(&destination_buffer[randloc], &source_buffer[randloc], 1024);
	}
	return NULL;
}

// this is to Read+Write a Mega byte data Randomly from Memory
void* readRanMByte(void *arg)
{
	int i,randloc;
	
/*	char * destination_buffer = (char *) malloc(1024*1024*100);
	char * source_buffer= (char *) malloc(1024*1024*100);*/

	for(i=0;i<100;i++) // 100mb data
	{
		randloc= (rand() % ((1024*1024*100)-(1024*1024)));
		memcpy(&destination_buffer[randloc], &source_buffer[randloc], (1024*1024));
	}
	return NULL;
}



int main(int argc, char *argv[]) {

	FILE *log;
	log=fopen("Memory_log.txt", "a+");
	
	//source_buffer= (char *) malloc(1024*1024*100);
	destination_buffer = (char *) malloc(1024*1024*100);
	source_buffer= (char *) malloc(1024*1024*100);
	
	int i,j,cnt=0;
	size_t size = 1;

	int number_of_operation=1;
	double latency,totaldata;
	double elapsed,throughput;
	int no_of_threads = atoi(argv[1]);
	pthread_t tid[no_of_threads];

	// Initialize the buffer so that we can write it in memory
	//printf("\nInitialize the buffer\n");
	for(i=0;i<(1024*1024);i++)
	{
		buffer[i]='k';
	}

	//--------------ReadWriteSeqByte-----------------------//
	
	//printf("\nReadWriteSeqByte\n");
	clock_t t1,t2;
	t1=clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]),NULL,readSeqByte,NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j],NULL);
	}
	t2=clock();
	elapsed = timediff(t1,t2);
	//printf("ReadWriteSeqByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteSeqByte operation %lf  milliseconds\n", elapsed);
	
	//printf("ReadSeqByte: %lf ms\n", elapsed);
	totaldata = (double) (no_of_threads * 100000000 * 1) / 1000000;
	latency = (double) (elapsed / totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);

	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	elapsed = elapsed / 1000;
	throughput = (double) (totaldata / elapsed);
	fprintf(log, "Throughput: operations(mb) per seconds %lf  \n", throughput);


	//--------------ReadWriteSeqKByte-----------------------//
	//printf("\nReadWriteSeqKByte\n");
	clock_t t3,t4;
	t3=clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]),NULL,readSeqKByte,NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j],NULL);
	}
	t4=clock();
	elapsed = timediff(t3,t4);
	//printf("ReadWriteSeqKByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteSeqKByte operation %lf  milliseconds\n", elapsed);

	totaldata= (double)(no_of_threads*100000*1024)/1000000;
		latency= (double)(elapsed/totaldata);
		fprintf(log, "Latency: time per operation %lf  \n", latency);
		
		//printf("ReadSeqKByte: %lf ms\n", elapsed);
		elapsed=elapsed/1000;
		throughput= (double)(totaldata/elapsed);	
		fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	
	//--------------ReadWriteSeqMByte-----------------------//
	
	//printf("\nReadWriteSeqMByte\n");
	clock_t t5,t6;
	t5=clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]),NULL,readSeqMByte,NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j],NULL);
	}
	t6=clock();
	elapsed = timediff(t5,t6);
	//printf("ReadWriteSeqMByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteSeqMByte operation %lf  milliseconds\n", elapsed);
	totaldata= (double)(no_of_threads*100*1024*1024)/1000000;
		latency= (double)(elapsed/totaldata);
		fprintf(log, "Latency: time per operation %lf  \n", latency);

		//printf("ReadSeqKByte: %lf ms\n", elapsed);
		elapsed=elapsed/1000;
		throughput= (double)(totaldata/elapsed);	
		fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);

	//--------------ReadWriteRanByte-----------------------//
	//printf("\nReadWriteRanByte\n");
	clock_t t7, t8;
	t7 = clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]), NULL, readRanByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j], NULL);
	}
	t8 = clock();
	elapsed = timediff(t7, t8);
	//printf("ReadWriteRanByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteRanByte operation %lf  milliseconds\n", elapsed);

	totaldata= (double)(no_of_threads*100000000*1)/1000000;
		latency= (double)(elapsed/totaldata);
		fprintf(log, "Latency: time per operation %lf  \n", latency);
			
		//printf("Throughput: operation per milliseconds %lf  \n", throughput);
		elapsed=elapsed/1000;
		throughput= (double)(totaldata/elapsed);	
		fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	
	
	//--------------ReadWriteRanKByte-----------------------//
	//printf("\nReadWriteRanKByte\n");
	clock_t t9, t10;
	t9 = clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]), NULL, readRanKByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j], NULL);
	}
	t10 = clock();
	elapsed = timediff(t9, t10);
	//printf("ReadWriteRanKByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteRanKByte operation %lf  milliseconds\n", elapsed);
	totaldata= (double)(no_of_threads*100000*1024)/1000000;
		latency= (double)(elapsed/totaldata);
		fprintf(log, "Latency: time per operation %lf  \n", latency);
			
		//printf("ReadSeqKByte: %lf ms\n", elapsed);
		elapsed=elapsed/1000;
		throughput= (double)(totaldata/elapsed);	
		fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
		//printf("Throughput: operation per milliseconds %lf  \n", throughput);

	//--------------ReadWriteRanMByte-----------------------//
	//printf("\nReadWriteRanMByte\n");
	clock_t t11, t12;
	t11 = clock();
	for (i = 0; i < no_of_threads; i++)
	{
		pthread_create(&(tid[i]), NULL, readRanMByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++)
	{
		pthread_join(tid[j], NULL);
	}
	t12 = clock();
	elapsed = timediff(t11, t12);
	//printf("ReadWriteRanMByte operation %lf  milliseconds\n", elapsed);
	fprintf(log, "ReadWriteRanMByte operation %lf  milliseconds\n", elapsed);
	totaldata= (double)(no_of_threads*100*1024*1024)/1000000;
		latency= (double)(elapsed/totaldata);
		fprintf(log, "Latency: time per operation %lf  \n", latency);

		//printf("ReadSeqKByte: %lf ms\n", elapsed);
		elapsed=elapsed/1000;
		throughput= (double)(totaldata/elapsed);	
		fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
		//printf("Throughput: operation per milliseconds %lf  \n", throughput);		
		
		
	fclose(log);
    return 0;
}
