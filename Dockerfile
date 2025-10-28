FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Копируем pom.xml из подпапки Kotolud
COPY Kotolud/pom.xml .

# Копируем src из подпапки Kotolud
COPY Kotolud/src ./src

# Собираем
RUN mvn clean package -DskipTests

# Запускаем JAR
EXPOSE 8080
ENV PORT=8080
CMD ["sh", "-c", "java -Xmx512m -Xms256m -jar target/Kotolud-*.jar --server.port=$PORT"]