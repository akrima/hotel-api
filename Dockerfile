# Use a base image with Java 17 and Maven
FROM adoptopenjdk:17-jdk-hotspot AS build
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a smaller base image for the application
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app

# Copy JAR file from the build stage
COPY --from=build /app/target/hotel-0.0.1-SNAPSHOT.jar .

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "your-spring-boot-app.jar"]
