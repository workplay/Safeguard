package com.hkust.swangbv.hsbcsafeguard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    TextView tvlocation;
    String TAG="LocatinActivity";
    final int MY_PERMISSIONS_REQUEST_FINE_LOCATION=1;
    Location locationGPS, locationNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvlocation = findViewById(R.id.locationTextView);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION );

            Toast.makeText(getBaseContext(),"No location permission", Toast.LENGTH_LONG).show();
            return;
        }

        locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        new MyTask().execute("haha ddd");

    }



    private class MyTask extends AsyncTask<String, Integer, String> {
        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground(Params... params) called");

            StringBuilder sb = new StringBuilder();
            sb.append("GPS Location" + locationGPS.toString() +"\n");
            sb.append("Network Location" + locationNet.toString());



            Geocoder geoCoder = new Geocoder(LocationActivity.this , Locale.getDefault()); //it is Geocoder
            Log.e("ddd",String.valueOf(Geocoder.isPresent()));
            try {
                List<Address> address = geoCoder.getFromLocation(40,116.3, 1);

                Log.e("ddd",address.size() + address.toString());
                while (address.isEmpty()){

                    address = geoCoder.getFromLocation(40,116.3, 1);
                }
                int maxLines = address.get(0).getMaxAddressLineIndex();
                for (int i = 0; i < maxLines; i++) {
                    String addressStr = address.get(0).getAddressLine(i);
                    sb.append(addressStr);
                    sb.append("\n");
                }
                Log.e("ddd",sb.toString());
                return sb.toString()+"1234";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "error location";
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
            tvlocation.setText(result);;
        }


    }

}
