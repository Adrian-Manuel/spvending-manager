#Imagen Modelo
FROM eclipse-temurin:22-jdk

#Informar el puerto donde se ejecuta el contenedor
EXPOSE 8080

#Directorio raiz
WORKDIR /root

#Copiar y pegar archivos dentro del contenedor
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw  /root

#Descargar dependencias
RUN chmod +x mvnw && ./mvnw dependency:go-offline

#Copiar codigo fuente
COPY ./src /root/src

#Construir app
RUN ./mvnw clean install -DskipTests

#Levantar la app cuando el contenedor inicie
ENTRYPOINT ["java", "-jar","/root/target/spvending-manager-1.0.0-SNAPSHOT.jar"]

