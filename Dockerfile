# Etapa de construcción
FROM maven:3.8.4-openjdk-17 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
#FROM openjdk:17-alpine
FROM bellsoft/liberica-openjdk-alpine-musl:17
COPY --from=build /target/noteapp-0.0.1-SNAPSHOT.jar noteapp.jar
ENTRYPOINT ["java", "-jar", "noteapp.jar"]