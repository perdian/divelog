FROM openjdk:16-slim

VOLUME /var/divelog/

COPY target/divelog.war /var/divelog/divelog.war

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-XX:+UnlockExperimentalVMOptions", "-jar", "/var/divelog/divelog.war"]
