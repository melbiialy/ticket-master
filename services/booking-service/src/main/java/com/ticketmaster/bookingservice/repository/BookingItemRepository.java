package com.ticketmaster.bookingservice.repository;

import com.ticketmaster.bookingservice.entities.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingItemRepository extends JpaRepository<BookingItem, UUID> {
    
}
