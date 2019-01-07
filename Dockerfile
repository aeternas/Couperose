FROM openjdk:11
WORKDIR ~
COPY target/uberjar/target/uberjar/couperose-0.1.0-SNAPSHOT-standalone.jar .
CMD ["java", "-jar", "couperose-0.1.0-SNAPSHOT-standalone.jar"]
