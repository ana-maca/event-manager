package com.example.eventmanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "eventId is required")
    private Long eventId;

    @Min(value = 1, message = "numberOfSeats must be at least 1")
    private int numberOfSeats = 1;
}
