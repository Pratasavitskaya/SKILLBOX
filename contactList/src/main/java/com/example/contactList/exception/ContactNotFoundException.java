package com.example.contactList.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String messages){
        super(messages);
    }
}
