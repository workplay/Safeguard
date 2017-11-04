package com.hkust.swangbv.hsbcsafeguard;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home Fragment");
                    fragmentTransaction.replace(R.id.fram,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("Dashboard Fragment");
                    fragmentTransaction.replace(R.id.fram,new DashboardFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    setTitle("Notification Fragment");
                    fragmentTransaction.replace(R.id.fram, new NotificationFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                return;
            }
        }, SPLASH_TIME_OUT);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
