package com.paad.amconsoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity  extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    Intent intent, intent2 = null;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if( getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).contains(KEY_CREDENTIALS)) {
            //if user is currently logged in;


            intent = new Intent(this, MainActivity.class);

            startActivity(intent);

        }

        else {

            intent = new Intent(this, LoginActivity.class);

            startActivity(intent);

        }



    }



}

