package com.paad.amconsoft.userlist;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paad.amconsoft.DetailActivity;
import com.paad.amconsoft.R;
import com.paad.amconsoft.model.User;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;
import static com.paad.amconsoft.LoginActivity.PREF_LOGIN;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;
    private OnUserClickListener listener;
    Context context;

    public UserAdapter(UserFragment userFragment, List<User> users, OnUserClickListener listener) {

    }

    public UserAdapter(Context  context,  List<User> dataList, OnUserClickListener listener) {
        this.context = context;
        this.userList = dataList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(result);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        holder.onBind(userList.get(position));

    }

    @Override
    public int getItemCount() {

        if(userList == null)
            return 0;
        return userList.size();
    }

    public void setListData(List<User> _userList) {
        userList = _userList;
        notifyDataSetChanged();

    }


    class MyViewHolder extends RecyclerView.ViewHolder implements com.paad.amconsoft.userlist.MyViewHolder {

        private LinearLayout item;

        private TextView userName;
        private TextView userEmail;

        MyViewHolder(final View itemView) {

            super(itemView);

            item = itemView.findViewById(R.id.user_item);

            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);


        }


        public void onBind(User user) {

            userName.setText(user.getName());
            userEmail.setText(user.getEmail());


            final String name = user.getName();


            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailActivity.class);

                    intent.putExtra("name", name);
                    startActivity(context, intent, null);

                }
            });

        }



    }



}
