FROM adoptopenjdk:11-jdk-hotspot
LABEL maintainer="rafel.diaz17@gmail.com"
VOLUME /tmp
ARG JAR_FILE=target/JhonnDeere_code_challenge-jar-with-dependencies.jar
ADD ${JAR_FILE} app.jar
# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]