package com.example.contact.ui.view;

import com.example.contact.ui.model.ContactModel;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableConsumer;
import net.pkhapps.mvvm4vaadin.model.support.MappingFunctions;

import static net.pkhapps.mvvm4vaadin.binder.BindingFactory.*;

public class ContactView extends VerticalLayout {

    private final ContactModel model;

    public ContactView(ContactModel model) {
        this.model = model;
        var name = new TextField("Name");
        name.setValueChangeMode(ValueChangeMode.TIMEOUT);
        name.addValueChangeListener(doIfClientEventOnly(model::updateName));

        var email = new EmailField("Email");
        email.setValueChangeMode(ValueChangeMode.TIMEOUT);
        email.addValueChangeListener(doIfClientEventOnly(model::updateEmail));

        var phone = new TextField("Phone");
        phone.setValueChangeMode(ValueChangeMode.TIMEOUT);
        phone.addValueChangeListener(doIfClientEventOnly(model::updatePhone));

        var note = new TextArea("Note");
        note.setValueChangeMode(ValueChangeMode.TIMEOUT);
        note.addValueChangeListener(doIfClientEventOnly(model::updateNote));

        bindFieldValueOnAttach(model.name(), name);
        bindFieldValueOnAttach(model.email(), email);
        bindFieldValueOnAttach(model.phone(), phone);
        bindFieldValueOnAttach(model.note(), note);

        bindReadOnlyOnAttach(model.readOnly(), name);
        bindReadOnlyOnAttach(model.readOnly(), email);
        bindReadOnlyOnAttach(model.readOnly(), phone);
        bindReadOnlyOnAttach(model.readOnly(), note);

        var nameMissing = new Div(new Text("Name is required"));
        bindVisibleOnAttach(model.nameMissing(), nameMissing);

        var emailAndPhoneMissing = new Div(new Text("You should specify either an email or a phone"));
        bindVisibleOnAttach(model.bothEmailAndPhoneMissing(), emailAndPhoneMissing);

        var undo = new Button(VaadinIcon.TIME_BACKWARD.create());
        undo.addClickListener(event -> model.undo());
        bindVisibleOnAttach(model.readOnly().map(MappingFunctions::invert), undo);

        var editMode = new Button("Edit Mode");
        editMode.addClickListener(event -> model.toggleEditMode());
        bindVisibleOnAttach(model.readOnly(), editMode);

        var viewMode = new Button("View Mode");
        viewMode.addClickListener(event -> model.toggleViewMode());
        bindVisibleOnAttach(model.readOnly().map(MappingFunctions::invert), viewMode);

        var toolbar = new HorizontalLayout(editMode, viewMode, undo);
        bindVisibleOnAttach(model.actionsVisible(), toolbar);

        add(name, nameMissing, email, phone, emailAndPhoneMissing, note, toolbar);
    }

    private <V> HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<?, V>> doIfClientEventOnly(SerializableConsumer<V> consumer) {
        return event -> {
            if (event.isFromClient()) {
                consumer.accept(event.getValue());
            }
        };
    }
}
