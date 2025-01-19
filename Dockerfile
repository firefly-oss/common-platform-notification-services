FROM eclipse-temurin:21-jdk

COPY target/common-platform-notification-services-web-*.jar /run/common-platform-notification-services-web.jar

USER 1001

EXPOSE 8080
EXPOSE 8081
EXPOSE 9090

CMD [ "java","-jar", "run/common-platform-notification-services-web.jar"]