1. use command "docker-compose -f mysqldb.yaml up" do download image of mysql database for docker.

2. get docker container id using command docker ps.

3. copy shema of dbskladiste to docker image using command " docker cp D:\Docker\db-docker-datastock\shema\datastock.sql dockerContainerId:.\var\lib\mysql\".

4. login into docker container using command "docker exec -it dockerContainerId bash".

5. go to dir where did u copy db shema '.\var\lib\mysql\'.

6. create database name like was previous name by command "mysqladmin -u root -p create mytestdatabase".

7. restore your db using nameOfDbShema.sql that u copied using command "mysql -u root -p datastock < datastock.sql"