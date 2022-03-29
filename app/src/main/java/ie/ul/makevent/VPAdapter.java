package ie.ul.makevent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    //public VPAdapter(@NonNull FragmentManager fm, int behavior) {
    //    super(fm, Lifecycle.Event);
    //}

//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return fragmentArrayList.get(position);
//    }

    //@Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    public void addFragment(Fragment fragment, String title)
    {
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }


    @Nullable
    //@Override
    public CharSequence getPageTitle(int position) {


        return fragmentTitle.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Profile();
            case 1:
                return new MessagesFragment();
            case 2:
                return new Private();
            case 3:
                return new Parametres();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
