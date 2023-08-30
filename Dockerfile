FROM eclipse-temurin:17

LABEL maintainer="mak.jesus.pb@compasso.com.br"

WORKDIR /app

COPY target/post-service-0.0.1-SNAPSHOT.jar /app/post-service-docker.jar

ENTRYPOINT ["java", "-jar", "post-service-docker.jar"]