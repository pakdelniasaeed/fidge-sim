mvn clean
mvn package -DskipTests
docker build --no-cache=true -t fridge .
