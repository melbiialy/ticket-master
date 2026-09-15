package com.ticketmaster.eventservice.dtos.seats;

import com.ticketmaster.eventservice.entities.Seat;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

import static com.ticketmaster.eventservice.mappers.SeatMapper.ToSeat;

/**
 * DTO for {@link com.ticketmaster.eventservice.entities.Seat}
 */
public record CreateSeatDto(
        Long venueId,
        @NotBlank String section,
        @NotBlank String rowNumber,
        @NotBlank String seatNumber
){}