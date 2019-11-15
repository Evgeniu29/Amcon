package com.paad.amconsoft.userlist;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paad.amconsoft.AdapterInterface;
import com.paad.amconsoft.DetailActivity;
import com.paad.amconsoft.R;
import com.paad.amconsoft.model.User;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>  implements AdapterInterface {

    private List<User> userList;
    private OnUserClickListener listener;
    Context context;
    String name;

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

    public void setListData(List<User> userList) {
        userList = userList;
        notifyDataSetChanged();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;

        private TextView userName;

        MyViewHolder(final View itemView) {

            super(itemView);

            item = itemView.findViewById(R.id.user_item);

            userName = itemView.findViewById(R.id.user_name);
        }

        public void onBind(User user) {

            userName.setText(user.getName());

            name = user.getName();


            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    buttonPressed();

                }
            });

        }



    }

    @Override
    public void buttonPressed() {


        Intent intent = new Intent(context, DetailActivity.class);

        intent.putExtra("name", name);
        startActivity(context, intent, null);
    }



}
