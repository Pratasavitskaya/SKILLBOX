#Базовый образ , содержащий java 17
FROM openjdk:17-oracle

#директория приложения внутри контейнера
WORKDIR /app

#Копирование JAR-файла приложения в контейнер
COPY build/libs/socketserver-0.0.1-SNAPSHOT.jar app.jar

#определение переменной среды
ENV SOCKET_SERVER_HOST=localhost
ENV SOCKET_SERVER_PORT=8888

#команда для запуска приложения
CMD ["java" , "-jar" , "app.jar"]
