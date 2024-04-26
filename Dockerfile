FROM openjdk:21

WORKDIR /app

CMD ["./gradlew", "clean", "build"]

VOLUME /app/storefile

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
