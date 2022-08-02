FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD

LABEL maintainer="sahrulramadan6121@gmail.com"

COPY pom.xml .docker/app/
COPY src .docker/app/src/

WORKDIR .docker/app/
RUN mvn package -DskipTests

FROM openjdk:17

WORKDIR .docker/app/

COPY --from=MAVEN_BUILD .docker/app/target/rest-api-0.0.1-SNAPSHOT.jar .docker/app/

RUN mkdir "/upload"

ENTRYPOINT ["java", "-jar", ".docker/app/rest-api-0.0.1-SNAPSHOT.jar"]