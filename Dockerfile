FROM openjdk:8-alpine
WORKDIR /obo
COPY target/demo-0.0.1-SNAPSHOT.jar .
# RUN mvn package -Dmaven.test.skip=true
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
