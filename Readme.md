# Start the Weather Microservice
1. go to this directory "\weather-service\src\main\java\com\sapient\services\weatherservice\"
2. run 'WeatherServiceApplication.java' run as Java Application
3. default spring boot tomcat should run the service on port 8080
4. if you wish to change the port add ths line "server.port=8081" @application.properties file located at "weather-service\src\main\resources\application.properties"


# Rest API calls
Access the Weather rest services:
1. Weather Forecast service for a week can be accessed here "/api/weather/weekly/{city}"
	syntax: http://hostname:port/api/weather/weekly/{city}
	example: http://localhost:8080/api/weather/weekly/London
	
2. Current Weather service can be accessed here "/api/weather/now/{city}"
	syntax: http://hostname:port/api/weather/now/{city}
	example: http://localhost:8080/api/weather/now/London

# Weather UI App
go to "\weather-service\src\main\resources\static\weather-ui" directory
run-> npm install (it should run all required node modules)
start-> npm start (it should run the app on port 3000 by default)
Access the Weather UI from http://localhost:3000