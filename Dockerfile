FROM openjdk:19-slim

VOLUME /var/divelog/

COPY target/divelog.jar /var/divelog/divelog.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-XX:+UnlockExperimentalVMOptions", "-jar", "/var/divelog/divelog.jar"]
