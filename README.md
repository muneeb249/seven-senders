How to execute the application
=========
In order to execute the applicaton, from the base directoy of the project
execute 

        docker-compose up

Solution
=========


Techstack
=========
* Java 11
* Apache Maven v3.6.xx
* Spring Framework (2.4.2)


Get started
===========

#### Prequisites
* JDK 11
* Apache Maven 3.5.xx
* Java IDE (Intellij IDEA recommended)
* Docker

#### Installation Build:
* To access the SWAGGER API, use the following URL

    
    <server>/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
    e.g
    http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

* use the following maven commands for building the project:

-> To execute the project execute the following command

    mvn clean install spring-boot:run

-> Command to build project and run Tests:
```
mvn clean install
```
-> Command to build project and skip skipTests:
```
mvn clean install -DskipTests
```
**Note:** All paths in this instruction are relative to project root folder.