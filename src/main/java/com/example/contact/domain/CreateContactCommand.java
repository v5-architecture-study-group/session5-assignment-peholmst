package com.example.contact.domain;

import jakarta.annotation.Nullable;

import java.util.Objects;

public final class CreateContactCommand implements ContactCommand {

    private final String name;
    private final String email;
    private final String phone;
    private final String note;

    public CreateContactCommand(String name, @Nullable String email, @Nullable String phone, @Nullable String note) {
        this.name = Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.email = email;
        this.phone = phone;
        this.note = note;
    }

    @Override
    public void apply(Contact contact) {
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setNote(note);
    }
}
