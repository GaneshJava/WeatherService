package com.sapient.services.weatherservice.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.services.weatherservice.integration.ows.Weather;
import com.sapient.services.weatherservice.integration.ows.WeatherForecast;
import com.sapient.services.weatherservice.integration.ows.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {

	private final WeatherService weatherService;

	public WeatherApiController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@RequestMapping("/now/{city}")
	public Weather getWeather(@PathVariable String city) {
		return this.weatherService.getWeather(city);
	}

	@RequestMapping("/weekly/{city}")
	public WeatherForecast getWeatherForecast(@PathVariable String city) {
		return this.weatherService.getWeatherForecast(city);
	}

}
