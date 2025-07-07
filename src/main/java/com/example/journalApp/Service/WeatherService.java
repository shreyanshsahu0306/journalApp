package com.example.journalApp.Service;

import com.example.journalApp.APIResponse.WeatherResponse;
import com.example.journalApp.Config.AppCache;
import com.example.journalApp.Constants.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalAPI = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholder.API_KEY, apikey).replace(Placeholder.CITY, city);
        ResponseEntity<WeatherResponse> respBody = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body = respBody.getBody();
        return body;
    }
}

