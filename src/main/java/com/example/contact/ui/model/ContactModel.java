package com.example.contact.ui.model;

import com.example.contact.application.ContactDTO;
import com.example.contact.application.ContactService;
import jakarta.annotation.Nullable;
import net.pkhapps.mvvm4vaadin.model.DefaultObservableValue;
import net.pkhapps.mvvm4vaadin.model.ObservableValue;
import net.pkhapps.mvvm4vaadin.model.support.MappingFunctions;

import java.util.UUID;

import static net.pkhapps.mvvm4vaadin.model.ModelFactory.computedValue;
import static net.pkhapps.mvvm4vaadin.model.ModelFactory.observableValue;

public final class ContactModel {

    private final ContactService contactService;
    private UUID uuid;
    private final DefaultObservableValue<String> name = observableValue();
    private final DefaultObservableValue<String> email = observableValue();
    private final DefaultObservableValue<String> phone = observableValue();
    private final DefaultObservableValue<String> note = observableValue();
    private final DefaultObservableValue<Boolean> readOnly = observableValue(true);
    private final ObservableValue<Boolean> nameMissing = name.map(MappingFunctions::isBlankString);
    private final ObservableValue<Boolean> emailMissing = email.map(MappingFunctions::isBlankString);
    private final ObservableValue<Boolean> phoneMissing = phone.map(MappingFunctions::isBlankString);
    private final ObservableValue<Boolean> bothEmailAndPhoneMissing = computedValue(() -> emailMissing.getValue() && phoneMissing.getValue(), emailMissing, phoneMissing);
    private final DefaultObservableValue<Boolean> actionsVisible = observableValue(false);

    public ContactModel(ContactService contactService) {
        this(contactService, null);
    }

    public ContactModel(ContactService contactService, @Nullable ContactDTO contact) {
        this.contactService = contactService;
        if (contact != null) {
            updateFromDTO(contact);
        }
        readOnly.setValue(contact != null);
        actionsVisible.setValue(contact != null);
    }

    private void updateFromDTO(ContactDTO contact) {
        uuid = contact.uuid();
        name.setValue(contact.name());
        email.setValue(contact.email());
        phone.setValue(contact.phone());
        note.setValue(contact.note());
        actionsVisible.setValue(true);
    }

    private boolean isNew() {
        return uuid == null;
    }

    private void tryCreate() {
        if (uuid == null && !nameMissing.getValue()) {
            updateFromDTO(contactService.create(name.getValue(), email.getValue(), phone.getValue(), note.getValue()));
        }
    }

    public ObservableValue<String> name() {
        return name;
    }

    public void updateName(String name) {
        if (isNew()) {
            this.name.setValue(name);
            tryCreate();
        } else {
            updateFromDTO(contactService.updateName(uuid, name));
        }
    }

    public ObservableValue<String> email() {
        return email;
    }

    public void updateEmail(String email) {
        if (isNew()) {
            this.email.setValue(email);
            tryCreate();
        } else {
            updateFromDTO(contactService.updateEmail(uuid, email));
        }
    }

    public ObservableValue<String> phone() {
        return phone;
    }

    public void updatePhone(String phone) {
        if (isNew()) {
            this.phone.setValue(phone);
            tryCreate();
        } else {
            updateFromDTO(contactService.updatePhone(uuid, phone));
        }
    }

    public ObservableValue<String> note() {
        return note;
    }

    public void updateNote(String note) {
        if (isNew()) {
            this.note.setValue(note);
            tryCreate();
        } else {
            updateFromDTO(contactService.updateNote(uuid, note));
        }
    }

    public ObservableValue<Boolean> readOnly() {
        return readOnly;
    }

    public ObservableValue<Boolean> nameMissing() {
        return nameMissing;
    }

    public ObservableValue<Boolean> bothEmailAndPhoneMissing() {
        return bothEmailAndPhoneMissing;
    }

    public void undo() {
        if (uuid != null) {
            updateFromDTO(contactService.undo(uuid));
        }
    }

    public void toggleEditMode() {
        readOnly.setValue(false);
    }

    public void toggleViewMode() {
        readOnly.setValue(true);
    }

    public ObservableValue<Boolean> actionsVisible() {
        return actionsVisible;
    }
}
