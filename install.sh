#create network
docker network create network4cash

#start the db
docker pull mysql:latest
docker run --name mysql -p 3306:3306 --net=network4cash --net-alias=mysql -e MYSQL_ROOT_PASSWORD=vovan -d mysql:latest

#start the back end
cd backend4cash/
mvn clean install -Dspring.profiles.active=production
docker build --build-arg SPRING_ACTIVE_PROFILE=production -t backend4cash:prod .
docker container run -d --name backend4cash_prod --net=network4cash --net-alias=backend4cash_prod -p 8069:8080 -e SPRING_PROFILES_ACTIVE=production backend4cash:prod

#start the frontend
cd ..
cd frontend4cash/
npm i
npm ci
ng build --prod=true

docker build -t frontend4cash:prod .
docker container run -d --name frontend4cash_prod --net=network4cash --net-alias=frontend4cash_prod -p 4003:80 frontend4cash:prod
