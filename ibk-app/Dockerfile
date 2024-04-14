FROM openjdk:17-alpine
WORKDIR /home
COPY /build/libs/ibk-app.jar ibk-app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "ibk-app.jar", "--spring.profiles.active=docker"]
