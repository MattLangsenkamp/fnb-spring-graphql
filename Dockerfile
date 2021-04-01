FROM openjdk:14 AS builder
RUN java --version
COPY ./ ./
RUN ./gradlew clean build --no-daemon -x test
RUN ls -al build/libs/

FROM alpine:latest
RUN apk add --no-cache wget tar
RUN wget https://cdn.azul.com/zulu/bin/zulu14.28.21-ca-jre14.0.1-linux_musl_x64.tar.gz
RUN tar -xvf zulu14.28.21-ca-jre14.0.1-linux_musl_x64.tar.gz

EXPOSE 8080

COPY --from=builder /build/libs/locations.jar .
CMD ["/zulu14.28.21-ca-jre14.0.1-linux_musl_x64/bin/java", "-jar", "/locations.jar", "--spring.config.name=container.application"]