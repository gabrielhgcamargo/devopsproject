FROM openjdk
WORKDIR /devopsproject
COPY target/*.jar /devopsproject/devopsproject.jar
EXPOSE 9090
ENTRYPOINT [ "java", "-Dserver.port=9090", "-jar", "devopsproject.jar" ]

