package com.example.contact.ui.model;

import com.example.contact.application.ContactService;
import jakarta.annotation.Nullable;
import net.pkhapps.mvvm4vaadin.model.*;

public final class ContactListModel {

    private final ContactService contactService;

    private final DefaultObservableList<ContactModel> contacts = ModelFactory.observableList();
    private final DefaultObservableValue<ContactModel> selectedContact = ModelFactory.observableValue();

    public ContactListModel(ContactService contactService) {
        this.contactService = contactService;
    }

    public ObservableList<ContactModel> contacts() {
        return contacts;
    }

    public ObservableValue<ContactModel> selectedContact() {
        return selectedContact;
    }

    public void refresh() {
        contacts.setItems(contactService.findAll().stream().map(dto -> new ContactModel(contactService, dto)));
        selectedContact.setValue(null);
    }

    public void select(@Nullable ContactModel contactModel) {
        selectedContact.setValue(contactModel);
    }

    public void add() {
        var newModel = new ContactModel(contactService);
        contacts.add(newModel);
        select(newModel);
    }
}
