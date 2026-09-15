package com.ticketmaster.eventservice.dtos.venues;

import com.ticketmaster.eventservice.entities.Venue;

/**
 * DTO for {@link com.ticketmaster.eventservice.entities.Venue}
 */
public record VenueResponse(
        Long id, String name, String city, String address, Integer capacity
) {
    public static VenueResponse from(Venue venue) {
        return new VenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getCity(),
                venue.getAddress(),
                venue.getCapacity()
        );
    }
}
