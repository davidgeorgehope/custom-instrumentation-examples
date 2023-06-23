# custom-instrumentation-examples

OTEL AGENT:

https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

java -javaagent:/path/to/opentelemetry-javaagent.jar -Dotel.exporter.otlp.endpoint=https://3b3c6ec7e6d84937bc2bb021d8b5ccf2.apm.us-central1.gcp.cloud.es.io:443 -Dotel.exporter.otlp.headers="Authorization=Bearer xxx" -Dotel.metrics.exporter=otlp -Dotel.logs.exporter=otlp -Dotel.resource.attributes=service.name=simple-java,service.version=1.0,deployment.environment=production -Dotel.service.name=your-service-name -Dotel.javaagent.extensions=/path/to/opentelemetry-custom-instrumentation-1.0-SNAPSHOT.jar -Dotel.javaagent.debug=true -jar simple-java-1.0-SNAPSHOT.jar


ELASTIC AGENT:

https://oss.sonatype.org/service/local/artifact/maven/redirect?r=releases&g=co.elastic.apm&a=elastic-apm-agent&v=LATEST

java -javaagent:/path/to/elastic-apm-agent-1.36.0.jar -Delastic.apm.service_name=my-service-2 -Delastic.apm.secret_token=xxx -Delastic.apm.server_url=https://3b3c6ec7e6d84937bc2bb021d8b5ccf2.apm.us-central1.gcp.cloud.es.io:443  -Delastic.apm.environment=my-environment -Delastic.apm.plugins_dir=/Users/davidhope/IdeaProjects/simple-java/target -jar simple-java-1.0-SNAPSHOT.jar
