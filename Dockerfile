FROM gradle:jdk21 as builder

WORKDIR /app
COPY . .

RUN gradle build

FROM openjdk:21



VOLUME /app/storefile

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
