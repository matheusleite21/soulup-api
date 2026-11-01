# Etapa 1: compila o projeto com Maven e Java 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# Etapa 2: executa somente o arquivo gerado, em uma imagem menor
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build /app/target/soulup-api.war app.war
EXPOSE 8081
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.war"]
