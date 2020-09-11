package entity_class;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.imagesChecker;
import com.example.myapplication.videosChecker;

public class AdapterTabMedia extends FragmentPagerAdapter {

    private int numOfTabs;

    public AdapterTabMedia(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new imagesChecker();
            case 1:
                return new videosChecker();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
