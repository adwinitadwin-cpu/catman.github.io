# ============== STAGE 1: BUILD ==============
FROM maven:3.9-openjdk-21 AS builder

WORKDIR /app

# Копируем pom.xml
COPY pom.xml .

# Загружаем зависимости
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# ============== STAGE 2: RUNTIME ==============
FROM openjdk:21-jdk-slim

WORKDIR /app

# Копируем JAR из builder
COPY --from=builder /app/target/Kotolud-*.jar app.jar

# Порт
EXPOSE 8080

# Переменные
ENV PORT=8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Запуск
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=$PORT"]