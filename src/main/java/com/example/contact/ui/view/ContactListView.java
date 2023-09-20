package com.example.contact.ui.view;

import com.example.contact.ui.model.ContactListModel;
import com.example.contact.ui.model.ContactModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static net.pkhapps.mvvm4vaadin.binder.BindingFactory.*;

public class ContactListView extends VerticalLayout {

    private final ContactListModel model;
    private final VerticalLayout contacts;

    public ContactListView(ContactListModel model) {
        setSizeFull();

        this.model = model;
        contacts = new VerticalLayout();
        contacts.setWidthFull();
        contacts.setPadding(false);

        var scroller = new Scroller(contacts);
        scroller.setSizeFull();

        var add = new Button("Add");
        add.addClickListener(event -> model.add());

        var refresh = new Button("Refresh");
        refresh.addClickListener(event -> model.refresh());

        var toolbar = new HorizontalLayout(add, refresh);

        add(scroller, toolbar);

        bindChildrenOnAttach(model.contacts().map(ContactListItem::new), contacts);
    }

    private class ContactListItem extends VerticalLayout {

        public ContactListItem(ContactModel contactModel) {
            var name = new Span(); // TODO Style me
            name.getStyle().set("font-weight", "bold");
            var email = new Span(); // TODO Style me

            add(name, email);

            bindTextOnAttach(contactModel.name(), name);
            bindTextOnAttach(contactModel.email(), email);
            addClickListener(event -> model.select(contactModel));

            // TODO Bind selected state

            addClassName(LumoUtility.BoxShadow.SMALL);
            addClassName(LumoUtility.Background.CONTRAST_5);
        }
    }
}
