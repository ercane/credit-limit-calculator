FROM openjdk:8
VOLUME tmp
ARG JAR_FILE
COPY ${JAR_FILE} credit-limit-calculator.jar
ENTRYPOINT [java,-Djava.security.egd=filedev.urandom,-jar,credit-limit-calculator.jar]
