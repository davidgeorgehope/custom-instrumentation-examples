# custom-instrumentation-examples

OTEL AGENT:

docker build -t djhope99/custom-otel-instrumentation:1 .           

docker run -it -e 'SERVER_URL=XXX' -e 'SECRET_KEY=XXX' djhope99/custom-otel-instrumentation:1


ELASTIC AGENT:

docker build -t djhope99/custom-elastic-instrumentation:2 -f Dockerfile.elastic .

docker run -it -e 'ELASTIC_APM_SERVER_URL=https://3b3c6ec7e6d84937bc2bb021d8b5ccf2.apm.us-central1.gcp.cloud.es.io:443' -e 'ELASTIC_APM_SECRET_TOKEN=UyFCxFiHgLjkromXuZ' djhope99/custom-elastic-instrumentation:2

