package com.example.studentsregistration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;

import java.util.UUID;

@ShellComponent
@ConditionalOnProperty(prefix = "add",name = "students",havingValue ="true")
public class AddStudents extends RegistrationStudents{

    public AddStudents(ApplicationEventPublisher publisher) {
        super(publisher);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listener() {
        UUID id = UUID.randomUUID();
        mapStudents.put(id, new Student(id, "Alena", "Mil", 18));
        id = UUID.randomUUID();
        mapStudents.put(id, new Student(id, "Anatol", "Kras", 19));
        id = UUID.randomUUID();
        mapStudents.put(id, new Student(id, "Masha", "Krasotka", 18));
        id = UUID.randomUUID();
        mapStudents.put(id, new Student(id, "Alisa", "Lin", 19));
        id = UUID.randomUUID();
        mapStudents.put(id, new Student(id, "Irina", "Krut", 17));

        System.out.println("students added");
    }
}
