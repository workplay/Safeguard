package com.hkust.swangbv.hsbcsafeguard;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    ImageView ivcontact,ivlocation;


    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //Update Contact Information
        ivcontact = myView.findViewById(R.id.imageviewContact);
        ivcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Update Phone Number",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PhoneNumberActivity.class);
                startActivity(intent);
            }
        });

        //Get Location Information.
        ivlocation = myView.findViewById(R.id.imageviewLocation);
        ivlocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(),"Collect Locatin Information", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

}
