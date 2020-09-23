package com.sapient.services.weatherservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherSummaryController {

	@RequestMapping("/")
	public String handlePath() {
		return "Please access these weather api's on this paths :/api/weather/weekly/{city} or /api/weather/now/{city} ";
	}

}
