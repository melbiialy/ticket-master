package com.ticketmaster.eventservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_venue_seat",
                        columnNames = {"venue_id", "section", "row_number", "seat_number"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private String section;

    @Column(name = "row_number", nullable = false)
    private String rowNumber;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;
}