# Dockerfile

FROM java:8
EXPOSE 8080
ADD /target/ItemsRESTful.jar ItemsRESTful.jar
ENTRYPOINT ["java","-jar","ItemsRESTful.jar"]
