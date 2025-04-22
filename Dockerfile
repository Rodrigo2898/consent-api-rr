FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk-alpine
LABEL "org.opencontainers.image.authors"="rodfeitosa28"
WORKDIR /app

# Copia o .jar gerado na etapa de build
COPY --from=builder /app/target/consents-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8099

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
