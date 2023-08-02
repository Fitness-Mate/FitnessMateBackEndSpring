#!/bin/bash

#sudo apt-get update
#sudo apt-get install ca-certificates
#sudo apt-get install apt-transport-https
#sudo apt-get install software-properties-common
#sudo apt-get install curl
#sudo apt-get install gnupg
#sudo apt-get install lsb-release
#
#sudo mkdir -p /etc/apt/keyrings
#curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
#
#echo \
#  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
#  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null


#도커 설치 커맨드
echo "docker installing start"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=$(dpkg --print-architecture)] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
echo "docker installing finish!"