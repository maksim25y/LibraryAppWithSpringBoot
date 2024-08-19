FROM maven:3.8 AS build
COPY . /app
WORKDIR /app
RUN mvn package

FROM openjdk:21
COPY --from=build /app/target/Project2Boot-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]