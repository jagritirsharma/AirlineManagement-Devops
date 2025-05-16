package com.example.airline.service;

import com.example.airline.model.Schedule;
import com.example.airline.model.Ticket;
import com.example.airline.model.TicketStatus;
import com.example.airline.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Transactional
    public Ticket createTicket(Long scheduleId, String passengerName, String passengerEmail) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        
        if (schedule.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available for this schedule");
        }
        
        Ticket ticket = new Ticket();
        ticket.setSchedule(schedule);
        ticket.setPassengerName(passengerName);
        ticket.setPassengerEmail(passengerEmail);
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setAmount(schedule.getPrice());
        
        schedule.setAvailableSeats(schedule.getAvailableSeats() - 1);
        
        return ticketRepository.save(ticket);
    }
    
    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }
    
    @Transactional
    public void cancelTicket(Long id) {
        Ticket ticket = getTicket(id);
        Schedule schedule = ticket.getSchedule();
        
        ticket.setStatus(TicketStatus.CANCELLED);
        schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);
        
        ticketRepository.save(ticket);
    }
} 