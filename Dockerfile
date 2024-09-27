# Use a base image with JDK
FROM openjdk:17-jdk-slim
LABEL authors="Vijay"

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/tasker-0.0.1-SNAPSHOT.jar tasker.jar

# Run the application
ENTRYPOINT ["java", "-jar", "tasker.jar"]
