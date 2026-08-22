package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}