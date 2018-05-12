@echo off
docker run --name scala_task_db -d -p 27017:27017 scala_mongo
PAUSE
@echo on