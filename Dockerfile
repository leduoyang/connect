# Use an official Maven image with OpenJDK 17
FROM maven:3.8.3-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY .. .

# Build the Maven project
RUN mvn clean install -DskipTests

# Use an official openjdk 17 image as the base image
FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y procps && \
    apt-get install -y curl

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/connect-web/target/connect-web-1.0.0-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
