# custom-instrumentation-examples

OTEL AGENT:

docker build -t djhope99/custom-otel-instrumentation:1 .           
docker run -it -e 'SERVER_URL=XXX' -e 'SECRET_KEY=XXX' djhope99/custom-otel-instrumentation:1


ELASTIC AGENT:

docker build -f Dockerfile.elastic djhope99/custom-elastic-instrumentation:1 .
docker run -it -e 'SERVER_URL=XXX' -e 'SECRET_KEY=XXX' djhope99/custom-elastic-instrumentation:1

