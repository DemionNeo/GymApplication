package com.example.body2baby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkoutPageViewActivity extends AppCompatActivity {

    private TextView workout, workout1, workout2, workout3, description;
    private CircleImageView workoutImage;
    private Button homButtonn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page_view);

        toolbar = findViewById(R.id.ToolBAr);
        getSupportActionBar().setTitle("Your Workouts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        workout = findViewById(R.id.workoutt);
        workout1 = findViewById(R.id.workout1t);
        workout2 = findViewById(R.id.workout2t);
        workout3 = findViewById(R.id.workout3t);
        description = findViewById(R.id.descriptionn);
        workoutImage = findViewById(R.id.workoutImagee);
        homButtonn = findViewById(R.id.homButtonn);

        DatabaseReference refee = FirebaseDatabase.getInstance().getReference()
                .child("workouts").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        refee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    workout.setText(snapshot.child("workout").getValue().toString());
                    workout1.setText(snapshot.child("workout1").getValue().toString());
                    workout2.setText(snapshot.child("workout2").getValue().toString());
                    workout3.setText(snapshot.child("workout3").getValue().toString());
                    description.setText(snapshot.child("description").getValue().toString());
                    Glide.with(getApplicationContext()).load(snapshot.child("workoutpictureurl").getValue().toString()).into(workoutImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPageViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}