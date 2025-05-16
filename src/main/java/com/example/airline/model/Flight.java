package com.example.airline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private Integer capacity;
    
    @Column(nullable = false)
    private Boolean active = true;
} 