package com.example.studentsregistration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@ConditionalOnProperty("app.add-students.enabled")
public class AddStudentOnStart {
    private final RegistrationStudents registrationStudents = new RegistrationStudents();

    //@EventListener(ApplicationStartedEvent.class)
    @EventListener(ApplicationStartedEvent.class)
    public void addStudents() {
        UUID id = UUID.randomUUID();
        Map<UUID, Student> mapStudents = new HashMap<>();
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
        registrationStudents.listener(mapStudents);
    }

}
