package com.example.studentsregistration.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class EventRemove extends ApplicationEvent {
    private final UUID id;
    public EventRemove(Object source, UUID id) {
        super(source);
        this.id = id;
    }
}
