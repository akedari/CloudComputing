
clear

gcc -o mydisk DiskBenchmarkW.c -lpthread
./mydisk 1


gcc -o mydisk DiskBenchmarkW.c -lpthread
./mydisk 2


gcc -o mydisk DiskBenchmarkR.c -lpthread
./mydisk 1


gcc -o mydisk DiskBenchmarkR.c -lpthread
./mydisk 2


exit 0
