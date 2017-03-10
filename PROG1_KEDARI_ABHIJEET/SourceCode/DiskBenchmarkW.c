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

char buffer[1024*1024];


// this is to write a byte data sequencially on disk
void* writeSeqByte(void *arg)
{
	FILE *fpw;
	int i;
	fpw=fopen("DiskWrite.txt", "a");
	//char buffer[1024*1024];
	fseek(fpw,0,SEEK_SET);
	for(i=0;i<100000000;i++) // 100mb file
	{
		//fread(buffer,1,1,fp);
		fwrite(buffer,1,1,fpw);
	}
	fclose(fpw);
}

// this is to write a Kilo byte data sequencially on disk
void* writeSeqKByte(void *arg)
{
	FILE *fpw;
	int i;
	fpw=fopen("DiskWrite.txt", "a");
	//char buffer[1024*1024];
	fseek(fpw,0,SEEK_SET);
	for(i=0;i<100000;i++) // 100mb file
	{
		//fread(buffer,1,1024,fp);
		fwrite(buffer,1,1024,fpw);
	}
	fclose(fpw);
}

// this is to write a Mega byte data sequencially on disk
void* writeSeqMByte(void *arg)
{
	FILE *fpw;
	int i;
	fpw = fopen("DiskWrite.txt", "a");
	//char buffer[1024 * 1024];
	fseek(fpw, 0, SEEK_SET);
	for (i = 0; i < 100; i++) // 100mb file
	{
		//fread(buffer, 1024, 1024, fp);
		fwrite(buffer,1024,1024,fpw);
	}
	fclose(fpw);
}

// this is to write a byte data Randomly on disk
void* writeRandByte(void *arg)
{
	FILE *fpw;
	int i,r,offset;
	fpw = fopen("DiskWrite.txt", "a");
	
		
	for (i = 0; i < 100000000; i++) // 100mb file
	{
		r = rand();
		offset = r % 1000;
		fseek(fpw, offset, SEEK_SET);
		fwrite(buffer, 1, 1, fpw);
	}
	fclose(fpw);
}

// this is to write a Kilo byte data Randomly on disk
void* writeRandKByte(void *arg)
{
	FILE *fpw;
	int i,r,offset;
	fpw = fopen("DiskWrite.txt", "a");
	//char buffer[1024*1024];
		
	for (i = 0; i < 100000; i++) // 100mb file
	{
		r = rand();
		offset = r % 1000;
		fseek(fpw, offset, SEEK_SET);
		fwrite(buffer, 1, 1024, fpw);
	}
	fclose(fpw);
}

// this is to write a Mega byte data Randomly on disk
void* writeRandMByte(void *arg)
{
	FILE *fpw;
	int i,r,offset;
	fpw = fopen("DiskWrite.txt", "a");
	//char buffer[1024 * 1024];
	
	for (i = 0; i < 100; i++) // 100mb file
	{
		r = rand();
		offset = r % 1000;
		fseek(fpw, offset, SEEK_SET);
		fwrite(buffer, 1024, 1024, fpw);
	}
	fclose(fpw);
}

// Return the time difference between two clocks	
long timediff(clock_t t1, clock_t t2) {
    long elapsed;
    elapsed = ((double)t2 - t1) / CLOCKS_PER_SEC * 1000;
    return elapsed;
}


