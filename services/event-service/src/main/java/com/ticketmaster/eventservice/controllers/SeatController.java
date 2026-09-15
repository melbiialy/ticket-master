package com.ticketmaster.eventservice.controllers;

import com.ticketmaster.eventservice.dtos.seats.CreateSeatDto;
import com.ticketmaster.eventservice.dtos.seats.SeatResponseDto;
import com.ticketmaster.eventservice.dtos.seats.UpdateSeatDto;
import com.ticketmaster.eventservice.result.BaseController;
import com.ticketmaster.eventservice.result.Result;
import com.ticketmaster.eventservice.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController extends BaseController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<Result<Void>> create(@RequestBody CreateSeatDto seatDto) {
        Result<Void> result = seatService.create(seatDto);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<Result<SeatResponseDto>> getById(@PathVariable Long seatId) {
        Result<SeatResponseDto> result = seatService.getBtId(seatId);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Void>> update(@PathVariable Long id, @RequestBody UpdateSeatDto seatDto) {
        Result<Void> result = seatService.update(id, seatDto);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable Long id) {
        Result<Void> result = seatService.deleteById(id);
        return ResponseEntity.status(resolveStatus(result)).body(result);
    }
}