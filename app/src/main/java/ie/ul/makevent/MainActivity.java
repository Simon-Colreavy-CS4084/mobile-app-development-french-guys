package ie.ul.makevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        //tabLayout.setupWithViewPager(viewPager);

        //VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        VPAdapter vpAdapter = new VPAdapter(this);
        vpAdapter.addFragment(new Profile(), "Profile");


        vpAdapter.addFragment(new MessagesFragment(), "Messages");
        vpAdapter.addFragment(new Private(), "Events");
        //vpAdapter.addFragment(new Parametres(), "Parametres");

        Messages mes = new Messages();
        viewPager.setAdapter(vpAdapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(vpAdapter.getPageTitle(position)))).attach();

    }

}