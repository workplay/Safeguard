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
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class PhoneNumberActivity extends AppCompatActivity {

    TextView tv_phonestate;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_phone_number);
        tv_phonestate = (TextView) findViewById(R.id.tvPhoneState);

        final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=1;

        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.READ_PHONE_STATE },
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE );
        }

        TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();

        String SimSerialNumber = telemamanger.getSimSerialNumber();

        sb.append("Sim Serial Number  " + telemamanger.getSimSerialNumber());

        tv_phonestate.setText(sb.toString());



    }

}
