# Используем официальный образ OpenJDK 21
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Maven wrapper и pom.xml (если используете Maven)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Даем права на выполнение Maven wrapper
RUN chmod +x ./mvnw

# Загружаем зависимости (кэшируется при изменении только pom.xml)
RUN ./mvnw dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# Открываем порт (Railway автоматически назначит PORT)
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "target/vadim-harovyuk-dev-0.0.1-SNAPSHOT.jar"]