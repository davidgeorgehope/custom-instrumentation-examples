#!/bin/sh
java \
-javaagent:/opentelemetry-javaagent.jar \
-Dotel.exporter.otlp.endpoint=${SERVER_URL} \
-Dotel.exporter.otlp.headers="Authorization=Bearer ${SECRET_KEY}" \
-Dotel.metrics.exporter=otlp \
-Dotel.logs.exporter=otlp \
-Dotel.resource.attributes=service.name=simple-java,service.version=1.0,deployment.environment=production \
-Dotel.service.name=your-service-name \
-Dotel.javaagent.extensions=/usr/local/lib/opentelemetry-custom-instrumentation-1.0-SNAPSHOT.jar \
-Dotel.javaagent.debug=true \
-jar /usr/local/lib/simple-java-1.0-SNAPSHOT.jar

