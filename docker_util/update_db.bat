@echo off
docker cp create_db.js scala_task_db:/data/create_db.js
docker exec -it scala_task_db mongo /data/create_db.js
pause
@echo on