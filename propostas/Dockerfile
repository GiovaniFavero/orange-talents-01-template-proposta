FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=/propostas/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]