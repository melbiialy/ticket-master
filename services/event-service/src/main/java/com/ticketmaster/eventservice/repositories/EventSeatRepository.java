package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.EventSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSeatRepository extends JpaRepository<EventSeat, Long> {
}