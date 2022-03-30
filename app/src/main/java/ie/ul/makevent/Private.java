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

import java.util.ArrayList;
import java.util.List;

import ie.ul.makevent.activities.ChatActivity;
import ie.ul.makevent.adapters.HighTechEventAdapter;
import ie.ul.makevent.models.HighTechEvent;
import ie.ul.makevent.utilities.Constants;



public class Private extends Fragment  {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_events , container, false);
        view.findViewById(R.id.buttonEvents).setOnClickListener(v -> onClickButton());
        // Inflate the layout for this fragment

        //List of events
        List<HighTechEvent> highTechEventList = new ArrayList<>();
        highTechEventList.add(new HighTechEvent("Clement Project X","24/04/22","22:00 to 06:00","60 Plassey Village Castleroy","Obligatory Unicorn",540));
        highTechEventList.add(new HighTechEvent("Alexandre Sleepover Party","10/05/22","18:00 to 06:00","61 Plassey Village Castleroy","Obligatory Pyjamas",5));
        highTechEventList.add(new HighTechEvent("Alexander Delegate Party","15/04/22","22:00 to 00:00","Student Office at EPITA","Obligatory PC",15));
        highTechEventList.add(new HighTechEvent("Birthday Antony","01/04/22","22:00 to 06:00","Angel Lane","Obligatory be drunk",50));

        // Get list view
        ListView eventsListView = view.findViewById(R.id.events_list_view);
        eventsListView.setAdapter(new HighTechEventAdapter(getContext(),highTechEventList));

        return view;
    }

    private void onClickButton() {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Public.class);
        startActivity(intent);
    }

}