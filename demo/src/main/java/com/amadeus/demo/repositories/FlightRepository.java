package com.amadeus.demo.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amadeus.demo.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	@Query(value = "SELECT * FROM flights WHERE departure_airport = ?1 AND arrival_airport = ?2 AND departure_date >= ?3 AND return_date IS NULL", nativeQuery = true)
	public List<Flight> searchOneWay(String departureAirport, String arrivalAirport, Timestamp departureDate);
	
	@Query(value = "SELECT * FROM flights WHERE departure_airport = ?1 AND arrival_airport = ?2 AND departure_date >= ?3 AND return_date <= ?4", nativeQuery = true)
	public List<Flight> searchRoundTrip(String departureAirport, String arrivalAirport, Timestamp departureDate, Timestamp returnDate);

}
