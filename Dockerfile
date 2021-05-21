FROM openjdk:11 AS build
COPY src/main/java/net/magdeev/poller/PollerApplication.java .
RUN javac PollerApplication.java

FROM openjdk:11
COPY --from=build PollerApplication.class .
CMD ["java", "PollerApplication"]