package com.paad.amconsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Config;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.paad.amconsoft.userlist.UserFragment;

import static com.paad.amconsoft.LoginActivity.KEY_CREDENTIALS;


public class  MainActivity extends AppCompatActivity implements TransferBetweenFragments, NavigationView.OnNavigationItemSelectedListener  {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    NavigationView mNavigationView;
    DrawerLayout drawer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       preferences  =  this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String fullName =  preferences.getString("Full name", "");
        String email =  preferences.getString("Email", "");
        String photo =  preferences.getString("Photo", "");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.bringToFront();

        mNavigationView.setNavigationItemSelectedListener(this);

        View headerLayout = mNavigationView.inflateHeaderView(R.layout.header_layout);

        TextView nameView = headerLayout.findViewById(R.id.fullName);

        nameView.setText(fullName);

        TextView emailView = headerLayout.findViewById(R.id.email);


        emailView.setText(email);

        ImageView headerImageView = headerLayout.findViewById(R.id.imageView);
        Glide.with(MainActivity.this).load(photo).into(headerImageView);




        final UserFragment userFragment = UserFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, userFragment).commit();

        }



    @Override
    public void goFromUserToPost(int userID) {

        UserFragment userFragment = UserFragment.newInstance();
        getSupportFragmentManager().beginTransaction().addToBackStack("user fragment")
                .replace(R.id.main_container, userFragment).commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.logout) {

            item.setChecked(true);

            editor = this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();

            editor.clear();

            editor.commit();


                finish();


            }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
