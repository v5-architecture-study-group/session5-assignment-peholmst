package com.example.contact.domain;

public final class UpdateContactEmailCommand implements ContactCommand {

    private final String newEmail;

    public UpdateContactEmailCommand(String newEmail) {
        this.newEmail = newEmail;
    }

    @Override
    public void apply(Contact contact) {
        contact.setEmail(newEmail);
    }
}
