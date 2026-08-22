package com.ticketmaster.eventservice.entities;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /**
     * References an EventSeat in Event Service.
     * No JPA relationship because this is another microservice.
     */
    @Column(name = "event_seat_id", nullable = false)
    private Long eventSeatId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}