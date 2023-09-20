package com.example.contact.application;

import com.example.contact.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> findAll() {
        return contactRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .sorted(Comparator.comparing(ContactDTO::name))
                .toList();
    }

    public ContactDTO create(String name, String email, String phone, String note) {
        return toDTO(contactRepository.save(new Contact(new CreateContactCommand(name, email, phone, note))));
    }

    public ContactDTO updateName(UUID uuid, String name) {
        return contactRepository.getByUUID(uuid).map(c -> c.apply(new UpdateContactNameCommand(name))).map(this::toDTO).orElseThrow(this::noSuchContact);
    }

    public ContactDTO updateEmail(UUID uuid, String email) {
        return contactRepository.getByUUID(uuid).map(c -> c.apply(new UpdateContactEmailCommand(email))).map(this::toDTO).orElseThrow(this::noSuchContact);
    }

    public ContactDTO updatePhone(UUID uuid, String phone) {
        return contactRepository.getByUUID(uuid).map(c -> c.apply(new UpdateContactPhoneCommand(phone))).map(this::toDTO).orElseThrow(this::noSuchContact);
    }

    public ContactDTO updateNote(UUID uuid, String note) {
        return contactRepository.getByUUID(uuid).map(c -> c.apply(new UpdateContactNoteCommand(note))).map(this::toDTO).orElseThrow(this::noSuchContact);
    }

    public ContactDTO undo(UUID uuid) {
        return contactRepository.getByUUID(uuid).map(Contact::undo).map(this::toDTO).orElseThrow(this::noSuchContact);
    }

    private IllegalArgumentException noSuchContact() {
        return new IllegalArgumentException("No such contact");
    }

    private ContactDTO toDTO(Contact contact) {
        return new ContactDTO(contact.uuid(), contact.name(), contact.email(), contact.phone(), contact.note());
    }
}
