package com.example.contact.domain;

import java.util.Stack;
import java.util.UUID;

public class Contact {

    private final UUID uuid;
    private final Stack<ContactCommand> commands = new Stack<>();

    private String name;
    private String email;
    private String phone;
    private String note;

    public Contact(CreateContactCommand createContactCommand) {
        this.uuid = UUID.randomUUID();
        apply(createContactCommand);
    }

    public Contact apply(ContactCommand command) {
        commands.push(command);
        command.apply(this);
        return this;
    }

    public Contact undo() {
        if (canUndo()) {
            commands.pop();
            commands.forEach(c -> c.apply(this));
        }
        return this;
    }

    public boolean canUndo() {
        return commands.size() > 1;
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String note() {
        return note;
    }

    void setName(String name) {
        this.name = name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    void setNote(String note) {
        this.note = note;
    }
}
