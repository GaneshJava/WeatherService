package com.sapient.services.weatherservice.integration.ows;

@SuppressWarnings("serial")
public class Weather extends WeatherEntry {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
