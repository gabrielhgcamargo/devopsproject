FROM openjdk
WORKDIR /devopsproject
COPY target/*.jar /devopsproject/devopsproject.jar
EXPOSE 9090
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-Dserver.port=9090", "-jar", "-Dspring.profiles.active=homol", "devopsproject.jar" ]

