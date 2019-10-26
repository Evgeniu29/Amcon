package com.paad.amconsoft;


import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paad.amconsoft.model.User;
import com.paad.amconsoft.room.AppDatabase;


public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {



    TextView  address;

    TextView phone;

    TextView website;

    TextView company;

    String name;

    LatLng newlatlng;


    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail);


        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "user")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        address  = (TextView) findViewById(R.id._address);
        phone = (TextView) findViewById(R.id._phone);
        website = (TextView) findViewById(R.id._website);
        company = (TextView) findViewById(R.id._company);

        Intent intent = getIntent();

        String test = "test";

        name = intent.getStringExtra("name");

        user = database.getUserDAO().getUser(name);

        address.setText(user.getAddress().getFullAddress());

        phone.setText(user.getPhone());

        website.setText(user.getWebsite());

        company.setText(user.getCompany().getCompanyDescription());

        newlatlng = new LatLng(Double.parseDouble(user.getAddress().getGeo().getLat()), Double.parseDouble(user.getAddress().getGeo().getLng()));

        database.close();

    }



    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        googleMap.addMarker(new MarkerOptions().position(newlatlng)
                .title(user.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newlatlng));
    }
}
