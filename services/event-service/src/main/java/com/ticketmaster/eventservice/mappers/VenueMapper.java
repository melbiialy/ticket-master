package com.ticketmaster.eventservice.mappers;

import com.ticketmaster.eventservice.dtos.venues.CreateVenueRequest;
import com.ticketmaster.eventservice.dtos.venues.UpdateVenueRequest;
import com.ticketmaster.eventservice.entities.Venue;

public class VenueMapper {

    public static Venue ToVenue(CreateVenueRequest venueRequest) {
        return Venue.builder()
                .name(venueRequest.name())
                .city(venueRequest.city())
                .address(venueRequest.address())
                .capacity(venueRequest.capacity())
                .build();
    }

    public static void UpdateVenue(Venue venue, UpdateVenueRequest updateVenueRequest) {
        venue.setAddress(updateVenueRequest.address());
        venue.setCity(updateVenueRequest.city());
        venue.setName(updateVenueRequest.name());
        venue.setCapacity(updateVenueRequest.capacity());
    }
}
