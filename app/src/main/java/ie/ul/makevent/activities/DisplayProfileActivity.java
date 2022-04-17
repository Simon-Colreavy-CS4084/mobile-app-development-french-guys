package ie.ul.makevent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ie.ul.makevent.R;
import ie.ul.makevent.databinding.ActivityDisplayProfileBinding;
import ie.ul.makevent.databinding.ActivitySignInBinding;
import ie.ul.makevent.models.User;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;

public class DisplayProfileActivity extends AppCompatActivity {

    private ActivityDisplayProfileBinding binding;
    private PreferenceManager preferenceManager;
    private User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
    }
}