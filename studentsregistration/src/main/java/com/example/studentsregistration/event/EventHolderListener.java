package com.example.studentsregistration.event;

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
        System.out.println("student with id "+eventRemove.getId()+" was removed ");
    }
}
