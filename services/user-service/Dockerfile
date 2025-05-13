# Use an official OpenJDK runtime as a parent image
FROM openjdk:25-jdk-bullseye

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file to the container
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]