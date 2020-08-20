package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        // Example of a call to a native method
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        /*Fragment frg = null;
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (MyApp.stateFragment){
            case 1:
                frg = getSupportFragmentManager().findFragmentById(R.id.basicfeature);
                ft.commit();
            case 2:
                frg = getSupportFragmentManager().findFragmentById(R.id.basicfeature);

                ft.commit();
            case 3:

            case 4:

            default:

        }*/

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

    /*@Override
    public void onResume() {
        super.onResume();
        Fragment frg = null;
        frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.checkin);
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }*/