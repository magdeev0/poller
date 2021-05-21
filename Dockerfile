FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} target/poller-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/poller-1.0.jar"]