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
    apt-get install -y curl && \
    apt-get install libc6

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/connect-web/target/connect-web-1.0.0-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Download and install Filebeat (ARM64 version)
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.15.2-linux-arm64.tar.gz \
    && tar xzvf filebeat-7.15.2-linux-arm64.tar.gz \
    && mv filebeat-7.15.2-linux-arm64 /usr/share/filebeat \
    && rm filebeat-7.15.2-linux-arm64.tar.gz

COPY filebeat.yml /usr/share/filebeat/filebeat.yml
COPY start.sh /usr/share/start.sh
RUN chmod +x /usr/share/start.sh

CMD ["/usr/share/start.sh"]
