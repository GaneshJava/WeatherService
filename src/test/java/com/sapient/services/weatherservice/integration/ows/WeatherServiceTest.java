package com.sapient.services.weatherservice.integration.ows;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
@TestPropertySource(properties = "app.weather.api.key=43076f45be3c11a45de3ff9ddfd3ef40")
public class WeatherServiceTest {
	
	private static final String api_key = "43076f45be3c11a45de3ff9ddfd3ef40";
	private static final String URL = "http://api.openweathermap.org/data/2.5/";
	
	@Autowired
	private WeatherService weatherService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void getWeather() {
		this.server.expect(
				requestTo(URL+"weather?q=bengaluru&units=metric&APPID="+api_key))
				.andRespond(withSuccess(
						new ClassPathResource("weather-bengaluru.json", getClass()),
						MediaType.APPLICATION_JSON));
		Weather weather = this.weatherService.getWeather("Bengaluru");
		System.out.println("forecast.getName()=======>>>"+weather.getName());
		assertThat(weather.getName()).isEqualTo("Bengaluru");
		assertThat(weather.getTemperature()).isEqualTo(286.72, Offset.offset(0.1));
		assertThat(weather.getWeatherId()).isEqualTo(800);
		assertThat(weather.getWeatherIcon()).isEqualTo("01d");
		this.server.verify();
	}

	@Test
	public void getWeatherForecast() {
		this.server.expect(
				requestTo(URL+"forecast?q=bengaluru&units=metric&APPID="+api_key))
				.andRespond(withSuccess(
						new ClassPathResource("forecast-bengaluru.json", getClass()),
						MediaType.APPLICATION_JSON));
		this.weatherService.getWeatherForecast("Bengaluru");
		this.server.verify();
	}

}
