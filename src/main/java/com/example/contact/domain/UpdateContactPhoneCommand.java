package com.example.contact.domain;

public final class UpdateContactPhoneCommand implements ContactCommand {

    private final String newPhone;

    public UpdateContactPhoneCommand(String newPhone) {
        this.newPhone = newPhone;
    }

    @Override
    public void apply(Contact contact) {
        contact.setPhone(newPhone);
    }
}
