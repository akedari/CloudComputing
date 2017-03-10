
readme.txt with explanations of code, which file contains what, and the commands needed to compile and execute the different scenarios

-----------------------------------------------------------------------------------------------------------------------------------------------
Shared-Memory: 

Files:
SharedMemory.java
SortFiles.java
MergeFiles.java

SharedMemory.java
	1. This file contains main class file which internally calling splitting of files (SortFiles.java) and merging of files class methods (MergeFiles.java).
	2. Calculating the starting time and ending time of whole process (Splitting files time + Merging files time)
	3. Used multi threading to generate multiple thread for splitting and merging file.
	4. To limit the concurrent execution of threads I have used ExecutorService to create thread pool and control them.
	5. divideFilesintoChunks() give call for SortFiles class, here we pass the size of small file. 
	6. mergeFileChunks() give call for MergeFiles class, here we pass 2 file name which has to be merge by Merge sort.
	7. To run the code for different number of threads we have to update the code file where we have mentioned thread numbers.
	
SortFiles.java
	1. This class has been extended by Thread to handle the multi threading
	2. Function is to open the input file and create small size of files with specified number of lines (records)
	3. To avoid more IO operations I have used strategy of reading and writing block of data by chunk. This chunk can easily get fit in to memory.
	4. This will minimise the IO operation and will increase CPU operations but it CPU operations are much faster than IO-Operation.
	5. Basic functionality is to create number of small size files, in short period of time.
	
MergeFiles.java
	1. This class has been extended by Thread to handle the multi threading.
	2. Function is to merge the 2 files and create a new sorted file as a output.
	3. Here we have read and write the file record by record so it is very time consuming operation. 

	
Steps for compile and execute the Shared-Memory on virtual cluster of 1 node.
Steps:
	1. Launch Amazon AWS c3.large instance and attach the EBS volume of size 200 GB to it.
	2. Go to the directory where you have saved CopyLocalFileToInstance.sh script on terminal
		cd /home/abhijeet/Downloads/CS553/Assignment2/SharedMemoryCode
	3. Update the CopyLocalFileToInstance.sh with master IP_Address (Instance) and source location for *.pem, gensort-linux-1.5.tar.gz, SharedMemoryCode.tar.gz and InstallationScript.sh
	4. Run CopyLocalFileToInstance.sh
		CopyLocalFileToInstance.sh this script will copy the local files to master node.
		This includes pem file, gensort, valsort, Shared-Memory code and InstallationScript.sh
		chmod 777 CopyLocalFileToInstance.sh
		./CopyLocalFileToInstance.sh
		
	5. Connect to Amazon Instance
		cd /home/abhijeet/Downloads/CS553/Assignment2/
		ssh-add abhijeetkedari.pem
		ssh ubuntu@IP_Address
		
	6. Run InstallationScript.sh 
		This script will install SSH, Java, ant on Amazon Instance. Also script will mount the EBS created with instance.
		Then gensort will create the input file and will save it on attached EBS volume 
		And finally will execute the Shared-Memory code by using Ant
	
		chmod 777 InstallationScript.sh
		./InstallationScript.sh
	
	7. Run valsort.sh
		To validate the resulted records, use valsort
		
		./valsort /data/SharedMemoryCode/output998
		
-----------------------------------------------------------------------------------------------------------------------------------------------
Hadoop:

Files:
TeraSort.java

TeraSort.java:
	1. This file contain main class TeraSort which internally contain 2 classes namely Map and Reduce which extends Mapper and Reducer respectively.
	2. Main class used to calculate the time taken for map and Reduce operations.
	3. In main class we have set up Job Name, jar by class, setOutputKeyClass, setOutputValueClass, setMapperClass, setReducerClass, setInputFormatClass, setOutputFormatClass
	4. We are taking source and destination address from command line arguments
	5. As we are running on Hadoop file system Map class  will receive input in form of offset address of line as a key and whole line as value.
	6. So map() in Map class will divide the input line (value) in Key-Value pairs and will send them to Reducer for further processing.
	7. In between mapper and Reducer, Combiner will sort and shuffle all the records and wil make in order.
	8. In Reducer phase will get order Key-value pair so Reducer will just emit them as it is they are coming.

