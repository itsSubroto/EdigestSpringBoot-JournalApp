package com.subroto.JournalApp.services;

import com.subroto.JournalApp.api.response.WeatherResponse;
import com.subroto.JournalApp.cache.AppCache;
import com.subroto.JournalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService ;

    @Value("${weather.api.key}")
    private String  apiKey;


//    private  static final String  API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";



    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);

        if (weatherResponse!=null){
            return weatherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);

            WeatherResponse body = response.getBody();

            if (body!=null){
                redisService.set("weather_of_" + city,body,300l);
            }

            return body;

        }




        //This is for the post call through the restTemplate just use this httpEntity in the place of requestEntity

//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.set("key","value");
//
//        String userequestBody="{\n" +
//                "    \"username\":\"dev\",\n" +
//                "    \"password\":\"dev\"\n" +
//                "}";
//        HttpEntity<String> httpEntity=new HttpEntity<>(userequestBody,httpHeaders);



    }


}
