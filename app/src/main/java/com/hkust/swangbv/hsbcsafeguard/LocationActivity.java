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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    TextView tvlocation;
    String TAG="LocationActivity";
    final int MY_PERMISSIONS_REQUEST_FINE_LOCATION=1;
    Location locationGPS, locationNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Location");
        setTitle("Location");

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

            return locationToAddress("GPS",locationGPS) + "\n" + locationToAddress("Net",locationNet);
        }

        private String locationToAddress(String type, Location location) {
            StringBuilder sb = new StringBuilder();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date(location.getTime()));
            sb.append(type +" location     "+dateTime + "\n");
            Geocoder geoCoder = new Geocoder(LocationActivity.this , Locale.getDefault()); //it is Geocoder
            try {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                List<Address> address = geoCoder.getFromLocation(latitude ,longitude, 1);
;
                Address addr = address.get(0);
                String countryName = addr.getCountryName();
                String admin = addr.getAdminArea();
                String subadmin = addr.getSubAdminArea();
                String locality = addr.getLocality();
                String sublocality = addr.getSubLocality();
                String thoroughfare = addr.getThoroughfare();

                sb.append("latitude, longitude: "+latitude+","+longitude + "\n");
                sb.append("country Name: "+countryName + "\n");
                sb.append("admin: " + admin + ","+ subadmin + "\n");
                sb.append("locality: "+locality+","+sublocality + "\n");
                sb.append("thoroughfare: "+ thoroughfare + "\n");
                sb.append("Address: "+addr.getAddressLine(0) + "\n");

            } catch (IOException e) {
                e.printStackTrace();
                return "Error get location from " + type +" \n";
            }
            return sb.toString();
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
            tvlocation.setText(result);;
        }
    }

}
