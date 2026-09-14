package com.example.eventmanager.service;

import com.example.eventmanager.exception.ResourceNotFoundException;
import com.example.eventmanager.model.Event;
import com.example.eventmanager.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event details) {
        Event event = getEventById(id);
        event.setTitle(details.getTitle());
        event.setDescription(details.getDescription());
        event.setLocation(details.getLocation());
        event.setEventDate(details.getEventDate());
        event.setCapacity(details.getCapacity());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    public List<Event> searchByTitle(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Event> searchByLocation(String location) {
        return eventRepository.findByLocationContainingIgnoreCase(location);
    }
}
