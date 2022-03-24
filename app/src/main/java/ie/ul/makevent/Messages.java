package ie.ul.makevent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.makeramen.roundedimageview.RoundedImageView;

import ie.ul.makevent.databinding.ActivityMainBinding;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.MainActivities;
import ie.ul.makevent.utilities.PreferenceManager;

public class Messages extends MainActivities {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    public Messages() {
        super();
        //onCreate(savedInstanceState);
        //loadUserDetails();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new MessagesFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        preferenceManager = new PreferenceManager(getApplicationContext());
        if (binding == null)
        {
            Log.e("Messages", "binding null");
        }
        //setContentView(binding.getRoot());
        loadUserDetails();

        TextView t = findViewById(R.id.textName);
        t.setText("AAAA");
    }

    public ActivityMainBinding getBinding()
    {
        if (binding == null)
        {
            Log.e("Messages", "binding null user");
        }
        return binding;
    }
    public void loadUserDetails()
    {
        Log.e("messages", "loadUserDetails");
        TextView text =  findViewById(R.id.textName) ;
        text = (TextView) binding.getRoot().getViewById(R.id.textName);
        if (text == null)
        {
            Log.e("Messages", "text = null");
        }

        text.setText(preferenceManager.getString(Constants.Key_NAME));
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ((RoundedImageView) findViewById(R.id.imageProfile)).setImageBitmap(bitmap);
    }

    public void debugMes()
    {
        Log.e("Messages", "Test Debug");
    }
}
