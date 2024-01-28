package com.amadeus.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.amadeus.demo.controllers.FlightController;
import com.amadeus.demo.entities.Flight;

@Component
public class DailyFlightInfo {

    private final RestTemplate restTemplate;
    private final FlightController flightController;

    @Autowired
    public DailyFlightInfo(RestTemplate restTemplate, FlightController flightController) {
        this.restTemplate = restTemplate;
        this.flightController = flightController;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void fetchAndProcessFlightData() {
        try {
            String apiUrl = "https://api.example.com/data"; // Replace with your API URL
            Flight responseData = restTemplate.getForObject(apiUrl, Flight.class);
            flightController.newFlight(responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}