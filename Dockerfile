# Stage 1: BUILD
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package -q -Dmaven.test.skip=true

# Stage 2: RUN
FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /app/target/markr-0.0.1-SNAPSHOT.jar /markr.jar
ENTRYPOINT ["java", "-jar", "/markr.jar"]