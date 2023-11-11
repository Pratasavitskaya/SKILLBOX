package com.example.studentsregistration;

import com.example.studentsregistration.event.EventHolder;
import com.example.studentsregistration.event.EventRemove;
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
public class RegistrationStudents {

    private final ApplicationEventPublisher publisher;
    public Map<UUID, Student> mapStudents = new HashMap<>();


    @ShellMethod
    public String add(String firstName, String lastName, int age) {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, firstName, lastName, age);
        mapStudents.put(id, student);
        publisher.publishEvent(new EventHolder(this, student));
        return "student was added:";
    }

    @ShellMethod
    @ShellMethodAvailability("canRemoveStudent")
    public String remove(UUID id) {
        Student student = mapStudents.get(id);
        publisher.publishEvent(new EventRemove(this, id));
        mapStudents.remove(id);

        return MessageFormat.format("student with id:{0} was removed ", id);
    }

    public Availability canRemoveStudent(UUID id) {
        return (mapStudents != null && !mapStudents.isEmpty() && mapStudents.get(id) != null) ?
                Availability.unavailable("not student with id = " + id) : Availability.available();


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
