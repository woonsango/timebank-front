FROM gradle:7.4-jdk17-alpine as builder
WORKDIR /build
COPY ./timepay/ /build
RUN apk add gettext
RUN G=$(cat settings.gradle) \
    && echo -e "pluginManagement {\n\
    repositories {\n\
    	maven {\n\
            allowInsecureProtocol true\n\
            url 'http://nexus.vm.cs.kookmin.ac.kr/registry/repository/maven-public'\n\
        }\n\
    }\n\
}\n\
$G" | tee settings.gradle
RUN export XXXX=$(echo -e "maven {\n\
        allowInsecureProtocol true\n\
        url 'http://nexus.vm.cs.kookmin.ac.kr/registry/repository/maven-public'\n\
    }") \
    && sed "s/mavenCentral()/\$XXXX/g" build.gradle | envsubst | tee /tmp/build.gradle
RUN cp /tmp/build.gradle build.gradle
RUN gradle -i build -x test --parallel

FROM openjdk:11-jdk
COPY --from=builder /build/build/libs/timepay-*-SNAPSHOT.jar /app.jar
#ARG JAR_FILE=build/libs/timepay-0.0.1-SNAPSHOT.jar
#ARG JAR_FILE=build/libs/timepay-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
