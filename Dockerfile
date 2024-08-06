FROM maven:3.8.8-amazoncorretto-17 as build

WORKDIR /app

COPY . .

RUN mvn clean package -X -DskipTests

FROM openjdk:17-ea-10-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build ./app/target/*.jar ./trilhabackendproject.jar


ENTRYPOINT [ "java", "-jar", "trilhabackendproject.jar" ]
