#
# Build stage
#
FROM openjdk:21-jdk-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml
COPY .mvn /home/app/.mvn
COPY mvnw /home/app/mvnw
COPY mvnw.cmd /home/app/mvnw.cmd

WORKDIR /home/app

RUN ./mvnw -f /home/app/pom.xml clean install

#
# Run stage
#
FROM build AS run
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
