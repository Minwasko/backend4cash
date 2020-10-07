docker pull mysql:latest
#TODO: maybe make separate db for prod/dev
docker run --name mysql -p 3306:3306 --net=network4cash --net-alias=mysql -e MYSQL_ROOT_PASSWORD=vovan -d mysql:latest