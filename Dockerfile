# Build Image From JAR
FROM --platform=linux/amd64 openjdk:17-jdk-slim-buster
COPY /target/payslipDemo-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
