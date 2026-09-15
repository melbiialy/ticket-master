package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.Venue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    @EntityGraph(attributePaths = {"seats"})
    @Query("select v from Venue v where v.id = ?1")
    Optional<Venue> findByIdIncludeSeats(Long id);
}