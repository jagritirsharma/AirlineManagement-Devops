package com.example.airline.controller;

import com.example.airline.model.Flight;
import com.example.airline.model.Schedule;
import com.example.airline.service.FlightService;
import com.example.airline.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @GetMapping
    public List<Flight> getAllFlights(@RequestParam(defaultValue = "asc") String sort) {
        List<Flight> flights = flightService.getAllFlights();
        if (sort.equalsIgnoreCase("desc")) {
            flights.sort((f1, f2) -> f2.getId().compareTo(f1.getId()));
        } else {
            flights.sort((f1, f2) -> f1.getId().compareTo(f2.getId()));
        }
        return flights;
    }
    
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.getFlight(id);
    }
    
    @GetMapping("/{id}/schedules")
    public List<Schedule> getFlightSchedules(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return scheduleService.getFlightSchedules(id, startDate, endDate);
    }
} 