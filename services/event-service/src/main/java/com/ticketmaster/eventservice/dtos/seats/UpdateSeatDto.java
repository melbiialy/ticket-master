package com.ticketmaster.eventservice.dtos.seats;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.ticketmaster.eventservice.entities.Seat}
 */
public record UpdateSeatDto(
        Long venueId,
        @NotBlank String section,
        @NotBlank String rowNumber,
        @NotBlank String seatNumber
) {}