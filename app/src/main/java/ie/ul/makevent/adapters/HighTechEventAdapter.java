package ie.ul.makevent.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ie.ul.makevent.R;
import ie.ul.makevent.models.HighTechEvent;

public class HighTechEventAdapter extends BaseAdapter {

    //fields
     private Context context;
     private List<HighTechEvent> highTechEventList;
     private LayoutInflater inflater;

     //constructor
    public HighTechEventAdapter(Context context, List<HighTechEvent> highTechEventList){
        this.context = context;
        this.highTechEventList = highTechEventList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return highTechEventList.size();
    }

    @Override
    public HighTechEvent getItem(int position) {
        return highTechEventList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.adapter_event, null);
        HighTechEvent currentEvent = getItem(i);
        String event_name = currentEvent.name_event;
        String date = currentEvent.date;
        String hour = currentEvent.hour;
        String location = currentEvent.location;
        String theme = currentEvent.theme;
        int nb_participant = currentEvent.nb_participant;

        //get event name view
        TextView eventNameView = view.findViewById(R.id.item_name);
        eventNameView.setText(event_name);

        //get event date
        TextView eventDateView = view.findViewById(R.id.item_date);
        eventDateView.setText(date);

        //get event hour
        TextView eventHourView = view.findViewById(R.id.item_hour);
        eventHourView.setText(hour);

        //get event location
        TextView eventLocationView = view.findViewById(R.id.item_location);
        eventLocationView.setText("Location : " + location);

        //get event theme
        TextView eventThemeView = view.findViewById(R.id.item_theme);
        eventThemeView.setText("Theme : " + theme);

        //get event nb participant
        TextView eventNbPView = view.findViewById(R.id.item_nb_participant);
        eventNbPView.setText("Number of participant : " + nb_participant);


        return view;
    }
}
