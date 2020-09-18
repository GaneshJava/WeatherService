package com.sapient.services.weatherservice.integration.ows;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@SuppressWarnings("serial")
public class WeatherEntry implements Serializable {

	private Instant timestamp;

	private double temperature;
	
	private Integer weatherId;

	private String weatherIcon;
	
	private double temperature_min;
	
	private double temperature_max;
	
	private String wetherCondition;
	
	
	public double getTemperature_min() {
		return temperature_min;
	}

	public void setTemperature_min(double temperature_min) {
		this.temperature_min = temperature_min;
	}

	public double getTemperature_max() {
		return temperature_max;
	}

	public void setTemperature_max(double temperature_max) {
		this.temperature_max = temperature_max;
	}

	public String getWetherCondition() {
		return wetherCondition;
	}

	public void setWetherCondition(String wetherCondition) {
		this.wetherCondition = wetherCondition;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public Integer getWeatherId() {
		return this.weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}

	public String getWeatherIcon() {
		return this.weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	@JsonProperty("timestamp")
	public Instant getTimestamp() {
		return this.timestamp;
	}

	@JsonSetter("dt")
	public void setTimestamp(long unixTime) {
		this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
	}

	@JsonProperty("main")
	public void setMain(Map<String, Object> main) {
		setTemperature(Double.parseDouble(main.get("temp").toString()));
		setTemperature_min(Double.parseDouble(main.get("temp_min").toString()));
		setTemperature_max(Double.parseDouble(main.get("temp_max").toString()));
	}

	@JsonProperty("weather")
	public void setWeather(List<Map<String, Object>> weatherEntries) {
		Map<String, Object> weather = weatherEntries.get(0);
		setWeatherId((Integer) weather.get("id"));
		setWeatherIcon((String) weather.get("icon"));
		setWetherCondition((String) weather.get("main"));
	}

}
