

There are 3 part of this PA1 (Programming Assignment 1)

A. CPU

1. To run the CPU Benchmark code, you first of all need to install gcc in Linux Enviornment OR C complier in Windows
2. After copying the code to desired location, open the terminal and navigate to the source folder location
3. In the terminal under the CPUBenchmark folder type below command to compile the code and then run it.
	
	//to run the Floating point bechanchmark
	gcc -o myCPU CPUBenchmarkF.c -lpthread
	./myCPU 1

	//to run the Integer point bechanchmark
	gcc -o myCPU CPUBenchmarkI.c -lpthread
	./myCPU 2

	//to run the 10 min Experiment for Floating Point Operations bechanchmark
	gcc -o myCPU BenchmarkF.c -lpthread
	./myCPU 4

	//to run the 10 min Experiment for Integer Point Operations bechanchmark
	gcc -o myCPU BenchmarkI.c -lpthread
	./myCPU 4


4. There no  GUI or command line interaction with end user here, so you just need to take care of command line argument as mentioned above (./myCPU 1)
   here "1" at the end of command represent the "number of threads"
5. Output of the code is log file named "log_for_Float.txt" "log_for_Integer.txt" "log_for_Experiment_Floating.txt" and "log_for_Experiment_Integer.txt"
6. Each file contain the number of Giga operations count (Floating or Integer) i.e. GFLOPS or GIOPS.
7. Along with GFLOP/GIOPS you will find the Latency and Througput calculated for that specific operation.
8. If you want to run run all yo can use .sh file file contained all command scripted inside, so you can directly fire all command like

	sh ./CPUScripts.sh


B. Disk

1. To run the Disk Benchmark code, you first of all need to install gcc in Linux Enviornment OR C complier in Windows
2. After copying the code to desired location, open the terminal and navigate to the source folder location
3. In the terminal under the DiskBenchmark folder type below command to compile the code and then run it.
	
	//Write Operation with 1 thread
	gcc -o mydisk DiskBenchmarkW.c -lpthread
	./mydisk 1

	//Write Operation with 2 threads
	gcc -o mydisk DiskBenchmarkW.c -lpthread
	./mydisk 2

	//Read Operation with 1 thread
	gcc -o mydisk DiskBenchmarkR.c -lpthread
	./mydisk 1

	//Read Operation with 2 threads
	gcc -o mydisk DiskBenchmarkR.c -lpthread
	./mydisk 2



4. There no  GUI or command line interaction with end user here, so you just need to take care of command line argument as mentioned above (./mydisk 1)
   here "1" at the end of command represent the "number of threads"
5. Output of the code is log file named "Disk_log.txt"
6. File contain the Read/Write operations time in milliseconds as well as you will find the Latency and Througput calculated for that specific operation.
8. If you want to run run all yo can use .sh file file contained all command scripted inside, so you can directly fire all command like

	sh ./DISKScripts.sh



C.Memory

1. To run the Memory Benchmark code, you first of all need to install gcc in Linux Enviornment OR C complier in Windows
2. After copying the code to desired location, open the terminal and navigate to the source folder location
3. In the terminal under the CPUBenchmark folder type below command to compile the code and then run it.
	
	//Read+Write Operation with 1 thread
	gcc -o myMemory MemoryBenchmark.c -lpthread
	./myMemory 1

	//Read+Write Operation with 2 threads
	gcc -o myMemory MemoryBenchmark.c -lpthread
	./myMemory 2



4. There no  GUI or command line interaction with end user here, so you just need to take care of command line argument as mentioned above (./myMemory 1)
   here "1" at the end of command represent the "number of threads"
5. Output of the code is log file named "Memory_log.txt"
6. File contain the Read/Write operations time in milliseconds as well as you will find the Latency and Througput calculated for that specific operation.
7. If you want to run run all yo can use .sh file file contained all command scripted inside, so you ca directly fire the command like

	sh ./MemoryScripts.sh

