package com.example.body2baby.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.body2baby.MainActivity;
import com.example.body2baby.Model.User;
import com.example.body2baby.R;
import com.example.body2baby.WorkoutpageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.user_displayed_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = userList.get(position);

        holder.userType.setText(user.getType());
// if the user logged in is a trainee then the "See User" button will not be visible by the
        //trainers
        if (user.getType().equals("trainer")){
            holder.emailNow.setVisibility(View.VISIBLE);
        }

        holder.userEmail.setText(user.getEmail());
        holder.phoneNumber.setText(user.getPhonenumber());
        holder.userName.setText(user.getName());
        holder.userTrimester.setText(user.getTrimester());

        Glide.with(context).load(user.getProfilepictureurl()).into(holder.userProfileImage);

        holder.emailNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, WorkoutpageActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    public CircleImageView userProfileImage;
    public TextView userType, userName, userEmail, phoneNumber, userTrimester;
    public Button emailNow;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        userProfileImage = itemView.findViewById(R.id.userProfileImage);
        userType = itemView.findViewById(R.id.userType);
        userName = itemView.findViewById(R.id.userName);
        userEmail = itemView.findViewById(R.id.userEmail);
        phoneNumber = itemView.findViewById(R.id.phoneNumber);
        userTrimester = itemView.findViewById(R.id.userTrimester);
        emailNow = itemView.findViewById(R.id.emailNow);
    }
}
}
