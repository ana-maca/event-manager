package com.example.eventmanager.controller;

import com.example.eventmanager.dto.ReservationRequest;
import com.example.eventmanager.model.Reservation;
import com.example.eventmanager.service.ReservationService;
import com.example.eventmanager.util.BrowserRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public Object getAllReservations(@RequestParam(required = false) Long userId,
                                     @RequestParam(required = false) Long eventId,
                                     Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("reservations", reservationService.getAllReservations());
            return "index";
        }
        if (userId != null) {
            return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
        }
        if (eventId != null) {
            return ResponseEntity.ok(reservationService.getReservationsByEvent(eventId));
        }
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public Object getReservationById(@PathVariable Long id, Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("reservation", reservationService.getReservationById(id));
            return "index";
        }
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@Valid @RequestBody ReservationRequest request) {
        return reservationService.createReservation(
                request.getUserId(), request.getEventId(), request.getNumberOfSeats());
    }

    @PatchMapping("/{id}/cancel")
    @ResponseBody
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}