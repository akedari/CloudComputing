
#!/bin/bash

sudo apt-get install ssh
eval "$(ssh-agent)"
chmod 400 abhijeetkedari.pem

sudo apt-get  update -y
sudo apt-get install gcc -y
sudo apt-get update -y
sudo apt-get install openjdk-7-jdk -y
sudo apt-get install ant

tar -xzvf gensort-linux-1.5.tar.gz
tar -xzvf SharedMemoryCode.tar.gz


sudo mkfs.ext4 /dev/xvdf
sudo mke2fs -F  -t  ext4 /dev/xvdf
sudo mkdir /data
sudo mount /dev/xvdf /data
sudo chmod 777 /data


cp -r /home/ubuntu/SharedMemoryCode/ /data/


cd /home/ubuntu/gensort-linux-1.5/64
./gensort -a 100000000 /data/SharedMemoryCode/input


cd /data/SharedMemoryCode/
ant

cd /home/ubuntu/gensort-linux-1.5/64

