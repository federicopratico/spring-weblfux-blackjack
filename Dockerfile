# Stage 1: Extraction
FROM eclipse-temurin:21-jre-jammy AS builder
WORKDIR /builder
# Points to your JAR in the target folder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
# Extract layers for optimized caching
RUN java -Djarmode=layertools -jar application.jar extract

# Stage 2: Final Runtime Image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /application
# Copy extracted layers from the builder stage
COPY --from=builder /builder/dependencies/ ./
COPY --from=builder /builder/spring-boot-loader/ ./
COPY --from=builder /builder/snapshot-dependencies/ ./
COPY --from=builder /builder/application/ ./

EXPOSE 8080

# Use the Spring Boot JarLauncher
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]