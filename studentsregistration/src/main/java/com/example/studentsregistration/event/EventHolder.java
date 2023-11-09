package com.example.studentsregistration.event;

import com.example.studentsregistration.Student;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class EventHolder extends ApplicationEvent {
    private final Student student;
    public EventHolder(Object source, Student student) {
        super(source);
        this.student = student;
    }
}
