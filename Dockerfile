# ---------- ETAPA 1: BUILD ----------
FROM eclipse-temurin:22-jdk AS builder

WORKDIR /app

COPY ./pom.xml .
COPY .mvn/ .mvn/
COPY mvnw  .
COPY ./src /app/src

RUN chmod +x mvnw && ./mvnw clean install -DskipTests

# ---------- ETAPA 2: RUN ----------
FROM eclipse-temurin:22-jdk
WORKDIR /app
EXPOSE 8080

COPY --from=builder /app/target/spvending-manager-1.0.0-SNAPSHOT.jar .

#Levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar","spvending-manager-1.0.0-SNAPSHOT.jar"]

