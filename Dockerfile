FROM openjdk:21

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY . .

VOLUME /app/storefile



ENTRYPOINT ["java","-jar","-Dspring.profiles.acvice=prod","app.jar"]
