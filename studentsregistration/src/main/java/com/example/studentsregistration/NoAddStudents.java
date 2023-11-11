package com.example.studentsregistration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@ConditionalOnProperty(prefix = "add",name = "students",havingValue ="false")
public class NoAddStudents extends RegistrationStudents{

    public NoAddStudents(ApplicationEventPublisher publisher) {
        super(publisher);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listener() {
        System.out.println("into no add");
    }
}
