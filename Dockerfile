FROM openjdk:21-jdk-slim

EXPOSE 8080

ENV APP_HOME /usr/src/app

COPY /home/ubuntu/jenkins-slave/workspace/test-pipeline/messengerWeb/target/ChatApp-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar  # Update with the correct path to your .jar

WORKDIR $APP_HOME

ENTRYPOINT exec java -jar app.jar
