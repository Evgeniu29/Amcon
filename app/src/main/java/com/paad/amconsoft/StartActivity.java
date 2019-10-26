package com.paad.amconsoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity  extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);



        Intent intent = null;
        if(preferences.contains(KEY_CREDENTIALS)){              //if user is currently logged in;
            intent = new Intent(this, MainActivity.class);
        }else {                                                 //if user is not yet logged in;
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }



}

