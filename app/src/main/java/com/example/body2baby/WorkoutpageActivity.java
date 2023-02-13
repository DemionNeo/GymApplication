package com.example.body2baby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.body2baby.Adapter.UserAdapter;
import com.example.body2baby.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkoutpageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView workoutImage;
    private Button homButton;
     private List<User> userList;
     private UserAdapter userAdapter;
    private Button emailNow;
    private RecyclerView recyclerView;
    private TextView workout, workout1, workout2, workout3, description;
    List<String> idList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutpage);

        toolbar = findViewById(R.id.ToolBAr);
        getSupportActionBar().setTitle("Workouts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        emailNow = findViewById(R.id.emailNow);
        workout = findViewById(R.id.workout);
        workout1 = findViewById(R.id.workout1);
        workout2 = findViewById(R.id.workout2);
        workout3 = findViewById(R.id.workout3);
        description = findViewById(R.id.description);
        workoutImage = findViewById(R.id.workoutImage);
        homButton = findViewById(R.id.homButton);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = snapshot.child("type").getValue().toString();
                if(type.equals("trainee")){
                    DatabaseReference refe = FirebaseDatabase.getInstance().getReference()
                    .child("workouts");
                    refe.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                workout.setText(snapshot.child("workout").getValue().toString());
                                workout1.setText(snapshot.child("workout1").getValue().toString());
                                workout2.setText(snapshot.child("workout2").getValue().toString());
                                workout3.setText(snapshot.child("workout3").getValue().toString());
                                description.setText(snapshot.child("description").getValue().toString());

                                Glide.with(getApplicationContext()).load(snapshot.child("workoutpictureurl").getValue().toString()).into(workoutImage);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {
                    Toast.makeText(WorkoutpageActivity.this, "Workouts not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        homButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutpageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}