# ----------- STAGE 1: BUILD với Gradle 8.13 + JDK 21 -----------
FROM gradle:8.13-jdk21 AS build
WORKDIR /app

# Copy toàn bộ source vào container
COPY . .

# Build ứng dụng Spring Boot (tạo file .jar)
RUN gradle bootJar --no-daemon

# ----------- STAGE 2: RUN với JRE nhẹ -----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Biến môi trường PORT để Render inject cổng
ENV PORT 8080
EXPOSE 8080

# Copy file .jar từ stage build
COPY --from=build /app/build/libs/*.jar app.jar

# Chạy ứng dụng, sử dụng biến PORT từ Render
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]