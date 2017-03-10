sudo apt-get update
sudo apt-add-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

sudo apt-get install ssh

sudo apt-get install ant

cd /home/ubuntu/LocalWorkerCloud
ant
cd /home/ubuntu/Utility
ant

echo ""
echo "**********************Throughput operations*********************"
echo ""
echo "**********************  0 ms tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100000 0
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 1 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100000 0
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 2 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100000 0
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 4 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100000 0
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 8 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100000 0
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 16 -w /home/ubuntu/LocalWorkerCloud/workload

echo ""
echo "**********************Efficiency operations*********************"
echo ""
echo "**********************  10 ms tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 1000 10
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 1 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 2000 10
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 2 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 4000 10
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 4 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 8000 10
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 8 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 16000 10
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 16 -w /home/ubuntu/LocalWorkerCloud/workload

echo ""
echo "**********************Efficiency operations*********************"
echo ""
echo "**********************  1 second tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100 1000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 1 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 200 1000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 2 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 400 1000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 4 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 800 1000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 8 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 1600 1000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 16 -w /home/ubuntu/LocalWorkerCloud/workload

echo ""
echo "**********************Efficiency operations*********************"
echo ""
echo "**********************  10 second tasks	**********************"

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 10 10000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 1 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 20 10000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 2 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 40 10000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 4 -w /home/ubuntu/LocalWorkerCloud/workload


java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 80 10000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 8 -w /home/ubuntu/LocalWorkerCloud/workload

java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 160 10000
java -cp /home/ubuntu/LocalWorkerCloud/build/jar/Local.jar LocalClient Client -s LOCAL -t 16 -w /home/ubuntu/LocalWorkerCloud/workload



