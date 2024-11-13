# Etapa de construcción
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /build

# Copiar el archivo POM y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y construir
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM bellsoft/liberica-openjdk-alpine-musl:17

WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /build/target/SpringBootSoapWebService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]