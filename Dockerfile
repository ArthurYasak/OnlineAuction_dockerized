FROM openjdk:17

WORKDIR /OnlineAuction

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

EXPOSE 8181

RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
