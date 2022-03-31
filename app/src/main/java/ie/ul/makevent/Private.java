package ie.ul.makevent;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ie.ul.makevent.activities.AddEventActivity;
import ie.ul.makevent.activities.ChatActivity;
import ie.ul.makevent.adapters.HighTechEventAdapter;
import ie.ul.makevent.models.ChatMessage;
import ie.ul.makevent.models.HighTechEvent;
import ie.ul.makevent.utilities.Constants;



public class Private extends Fragment  {

    private View view;
    private FirebaseFirestore database;
    List<HighTechEvent> myEvents = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_events , container, false);
        view.findViewById(R.id.buttonEvents).setOnClickListener(v -> onClickButton());
        setListener();
        // Inflate the layout for this fragment

        //database
        database = FirebaseFirestore.getInstance();

        //List of events


        // Get list view
        ListView eventsListView = view.findViewById(R.id.events_list_view);
        eventsListView.setAdapter(new HighTechEventAdapter(getContext(),myEvents));
        listenEvent();

        return view;
    }

    private void onClickButton() {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Public.class);
        startActivity(intent);
    }

    public void setListener(){
        view.findViewById(R.id.button_addEvent).setOnClickListener(v -> startAddEvent());
    }

    public void startAddEvent() {
        Intent intent = new Intent(getContext(), AddEventActivity.class);
        startActivity(intent);
    }


    //creer document ref = database.collection(KEY_EVENT_NAME)

    private void listenEvent() {
        database.collection(Constants.KEY_COLLECTION_EVENT)
                .addSnapshotListener(eventListener);

    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {

                    HighTechEvent event = new HighTechEvent();
                    event.name_event = documentChange.getDocument().getString(Constants.KEY_EVENT_NAME);
                    event.date = documentChange.getDocument().getString(Constants.KEY_EVENT_DATE);
                    event.hour = documentChange.getDocument().getString(Constants.KEY_EVENT_HOUR);
                    event.location = documentChange.getDocument().getString(Constants.KEY_EVENT_LOCATION);
                    event.theme = documentChange.getDocument().getString(Constants.KEY_EVENT_THEME);
                    event.nb_participant = documentChange.getDocument().getString(Constants.KEY_EVENT_NB_PARTICIPANT);

                    myEvents.add(event);
                }
            }
        }
    };
}