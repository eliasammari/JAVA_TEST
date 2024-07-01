# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copy the Maven wrapper files
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .

# Copy the source code
COPY src ./src

# Install dependencies and build the application using the Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/transactions-simulation-1.0-SNAPSHOT.jar /app/transactions-simulation.jar

# Copy the application properties
COPY src/main/resources/application.properties /app/application.properties

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "transactions-simulation.jar"]
