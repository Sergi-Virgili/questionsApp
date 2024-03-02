
FROM eclipse-temurin:21-alpine as builder
WORKDIR /app

# Instalar las herramientas necesarias para la construcción
RUN apk add --no-cache bash

# Copiar archivos de configuración de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar código fuente
COPY src src

# Dar permisos de ejecución al script de Gradle
RUN chmod +x ./gradlew

# Cachear dependencias para mejorar la reconstrucción
RUN ./gradlew --no-daemon dependencies

# Ejecutar pruebas
RUN ./gradlew --no-daemon test

# Construir la aplicación omitiendo las pruebas ya que se ejecutaron en el paso anterior
RUN ./gradlew --no-daemon build -x test

# Etapa de ejecución
FROM eclipse-temurin:21-alpine
WORKDIR /app

# Copiar el JAR construido desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar app.jar

# Definir el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
