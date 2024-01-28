package com.amadeus.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amadeus.demo.entities.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

}
