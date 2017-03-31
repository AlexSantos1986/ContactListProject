package com.alexsantos.contactlistproject.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alexsantos.contactlistproject.R;
import com.alexsantos.contactlistproject.model.Contact;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by Alex on 30/03/2017.
 */

public class ContactFirebaseAdapter extends FirebaseListAdapter<Contact> {

    static final Class<Contact> modelClass= Contact.class;
    static final int modelLayout= R.layout.contact;

    public ContactFirebaseAdapter(Activity activity, Firebase ref) {
        super(activity, modelClass, modelLayout, ref);


    }

    public ContactFirebaseAdapter(Activity activity,  Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View view, Contact contact, int position) {
        ((TextView)view.findViewById(R.id.name)).setText(contact.getName());
        ((TextView)view.findViewById(R.id.phone)).setText(contact.getPhone());

    }

}