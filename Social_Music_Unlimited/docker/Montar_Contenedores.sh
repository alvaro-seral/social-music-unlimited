#!/bin/bash

# Paramos y borramos la versión anterior del portal, si existe
docker stop sisinf-database
docker rm sisinf-database

cd postgres
unzip main.zip

sudo docker build -t sisinf/postgresql:latest .

sudo docker run --name sisinf-database -e ALLOW_EMPTY_PASSWORD=yes -d sisinf/postgresql:latest

cd ..

# Paramos y borramos la versión anterior del portal, si existe
docker stop sisinf-tomcat
docker rm sisinf-tomcat

cd tomcat
sudo docker build -t sisinf/tomcat:latest .

docker run -d --name sisinf-tomcat \
	--link sisinf-database \
	-p 8080:8080 \
	sisinf/tomcat:latest
