package com.example.studentsregistration.config;

import com.example.studentsregistration.RegistrationStudents;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("app.event-students.enabled")
public class StudentsConfiguration {
    @Bean
    public RegistrationStudents registrationStudents(ApplicationEventPublisher publisher){
        return new RegistrationStudents(publisher);
    }

}
