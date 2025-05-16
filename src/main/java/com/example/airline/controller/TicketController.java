package com.example.airline.controller;

import com.example.airline.model.Ticket;
import com.example.airline.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    @PostMapping
    public Ticket createTicket(
            @RequestParam Long scheduleId,
            @RequestParam String passengerName,
            @RequestParam String passengerEmail) {
        return ticketService.createTicket(scheduleId, passengerName, passengerEmail);
    }
    
    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }
} 