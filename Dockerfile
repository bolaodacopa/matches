FROM amazoncorretto:17-alpine-jdk
MAINTAINER boladodacopa.tk
COPY matches.jar matches.jar
ENTRYPOINT ["java","-jar","/matches.jar"]