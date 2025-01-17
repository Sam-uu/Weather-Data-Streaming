package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeatherDataByCity(String city) {
        // First, get the coordinates of the city
        double[] coordinates = getCoordinates(city);

        // Use the coordinates to get the weather data
        if (coordinates != null) {
            return getWeatherData(coordinates[0], coordinates[1]);
        } else {
            return "City not found";
        }
    }

    private double[] getCoordinates(String city) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", city, apiKey);
        GeocodeResponse[] response = restTemplate.getForObject(url, GeocodeResponse[].class);
        if (response != null && response.length > 0) {
            return new double[]{response[0].getLat(), response[0].getLon()};
        } else {
            return null;
        }
    }

    private String getWeatherData(double lat, double lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", lat, lon, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    // GeocodeResponse class to hold the geocoding response
    public static class GeocodeResponse {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}