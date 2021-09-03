FROM openjdk:8-jdk-alpine
ADD ./target/TesseractApi-0.0.1-SNAPSHOT.jar .
CMD java -jar ./TesseractApi-0.0.1-SNAPSHOT.jar