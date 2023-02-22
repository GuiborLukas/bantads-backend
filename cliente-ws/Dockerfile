FROM maven:3.8.6-jdk-11-slim AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean install -DskipTests


FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENV DATABASE_HOST=localhost
ENV RABBITMQ_HOST=localhost
ENV DATABASE_PORT=5432
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
EXPOSE 8080