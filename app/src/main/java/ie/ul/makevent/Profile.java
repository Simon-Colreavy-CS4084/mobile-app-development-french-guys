package ie.ul.makevent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.EventListener ;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//import com.google.firebase.firestore.DatabaseReference ;

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
    private FirebaseFirestore database ;
    private FirebaseFirestore databaseReference ;

    private TextView retrieveTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //DatabaseReference databaseReference;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile , container, false);
        preferenceManager = new PreferenceManager(getContext()) ;

        ((TextInputEditText) view.findViewById(R.id.descriptioninput)).setText(preferenceManager.getString(Constants.KEY_DESCRIPTION));
        ((TextInputEditText) view.findViewById(R.id.nameinput)).setText(preferenceManager.getString(Constants.KEY_NAME));
        ((TextInputEditText) view.findViewById(R.id.emailinput)).setText(preferenceManager.getString(Constants.KEY_EMAIL));

        setListeners();
        database = FirebaseFirestore.getInstance() ;
       // databaseReference  = database.getReference("Data");


        view.findViewById(R.id.Editbutton).setOnClickListener(v->editProfile());
        return view;
    }
//    protected void onCreate(Bundle savedInstanceState)
//    {
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


    private void editProfile()
    {
        Log.e("profile" ," bjr" ) ;
        showToast("bloc");
        Log.e("profile", "apres");
        String modifname = ((TextInputEditText)view.findViewById(R.id.nameinput)).getText().toString() ;
        //String modifdescription = ((TextInputEditText)view.findViewById(R.id.descriptioninput)).getText().toString() ;
        //String modifemail = ((TextInputEditText)view.findViewById(R.id.emailinput)).getText().toString() ;
        HashMap<String, Object> updates = new HashMap<>();
        preferenceManager.putString(Constants.KEY_NAME, modifname);
        //preferenceManager.putString(Constants.KEY_DESCRIPTION, modifdescription);
        //preferenceManager.putString(Constants.KEY_EMAIL, modifemail);

        updates.put(Constants.KEY_NAME, modifname);
        //updates.put(Constants.KEY_DESCRIPTION , modifdescription) ;
        //updates.put(Constants.KEY_EMAIL , modifemail) ;

        DocumentReference reference = database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));

        reference
                .update(updates)
                .addOnSuccessListener(documentReference -> {
                    showToast("modif success");

                } )
        .addOnFailureListener(documentReference -> {
            showToast("modif fail");

        });
        editProfilee();
        editProfileee();
    }

    private void editProfilee()
    {
        Log.e("profile" ," bjr" ) ;
        showToast("bloc");
        Log.e("profile", "apres");
        // String modifname = ((TextInputEditText)view.findViewById(R.id.nameinput)).getText().toString() ;
        String modifdescription = ((TextInputEditText)view.findViewById(R.id.descriptioninput)).getText().toString() ;
        //String modifemail = ((TextInputEditText)view.findViewById(R.id.emailinput)).getText().toString() ;
        HashMap<String, Object> updates = new HashMap<>();
        preferenceManager.putString(Constants.KEY_NAME, modifdescription);

         //updates.put(Constants.KEY_NAME, modifname);
        updates.put(Constants.KEY_DESCRIPTION , modifdescription) ;
        //updates.put(Constants.KEY_EMAIL , modifemail) ;

        DocumentReference reference = database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));

        reference
                .update(updates)
                .addOnSuccessListener(documentReference -> {
                    showToast("modif success");

                } )
                .addOnFailureListener(documentReference -> {
                    showToast("modif fail");

                });
    }

    private void editProfileee()
    {
        Log.e("profile" ," bjr" ) ;
        showToast("bloc");
        Log.e("profile", "apres");
         //String modifname = ((TextInputEditText)view.findViewById(R.id.nameinput)).getText().toString() ;
        //String modifdescription = ((TextInputEditText)view.findViewById(R.id.descriptioninput)).getText().toString() ;
        String modifemail = ((TextInputEditText)view.findViewById(R.id.emailinput)).getText().toString() ;
        HashMap<String, Object> updates = new HashMap<>();
        preferenceManager.putString(Constants.KEY_EMAIL, modifemail);

        //updates.put(Constants.KEY_NAME, modifname);
        //updates.put(Constants.KEY_DESCRIPTION , modifdescription) ;
        updates.put(Constants.KEY_EMAIL , modifemail) ;

        DocumentReference reference = database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));

        reference
                .update(updates)
                .addOnSuccessListener(documentReference -> {
                    showToast("modif success");

                } )
                .addOnFailureListener(documentReference -> {
                    showToast("modif fail");

                });
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
            .addOnSuccessListener(unused ->
            {
                preferenceManager.clear();
                startActivity(new Intent(getContext(), SignInActivity.class));
                getActivity().finish();
            })
            .addOnFailureListener(e -> showToast("Unable to sign out"));
}
}