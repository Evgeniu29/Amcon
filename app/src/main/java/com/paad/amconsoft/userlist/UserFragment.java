package com.paad.amconsoft.userlist;


import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.paad.amconsoft.R;
import com.paad.amconsoft.TransferBetweenFragments;
import com.paad.amconsoft.model.User;
import com.paad.amconsoft.room.AppDatabase;
import com.paad.amconsoft.room.UserDao;


import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment implements UserContract.View {

    private UserContract.Presenter presenter;
    TransferBetweenFragments transferBetweenFragments;
    private RecyclerView recyclerView;
    private ProgressBar spinner;
    private Context mContext;

    private List<User> userList;
    public UserAdapter userAdapter;



    public void setListData(List<User> userList) {
        //if data changed, set new list to adapter of recyclerview

        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.clear();
        userList.addAll(userList);

        if (userAdapter != null) {
            userAdapter.setListData(userList);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public static UserFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        presenter = new UserPresenter(this, mContext);

        recyclerView = view.findViewById(R.id.user_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        presenter.onLoadUser();



    }


    @Override
    public void showUserList(List<User> users) {

        if(mContext!= null) {

            AppDatabase database = Room.databaseBuilder(mContext, AppDatabase.class, "user")
                    .allowMainThreadQueries()   //Allows room to do operation on main thread
                    .build();


            UserDao userDao = database.getUserDAO();

            userDao.deleteAll();


            userDao.insertAllUsers(users);



            UserAdapter adapter = new UserAdapter(mContext, userDao.getAllData(), new OnUserClickListener() {

                @Override
                public void onUserClick(int userID) {

                    Log.v("user id", "" + userID);
                    Log.v("tbf instance", "" + transferBetweenFragments); // this is null
                    transferBetweenFragments.goFromUserToPost(userID);
                }
            });


            recyclerView.setAdapter(adapter);

            database.close();




        }
    }

    @Override
    public void showErrorMessage() {

        Toast.makeText(this.getContext(),"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

    }
}
