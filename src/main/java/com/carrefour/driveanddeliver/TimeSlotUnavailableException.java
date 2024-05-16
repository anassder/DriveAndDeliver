package com.carrefour.driveanddeliver;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TimeSlotUnavailableException extends Exception {

    public TimeSlotUnavailableException(String message) {
        super(message);
    }
}
