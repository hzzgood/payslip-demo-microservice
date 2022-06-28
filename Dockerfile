# Build JAR From SRC
FROM maven:3.8-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Build Image From JAR
FROM --platform=linux/amd64 openjdk:17-jdk-slim-buster
COPY  --from=build /home/app/target/payslipDemo-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
