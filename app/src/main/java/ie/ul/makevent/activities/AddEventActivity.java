package ie.ul.makevent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.HashMap;

import ie.ul.makevent.R;
import ie.ul.makevent.databinding.ActivityAddEventBinding;
import ie.ul.makevent.models.ChatMessage;
import ie.ul.makevent.models.HighTechEvent;
import ie.ul.makevent.utilities.Constants;

public class AddEventActivity extends AppCompatActivity {

    private ActivityAddEventBinding binding;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        //setListener();
    }

    public void addEvent(View v) {
        String edt_name = ((EditText) findViewById(R.id.input_event_name)).getText().toString();
        String edt_date = ((EditText) findViewById(R.id.input_event_date)).getText().toString();
        String edt_hour = ((EditText) findViewById(R.id.input_event_hour)).getText().toString();
        String edt_location = ((EditText) findViewById(R.id.input_event_location)).getText().toString();
        String edt_theme = ((EditText) findViewById(R.id.input_event_theme)).getText().toString();
        String edt_nb_participant = ((EditText) findViewById(R.id.input_event_nbParticipant)).getText().toString();

        HashMap<String, Object> event = new HashMap<>();
        event.put(Constants.KEY_EVENT_NAME, edt_name);
        event.put(Constants.KEY_EVENT_DATE, edt_date);
        event.put(Constants.KEY_EVENT_HOUR, edt_hour);
        event.put(Constants.KEY_EVENT_LOCATION, edt_location);
        event.put(Constants.KEY_EVENT_THEME, edt_theme);
        event.put(Constants.KEY_EVENT_NB_PARTICIPANT, edt_nb_participant);
        // rajouter son id dans la liste des partcipants


        binding.getRoot();
        database.collection(Constants.KEY_COLLECTION_EVENT)
                .add(event)
                .addOnSuccessListener(documentReference -> {
                    showToast("creation de l'event");

                })
                .addOnFailureListener(exception -> {
                    showToast(exception.getMessage());
                });

        ((EditText) findViewById(R.id.input_event_name)).setText("");
        ((EditText) findViewById(R.id.input_event_date)).setText("");
        ((EditText) findViewById(R.id.input_event_hour)).setText("");
        ((EditText) findViewById(R.id.input_event_location)).setText("");
        ((EditText) findViewById(R.id.input_event_theme)).setText("");
        ((EditText) findViewById(R.id.input_event_nbParticipant)).setText("");

        onBackPressed();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //public void setListener(){
    //  binding.buttonValidate.setOnClickListener(v -> addEvent());
    //}
}