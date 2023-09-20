package com.example.contact.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ContactRepository {

    private final Map<UUID, Contact> contacts = new ConcurrentHashMap<>();

    public Contact save(Contact contact) {
        contacts.put(contact.uuid(), contact);
        return contact;
    }

    public Optional<Contact> getByUUID(UUID uuid) {
        return Optional.ofNullable(contacts.get(uuid));
    }

    public List<Contact> findAll() {
        return List.copyOf(contacts.values());
    }

}
