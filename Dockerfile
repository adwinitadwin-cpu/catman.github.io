# ============== STAGE 1: BUILD (Сборка) ==============
FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

# Копируем Maven wrapper и pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Даем права на выполнение
RUN chmod +x ./mvnw

# Загружаем зависимости
RUN ./mvnw dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# ============== STAGE 2: RUNTIME (Запуск) ==============
FROM openjdk:21-jdk-slim

WORKDIR /app

# Копируем собранный JAR из stage 1
COPY --from=builder /app/target/Kotolud-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт (Railway автоматически назначит PORT)
EXPOSE 8080

# Переменные окружения по умолчанию
ENV PORT=8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Запускаем приложение с поддержкой PORT переменной
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=$PORT"]