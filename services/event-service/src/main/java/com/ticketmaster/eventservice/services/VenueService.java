package com.ticketmaster.eventservice.services;

import com.ticketmaster.eventservice.entities.Seat;
import com.ticketmaster.eventservice.validators.validationResults.VenueSeatValidData;
import com.ticketmaster.eventservice.dtos.seats.AddSeatToVenueDto;
import com.ticketmaster.eventservice.dtos.seats.RemoveSeatFromVenueDto;
import com.ticketmaster.eventservice.dtos.venues.CreateVenueRequest;
import com.ticketmaster.eventservice.dtos.venues.UpdateVenueRequest;
import com.ticketmaster.eventservice.dtos.venues.VenueResponse;
import com.ticketmaster.eventservice.entities.Venue;
import com.ticketmaster.eventservice.mappers.VenueMapper;
import com.ticketmaster.eventservice.repositories.SeatRepository;
import com.ticketmaster.eventservice.repositories.VenueRepository;
import com.ticketmaster.eventservice.result.ApiError;
import com.ticketmaster.eventservice.result.Result;
import com.ticketmaster.eventservice.validators.VenueSeatValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueService {

    VenueRepository    venueRepository;
    SeatRepository     seatRepository;
    VenueSeatValidator venueSeatValidator;

    public Result<VenueResponse> getBtId(Long venueId) {
        return venueRepository.findById(venueId).map(v -> Result.Success(
                VenueResponse.from(v))).orElse(
                Result.Failure(ApiError.NotFound("No Venue associated with this id"))
        );
    }

    public Result<Void> create(CreateVenueRequest venueRequest) {
        venueRepository.save(VenueMapper.ToVenue(venueRequest));
        return Result.Success(null);
    }

    public Result<Void> update(Long id, UpdateVenueRequest venueRequest) {
        Venue venue = venueRepository.findById(id).orElse(null);
        if(venue == null) {
            return Result.Failure(ApiError.NotFound("No Venue associated with this id"));
        }

        VenueMapper.UpdateVenue(venue, venueRequest);
        venueRepository.save(venue);

        return Result.Success(null);
    }

    public Result<Void> addSeatToVenue(AddSeatToVenueDto seatToVenueDto) {
        Seat seat = seatRepository.findById(seatToVenueDto.seatId()).orElse(null);
        Venue venue = venueRepository.findByIdIncludeSeats(seatToVenueDto.venueId()).orElse(null);

        if (seat == null || venue == null) {
            return Result.Failure(ApiError.NotFound("Seat or venue not found"));
        }


        Result<Void> validationRes = venueSeatValidator.validateVenueEnoughSpace(venue);
        if(!validationRes.isSuccess()) {
            return Result.Failure(validationRes.getError());
        }

        seat.setVenue(venue);
        venue.getSeats().add(seat);
        seatRepository.save(seat);

        return Result.Success(null);
    }

    public Result<Void> removeSeatFromVenue(RemoveSeatFromVenueDto venueSeatDto) {
        Result<VenueSeatValidData> validationRes = venueSeatValidator.validateVenueHasSeat(
                venueSeatDto.venueId(), venueSeatDto.seatId()
        );

        if(!validationRes.isSuccess()) {
            return Result.Failure(validationRes.getError());
        }

        VenueSeatValidData data = validationRes.getData();
        data.venue().getSeats().removeIf(s -> s.getId().equals(venueSeatDto.seatId()));
        venueRepository.save(data.venue());

        return Result.Success(null);
    }

    public Result<List<VenueResponse>> getAll(Integer pageIdx, Integer pageSize) {
        return null;
    }

    public Result<Void> deleteById(Long id) {
        venueRepository.deleteById(id);
        return Result.Success(null);
    }
}
