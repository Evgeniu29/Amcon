package com.paad.amconsoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;

    private CallbackManager callbackManager;
    SharedPreferences sharedpreferences;
    Intent intent = null;


    String MyPREFERENCES;

    SharedPreferences.Editor editor;

    String email, first_name, last_name, image_url, credentials;

    public static final String PREF_LOGIN = "LOGIN_PREF";
    public static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";


    String defaultIImage = "https://scontent.fiev12-1.fna.fbcdn.net/v/t1.0-0/p370x247/15665557_534165793457516_1104948354887880816_n.png?_nc_cat=100&_nc_oc=AQlH-XV8BJMBiwBG_MCkURwYM-VH0QePycz7QUlIn7sWdpFjdTTmA4XkEtTZ3_aVh6Y&_nc_ht=scontent.fiev12-1.fna&oh=4531f5def1bf514f228f0a7f68a2c6ac&oe=5E272A54";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        loginButton = findViewById(R.id.login_button);
        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);

        setDefault();


        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException error) {


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                setDefault();
            } else
                loadUserProfile(currentAccessToken);


        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    email = object.getString("email");
                    String id = object.getString("id");
                    image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    txtEmail.setText(email);
                    txtName.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(LoginActivity.this).load(image_url).into(circleImageView);
                    editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();

                    String fullName = first_name + " " + last_name;


                    editor.putString("Photo", image_url);
                    editor.putString("Full name", fullName);
                    editor.putString("Email", email);
                    editor.putString("Credentials", KEY_CREDENTIALS);

                    editor.commit();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");

        request.setParameters(parameters);
        request.executeAsync();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        thread.start();



    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());

        }


    }

    void setDefault() {
        txtName.setText(getResources().getString(R.string.login));
        txtEmail.setText(getResources().getString(R.string.password));
        Glide.with(LoginActivity.this).load(defaultIImage).into(circleImageView);

    }



}
