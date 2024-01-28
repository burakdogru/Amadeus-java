package com.amadeus.demo.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amadeus.demo.entities.Flight;
import com.amadeus.demo.repositories.FlightRepository;

@RestController
@RequestMapping("/api/flight")
public class FlightController {
	java.util.Date parsedDate;
	// we can use @Autowired
	private FlightRepository flightRepository;

	public FlightController(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@PostMapping("new")
	public ResponseEntity<?> newFlight(@RequestBody Flight newFlight) {
		try {
			if (newFlight.getArrival_airport().isEmpty() || newFlight.getDeparture_airport().isEmpty()) {
				return ResponseEntity.status(400).body("Invalid Credential!");
			}
			return ResponseEntity.status(400).body(flightRepository.save(newFlight));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllFlights() {
		try {
			return ResponseEntity.status(200).body(flightRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getFlight(@PathVariable Long id) {
		try {
			return ResponseEntity.status(200).body(flightRepository.findById(id).orElse(null));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateFlight(@PathVariable Long id, @RequestBody Flight newFlight) {
		try {
			if (newFlight.getArrival_airport().isEmpty() || newFlight.getDeparture_airport().isEmpty()) {
				return ResponseEntity.status(400).body("Invalid Credential!");
			}
			Optional<Flight> flight = flightRepository.findById(id);
			if (flight.isPresent()) {
				Flight updatedFlight = flight.get();
				updatedFlight.setArrival_airport(newFlight.getArrival_airport());
				updatedFlight.setDeparture_airport(newFlight.getDeparture_airport());
				updatedFlight.setDeparture_date(newFlight.getDeparture_date());
				updatedFlight.setReturn_date(newFlight.getReturn_date());
				updatedFlight.setPrice(newFlight.getPrice());
				flightRepository.save(updatedFlight);
				return ResponseEntity.status(200).body(updatedFlight);
			} else
				return ResponseEntity.status(404).body("Not Found!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteFlight(@PathVariable Long id) {
		try {
			flightRepository.deleteById(id);
			return ResponseEntity.status(200).body("Deleted Succesfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}

	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchFliEntity(
			@RequestParam String departureAirport,
            @RequestParam String arrivalAirport,
            @RequestParam  String  departureDate,
            @RequestParam(required = false) String returnDate){
		Timestamp departureTimestamp = null;
		Timestamp arrivalTimestamp = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
		try {
			 parsedDate = dateFormat.parse(departureDate);
			 departureTimestamp = new Timestamp(parsedDate.getTime());

			 if(returnDate.isEmpty() || returnDate=="") {
				 returnDate = null;
			 }
			 List<Flight> flights = null;
		        if (returnDate == null) {
		            flights = flightRepository.searchOneWay(
		                    departureAirport, arrivalAirport, departureTimestamp);
		        }else {
					 parsedDate = dateFormat.parse(returnDate);
					 arrivalTimestamp = new Timestamp(parsedDate.getTime());
		        	flights = flightRepository.searchRoundTrip(
		                    departureAirport, arrivalAirport, departureTimestamp, arrivalTimestamp);
		        }

		        return ResponseEntity.status(200).body(flights);
		} catch (ParseException e) {
			 return ResponseEntity.status(500).body(e.getMessage());
		}
       
	}
	
}
