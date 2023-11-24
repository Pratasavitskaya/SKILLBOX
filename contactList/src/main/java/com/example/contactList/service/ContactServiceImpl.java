package com.example.contactList.service;

import com.example.contactList.Contact;
import com.example.contactList.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.text.CompactNumberFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;
    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in ContactServiceImpl");

        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Call findById in ContactServiceImpl");

        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in ContactServiceImpl");

        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in ContactServiceImpl");

        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in ContactServiceImpl");

        contactRepository.deleteById(id);

    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Call batchInsert in ContactServiceImpl");

        contactRepository.batchInsert(contacts);
    }
}
