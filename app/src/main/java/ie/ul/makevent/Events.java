package ie.ul.makevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Events extends Fragment  {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_events , container, false);

        Button buttonEvents = view.findViewById(R.id.buttonEvents);
        buttonEvents.setOnClickListener(v -> onClickButton());
        // Inflate the layout for this fragment
        return view;
    }

    private void onClickButton() {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();

    }

}