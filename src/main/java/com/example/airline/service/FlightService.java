package com.example.airline.service;

import com.example.airline.model.Flight;
import com.example.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    public List<Flight> getAllFlights() {
        return flightRepository.findByActiveTrue();
    }
    
    public Flight getFlight(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }
} 