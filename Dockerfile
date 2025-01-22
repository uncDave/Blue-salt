# Use an OpenJDK runtime as a base image
FROM amazoncorretto:21
LABEL authors="David"

WORKDIR /app

COPY target/bluesalt-0.0.1-SNAPSHOT.jar /app/bluesalt.jar

EXPOSE 7880
CMD ["java", "-jar", "pbt.jar", "--server.port=7880"]