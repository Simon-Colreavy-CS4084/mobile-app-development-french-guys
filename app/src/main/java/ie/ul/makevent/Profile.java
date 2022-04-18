package ie.ul.makevent;
//import android.content;
import android.content.ContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
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
import com.makeramen.roundedimageview.RoundedImageView;

//import com.google.firebase.firestore.DatabaseReference ;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import ie.ul.makevent.activities.SignInActivity;
import ie.ul.makevent.activities.SignUpActivity;
import ie.ul.makevent.databinding.ActivityMainBinding;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;



public class Profile extends Fragment
{
    public static final int RESULT_OK = -1;
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager ;
    private String encodedImage;

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

        //byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //((RoundedImageView) view.findViewById(R.id.imageProfile)).setImageBitmap(bitmap);

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
        view.findViewById(R.id.imageProfile).setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
            pickImage.launch(intent);
        });

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
        //editProfileeee();
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


    private void editProfileeee()
    {
        Log.e("profile" ," bjr" ) ;
        showToast("bloc");
        Log.e("profile", "apres");
        //String modifname = ((TextInputEditText)view.findViewById(R.id.nameinput)).getText().toString() ;
        //String modifdescription = ((TextInputEditText)view.findViewById(R.id.descriptioninput)).getText().toString() ;
        //String modifemail = ((TextInputEditText)view.findViewById(R.id.emailinput)).getText().toString() ;
       // String modifimage = ((RoundedImageView) view.findViewById(R.id.imageProfile)).

        HashMap<String, Object> updates = new HashMap<>();
        preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);


        //updates.put(Constants.KEY_NAME, modifname);
        //updates.put(Constants.KEY_DESCRIPTION , modifdescription) ;
        //updates.put(Constants.KEY_EMAIL , modifemail) ;
        updates.put(Constants.KEY_IMAGE , encodedImage) ;

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



    private String encodeImage(Bitmap bitmap)
    {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50 ,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes , Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK)
                {
                    if (result.getData() != null)
                    {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                            ((RoundedImageView) view.findViewById(R.id.imageProfile)).setImageBitmap(bitmap);
                            ((TextView) view.findViewById(R.id.textAddImage)).setVisibility(View.GONE);

                            encodedImage = encodeImage(bitmap);
                            editProfileeee();
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );


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