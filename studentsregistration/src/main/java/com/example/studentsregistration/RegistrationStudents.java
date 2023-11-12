package com.example.studentsregistration;

import com.example.studentsregistration.event.EventHolder;
import com.example.studentsregistration.event.EventRemove;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class RegistrationStudents {

    private final ApplicationEventPublisher publisher;
    public Map<UUID, Student> mapStudents = new HashMap<>();
    public UUID idStudent = null;

    @ShellMethod
    public String add(String firstName, String lastName, int age) {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, firstName, lastName, age);
        mapStudents.put(id, student);
        assert publisher != null;
        publisher.publishEvent(new EventHolder(this, student));
        return "student was added:";
    }

    @ShellMethod
    @ShellMethodAvailability("canRemoveStudent")
    public String remove(UUID id) {

        assert publisher != null;
        publisher.publishEvent(new EventRemove(this, id));
        if (mapStudents.get(id) != null) {
            mapStudents.remove(id);
            return ("Student with id: " + id + " was remove");
        } else
            return ("No student with id: " + id);
    }

    public Availability canRemoveStudent() {
        return (mapStudents != null && !mapStudents.isEmpty()) ?
                Availability.available() : Availability.unavailable("students list is empty  ");


    }

    @ShellMethod
    @ShellMethodAvailability("canView")
    public String view() {

        for (Map.Entry<UUID, Student> entry : mapStudents.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        return "List Students: ";
    }

    public Availability canView() {

        return (mapStudents == null || mapStudents.isEmpty()) ? Availability.unavailable("No students") : Availability.available();

    }

    @ShellMethod
    @ShellMethodAvailability("canRemoveAll")
    public String remove_all() {

        mapStudents = new HashMap<>();

        return "All  students was removed";
    }

    public Availability canRemoveAll() {
        return (mapStudents == null || mapStudents.isEmpty()) ? Availability.unavailable("List is empty") : Availability.available();

    }


}
