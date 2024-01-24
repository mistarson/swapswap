FROM openjdk:17 as build

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV TZ Asia/Seoul

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
