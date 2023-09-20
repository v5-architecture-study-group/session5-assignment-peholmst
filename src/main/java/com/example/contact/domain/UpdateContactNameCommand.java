package com.example.contact.domain;

import java.util.Objects;

public final class UpdateContactNameCommand implements ContactCommand {

    private final String newName;

    public UpdateContactNameCommand(String newName) {
        this.newName = Objects.requireNonNull(newName);
        if (newName.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
    }

    @Override
    public void apply(Contact contact) {
        contact.setName(newName);
    }
}
