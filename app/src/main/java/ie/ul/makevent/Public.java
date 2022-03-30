package ie.ul.makevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ie.ul.makevent.databinding.ActivityPublicBinding;

public class Public extends AppCompatActivity {
    private ActivityPublicBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_public);
        binding.imageBackEvents.setOnClickListener(v -> onBackPressed());
    }
}