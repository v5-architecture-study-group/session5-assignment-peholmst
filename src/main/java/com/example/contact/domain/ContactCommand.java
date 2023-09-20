package com.example.contact.domain;

public interface ContactCommand {

    void apply(Contact contact);
}
