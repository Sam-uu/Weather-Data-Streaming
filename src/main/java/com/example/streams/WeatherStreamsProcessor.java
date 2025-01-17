// WeatherStreamsProcessor.java
package com.example.streams;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import com.example.model.WeatherData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class WeatherStreamsProcessor {

    @Value("${kafka.topic.input}")
    private String inputTopic;

    @Value("${kafka.topic.output}")
    private String outputTopic;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "weather-processing-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Or "latest" or "none" depending on your needs

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, WeatherData> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> weatherDataInput = streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, WeatherData> parsedWeatherDataStream = weatherDataInput
                .mapValues(value -> {
                    try {
                		WeatherData weatherData = objectMapper.readValue(value, WeatherData.class);
                		
                		processWeatherData(weatherData);

						return weatherData;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });

        parsedWeatherDataStream.to(outputTopic, Produced.with(Serdes.String(), new JsonSerde<>(WeatherData.class)));

        return parsedWeatherDataStream;
    }
    
    public void processWeatherData(WeatherData weatherData) {
    	if(weatherData == null)
    		return;
    	
    	weatherData.setLdt(LocalDateTime(weatherData.getDt(), weatherData.getTimezone()));
    	weatherData.getSys().setLocal_sunrise(LocalDateTime(weatherData.getSys().getSunrise(), weatherData.getTimezone()));
    	weatherData.getSys().setLocal_sunset((LocalDateTime(weatherData.getSys().getSunset(), weatherData.getTimezone())));

        weatherData.getMainWeather().setTemp(kelvinToCelsius(weatherData.getMainWeather().getTemp()));
        weatherData.getMainWeather().setFeelsLike(kelvinToCelsius(weatherData.getMainWeather().getFeelsLike()));
        weatherData.getMainWeather().setTempMin(kelvinToCelsius(weatherData.getMainWeather().getTempMin()));
        weatherData.getMainWeather().setTempMax(kelvinToCelsius(weatherData.getMainWeather().getTempMax()));
    }
    
    public String LocalDateTime(long time, int timeZone) {

    	LocalDateTime utcDateTime = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC);
    	LocalDateTime localDateTime = utcDateTime.plusSeconds(timeZone);

        // Format and print the local date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = localDateTime.format(formatter);
        
        return dateTime;
    }
    
    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
