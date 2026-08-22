package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}