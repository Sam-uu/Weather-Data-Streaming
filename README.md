# Weather Data Streaming App

This is a backend real-time weather data streaming application built with Java, Spring Boot, and Apache Kafka. The application collects weather data from the [OpenWeatherAPI](https://openweathermap.org/api) and streams it to Kafka topics, which can be consumed by other services or clients.

## Features
- **Real-time weather data streaming**: Continuously fetches weather data for specific locations from the OpenWeatherAPI.
- **Kafka Integration**: Uses Apache Kafka for real-time messaging and streaming of weather data.
- **Spring Boot Backend**: A backend built with Spring Boot to handle API calls, data processing, and Kafka communication.

## Architecture

- **OpenWeatherAPI**: Data source for weather information (e.g., temperature, humidity, wind speed, etc.).
- **Spring Boot**: Handles API requests, interacts with Kafka, and processes data.
- **Apache Kafka**: Acts as a messaging layer to stream weather data to interested consumers.

## Technologies Used

- **Java 17+**
- **Spring Boot 3.3.1**
- **Apache Kafka**
- **OpenWeatherAPI** (for weather data)
- **Maven 4.0.0** (for build and dependency management)

## Setup & Installation

### Prerequisites
Before you start, make sure you have the following installed:
- **JDK 17+**
- **Apache Kafka** (you can run locally or use a managed service like Confluent Cloud)
- **Maven 4.0.0**
- **Docker** (optional for containerized deployment)

### Steps to Run Locally

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/weather-data-streaming-app.git
    cd weather-data-streaming-app
    ```

2. **Install dependencies**:
    If you're using Maven:
    ```bash
    mvn clean install
    ```

3. **Configure OpenWeatherAPI**:
    - Obtain an API key from [OpenWeatherAPI](https://openweathermap.org/appid).
    - Add the API key to your `application.properties` or `application.yml` file:
      ```properties
      openweather.api.key=YOUR_API_KEY
      ```

4. **Configure Kafka**:
    - Set up a local Kafka instance, or configure the app to connect to a remote Kafka cluster (e.g., Confluent Cloud).
    - In `application.properties`, set the Kafka broker URL:
      ```properties
      spring.kafka.bootstrap-servers=localhost:9092
      ```

5. **Run the application**:
    To start the Spring Boot app, use the following command:
    ```bash
    mvn spring-boot:run
    ```

6. **Verify Kafka Streaming**:
    Once the application is running, it will start streaming weather data to a Kafka topic (e.g., `weather-data`). You can consume messages from Kafka to see the streamed weather data.

### Docker Setup (Optional)
You can containerize the application using Docker.

1. Build the Docker image:
    ```bash
    docker build -t weather-data-streaming-app .
    ```

2. Run the Docker container:
    ```bash
    docker run -p 8080:8080 weather-data-streaming-app
    ```

## Endpoints

### `/fetchWeather`

This endpoint fetches weather data from OpenWeatherAPI and streams it to Kafka.

- **Method**: `GET`
- **Path**: `/fetchWeather`
  - Using latitude and longtitude `http://localhost:8080/fetchWeather?lat=34.0209&lon=-6.8415`
  - Using city name `http://localhost:8080/fetchWeather?city=tanger`
- **Response**: A stream of weather data in JSON format sent to the configured Kafka topic.

### Kafka Topics

- `weather-data`: The topic where the weather data is being streamed.

## Configuration

You can configure different parameters for the OpenWeatherAPI, Kafka, and Spring Boot in the `application.properties` file.

### Example configuration:

```properties
# OpenWeatherAPI configuration
openweather.api.key=YOUR_API_KEY
openweather.city.name=London
openweather.units=metric

# Kafka configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=weather-consumers
spring.kafka.consumer.auto-offset-reset=earliest

# Spring Boot application properties
server.port=8080
