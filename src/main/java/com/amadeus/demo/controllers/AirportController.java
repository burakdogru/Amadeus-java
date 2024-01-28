package com.amadeus.demo.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeus.demo.entities.Airport;
import com.amadeus.demo.repositories.AirportRepository;

@RestController
@RequestMapping("/api/airport")
public class AirportController {
	// we can use @Autowired
	private AirportRepository airportRepository;

	public AirportController(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	@PostMapping("new")
	public ResponseEntity<?> newAirport(@RequestBody Airport newAirport) {
		try {
			if(newAirport.getCity().isEmpty()) {
				return ResponseEntity.status(400).body("Invalid Credential!");
			}
			 airportRepository.save(newAirport);
			 return ResponseEntity.status(200).body(newAirport);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
		
	}

	@GetMapping
	public ResponseEntity<?> getAllAirports() {
		try {
			return ResponseEntity.status(200).body(airportRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getAirport(@PathVariable Long id) {
		try {	 
		return ResponseEntity.status(200).body(airportRepository.findById(id).orElse(null));
		}
		catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateAirport(@PathVariable Long id, @RequestBody Airport newAirport) {
		try {
			if(newAirport.getCity().isEmpty()) {
				return ResponseEntity.status(400).body("Invalid Credential!");
			}
			Optional<Airport> airport = airportRepository.findById(id);
			if (airport.isPresent()) {
				Airport updatedAirport = airport.get();
				updatedAirport.setCity(newAirport.getCity());
				return ResponseEntity.status(200).body(newAirport);
			} else
				return ResponseEntity.status(404).body("Not Found!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteAirport(@PathVariable Long id) {
		try {
			airportRepository.deleteById(id);
			return ResponseEntity.status(200).body("Deleted Succesfully!");	
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
		
	}

}
