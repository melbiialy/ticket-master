package com.ticketmaster.eventservice.controllers;

import com.ticketmaster.eventservice.dtos.seats.AddSeatToVenueDto;
import com.ticketmaster.eventservice.dtos.seats.RemoveSeatFromVenueDto;
import com.ticketmaster.eventservice.dtos.venues.CreateVenueRequest;
import com.ticketmaster.eventservice.dtos.venues.UpdateVenueRequest;
import com.ticketmaster.eventservice.dtos.venues.VenueResponse;
import com.ticketmaster.eventservice.result.BaseController;
import com.ticketmaster.eventservice.result.Result;
import com.ticketmaster.eventservice.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
@RequiredArgsConstructor
public class VenueController extends BaseController {

    private final VenueService venueService;

    @GetMapping("/{venueId}")
    public ResponseEntity<Result<VenueResponse>> getById (@PathVariable Long venueId) {
        Result<VenueResponse> result = venueService.getBtId(venueId);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @PostMapping
    public ResponseEntity<Result<Void>> create(@RequestBody CreateVenueRequest venueRequest) {
        Result<Void> result = venueService.create(venueRequest);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Void>> update (@PathVariable Long id, @RequestBody UpdateVenueRequest venueRequest) {
        Result<Void> result = venueService.update(id, venueRequest);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @PostMapping("/seats")
    public ResponseEntity<Result<Void>> addSeatToVenue (@RequestBody AddSeatToVenueDto seatToVenueDto) {
        Result<Void> result = venueService.addSeatToVenue(seatToVenueDto);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @DeleteMapping("/seats")
    public ResponseEntity<Result<Void>> removeSeatFromVenue(@RequestBody RemoveSeatFromVenueDto venueSeatDto) {
        Result<Void> result = venueService.removeSeatFromVenue(venueSeatDto);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @GetMapping
    public ResponseEntity<Result<List<VenueResponse>>> getAll(
            @RequestParam(defaultValue = "0") Integer pageIdx,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        Result<List<VenueResponse>> result = venueService.getAll(pageIdx, pageSize);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable Long id) {
        Result<Void> result = venueService.deleteById(id);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }
}
