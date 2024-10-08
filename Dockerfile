FROM gradle:8.8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --warning-mode=all --scan -x test

FROM openjdk:17
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app/spring-boot-application.jar"]
