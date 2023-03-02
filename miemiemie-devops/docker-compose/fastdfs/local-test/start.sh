sudo docker run -d --name tracker --network=host -v ~/fastdfs/tracker:/var/fdfs delron/fastdfs tracker
sudo docker run -d --name storage --network=host -e TRACKER_SERVER=主机ip:22122 -v ~/fastdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage
