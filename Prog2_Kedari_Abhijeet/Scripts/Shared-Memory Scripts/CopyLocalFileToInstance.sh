#!/bin/bash

chmod 400 abhijeetkedari.pem
ssh-add abhijeetkedari.pem
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/abhijeetkedari.pem ubuntu@52.207.244.177:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/gensort-linux-1.5.tar.gz ubuntu@52.207.244.177:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/SharedMemoryCode.tar.gz ubuntu@52.207.244.177:/home/ubuntu

chmod 777 InstallationScript.sh

scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/SharedMemoryCode/InstallationScript.sh ubuntu@52.207.244.177:/home/ubuntu


