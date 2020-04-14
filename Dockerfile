
#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn package -DskipTests -f /home/app/pom.xml


# Package stage

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/trello-0.0.1-SNAPSHOT.jar /usr/local/lib/trello-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/trello-0.0.1-SNAPSHOT.jar"]
