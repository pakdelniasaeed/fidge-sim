FROM openjdk:17-jdk-slim
ENV SERVER_PORT=80
EXPOSE 80
VOLUME /data
COPY ./target/fridge-0.0.1-SNAPSHOT.jar .
COPY ./target/front-build/ .
ENTRYPOINT ["java","-jar","fridge-0.0.1-SNAPSHOT.jar"]
