package com.example.contact.ui.view;

import com.example.contact.application.ContactService;
import com.example.contact.ui.model.ContactListModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;

import static net.pkhapps.mvvm4vaadin.binder.BindingFactory.bindMethodOnAttach;

@Route("")
public class AddressBook extends SplitLayout {

    private final ContactService contactService;
    private final ContactListView contactListView;
    private final Component noContactSelectedView = new Div(new Text("No contact selected"));

    public AddressBook(ContactService contactService) {
        this.contactService = contactService;
        setSizeFull();

        var contactListModel = new ContactListModel(contactService);
        contactListView = new ContactListView(contactListModel);
        addToPrimary(contactListView);

        setSplitterPosition(20);

        bindMethodOnAttach(contactListModel.selectedContact().map(model -> model == null ? noContactSelectedView : new ContactView(model)), this, this::addToSecondary);

        contactListModel.refresh();
    }
}
