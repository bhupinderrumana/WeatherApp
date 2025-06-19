package model;

public class WeatherData {
    public String city;
    public double temperature;
    public String description;
    public double humidity;

    public WeatherData(String city, double temperature, String description, double humidity) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
    }
}
