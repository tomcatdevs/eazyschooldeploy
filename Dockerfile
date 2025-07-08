# Use official OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port (match your application port)
EXPOSE 9006

# Run the app
CMD ["java", "-jar", "target/eazyschool-aws-deployment.jar"]
