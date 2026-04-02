# Build stage
FROM gradle:8.10-jdk21 AS build
WORKDIR /app

COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY subprojects ./subprojects
COPY gradle.properties ./

COPY photo/ ./
COPY media/ ./

RUN chmod +x gradlew
RUN ./gradlew :ui:api:bootJar --no-daemon -x test

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/subprojects/ui/api/build/libs/*.jar app.jar
COPY --from=build /app/photo ./photo
COPY --from=build /app/media ./media

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
