# Usa la imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Define el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación a la imagen
COPY build/libs/*.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]