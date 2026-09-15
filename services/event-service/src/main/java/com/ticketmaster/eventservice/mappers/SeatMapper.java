package com.ticketmaster.eventservice.mappers;

import com.ticketmaster.eventservice.dtos.seats.CreateSeatDto;
import com.ticketmaster.eventservice.dtos.seats.UpdateSeatDto;
import com.ticketmaster.eventservice.entities.Seat;
import com.ticketmaster.eventservice.entities.Venue;

public class SeatMapper {

    public static Seat ToSeat(CreateSeatDto seatDto, Venue venue) {
        return Seat.builder()
                .venue(venue)
                .section(seatDto.section())
                .rowNumber(seatDto.rowNumber())
                .seatNumber(seatDto.seatNumber())
                .build();
    }

    public static void UpdateSeat(Seat seat, UpdateSeatDto seatDto, Venue newVenue) {
        seat.setSeatNumber(seatDto.seatNumber());
        seat.setSection(seatDto.section());
        seat.setRowNumber(seatDto.rowNumber());
        seat.setVenue(newVenue);
    }
}
