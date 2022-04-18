package ie.ul.makevent.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ie.ul.makevent.R;
import ie.ul.makevent.databinding.ActivityDisplayProfileBinding;
import ie.ul.makevent.databinding.ActivitySignInBinding;
import ie.ul.makevent.models.User;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;

public class DisplayProfileActivity extends AppCompatActivity {

    private ActivityDisplayProfileBinding binding;
    private FirebaseFirestore database;
    private User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();

        loadUserDetails();
        setListeners();
    }

    private void loadUserDetails()
    {
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);

        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(receiverUser.id);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                      DocumentSnapshot document = task.getResult();
                      if (document.exists()) {
                          binding.email.setText(document.getString(Constants.KEY_EMAIL));
                          binding.description.setText(document.getString(Constants.KEY_DESCRIPTION));
                      }
                  }
              }
          });
        binding.textName.setText(receiverUser.name);


        byte[] bytes = Base64.decode(receiverUser.image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }

    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
}