package com.amadeus.demo.entities;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity(name = "flights")
@Table(name = "flights")
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String departure_airport;
	@NotNull
	private String arrival_airport;
	@NotNull
    @Column(columnDefinition = "TIMESTAMP")
	private Timestamp departure_date;
    @Column(columnDefinition = "TIMESTAMP")
	private Timestamp return_date;
    @NotNull
    private float price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeparture_airport() {
		return departure_airport;
	}
	public void setDeparture_airport(String departure_airport) {
		this.departure_airport = departure_airport;
	}
	public String getArrival_airport() {
		return arrival_airport;
	}
	public void setArrival_airport(String arrival_airport) {
		this.arrival_airport = arrival_airport;
	}
	public Timestamp getDeparture_date() {
		return departure_date;
	}
	public void setDeparture_date(Timestamp departure_date) {
		this.departure_date = departure_date;
	}
	public Timestamp getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Timestamp return_date) {
		this.return_date = return_date;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

}
