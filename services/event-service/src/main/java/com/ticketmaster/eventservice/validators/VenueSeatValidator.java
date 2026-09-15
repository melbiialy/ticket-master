package com.ticketmaster.eventservice.validators;

import com.ticketmaster.eventservice.validators.validationResults.VenueSeatValidData;
import com.ticketmaster.eventservice.entities.Seat;
import com.ticketmaster.eventservice.entities.Venue;
import com.ticketmaster.eventservice.repositories.SeatRepository;
import com.ticketmaster.eventservice.repositories.VenueRepository;
import com.ticketmaster.eventservice.result.ApiError;
import com.ticketmaster.eventservice.result.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueSeatValidator {

    SeatRepository  seatRepository;
    VenueRepository venueRepository;

    public Result<Void> validateVenueEnoughSpace(Venue venue) {
        if(venue.getSeats().size() >= venue.getCapacity()) {
            return Result.Failure(ApiError.BadRequest("Venue does not have enough space for the seat"));
        }

        return Result.Success(null);
    }

    public Result<VenueSeatValidData> validateVenueHasSeat(Long venueId, Long seatId) {
        Venue venue = venueRepository.findByIdIncludeSeats(venueId).orElse(null);
        Seat seat = seatRepository.findById(seatId).orElse(null);

        if (seat == null || venue == null) {
            return Result.Failure(ApiError.NotFound("Seat or venue not found"));
        }

        if (!venue.getSeats().contains(seat)) {
            return Result.Failure(ApiError.BadRequest("Venue does not have the specified seat"));
        }

        return Result.Success(new VenueSeatValidData(venue, seat));
    }
}
