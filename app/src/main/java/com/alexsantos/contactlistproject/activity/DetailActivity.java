package com.alexsantos.contactlistproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.alexsantos.contactlistproject.R;
import com.alexsantos.contactlistproject.model.Contact;
import com.alexsantos.contactlistproject.utils.Constant;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.authentication.Constants;

public class DetailActivity extends AppCompatActivity {

    private Contact c;
    String FirebaseID;
    Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase(Constant.FIREBASE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("FirebaseID")) {

            FirebaseID = getIntent().getStringExtra("FirebaseID");
            Firebase refContact = myFirebaseRef.child(FirebaseID);


            ValueEventListener refContatoListener = refContact.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    c = snapshot.getValue(Contact.class);

                    if (c != null) {
                        EditText nameText = (EditText) findViewById(R.id.editText1);
                        nameText.setText(c.getName());


                        EditText foneText = (EditText) findViewById(R.id.editText2);
                        foneText.setText(c.getPhone());


                        EditText emailText = (EditText) findViewById(R.id.editText3);
                        emailText.setText(c.getEmail());

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e("LOG", firebaseError.getMessage());
                }

            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        if (FirebaseID == null) {
            MenuItem item = menu.findItem(R.id.delContact);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveContact:
                save();
                return true;
            case R.id.delContact:
                myFirebaseRef.child(FirebaseID).removeValue();
                Toast.makeText(getApplicationContext(), "Contact successfully deleted", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();

        if (c == null) {
            c = new Contact();
            c.setName(name);
            c.setPhone(fone);
            c.setEmail(email);

            myFirebaseRef.push().setValue(c);
            Toast.makeText(this, "Contact successfully Added!!!", Toast.LENGTH_SHORT).show();
        } else {

            c.setName(name);
            c.setPhone(fone);
            c.setEmail(email);

            myFirebaseRef.child(FirebaseID).setValue(c);

            Toast.makeText(this, "Contact successfully Edited!!!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}
