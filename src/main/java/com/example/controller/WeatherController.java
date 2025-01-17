// WeatherController.java
package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.consumer.KafkaStreamsConsumer;
import com.example.producer.Producer;
import com.example.service.WeatherService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private KafkaStreamsConsumer ksc;

    @Autowired
    private Producer producer;

    @GetMapping("/fetchWeather")
    public Object fetchWeather(@RequestParam("city") String city) {
        String weatherData = weatherService.getWeatherDataByCity(city);
        producer.sendMessage(weatherData);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Poll the latest message synchronously
        String latestMessage = ksc.pollLatestMessage();
                
        System.out.println("Latest value: "+latestMessage);
        return latestMessage;
    }
}