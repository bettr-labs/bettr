# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Gradle wrapper and build files first (for caching)
COPY gradlew settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Copy source code
COPY bettr-core ./bettr-core
COPY bettr-deployments ./bettr-deployments

# Build the application
RUN chmod +x gradlew && ./gradlew :bettr-deployments:bettr-api:bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar
COPY --from=builder /app/bettr-deployments/bettr-api/build/libs/bettr-api.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
