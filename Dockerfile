FROM maven:3.8.4-openjdk-17-slim as build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

# Copy the jar file from the 'build' stage
COPY --from=build /app/target/JhonnDeere_code_challenge.jar ./app.jar

# Expose the application's port(If needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]