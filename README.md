Prerequisites:
- JDK 1.8
- MySQL
- Gradle
- *Optional* Docker (and docker-machine + virtualbox if not running on Linux)

1) Setup
 1.1) Export variables used in SpringBoot's application.properties file:
  export DB_HOST=localhost
  export DB_PORT=3306
  export DB_NAME=springdb
  export DB_USERNAME=dbuser
  export DB_PASSWORD=dbpassword

  1.2) Setup DB:
  echo "create database $DB_NAME;" | mysql -uroot
  echo "create user '$DB_USERNAME'@'$DB_HOST' identified by '$DB_PASSWORD';" | mysql -uroot
  echo "grant all on $DB_NAME.* to '$DB_USERNAME'@'$DB_HOST';" | mysql -uroot

2) Run tests: gradle test

3) Start the application server locally: gradle bootRun

4) Running with docker:
 4.1) Create a local VM (once only): docker-machine create --driver virtualbox default && docker-machine env default && eval $(docker-machine env default)
 4.2) Create jar: gradle bootJar
 4.3) Copy jar to docker directory: cp build/libs/<JARFILE>.jar docker/springapp/app.jar
 4.4) Start docker: cd docker && docker-compose up (You must have executed the export commands above)

5) Testing the API using CURL:
 5.1) Acquire ip by running "docker-machine ip default" if using docker, else use "localhost"
 5.2) Create sentence:
    curl -v -X POST -H "Content-Type: application/json" -d '{"sentence":"The red fox crosses the ice, intent on none of my business."}' localhost:8080/api/sentence
 5.3) List sentences:
   curl -v -X GET localhost:8080/api/sentence/list
 5.4) Delete sentence:
   curl -v -X DELETE localhost:8080/api/sentence?externalId="444-11-23123"

