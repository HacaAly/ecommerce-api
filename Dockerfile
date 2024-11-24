FROM openjdk:21-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src src/

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 3000

# run jar file
CMD ["java", "-jar", "app.jar"]