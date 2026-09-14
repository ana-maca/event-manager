package com.example.eventmanager.repository;

import com.example.eventmanager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByEventId(Long eventId);

    List<Reservation> findByEventIdAndStatus(Long eventId, Reservation.Status status);
}
