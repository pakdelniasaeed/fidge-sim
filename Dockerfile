FROM openjdk:17-jre
ENV SERVER_PORT=80
EXPOSE 80
VOLUME /data
COPY ./target/fridge-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","fridge-0.0.1-SNAPSHOT.jar"]
