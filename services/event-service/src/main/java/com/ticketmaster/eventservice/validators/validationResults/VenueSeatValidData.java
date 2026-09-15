package com.ticketmaster.eventservice.validators.validationResults;

import com.ticketmaster.eventservice.entities.Seat;
import com.ticketmaster.eventservice.entities.Venue;

public record VenueSeatValidData(Venue venue, Seat seat) {
}
