# Build Stage: Gradle Build
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Kopiere Gradle Files
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Kopiere Source Code
COPY src ./src

# Build durchführen
RUN gradle build -x test --no-daemon

# Runtime Stage: Nur die JAR ausführen
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Kopiere die gebaute JAR vom Build Stage
COPY --from=build /app/build/libs/*.jar app.jar

# Port freigeben
EXPOSE 8080

# App starten
ENTRYPOINT ["java", "-jar", "app.jar"]