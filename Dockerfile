FROM openjdk:21-jdk-slim

EXPOSE 8080

ENV APP_HOME /usr/src/app

COPY target/ChatApp-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

ENTRYPOINT exec java -jar app.jar
