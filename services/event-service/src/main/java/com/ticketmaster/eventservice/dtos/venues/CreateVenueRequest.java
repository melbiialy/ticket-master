package com.ticketmaster.eventservice.dtos.venues;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO for {@link com.ticketmaster.eventservice.entities.Venue}
 */
public record CreateVenueRequest(
        @NotBlank String name,
        @NotBlank String city,
        String address,
        @NotNull @PositiveOrZero Integer capacity
) {}