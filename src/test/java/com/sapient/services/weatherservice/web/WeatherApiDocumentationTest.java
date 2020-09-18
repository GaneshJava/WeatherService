package com.sapient.services.weatherservice.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.services.weatherservice.integration.ows.Weather;
import com.sapient.services.weatherservice.integration.ows.WeatherForecast;
import com.sapient.services.weatherservice.integration.ows.WeatherService;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@TestPropertySource(properties="spring.jackson.serialization.indent-output:true")
public class WeatherApiDocumentationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WeatherService weatherService;

	@Autowired
	private ObjectMapper objectMapper;

	private final FieldDescriptor[] weatherFields = new FieldDescriptor[] {
			fieldWithPath("weatherId").description("ID of the weather conditions"),
			fieldWithPath("weatherIcon").description("ID of the weather icon"),
			fieldWithPath("temperature").description("Temperature in Kelvin"),
			fieldWithPath("timestamp").description("ISO-8601 timestamp of the weather data")};

	@Test
	public void weather() throws Exception {
		Weather weather = this.objectMapper.readValue(
				new ClassPathResource("/com/sapient/services/weatherservice/integration/ows/weather-bengaluru.json").getURL(),
				Weather.class);
		given(this.weatherService.getWeather("bengaluru")).willReturn(weather);
		given(this.weatherService.getWeather("bengaluru")).willReturn(weather);
		this.mockMvc.perform(get("/api/weather/now/{city}", "bengaluru"))
				.andExpect(status().isOk())
				.andDo(document("weather",
						pathParameters(
								parameterWithName("city").description("Name of the city")),
						responseFields(
								fieldWithPath("name").description("Name of the weather data"))
								.and(this.weatherFields)));
	}

	@Test
	public void weatherForecast() throws Exception {
		WeatherForecast weatherForecast = this.objectMapper.readValue(
				new ClassPathResource("/com/sapient/services/weatherservice/integration/ows/forecast-bengaluru.json").getURL(),
				WeatherForecast.class);
		given(this.weatherService.getWeatherForecast("bengaluru")).willReturn(weatherForecast);
		given(this.weatherService.getWeatherForecast("bengaluru")).willReturn(weatherForecast);
		this.mockMvc.perform(get("/api/weather/weekly/{city}", "bengaluru"))
				.andExpect(status().isOk())
				.andDo(document("weather-forecast",
						pathParameters(
								parameterWithName("city").description("Name of the city")),
						responseFields(
								fieldWithPath("name").description("Name of the weather forecast"),
								fieldWithPath("entries").description("Array of weather data"))
								.andWithPrefix("entries[].", this.weatherFields)));
	}

}
