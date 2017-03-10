#!/bin/bash

eval "$(ssh-agent)"
cd /home/ubuntu
ssh-add abhijeetkedari.pem
ssh-keygen  -t rsa
cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys
ssh-copy-id -i ~/.ssh/id_rsa.pub ubuntu@ec2-54-172-180-220.compute-1.amazonaws.com
chmod 600 ~/.ssh/authorized_keys
cd hadoop-2.7.2/


sudo mkfs.ext4 /dev/xvdf
sudo mke2fs -F  -t  ext4 /dev/xvdf
sudo mkdir /data
sudo mount /dev/xvdf /data
sudo chmod 777 /data



bin/hadoop namenode -format
cd /home/ubuntu/hadoop-2.7.2/sbin
./start-dfs.sh  
./start-yarn.sh
jps


cd /home/ubuntu/gensort-linux-1.5/64
./gensort -a 100000000 /data/input

cd /home/ubuntu/hadoop-2.7.2
bin/hadoop fs -mkdir /abhijeet
bin/hadoop dfs -copyFromLocal /data/input /abhijeet	
rm -rf /data/input

bin/hadoop jar /home/ubuntu/TeraSortJob.jar /abhijeet/input /abhijeet/output

bin/hadoop dfs -get /abhijeet/output/part-r-00000 /data/

cd /home/ubuntu/gensort-linux-1.5/64
./valsort /data/part-r-00000

cd /data/
head -10 part-r-00000 >>/data/head10
tail -10 part-r-00000 >>/data/tail10
