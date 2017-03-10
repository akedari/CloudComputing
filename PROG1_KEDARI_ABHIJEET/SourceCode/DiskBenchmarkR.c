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

#define BUFFER_SIZE (1*1024*10240)
// this is buffered space we are using for reading and writing purpose form Disk and to disk 
char buffer[1*1024*1024];

// this is to Read a byte data sequencially from disk
void* readSeqByte(void *arg)
{
	FILE *fp;
	int i;
	fp=fopen("DiskWrite.txt", "r");
	char buffer[1024*1024];
	fseek(fp,0,SEEK_SET);
	for(i=0;i<100000000;i++) // 100mb file
	{
		fread(buffer,1,1,fp);
	}
	fclose(fp);
}

// this is to Read a Kilo byte data sequencially from disk
void* readSeqKByte(void *arg)
{
	FILE *fp;
	int i;
	fp=fopen("DiskWrite.txt", "r");
	char buffer[1024*1024];
	fseek(fp,0,SEEK_SET);
	for(i=0;i<100000;i++) // 100mb file
	{
		fread(buffer,1,1024,fp);
	}
	fclose(fp);
}

// this is to Read a mega byte data sequencially from disk
void* readSeqMByte(void *arg)
{
	FILE *fp;
	int i;
	fp = fopen("DiskWrite.txt", "r");
	char buffer[1024 * 1024];
	fseek(fp, 0, SEEK_SET);
	for (i = 0; i < 100; i++) // 100mb file
	{
		fread(buffer, 1024, 1024, fp);
	}
	fclose(fp);
}

// this is to Read a byte data Randomly from disk
void* readRandByte(void *arg)
{
	FILE *fp;
	int i,r,offset;
	fp = fopen("DiskWrite.txt", "r");
	char buffer[1024 * 1024];
	
	int size_of_file=*(int *)arg;
	
	for (i = 0; i < 10000000; i++) // 10mb file
	{
		r=rand();
		offset = r % size_of_file;
		fseek(fp, offset, SEEK_SET);
		fread(buffer, 1, 1, fp);
	}
	fclose(fp);
}

// this is to Read a kilo byte data Randomly from disk
void* readRandKByte(void *arg)
{
	FILE *fp;
	int i,r,offset;
	fp=fopen("DiskWrite.txt", "r");
	char buffer[1024*1024];
	
	int size_of_file=*(int *)arg;
	size_of_file= size_of_file - 1024;
	
	//fseek(fp,0,SEEK_SET);
	for(i=0;i<10000;i++) // 10mb file
	{
		r = rand();
		offset = r % size_of_file;
		fseek(fp, offset, SEEK_SET);
		fread(buffer,1,1024,fp);
	}
	fclose(fp);
}

// this is to Read a Mega byte data Randomly from disk
void* readRandMByte(void *arg)
{
	FILE *fp;
	int i,r,offset;
	fp = fopen("DiskWrite.txt", "r");
	char buffer[1024 * 1024];
	
	int size_of_file=*(int *)arg;
	size_of_file= size_of_file - (1024*1024);
	
	for (i = 0; i < 10; i++) // 1mb file
	{
		r = rand();
		offset = r % size_of_file;
		fseek(fp, offset, SEEK_SET);
		fread(buffer, 1024, 1024, fp);
	}
	fclose(fp);
}

// Return the time difference between two clocks	
double timediff(clock_t t1, clock_t t2) {
	double elapsed;
    elapsed = ((double)t2 - t1) / CLOCKS_PER_SEC * 1000;
    return elapsed;
}


int main(int argc, char *argv[]) {

	FILE *fp,*log;
	fp=fopen("DiskWrite.txt", "r");
	log=fopen("Disk_log.txt", "a+");
	
	int i,j,cnt=0,EndPos,StartPos,Size_file;
	size_t size = 1;
	double latency,throughput,totaldata,elapsed;
	int number_of_operation=1;
	
	char str[]= "Abhijeet Kedari";
	
	fseek(fp, 0, SEEK_END);
	EndPos=ftell(fp);
	fseek(fp, 0, SEEK_SET);
	StartPos=ftell(fp);
	Size_file = (EndPos - StartPos);
	
	int no_of_threads=atoi(argv[1]);
	pthread_t tid[no_of_threads];
	
	
	//--------------ReadSeqByte-----------------------//
	clock_t t1,t2;
	t1=clock();
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,readSeqByte,NULL);
	}
	for(j=0;j<no_of_threads;j++)
	{
		pthread_join(tid[j],NULL);
	}
	t2=clock();
	elapsed = timediff(t1,t2);
	fprintf(log,"ReadSeqByte: %lf\n",elapsed);

	//printf("ReadSeqByte: %lf ms\n", elapsed);
	totaldata= (double)(no_of_threads*100000000*1)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
	
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	elapsed=elapsed/1000;
	throughput= (double)(totaldata/elapsed);	
	fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	
	//--------------ReadSeqKByte-----------------------//
	clock_t t3,t4;
	t3=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, readSeqKByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t4=clock();
	elapsed = timediff(t3,t4);
	fprintf(log,"ReadSeqKByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100000*1024)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
	
	//printf("ReadSeqKByte: %lf ms\n", elapsed);
	elapsed=elapsed/1000;
	throughput= (double)(totaldata/elapsed);	
	fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
		
	
	//--------------ReadSeqMByte-----------------------//
	clock_t t5,t6;
	t5=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, readSeqMByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t6=clock();
	elapsed = timediff(t5,t6);
	fprintf(log,"ReadSeqMByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100*1024*1024)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);

	//printf("ReadSeqKByte: %lf ms\n", elapsed);
	elapsed=elapsed/1000;
	throughput= (double)(totaldata/elapsed);	
	fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	
	
		
	//--------------ReadRandByte-----------------------//
	clock_t t7,t8;
	t7=clock();
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,readRandByte,&Size_file);
	}
	for(j=0;j<no_of_threads;j++)
	{
		pthread_join(tid[j],NULL);
	}
	t8 = clock();
	elapsed = timediff(t7, t8);
	//printf("ReadRandByte: %lf ms\n", elapsed);
	fprintf(log,"\tReadRandByte: %lf\n",elapsed);
	//printf("ReadSeqByte: %lf ms\n", elapsed);
	
	totaldata= (double)(no_of_threads*100000000*1)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
		
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	elapsed=elapsed/1000;
	throughput= (double)(totaldata/elapsed);	
	fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
		
	
	//--------------ReadRandKByte-----------------------//
	clock_t t9,t10;
	t9=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, readRandKByte, &Size_file);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t10 = clock();
	elapsed = timediff(t9, t10);
	fprintf(log,"\tReadRanKByte: %lf\n",elapsed);

	totaldata= (double)(no_of_threads*100000*1024)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
		
	//printf("ReadSeqKByte: %lf ms\n", elapsed);
	elapsed=elapsed/1000;
	throughput= (double)(totaldata/elapsed);	
	fprintf(log,"Throughput: operations(mb) per seconds %lf  \n", throughput);
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
		
		
	//--------------ReadRandMByte-----------------------//
	clock_t t11,t12;
	t11=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, readRandMByte, &Size_file);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t12 = clock();
	elapsed = timediff(t11, t12);
	fprintf(log,"\tReadRandByte: %lf\n",elapsed);
	
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
