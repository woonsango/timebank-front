FROM gradle:8.3-openjdk11-alpine as build
ADD . /build
WORKDIR /build
RUN gradle build -i info

FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /build/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
