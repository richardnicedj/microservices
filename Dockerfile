# Dockerfile
FROM openjdk:21

# Copiar los archivos JAR de los microservicios al contenedor
COPY clientes-service/target/clientes-service-1.0.0-SNAPSHOT.jar /app/clientes-service.jar
COPY cuentas-service/target/cuentas-service-1.0.0-SNAPSHOT.jar /app/cuentas-service.jar

# Definir el directorio de trabajo dentro del contenedor
WORKDIR /app

# Ejecutar el comando para construir los microservicios
RUN ["java", "-jar", "clientes-service.jar", "--spring.profiles.active=docker"]
RUN ["java", "-jar", "cuentas-service.jar", "--spring.profiles.active=docker"]

# Exponer los puertos necesarios para acceder a los microservicios
EXPOSE 8081
EXPOSE 8082

# Definir el punto de entrada al contenedor
CMD ["java", "-jar", "clientes-service.jar"]