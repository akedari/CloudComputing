
#!/bin/bash

sudo wget http://apache.mirrors.pair.com/hadoop/common/stable2/hadoop-2.7.2.tar.gz
tar -xzvf hadoop-2.7.2.tar.gz
tar -xzvf gensort-linux-1.5.tar.gz
chmod 777 hadoop-2.7.2
sudo apt-get install ssh

eval "$(ssh-agent)"
chmod 400 abhijeetkedari.pem

sudo apt-get  update
sudo apt-get install gcc
sudo apt-get update
sudo apt-get install openjdk-7-jdk

