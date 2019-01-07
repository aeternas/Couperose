FROM openjdk:11
WORKDIR ~
CMD ["java", "-jar", "couperose-0.1.0-SNAPSHOT-standalone.jar"]
