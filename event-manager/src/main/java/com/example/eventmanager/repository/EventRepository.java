package com.example.eventmanager.repository;

import com.example.eventmanager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findByLocationContainingIgnoreCase(String location);

    List<Event> findByEventDateAfter(LocalDateTime date);
}
