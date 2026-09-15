package com.ticketmaster.eventservice.services;

import com.ticketmaster.eventservice.dtos.seats.CreateSeatDto;
import com.ticketmaster.eventservice.dtos.seats.SeatResponseDto;
import com.ticketmaster.eventservice.dtos.seats.UpdateSeatDto;
import com.ticketmaster.eventservice.entities.Seat;
import com.ticketmaster.eventservice.entities.Venue;
import com.ticketmaster.eventservice.mappers.SeatMapper;
import com.ticketmaster.eventservice.repositories.SeatRepository;
import com.ticketmaster.eventservice.repositories.VenueRepository;
import com.ticketmaster.eventservice.result.ApiError;
import com.ticketmaster.eventservice.result.Result;
import com.ticketmaster.eventservice.validators.VenueSeatValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatService {

    SeatRepository     seatRepository;
    VenueRepository    venueRepository;
    VenueSeatValidator venueSeatValidator;

    public Result<Void> create(CreateSeatDto seatDto) {
        Venue venue = venueRepository.findById(seatDto.venueId()).orElse(null);
        if (venue == null) {
            return Result.Failure(
                    ApiError.NotFound("No Venue associated with this id")
            );
        }

        seatRepository.save(SeatMapper.ToSeat(seatDto, venue));

        return Result.Success(null);
    }

    public Result<SeatResponseDto> getBtId(Long seatId) {
        return seatRepository.findById(seatId).map(s -> Result.Success(
                SeatResponseDto.from(s))).orElse(
                Result.Failure(ApiError.NotFound("No Seat associated with this id"))
        );
    }

    public Result<Void> update(Long id, UpdateSeatDto seatDto) {
        Seat seat = seatRepository.findByIdIncludeVenue(id).orElse(null);
        Venue venue = venueRepository.findById(seatDto.venueId()).orElse(null);

        if (seat == null || venue == null) {
            return Result.Failure(
                    ApiError.NotFound("No Seat associated with this id")
            );
        }

        if(!Objects.equals(venue.getId(), seat.getVenue().getId())) {
            Result<Void> result = venueSeatValidator.validateVenueEnoughSpace(venue);
            if(!result.isSuccess()) {
                return Result.Failure(result.getError());
            }
        }

        SeatMapper.UpdateSeat(seat, seatDto, venue);
        seatRepository.save(seat);

        return Result.Success(null);
    }

    public Result<Void> deleteById(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);

        if (seat == null) {
            return Result.Failure(
                    ApiError.NotFound("No Seat associated with this id")
            );
        }

        seatRepository.delete(seat);

        return Result.Success(null);
    }
}
