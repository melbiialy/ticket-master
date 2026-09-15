package com.ticketmaster.eventservice.dtos.seats;

import jakarta.validation.constraints.NotNull;

public record AddSeatToVenueDto(
        @NotNull Long venueId,
        @NotNull Long seatId
) {
}
