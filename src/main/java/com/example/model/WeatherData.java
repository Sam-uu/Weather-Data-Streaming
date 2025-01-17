// WeatherData.java
package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class WeatherData {

    @JsonProperty("coord")
    private Coord coordinates;

    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private MainWeather mainWeather;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    private long dt;
    
    private String ldt;
    

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cod")
    private int cod;

    // Getters and setters
    public Coord getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coord coordinates) {
        this.coordinates = coordinates;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainWeather getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(MainWeather mainWeather) {
        this.mainWeather = mainWeather;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
    
    public String getLdt() {
		return ldt;
	}

	public void setLdt(String ldt) {
		this.ldt = ldt;
	}

    // Inner classes representing nested JSON structures

	public static class Coord {
        @JsonProperty("lon")
        private double lon;

        @JsonProperty("lat")
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class Weather {
        @JsonProperty("id")
        private int id;

        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class MainWeather {
        @JsonProperty("temp")
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("humidity")
        private int humidity;

        @JsonProperty("sea_level")
        private int seaLevel;

        @JsonProperty("grnd_level")
        private int groundLevel;

        // Getters and setters
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
        	BigDecimal bd = new BigDecimal(temp).setScale(0, RoundingMode.HALF_UP);
            this.temp = bd.doubleValue();
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(double feelsLike) {
        	BigDecimal bd = new BigDecimal(feelsLike).setScale(0, RoundingMode.HALF_UP);
            this.feelsLike = bd.doubleValue();
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
        	BigDecimal bd = new BigDecimal(tempMin).setScale(0, RoundingMode.HALF_UP);
            this.tempMin = bd.doubleValue();
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
        	BigDecimal bd = new BigDecimal(tempMax).setScale(0, RoundingMode.HALF_UP);
            this.tempMax = bd.doubleValue();
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(int seaLevel) {
            this.seaLevel = seaLevel;
        }

        public int getGroundLevel() {
            return groundLevel;
        }

        public void setGroundLevel(int groundLevel) {
            this.groundLevel = groundLevel;
        }
    }

    public static class Wind {
        @JsonProperty("speed")
        private BigDecimal speed;

        @JsonProperty("deg")
        private int deg;

        // Getters and setters
        public BigDecimal getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
        	speed *= 3.6;
        	BigDecimal bd = new BigDecimal(speed).setScale(0, RoundingMode.HALF_UP);
            this.speed = bd;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }
    }

    public static class Clouds {
        @JsonProperty("all")
        private int all;

        // Getters and setters
        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class Sys {
        @JsonProperty("type")
        private int type;

        @JsonProperty("id")
        private int id;

        @JsonProperty("country")
        private String country;

        @JsonProperty("sunrise")
        private long sunrise;
        
        private String Local_sunrise;

        @JsonProperty("sunset")
        private long sunset;
        
        private String Local_sunset;

        // Getters and setters
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
        
        public String getLocal_sunrise() {
            return Local_sunrise;
        }

        public void setLocal_sunrise(String localSunrise) {
            this.Local_sunrise = localSunrise;
        }
        
        public String getLocal_sunset() {
            return Local_sunset;
        }

        public void setLocal_sunset(String localSunset) {
            this.Local_sunset = localSunset;
        }
    }
}
