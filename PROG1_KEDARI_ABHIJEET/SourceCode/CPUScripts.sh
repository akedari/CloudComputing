
clear

gcc -o myCPU CPUBenchmarkF.c -lpthread
./myCPU 1

gcc -o myCPU CPUBenchmarkF.c -lpthread
./myCPU 2

gcc -o myCPU CPUBenchmarkF.c -lpthread
./myCPU 4

gcc -o myCPU CPUBenchmarkI.c -lpthread
./myCPU 1


gcc -o myCPU CPUBenchmarkI.c -lpthread
./myCPU 2


gcc -o myCPU CPUBenchmarkI.c -lpthread
./myCPU 4


gcc -o myCPU BenchmarkF.c -lpthread
./myCPU 4


gcc -o myCPU BenchmarkI.c -lpthread
./myCPU 4


exit 0
