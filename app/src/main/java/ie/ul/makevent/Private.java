package ie.ul.makevent;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ie.ul.makevent.activities.AddEventActivity;
import ie.ul.makevent.adapters.HighTechEventAdapter;
import ie.ul.makevent.models.HighTechEvent;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;


public class Private extends Fragment  {

    private View view;
    private FirebaseFirestore database;
    List<HighTechEvent> myEvents = new ArrayList<>();
    HighTechEventAdapter adapter;
    private PreferenceManager preferenceManager;

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
        preferenceManager = new PreferenceManager(getContext());

        //List of events


        // Get list view
        listenEvent();
        ListView eventsListView = view.findViewById(R.id.events_list_view);
        adapter = new HighTechEventAdapter(getContext(),myEvents);
        eventsListView.setAdapter(adapter);


        return view;
    }

    private void onClickButton() {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Public.class);
        startActivity(intent);
    }

    public void setListener()
    {
        view.findViewById(R.id.button_addEvent).setOnClickListener(v -> startAddEvent());

        ListView listView = view.findViewById(R.id.events_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView idText = view.findViewById(R.id.idEventText);
                String code = idText.getText().toString();
                String subCode = code.substring(7);

                if (idText.getVisibility() == View.VISIBLE)
                {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("event code", subCode);
                    clipboard.setPrimaryClip(clip);
                }
            }
        });
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
            int count = myEvents.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {

                    HighTechEvent event = new HighTechEvent();

                    event.participants = (ArrayList<String>) documentChange.getDocument().get(Constants.KEY_EVENT_PARTICIPANT);


                    if (event.participants == null)
                    {
                        continue;
                    }
                    if (event.participants.contains(preferenceManager.getString(Constants.KEY_USER_ID)))
                    {
                        event.idEvent = documentChange.getDocument().getId();

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
            if (count == 0)
            {
                adapter.notifyDataSetChanged();
            }
            else
            {
                adapter.notifyDataSetChanged();

                //adapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                //binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
        }
    };


}