package ie.ul.makevent.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ie.ul.makevent.R;
import ie.ul.makevent.adapters.UsersAdapter;
import ie.ul.makevent.databinding.ActivityUserBinding;
import ie.ul.makevent.listeners.UserListener;
import ie.ul.makevent.models.User;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;

public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUserBinding binding;
    private PreferenceManager preferenceManager;
    private View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityUserBinding.bind();
        binding = ActivityUserBinding.inflate(getLayoutInflater());

        view = getLayoutInflater().inflate(R.layout.activity_user , binding.getRoot(), false);
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(view);
        getUsers();
        setListener();
    }

    private void setListener()
    {
        view.findViewById(R.id.imageBack).setOnClickListener(v -> onBackPressed());

    }

    private void getUsers()
    {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null)
                    {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                        {
                            if (currentUserId.equals(queryDocumentSnapshot.getId()))
                            {
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            user.description = queryDocumentSnapshot.getString(Constants.KEY_DESCRIPTION);
                            users.add(user);
                        }
                        if (users.size() > 0)
                        {
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            RecyclerView recyclerView = view.findViewById(R.id.UsersRecyclerView);
                            recyclerView.setAdapter(usersAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            showErrorMessage();
                        }
                    }
                    else
                    {
                        showErrorMessage();
                    }
                });
    }


    private void showErrorMessage()
    {
        TextView textView = ((TextView) view.findViewById(R.id.textErrorMessage));
        textView.setText(String.format("%s", "No user available"));
        textView.setVisibility(View.VISIBLE);
    }


    private void loading(Boolean isLoading)
    {
        if (isLoading)
        {
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
        else
        {
            view.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user)
    {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}