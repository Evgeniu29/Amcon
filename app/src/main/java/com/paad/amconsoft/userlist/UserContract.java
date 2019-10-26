package com.paad.amconsoft.userlist;


import com.paad.amconsoft.model.User;

import java.util.List;

public interface UserContract {

    interface View {


        void showUserList(List<User> users);
        void showErrorMessage();
    }

    interface Presenter {

        void onLoadUser();
    }
}
