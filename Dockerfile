FROM openjdk:11
ARG SW_HOSTNAME
ENV HOSTNAME $SW_HOSTNAME
WORKDIR ~
COPY target/uberjar/couperose-0.1.0-SNAPSHOT-standalone.jar .
CMD ["java", "-jar", "couperose-0.1.0-SNAPSHOT-standalone.jar"]
