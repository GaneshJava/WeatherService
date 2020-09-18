package com.sapient.services.weatherservice.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sapient.services.weatherservice.WeatherServiceProperties;
import com.sapient.services.weatherservice.integration.ows.Weather;
import com.sapient.services.weatherservice.integration.ows.WeatherService;

@Controller
@RequestMapping("/")
public class WeatherSummaryController {

	private final WeatherService weatherService;

	private final WeatherServiceProperties properties;

	public WeatherSummaryController(WeatherService weatherService, WeatherServiceProperties properties) {
		this.weatherService = weatherService;
		this.properties = properties;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView conferenceWeather() {
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("summary", getSummary());
		return new ModelAndView("summary", model);
	}

	private Object getSummary() {
		List<WeatherSummary> summary = new ArrayList<>();
		for (String location : this.properties.getLocations()) {
			//String country = location.split("/")[0];
			String city = location.split("/")[1];
			Weather weather = this.weatherService.getWeather(city);
			summary.add(createWeatherSummary(city, weather));
		}
		return summary;
	}



	private WeatherSummary createWeatherSummary(String city,
			Weather weather) {
		if ("Las Vegas".equals(city)) {
			weather.setWeatherId(666);
		}
		return new WeatherSummary(city, weather);
	}

}
