FROM eclipse-temurin:11-jre

WORKDIR /app

COPY target/WalletBalance-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
