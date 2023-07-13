# OpenTelemetry Java Custom Instrumentation Example

This repository provides an example of using custom instrumentation with OpenTelemetry in a simple Java application. The application consists of two parts: the application code (in `/simple-java/src/main/java/org/davidgeorgehope/Main.java`) and the custom instrumentation code (in `/opentelemetry-custom-instrumentation/src/main/java/org/davidgeorgehope/WordCountInstrumentation.java`).

## Files

1. `WordCountInstrumentation.java` - This file contains the code for custom instrumentation. It instruments the method `countWords` in the `Main` class and adds custom telemetry to track method execution.

2. `Main.java` - This file contains the code for the simple Java application. It asks for a sentence from the user and calculates the word count of the sentence.

3. `Dockerfile` - This is a Dockerfile for packaging the application and the instrumentation into a single Docker image.

## Building and Running the Application

1. You can build the application using Docker. The provided `Dockerfile` builds the `simple-java` and `opentelemetry-custom-instrumentation` Maven projects and packages them into a Docker image along with the OpenTelemetry Java agent.

    To build the Docker image, navigate to the root directory of this repository and run:

    ```
    docker build -t djhope99/custom-otel-instrumentation:1 .
    ```

    Replace `djhope99/custom-otel-instrumentation:1` with your desired image name.

2. To run the Docker container, use the following command:

    ```
    docker run -it -e 'SERVER_URL=XXX' -e 'SECRET_KEY=XXX' djhope99/custom-otel-instrumentation:1
    ```

    Replace `YourEndpointURL` and `YourSecretKey` with your actual endpoint URL and secret key for the OpenTelemetry collector. Replace `simple-java-opentelemetry` with your image name if you chose a different name during build.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)

