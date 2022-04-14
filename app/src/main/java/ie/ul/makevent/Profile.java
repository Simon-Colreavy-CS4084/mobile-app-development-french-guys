package ie.ul.makevent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import ie.ul.makevent.activities.SignInActivity;
import ie.ul.makevent.databinding.ActivityMainBinding;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;


public class Profile extends Fragment
{
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager ;
    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile , container, false);
        preferenceManager = new PreferenceManager(getContext()) ;
        setListeners();
        return view;
    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//    }

    private  void setListeners()
    {
        view.findViewById(R.id.logoutbutton).setOnClickListener(v->signOut());
    }
private void showToast(String message)
{
    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
}

    private void signOut()
{
    Log.e("Messages Fragment", "deconnection");
    showToast("Signig out...");
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference documentReference =
            database.collection(Constants.KEY_COLLECTION_USERS).document(
                    preferenceManager.getString(Constants.KEY_USER_ID)
            );
    HashMap<String, Object> updates = new HashMap<>();
    updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
    documentReference.update(updates)
            .addOnSuccessListener(unused -> {
                preferenceManager.clear();
                startActivity(new Intent(getContext(), SignInActivity.class));
                getActivity().finish();
            })
            .addOnFailureListener(e -> showToast("Unable to sign out"));
}
}