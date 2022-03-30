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
import android.widget.Toast;

import ie.ul.makevent.activities.ChatActivity;
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
        return view;
    }

    private void onClickButton() {
        Toast.makeText(getContext(),"Ceci",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), Public.class);
        startActivity(intent);
    }

}