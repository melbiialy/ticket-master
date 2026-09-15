package com.ticketmaster.eventservice.dtos.seats;

import com.ticketmaster.eventservice.entities.Seat;

import java.io.Serializable;

/**
 * DTO for {@link com.ticketmaster.eventservice.entities.Seat}
 */
public record SeatResponseDto(
        Long id, Long venueId, String section, String rowNumber, String seatNumber
) {

    public static SeatResponseDto from(Seat seat) {
        return new SeatResponseDto(
                seat.getId(),
                seat.getVenue().getId(),
                seat.getSection(),
                seat.getRowNumber(),
                seat.getSeatNumber()
        );
    }
}