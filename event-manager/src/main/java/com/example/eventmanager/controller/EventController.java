package com.example.eventmanager.controller;

import com.example.eventmanager.model.Event;
import com.example.eventmanager.service.EventService;
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
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public Object getAllEvents(@RequestParam(required = false) String title,
                               @RequestParam(required = false) String location,
                               Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("events", eventService.getAllEvents());
            return "index";
        }
        if (title != null) {
            return ResponseEntity.ok(eventService.searchByTitle(title));
        }
        if (location != null) {
            return ResponseEntity.ok(eventService.searchByLocation(location));
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public Object getEventById(@PathVariable Long id, Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("event", eventService.getEventById(id));
            return "index";
        }
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Event updateEvent(@PathVariable Long id, @Valid @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}