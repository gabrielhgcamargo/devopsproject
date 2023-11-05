FROM openjdk:17-jdk-slim
WORKDIR /devopsproject
COPY target/*.jar /devopsproject/devopsproject.jar
EXPOSE 9090
CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=9090 -jar devopsproject.jar


