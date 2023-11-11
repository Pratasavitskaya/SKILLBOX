package com.example.studentsregistration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private UUID id;
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return  id+" : " + firstName + " " + lastName + " " + age ;

    }
}
