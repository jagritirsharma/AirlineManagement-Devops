package com.example.airline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
    
    private String passengerName;
    private String passengerEmail;
    private String seatNumber;
    private LocalDateTime bookingTime;
    private Double amount;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.BOOKED;
} 