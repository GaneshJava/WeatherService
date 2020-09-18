package com.sapient.services.weatherservice.web;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sapient.services.weatherservice.integration.ows.Weather;
import com.sapient.services.weatherservice.integration.ows.WeatherEntry;
import com.sapient.services.weatherservice.integration.ows.WeatherForecast;
import com.sapient.services.weatherservice.integration.ows.WeatherService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherApiController.class)
public class WeatherApiControllerTest {

	@MockBean
	private WeatherService weatherService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void weather() throws Exception {
		Weather weather = new Weather();
		weather.setName("London");
		setWeatherEntry(weather, 286.72, 800, "01d", Instant.ofEpochSecond(1234));
		given(this.weatherService.getWeather("london")).willReturn(weather);
		this.mvc.perform(get("/api/weather/now/london"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("London")))
				.andExpect(jsonPath("$.temperature", is(286.72)))
				.andExpect(jsonPath("$.weatherId", is(800)))
				.andExpect(jsonPath("$.weatherIcon", is("01d")))
				.andExpect(jsonPath("$.timestamp", is("1970-01-01T00:20:34Z")));
		verify(this.weatherService).getWeather("london");
	}

	@Test
	public void weatherForecast() throws Exception {
		WeatherForecast forecast = new WeatherForecast();
		forecast.setName("Brussels");
		forecast.getEntries().add(createWeatherEntry(285.45, 600, "02d", Instant.ofEpochSecond(1234)));
		forecast.getEntries().add(createWeatherEntry(294.45, 800, "01d", Instant.ofEpochSecond(5678)));
		given(this.weatherService.getWeatherForecast("brussels")).willReturn(forecast);
		this.mvc.perform(get("/api/weather/weekly/brussels"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Brussels")))
				.andExpect(jsonPath("$.entries[0].temperature", is(285.45)))
				.andExpect(jsonPath("$.entries[1].temperature", is(294.45)));
		verify(this.weatherService).getWeatherForecast("brussels");
	}

	private static WeatherEntry createWeatherEntry(double temperature, int id, String icon,
			Instant timestamp) {
		WeatherEntry entry = new WeatherEntry();
		setWeatherEntry(entry, temperature, id, icon, timestamp);
		return entry;
	}

	private static void setWeatherEntry(WeatherEntry entry, double temperature, int id, String icon,
			Instant timestamp) {
		entry.setTemperature(temperature);
		entry.setWeatherId(id);
		entry.setWeatherIcon(icon);
		entry.setTimestamp(timestamp.getEpochSecond());
	}

}
