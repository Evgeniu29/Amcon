package com.paad.amconsoft;



import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.paad.amconsoft.model.Post;
import com.paad.amconsoft.model.User;
import com.paad.amconsoft.remote.ApiClient;
import com.paad.amconsoft.remote.ApiResult;
import com.paad.amconsoft.remote.ApiService;
import com.paad.amconsoft.room.AppDatabase;
import com.paad.amconsoft.room.UserDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// repository is used for preparing data
// it will decide to prepare online or offline

public class Repository {

    private ApiService service;

    private UserDao userDao;
    private List<User> users;
    Context context;


    public Repository(Context context) {
        this.context = context;

        service = ApiClient.getRetrofitInstance().create(ApiService.class);

    }

    public void getUsers(final ApiResult<List<User>> callback) {

        Call<List<User>> call = service.getAllUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                callback.onFail();
            }
        });
    }


}
