# Используем образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем все файлы проекта в контейнер
COPY . .

# Устанавливаем права на выполнение для gradlew
RUN chmod +x ./gradlew

# Компилируем и запускаем приложение
CMD ["./gradlew", ":controller:bootRun"]
