package com.example.contact.domain;

public final class UpdateContactNoteCommand implements ContactCommand {

    private final String newNote;

    public UpdateContactNoteCommand(String newNote) {
        this.newNote = newNote;
    }

    @Override
    public void apply(Contact contact) {
        contact.setNote(newNote);
    }
}
