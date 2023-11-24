package com.example.contactList.listener;

import com.example.contactList.Contact;
import com.example.contactList.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactCreator {

    private final ContactService contactService;

   // @EventListener(ApplicationStartedEvent.class)
    public void createContactData(){
        log.debug("Calling DatabaseContactCreator -> createContactData...");

        List<Contact> contacts = new ArrayList<>();
        for (int i=0; i < 10; i++){
            int value = i + 1;
            Contact contact = new Contact();
            contact.setId((long)value);
            contact.setFirstName("First Name " + value);
            contact.setLastName("Last Name " + value);
            contact.setEmail("email@mail.com" + value);
            contact.setPhone("+37529333333" + value);

            contacts.add(contact);
        }

        contactService.batchInsert(contacts);

    }
}
