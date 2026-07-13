# ---- Stage 1: Build the jar with Maven ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first so dependency downloads are cached between builds
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Now copy the rest of the source and build
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Stage 2: Lightweight runtime image ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render assigns the actual port via the PORT env var at runtime;
# application.properties already reads it (server.port=${PORT:8080}).
EXPOSE 8080

ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]
