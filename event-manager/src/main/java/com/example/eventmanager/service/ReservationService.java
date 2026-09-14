package com.example.eventmanager.service;

import com.example.eventmanager.exception.BadRequestException;
import com.example.eventmanager.exception.ResourceNotFoundException;
import com.example.eventmanager.model.Event;
import com.example.eventmanager.model.Reservation;
import com.example.eventmanager.model.User;
import com.example.eventmanager.repository.EventRepository;
import com.example.eventmanager.repository.ReservationRepository;
import com.example.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByEvent(Long eventId) {
        return reservationRepository.findByEventId(eventId);
    }

    public Reservation createReservation(Long userId, Long eventId, int numberOfSeats) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        int reservedSeats = reservationRepository.findByEventIdAndStatus(eventId, Reservation.Status.CONFIRMED)
                .stream()
                .mapToInt(Reservation::getNumberOfSeats)
                .sum();

        if (reservedSeats + numberOfSeats > event.getCapacity()) {
            throw new BadRequestException(
                    "Not enough seats available for event '" + event.getTitle() + "'. "
                            + (event.getCapacity() - reservedSeats) + " seat(s) left.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setNumberOfSeats(numberOfSeats);
        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setStatus(Reservation.Status.CANCELLED);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }
}
