package com.paad.amconsoft.userlist;

import android.content.Context;

import com.paad.amconsoft.Repository;
import com.paad.amconsoft.model.User;
import com.paad.amconsoft.remote.ApiResult;

import java.util.List;

public class UserPresenter  implements UserContract.Presenter {

    private UserContract.View view;
    private Repository repository;
    private Context context;

    public UserPresenter(UserContract.View view, Context context) {
        this.view = view;
        this.context = context;
        repository = new Repository(context);
    }

    @Override
    public void onLoadUser() {


        repository.getUsers(new ApiResult<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {

                view.showUserList(result);
            }

            @Override
            public void onFail() {
                view.showErrorMessage();
            }
        });
    }

}
