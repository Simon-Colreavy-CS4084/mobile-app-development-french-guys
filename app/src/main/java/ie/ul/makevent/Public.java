package ie.ul.makevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ie.ul.makevent.activities.AddEventActivity;
import ie.ul.makevent.activities.BaseActivity;
import ie.ul.makevent.adapters.HighTechEventAdapter;
import ie.ul.makevent.databinding.ActivityPublicBinding;
import ie.ul.makevent.models.HighTechEvent;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;

public class Public extends AppCompatActivity {

    private ActivityPublicBinding binding;

    private FirebaseFirestore database;
    List<HighTechEvent> OtherEvents = new ArrayList<>();
    HighTechEventAdapter adapter;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

        database = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        listenEvent();

        ListView ListView = binding.eventsListView.findViewById(R.id.events_list_view);
        adapter = new HighTechEventAdapter(getApplicationContext(),OtherEvents);
        ListView.setAdapter(adapter);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                HashMap<String, Object> updates = new HashMap<>();
                updates.put(Constants.KEY_EVENT_PARTICIPANT,OtherEvents.get(0).participants);
                DocumentReference reference = database.collection(Constants.KEY_COLLECTION_EVENT).document();

                reference
                        .update(updates)
                        .addOnSuccessListener(documentReference -> {
                            //showToast("modif success");

                        } )
                        .addOnFailureListener(documentReference -> {
                            //showToast("modif fail");

                        });
                Toast.makeText(getApplicationContext(),String.valueOf(OtherEvents.get(0).participants.size()),Toast.LENGTH_SHORT).show();
                OtherEvents.get(0).participants.add(preferenceManager.getString(Constants.KEY_USER_ID));
                Toast.makeText(getApplicationContext(),String.valueOf(OtherEvents.get(0).participants.size()),Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();

                //listenEvent();
                //Toast.makeText(getApplicationContext(),String.valueOf(OtherEvents.size()),Toast.LENGTH_SHORT).show();


                //String code = ((TextView) view.findViewById(R.id.idEventText)).getText().toString();
                //if (code != "")
                //{
                    /*ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("event code", code);
                    clipboard.setPrimaryClip(clip);
                }*/
            }
        });

    }
    private void setListeners()
    {
        binding.imageBackEvents.setOnClickListener(v -> onBackPressed());
    }

    private void listenEvent() {
        database.collection(Constants.KEY_COLLECTION_EVENT)
                .addSnapshotListener(eventListener);

    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            //int count = OtherEvents.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {

                    HighTechEvent event = new HighTechEvent();

                    event.participants = (ArrayList<String>) documentChange.getDocument().get(Constants.KEY_EVENT_PARTICIPANT);

                    if (event.participants == null)
                    {
                        continue;
                    }
                    if (!event.participants.contains(preferenceManager.getString(Constants.KEY_USER_ID)))
                    {
                        event.name_event = documentChange.getDocument().getString(Constants.KEY_EVENT_NAME);
                        event.date = documentChange.getDocument().getString(Constants.KEY_EVENT_DATE);
                        event.hour = documentChange.getDocument().getString(Constants.KEY_EVENT_HOUR);
                        event.location = documentChange.getDocument().getString(Constants.KEY_EVENT_LOCATION);
                        event.theme = documentChange.getDocument().getString(Constants.KEY_EVENT_THEME);
                        event.nb_participant = documentChange.getDocument().getString(Constants.KEY_EVENT_NB_PARTICIPANT);
                        OtherEvents.add(event);
                        //count++;
                    }
                }
            }
            //if (count == 0)
            //{
            adapter.notifyDataSetChanged();
            //Toast.makeText(getApplicationContext(),String.valueOf(count),Toast.LENGTH_SHORT).show();
            //}
           // else
            //{
                //adapter.notifyDataSetChanged();

                //adapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                //binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            //}
        }
    };

}