package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}