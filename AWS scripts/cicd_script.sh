#!/bin/bash
echo running script!
sudo -S docker stop fitnessmateback;
sudo -S docker rm fitnessmateback;
sudo -S docker pull chanhalee/fitnessmateback:latest;
sudo -S docker run -d -p 8080:8080 -v /volume1/docker/fitnessmate:/fitnessmate --name fitnessmateback chanhalee/fitnessmateback:latest;