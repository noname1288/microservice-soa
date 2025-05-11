# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim-bullseye

# Update system packages to patch vulnerabilities
RUN apt-get update && apt-get upgrade -y && apt-get clean

# Set the working directory in the container
WORKDIR /app

# Copy the build output to the container
COPY build/libs/team-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]