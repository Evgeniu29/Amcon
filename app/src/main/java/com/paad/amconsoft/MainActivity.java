package com.paad.amconsoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Config;

import androidx.appcompat.app.AppCompatActivity;
import com.paad.amconsoft.userlist.UserFragment;


public class  MainActivity extends AppCompatActivity implements TransferBetweenFragments  {

    private static final String PREF_LOGIN = "LOGIN_PREF";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String fullName = sharedPreferences.getString("Full name", "");
        String email = sharedPreferences.getString("Email", "");
        String photo = sharedPreferences.getString("Photo", "");

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


}
