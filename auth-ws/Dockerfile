FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY target/auth-ws-*.jar app.jar
ENV MONGO_HOST=localhost
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
EXPOSE 8080