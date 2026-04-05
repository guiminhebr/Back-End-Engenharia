FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests \
    -Dspring.datasource.url=jdbc:mysql://localhost/test \
    -Dspring.datasource.username=test \
    -Dspring.datasource.password=test \
    -Daws.accessKeyId=test \
    -Daws.secretKey=test \
    -Daws.region=us-east-1 \
    -Daws.bucketName=test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/Engenharia-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
