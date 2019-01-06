FROM openjdk:latest
COPY . /home/circleci/repo/target/uberjar/
WORKDIR /home/circleci/repo/target/uberjar/
CMD ["java", "-jar", "couperose-0.1.0-SNAPSHOT-standalone.jar"]
