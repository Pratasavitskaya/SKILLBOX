package com.example.studentsregistration.event;

import com.example.studentsregistration.RegistrationStudents;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHolderListener {
    @EventListener
    public void listen(EventHolder eventHolder){
        System.out.println(eventHolder.getStudent());
    }

    @EventListener
    public void listenRemove(EventRemove eventRemove){

    }
}
