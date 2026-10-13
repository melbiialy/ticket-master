package com.ticketmaster.bookingservice.repository;

import com.ticketmaster.bookingservice.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
}
