FROM maven:3-openjdk-15-slim AS build
WORKDIR /app/build
COPY . .
RUN mvn package -B
RUN mv target/http-server-1.0-jar-with-dependencies.jar target/app.jar

FROM openjdk:17-slim
WORKDIR /app/bin
COPY --from=build /app/build/target/app.jar .
CMD ["java", "-jar", "app.jar"]