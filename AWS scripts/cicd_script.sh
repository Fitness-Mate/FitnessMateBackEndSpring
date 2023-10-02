#!/bin/bash
echo running script!
sudo -S docker stop fitnessmateback;
sudo -S docker rm fitnessmateback;
sudo -S docker pull chanhalee/fitnessmateback:latest;
sudo -S docker run -d -p 8080:8080 -v /volume1/docker/fitnessmate:/fitnessmate --name fitnessmateback chanhalee/fitnessmateback:0.58.1;
docker run -d -p 8080:8080 -m=1000m --memory-swap=1000m --cpu-shares 800 -v /volume1/docker/fitnessmate:/fitnessmate --name fitnessmateback chanhalee/fitnessmateback:0.66.1;
docker run -d -p 8080:8080 -m=2000m --memory-swap=2000m --cpu-shares 1600 -v /volume1/docker/fitnessmate:/fitnessmate --name fitnessmateback2 chanhalee/fitnessmateback:0.66.1;