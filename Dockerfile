FROM openjdk:17-jdk-alpine
EXPOSE 8082
COPY target/henirebhisleam1-1.0.0.jar henirebhi_5sleam1.jar
ENTRYPOINT ["java", "-jar", "/henirebhi_5sleam1.jar"]