Steps for compile and execute the Hadoop on virtual cluster of 1 node.
Steps:
	1. Launch Amazon AWS c3.large instance and attach the EBS volume of size 200 GB to it.
	2. Go to the directory where you have saved CopyLocalFileToInstance.sh script on terminal
		cd /home/abhijeet/Downloads/CS553/Assignment2/HadoopCode
	3. Update the CopyLocalFileToInstance.sh with master IP_Address (Instance) and source location for *.pem, gensort-linux-1.5.tar.gz, TeraSortJob.jar, KeygenFormat.sh and InstallationScript.sh
	4. Run CopyLocalFileToInstance.sh
		CopyLocalFileToInstance.sh this script will copy the local files to master node.
		This includes pem file, gensort, valsort, TeraSortJob.jar, InstallationScript.sh and KeygenFormat.sh.
		
		cd /home/abhijeet/Downloads/CS553/Assignment2/HadoopCode
		chmod 777 CopyLocalFileToInstance.sh
		./CopyLocalFileToInstance.sh
		
	5. Connect to Amazon Instance
		cd /home/abhijeet/Downloads/CS553/Assignment2/
		ssh-add abhijeetkedari.pem
		ssh ubuntu@IP_Address
		
	6.  Run InstallationScript.sh
		This script will install Hadoop, SSH, Java. 	
		chmod 777 InstallationScript.sh
		./InstallationScript.sh
		
	7. Update all configuration files
		Update below file for 1 node virtual cluster as files send in folder.
		core-site.xml, 
		hadoop-env.sh, 
		hdfs-site.xml, 
		yarn-site.xml,
		mapred-site.xml
		
	8. Update hosts and slave file
		slaves : public dns of master
		hosts  : private_ip and public_dns of master
		
	9. Run ./KeygenFormat.sh
		This script will generate rsa key
		Will mount external EBS volume
		Format namenode and ./start-dfs.sh  ./start-yarn.sh
		Will generate input file for sorting
		Will execute the source code
		After successful execution of code valsort will validate the result and head and tail command will get fired and generated output will get saved in separate files.
		

Steps for compile and execute the Hadoop on virtual cluster of 16 node.
Steps:
	1. Repeat step from 1 to 7 as mentioned for 1 node virtual cluster set up.
	2. Update hosts and slave file
	Master:   slaves : public dns of master and all the slaves
			  hosts  : private_ip and public_dns of master and all the slaves

	Slaves:   slaves : public dns of master and that slave
			  hosts  : private_ip and public_dns of master and that slave
			  
	3. Run ./KeygenFormat.sh
		This script will generate rsa key
		Will mount external EBS volume
		Format namenode and ./start-dfs.sh  ./start-yarn.sh
		Will generate input file for sorting
		Will execute the source code
		After successful execution of code valsort will validate the result and head and tail command will get fired and generated output will get saved in separate files.

-----------------------------------------------------------------------------------------------------------------------------------------------
Spark

Files:
abhijeetspark.scala
	This is main class, will calculate start and end time and difference between then i.e. Time required for execution. 
	Then code will take one file as input file, we have to provide the source directory for that input file. and put that file in map in form of Key and value.
	Then we have sort function to sort the contents of map and then put them to output file.

Steps for compile and execute the Spark on virtual cluster of 1 node.
Steps:
	1. Install Spark on Local / run script called Installspark.sh
		wget www-eu.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
		tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz
		cd spark-1.6.0-bin-hadoop2.6
		cd ec2
		
	2. Export Keys / Installspark.sh has included this
		export AWS_ACCESS_KEY_ID=XXXXXXXXXXXXXXXXXXXXXXXX
		export AWS_SECRET_ACCESS_KEY=XXXXXXXXXXXXXXXXXXXX
		
	3. Initialise the 1 nodes
		./spark-ec2 -k abhijeetkedari -i /home/abhijeet/Downloads/CS553/Assignment2/Spark/abhijeetkedari.pem -s 1 -t c3.large --spot-price=0.046 launch spark_instance
				
	4. Sending files to Master
	
		scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/Spark/valsort root@IP_Address:/root
		scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/Spark/gensort root@IP_Address:/root
		scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/Spark/abhijeetspark.scala root@IP_Address:/root
		
	5. Login to master
		./spark-ec2 -k abhijeetkedari -i /home/abhijeet/Downloads/CS553/Assignment2/Spark/abhijeetkedari.pem login spark_instance
		
	6. Mount EBS on master
		sudo mkfs.ext4 /dev/xvdp
		sudo mke2fs -F  -t  ext4 /dev/xvdp
		sudo mkdir /data
		sudo mount /dev/xvdp /data
		sudo chmod 777 /data
		
	7. genrate inout file
		./gensort -a 100000000 /data/input
		
	8. Make directory for HDFS
		cd /root/ephemeral-hdfs
		bin/hadoop fs -mkdir /abhijeet
		
	9. Copy file from EBX to HDFS
		./hadoop fs -Ddfs.replication=1 -put /data/input /abhijeet

	10. Execute code
		cd spark/bin
		./spark-shell -i /root/abhijeetspark.scala
		
	11. Validation
		First take file into EBS again
		bin/hadoop dfs -getmerge /abhijeet/output /data/output
		
		Then run valsort on output file
		./valsort /data/part-r-00000
		
	12. Take first 10 and last 10 line in separate files
		head -10 part-r-00000 >>/data/head
		tail -10 part-r-00000 >>/data/tail
		
Steps for compile and execute the Spark on virtual cluster of 16 node.
Steps:
	1. Repeat step 1 and 2 same as 1 node virtual cluster
	2. Initialise the 16 nodes
		./spark-ec2 -k abhijeetkedari -i /home/abhijeet/Downloads/CS553/Assignment2/Spark/abhijeetkedari.pem -s 16 -t c3.large --spot-price=0.046 launch spark_instance
	
	3. Repeat from step 4 to 12 same as 1 node virtual cluster
-----------------------------------------------------------------------------------------------------------------------------------------------