int main(int argc, char *argv[]) {

	FILE *log;
	log=fopen("Disk_log.txt", "a+");
	//fpw=fopen("Disk_Write.txt", "w+");

	int i,j,cnt=0;
	size_t size = 1;
	int number_of_operation=1;
	double elapsed;
	double latency,throughput,totaldata;	
	char str[]= "Abhijeet Kedari";
	
	/*fseek(fp, 0, SEEK_END);
	EndPos = ftell(fp);
	fseek(fp, 0, SEEK_SET);
	StartPos = ftell(fp);
	Size_file = (EndPos - StartPos);
	printf("Size of File= %d BYTE\n\n", Size_file);*/
	
	int no_of_threads=atoi(argv[1]);
	pthread_t tid[no_of_threads];
	
	
	for(i=0;i<(1024*1024);i++)
	{
		buffer[i]='k';
	}
	

	//--------------WriteSeqByte-----------------------//
	clock_t t1,t2;
	t1=clock();
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,writeSeqByte,NULL);
	}
	for(j=0;j<no_of_threads;j++)
	{
		pthread_join(tid[j],NULL);
	}
	t2=clock();
	elapsed = timediff(t1,t2);
	fprintf(log,"WriteSeqByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100000000*1)/1000000;
	latency= (double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
	
	elapsed=elapsed/1000;
	throughput = (double)(totaldata/elapsed);		//As 1 thread writing 100 mb, 4 threads are writing 400 data... 
	fprintf(log, "Throughput: operations(mb) per seconds %lf  \n", throughput);
	
	
	//--------------WriteSeqKByte-----------------------//
	clock_t t3,t4;
	t3=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, writeSeqKByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t4=clock();
	elapsed = timediff(t3,t4);
	fprintf(log,"WriteSeqKByte: %lf\n",elapsed);

	totaldata= (double)(no_of_threads*100000*1024)/1000000;
	latency=(double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
	
	elapsed =elapsed /1000;
	throughput = (double)(totaldata)/elapsed;
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	//printf("\n totaldata= %lf",totaldata);
	fprintf(log, "Throughput: operations[mb] per seconds %lf  \n", throughput);
	
	
		//--------------WriteSeqMByte-----------------------//
	clock_t t5,t6;
	t5=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, writeSeqMByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t6=clock();
	elapsed = timediff(t5,t6);
	fprintf(log,"WriteSeqMByte: %lf\n",elapsed);

	totaldata= (double)(no_of_threads*100*1024*1024)/1000000;
	latency=(double)(elapsed/totaldata);
	fprintf(log, "Latency: time per operation %lf  \n", latency);
	
	elapsed =elapsed /1000;
	throughput = (double)(totaldata/elapsed);
	//printf("Throughput: operation(mb) per seconds %lf  \n", throughput);
	//printf("\n totaldata= %lf",totaldata);
	fprintf(log, "Throughput: operations(mb) per seconds %lf  \n", throughput);
		
	//--------------WriteRandByte-----------------------//
	clock_t t7,t8;
	t7=clock();
	for(i=0;i<no_of_threads;i++)
	{
		pthread_create(&(tid[i]),NULL,writeRandByte,NULL);
	}
	for(j=0;j<no_of_threads;j++)
	{
		pthread_join(tid[j],NULL);
	}
	t8 = clock();
	elapsed = timediff(t7, t8);
	fprintf(log,"\tWriteRandByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100000000*1)/1000000;
	latency=(double)(elapsed/totaldata);
	fprintf(log, "\tLatency: time per operation %lf  \n", latency);
	
	elapsed =elapsed /1000;
	throughput = (double)(totaldata/elapsed);
	fprintf(log, "\tThroughput: operations per milliseconds %lf  \n", throughput);
		
		
	//--------------WriteRandKByte-----------------------//
	clock_t t9,t10;
	t9=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, writeRandKByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t10 = clock();
	elapsed = timediff(t9, t10);
	fprintf(log,"\tWriteRandKByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100000*1024)/1000000;
	latency=(double)(elapsed/totaldata);
	fprintf(log, "\tLatency: time per operation %lf  \n", latency);
	//printf("WriteRandKByte: %lf ms\n", elapsed);

	elapsed =elapsed /1000;
	throughput =(double)(totaldata/elapsed);
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);
	fprintf(log, "\tThroughput: operations per milliseconds %lf  \n", throughput);
	
	
	//--------------WriteRandMByte-----------------------//
	clock_t t11,t12;
	t11=clock();
	for (i = 0; i < no_of_threads; i++) 
	{
		pthread_create(&(tid[i]), NULL, writeRandMByte, NULL);
	}
	for (j = 0; j < no_of_threads; j++) 
	{
		pthread_join(tid[j], NULL);
	}
	t12 = clock();
	elapsed = timediff(t11, t12);
	fprintf(log,"\tWriteRandMByte: %lf\n",elapsed);
	
	totaldata= (double)(no_of_threads*100*1024*1024)/1000000;
	latency=(double)(elapsed/totaldata);
	fprintf(log, "\tLatency: time per operation %lf  \n", latency);		
	
	//printf("WriteRandMByte: %lf ms\n", elapsed);
	elapsed =elapsed /1000;
	throughput = (double)(totaldata/elapsed);
	//printf("Throughput: operation per milliseconds %lf  \n", throughput);	
	fprintf(log, "\tThroughput: operations per milliseconds %lf  \n", throughput);
	
	fclose(log);
    return 0;


}
