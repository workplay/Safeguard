package com.hkust.swangbv.hsbcsafeguard;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hkust.swangbv.hsbcsafeguard.HttpUtility.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.content.Intent.*;

public class UpdateAccountActivity extends AppCompatActivity {


    EditText etid, etname, etphone, etemail, etaddress, etpost, etjob;
    Button btupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Account Information");

        etid = findViewById(R.id.etid);
        etid = findViewById(R.id.etid);
        etname = findViewById(R.id.etname);
        etphone = findViewById(R.id.etphone);
        etemail = findViewById(R.id.etemail);
        etaddress = findViewById(R.id.etaddress);
        etpost =  findViewById(R.id.etpost);
        etjob =  findViewById(R.id.etjob);
        btupdate = findViewById(R.id.buttonUpdate);

        new LoadTask().execute();

        btupdate.setOnClickListener((view)->{
            new UpdateTask().execute();
        });

    }



    private class UpdateTask extends AsyncTask<String, Integer, String> {
        static final String TAG = "UpdateAccount";

        @Override
        protected String doInBackground(String... params) {
            try {
                return HttpHelper.updateAccount(etid.getText().toString(),etname.getText().toString(),
                        etphone.getText().toString(),etemail.getText().toString(),etpost.getText().toString(),etaddress.getText().toString(),etjob.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT);
            Intent intent = new Intent(UpdateAccountActivity.this ,MainActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        }
    }




    private class LoadTask extends AsyncTask<String, Integer, String> {
        static final String TAG = "UpdateAccount";

        @Override
        protected String doInBackground(String... params) {
            try {
                return HttpHelper.getAccount("swangbv");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);
                StringBuilder sb = new StringBuilder();
                etid.setText(obj.getString("id"));
                etid.setEnabled(false);
                etname.setText(obj.getString("name"));
                etphone.setText(obj.getString("phoneNumber"));
                etemail.setText(obj.getString("email"));
                etpost.setText(obj.getString("correspondenceAddress"));
                etaddress.setText(obj.getString("residentialAddress"));
                etjob.setText(obj.getString("job"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
