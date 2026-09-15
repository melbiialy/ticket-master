package com.ticketmaster.eventservice.repositories;

import com.ticketmaster.eventservice.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select s from Seat s join fetch s.venue where s.id = ?1")
    Optional<Seat> findByIdIncludeVenue(Long id);
}