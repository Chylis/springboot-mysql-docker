## Prerequisites:
* JDK 1.8
* Gradle
* MySQL (if running locally)
* *Optional* docker and docker-compose (and docker-machine + virtualbox if not running on Linux)

## 1) Local Setup
### Export variables used in SpringBoot's application.properties file
```
  export DB_HOST=localhost
  export DB_PORT=3306
  export DB_NAME=springdb
  export DB_USERNAME=dbuser
  export DB_PASSWORD=dbpassword
```

### Setup a local DB
```
  echo "create database $DB_NAME;" | mysql -uroot
  echo "create user '$DB_USERNAME'@'$DB_HOST' identified by '$DB_PASSWORD';" | mysql -uroot
  echo "grant all on $DB_NAME.* to '$DB_USERNAME'@'$DB_HOST';" | mysql -uroot
```

## 2) Run tests locally
```
gradle test
```

## 3) Start the application server locally
```
gradle bootRun
```

## 4) Running with docker:

### 1) Create a local VM (once only)
```
docker-machine create --driver virtualbox default && docker-machine env default && eval $(docker-machine env default)
```

### 2) Create jar and copy it to the docker directory
```
gradle bootJar && cp build/libs/mag-spring-boot-0.1.0.jar docker/springapp/app.jar
```

### 3) Start docker
* Execute the export commands from step 1 above if you have not already done so
```
cd docker && docker-compose up
```


## 5) Testing the API using CURL:

### 1) Acquire ip by running "docker-machine ip default" if using docker, else use "localhost"
```
SPRINGBOOT_IP=<ENTER_IP_HERE>
```

### 2) Create a sentence
```
curl -v -X POST -H "Content-Type: application/json" -d '{"sentence":"The red fox crosses the ice, intent on none of my business."}' $SPRINGBOOT_IP:8080/api/sentence
```

### 3) List sentences
```
curl -v -X GET $SPRINGBOOT_IP:8080/api/sentence/list
```

### 4) Delete a sentence
```
curl -v -X DELETE $SPRINGBOOT_IP:8080/api/sentence?externalId="<EXTERNAL_ID>"
```

    
## 6) Misc
* If you for some reason change the database username/password and getting an access denied exception from Springboot  you'll probably need to remove the docker volume by running
```
docker compose rm -v && docker volume rm docker_mysql-data
```

* To start fresh, remove previous containers and images


