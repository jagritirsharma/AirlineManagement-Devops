package com.example.airline.service;

import com.example.airline.model.Schedule;
import com.example.airline.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    public List<Schedule> getFlightSchedules(Long flightId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findByFlightIdAndDepartureTimeBetweenAndActiveTrue(
            flightId, startDate, endDate);
    }
    
    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
    }
} 