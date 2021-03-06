package com.hkust.swangbv.hsbcsafeguard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneNumberActivity extends AppCompatActivity {

    TextView tv_currentSim;
    Button bt_updatephone;
    final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_phone_number);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        setTitle("Update Phone Number");


        tv_currentSim = (TextView) findViewById(R.id.tvCurrentSim);
        bt_updatephone = findViewById(R.id.button_updatePhoneNumber);
        bt_updatephone.setOnClickListener((button)->{
            Toast.makeText(getBaseContext(), "Phone Number Updated",Toast.LENGTH_SHORT).show();
        });


        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.READ_PHONE_STATE },
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE );
        }

        TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append( telemamanger.getSimSerialNumber());

        tv_currentSim.setText(sb.toString());

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("ddd","granted!");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

}
