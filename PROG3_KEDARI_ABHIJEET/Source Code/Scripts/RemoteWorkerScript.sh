sudo apt-get update
sudo apt-add-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

sudo apt-get install ssh

sudo apt-get install ant


*************************	On Worker Side	******************************


cd /home/ubuntu/CloudKonRemoteWorker
ant

java -cp /home/ubuntu/CloudKonRemoteWorker/build/jar/RemoteWorker.jar SQSRemoteWorker client -s SOURCEQUEUE -t 1



*************************	On Client Side	*****************************

ON CLient side Generate workload first

echo "**********************  0 ms tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 10000 0


cd /home/ubuntu/CloudKonClient
ant
java -cp /home/ubuntu/CloudKonClient/build/jar/ClientSide.jar SQSClient client -s SOURCEQUEUE -w workload

**********************************************************************************************************************************************************
echo "**********************  10 ms tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 1000 10

cd /home/ubuntu/CloudKonClient
ant
java -cp /home/ubuntu/CloudKonClient/build/jar/ClientSide.jar SQSClient client -s SOURCEQUEUE -w workload


**********************************************************************************************************************************************************
echo "**********************  1 second tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 100 1000

cd /home/ubuntu/CloudKonClient
ant
java -cp /home/ubuntu/CloudKonClient/build/jar/ClientSide.jar SQSClient client -s SOURCEQUEUE -w workload


**********************************************************************************************************************************************************
echo "**********************  10 second tasks	**********************"
java -cp /home/ubuntu/Utility/build/jar/Utility.jar TaskCreation 10 10000


cd /home/ubuntu/CloudKonClient
ant
java -cp /home/ubuntu/CloudKonClient/build/jar/ClientSide.jar SQSClient client -s SOURCEQUEUE -w workload


