FROM openjdk

WORKDIR /devopsproject

COPY target/devopsproject.jar /devopsproject/devopsproject.jar

ENTRYPOINT ["java", "-jar", "devopsproject.jar"]


