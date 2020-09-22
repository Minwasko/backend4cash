FROM maven:3.6.3-openjdk-16 AS MAVEN_BUILD

ARG SPRING_ACTIVE_PROFILE
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
# THERE WAS clean install BEFORE AND EVERYTHING WAS KINDA WORKING
RUN mvn install -o -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE && mvn package -o -B -e -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE
FROM openjdk:16-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/Backend4Cash.jar
ENTRYPOINT ["java", "-jar", "Backend4Cash.jar"]
