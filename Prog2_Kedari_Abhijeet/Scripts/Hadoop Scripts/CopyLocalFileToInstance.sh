#!/bin/bash

chmod 400 abhijeetkedari.pem
ssh-add abhijeetkedari.pem
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/abhijeetkedari.pem ubuntu@54.172.180.220:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/gensort-linux-1.5.tar.gz ubuntu@54.172.180.220:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/HadoopCode/TeraSort/build/jar/TeraSortJob.jar ubuntu@54.172.180.220:/home/ubuntu

chmod 777 InstallationScript.sh
chmod 777 KeygenFormat.sh

scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/HadoopCode/InstallationScript.sh ubuntu@54.172.180.220:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/Downloads/CS553/Assignment2/HadoopCode/KeygenFormat.sh ubuntu@54.172.180.220:/home/ubuntu

