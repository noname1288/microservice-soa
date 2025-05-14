# ----------- STAGE 1: BUILD với Gradle 8.13 + JDK 21 -----------
FROM gradle:8.13-jdk21 AS build
WORKDIR /app

# Copy project vào container
COPY . .

# Build ứng dụng Spring Boot (file .jar)
RUN gradle bootJar --no-daemon

# ----------- STAGE 2: RUN với JRE nhẹ -----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy file jar từ stage build
COPY --from=build /app/build/libs/*.jar app.jar

# Mặc định chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
