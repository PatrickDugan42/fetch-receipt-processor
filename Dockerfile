FROM gradle:jdk21 AS builder
LABEL authors="patrickdugan"
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar dugan-fetch-receipt-processor.jar
ENTRYPOINT ["java", "-jar", "dugan-fetch-receipt-processor.jar"]
