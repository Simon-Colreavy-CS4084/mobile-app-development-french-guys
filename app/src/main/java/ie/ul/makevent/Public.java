package ie.ul.makevent;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.lights.LightState;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ie.ul.makevent.adapters.HighTechEventAdapter;
import ie.ul.makevent.databinding.ActivityPublicBinding;
import ie.ul.makevent.models.HighTechEvent;

public class Public extends AppCompatActivity {
    private ActivityPublicBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityPublicBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_public);
        //binding.imageBackEvents.setOnClickListener(v -> onBackPressed());
    }
}