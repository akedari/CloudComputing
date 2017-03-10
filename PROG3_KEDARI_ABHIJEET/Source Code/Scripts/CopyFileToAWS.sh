
//How to connect to AWS instance


cd /home/abhijeet/workspace
chmod 400 abhijeetkedari.pem
ssh-add abhijeetkedari.pem

worker
ssh ubuntu@54.164.127.154

client
ssh ubuntu@54.164.214.73

********************************************************* LOCAL  **************************************

scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/LocalWorkerCloud ubuntu@52.23.166.253:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/Utility ubuntu@52.23.166.253:/home/ubuntu
scp -i abhijeetkedari.pem /home/abhijeet/workspace/localworkerscript.sh ubuntu@52.23.166.253:/home/ubuntu


scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/LocalWorkerCloud.tar.gz ubuntu@52.23.166.253:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/Utility.tar.gz ubuntu@52.23.166.253:/home/ubuntu

./localworkerscript.sh


--------------------------------------------------------------------------------------------

*******************************************	REMOTE 	***************************************

--------------------------------client--------------------------------------
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/Utility ubuntu@54.164.214.73:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonClient ubuntu@54.164.214.73:/home/ubuntu

--------------------------------worker--------------------------------------
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.87.183.106:/home/ubuntu

scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.90.57.75:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.91.233.114:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.207.211.121:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.205.250.68:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.90.84.83:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.23.181.161:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.205.254.70:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.87.241.63:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.91.114.245:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.91.107.99:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.90.89.253:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.90.86.171:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.23.233.136:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.91.24.62:/home/ubuntu
scp -i abhijeetkedari.pem -r /home/abhijeet/workspace/CloudKonRemoteWorker ubuntu@52.87.176.155:/home/ubuntu

