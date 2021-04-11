FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim

COPY --from=build /home/app/target/challenge-1.0.0.jar /usr/local/lib/challenge.jar

WORKDIR /usr/local/lib

RUN sh -c 'touch challenge.jar'

ENTRYPOINT ["java","-jar","challenge.jar"]
