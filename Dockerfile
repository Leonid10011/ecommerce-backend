# Verwenden Sie ein offizielles OpenJDK-Image als Basisimage
FROM openjdk:11-jre-slim

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopieren Sie das JAR-Datei in den Container
COPY target/your-app.jar /app/your-app.jar

# Starten Sie die Anwendung beim Containerstart
CMD ["java", "-jar", "your-app.jar"]
