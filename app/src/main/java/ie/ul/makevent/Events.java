package ie.ul.makevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Events extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Button buttonEvents = getView().findViewById(R.id.buttonEvents);
        buttonEvents.setOnClickListener(this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();
    }
}