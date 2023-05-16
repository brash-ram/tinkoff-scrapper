FROM maven:3.8.5-openjdk-17 as BUILDER

WORKDIR /opt/app

COPY scrapper ./scrapper
COPY link-parser ./link-parser
COPY bot ./bot
COPY pom.xml .

RUN mvn clean package -DskipTests -pl bot -am

FROM openjdk:17
WORKDIR /opt/app
COPY --from=builder /opt/app/bot/target/*.jar ./app.jar
CMD [ "java", "-jar", "/opt/app/app.jar" ]