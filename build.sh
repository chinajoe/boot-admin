#!/bin/bash

PROJECT_NAME=boot-admin

# clone code
ssh -T git@gitee.com
git clone git@gitee.com:hnmy/boot-admin.git -b v4

# compile code
cd $PROJECT_NAME
mvn -U -DskipTests=true -DmavenCompileFork=true  -T 1C  clean package

# docker build
docker build . -t boot-admin:v3

# start up
docker-compose up -d
