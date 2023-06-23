# Build stage
FROM maven:3.8.7-openjdk-18 as build

COPY simple-java /home/app/simple-java
COPY opentelemetry-custom-instrumentation /home/app/opentelemetry-custom-instrumentation

WORKDIR /home/app/simple-java
RUN mvn install

WORKDIR /home/app/opentelemetry-custom-instrumentation
RUN mvn install

# Package stage
FROM maven:3.8.7-openjdk-18
COPY --from=build /home/app/simple-java/target/simple-java-1.0-SNAPSHOT.jar /usr/local/lib/simple-java-1.0-SNAPSHOT.jar
COPY --from=build /home/app/opentelemetry-custom-instrumentation/target/opentelemetry-custom-instrumentation-1.0-SNAPSHOT.jar /usr/local/lib/opentelemetry-custom-instrumentation-1.0-SNAPSHOT.jar

WORKDIR /

RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

CMD ["java", "-javaagent:/opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.endpoint=${ENDPOINT_URL}", "-Dotel.exporter.otlp.headers=Authorization=Bearer ${SECRET_KEY}", "-Dotel.metrics.exporter=otlp", "-Dotel.logs.exporter=otlp", "-Dotel.resource.attributes=service.name=simple-java,service.version=1.0,deployment.environment=production", "-Dotel.service.name=your-service-name", "-Dotel.javaagent.extensions=/usr/local/lib/opentelemetry-custom-instrumentation-1.0-SNAPSHOT.jar", "-Dotel.javaagent.debug=true", "-jar", "/usr/local/lib/simple-java-1.0-SNAPSHOT.jar"]

