FROM openjdk:11
WORKDIR /home/circleci/repo/target/uberjar/
COPY . .
CMD ["java", "-jar", "couperose-0.1.0-SNAPSHOT-standalone.jar"]
