FROM maven:3.5-jdk-8-alpine AS build

WORKDIR /crud-service

COPY pom.xml /crud-service/pom.xml
#RUN ["mvn", "dependency:resolve"]

# Adding source, compile and package into a fat jar
COPY ["src/main", "/crud-service/src/main"]
RUN ["mvn", "package"]

FROM openjdk:8-jre-alpine

COPY --from=build /crud-service/target/crud-service.jar /
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/crud-service.jar"]
EXPOSE 8080