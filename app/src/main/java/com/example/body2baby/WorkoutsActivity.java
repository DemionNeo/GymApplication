package com.example.body2baby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkoutsActivity extends AppCompatActivity {

    private TextView homeButton;

    private CircleImageView workout_image;

    private TextInputEditText registerDescription;

    private Spinner workoutSpinner, workoutSpinner1, workoutSpinner2, workoutSpinner3;

    private Button postButton;

    private Uri resultUri;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        workout_image = findViewById(R.id.workout_image);
        registerDescription = findViewById(R.id.registerDescription);
        workoutSpinner = findViewById(R.id.workout_Spinner);
        workoutSpinner1 = findViewById(R.id.workout_Spinner1);
        workoutSpinner2 = findViewById(R.id.workout_Spinner2);
        workoutSpinner3 = findViewById(R.id.workout_Spinner3);

        postButton = findViewById(R.id.post_Button);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //Profile picture from the gallery
        workout_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = registerDescription.getText().toString().trim();
                final String workout = workoutSpinner.getSelectedItem().toString();
                final String workout1 = workoutSpinner1.getSelectedItem().toString();
                final String workout2 = workoutSpinner2.getSelectedItem().toString();
                final String workout3 = workoutSpinner3.getSelectedItem().toString();

                if (TextUtils.isEmpty(description)){
                    registerDescription.setError("Description is required!");
                    return;
                }
                if (workout.equals("Select the required Workout")){
                    Toast.makeText(WorkoutsActivity.this, "Select Workout", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    loader.setMessage("Posting workout...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    postButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                                if (!task.isSuccessful()){
//                                    String error = task.getException().toString();
//                                    Toast.makeText(WorkoutsActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
//                                }
//                                else {
                                    String currentUserId = mAuth.getCurrentUser().getUid();
                                    userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("workouts").child(currentUserId);
                                    HashMap workoutInfo = new HashMap();
                                    workoutInfo.put("id", currentUserId);
                                    workoutInfo.put("description", description);
                                    workoutInfo.put("workout", workout);
                                    workoutInfo.put("workout1", workout1);
                                    workoutInfo.put("workout2", workout2);
                                    workoutInfo.put("workout3", workout3);
                                    workoutInfo.put("type", "trainer");
                                    workoutInfo.put("search", "trainer"+workout);

                                    userDatabaseRef.updateChildren(workoutInfo).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(WorkoutsActivity.this, "Data set Successfully", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(WorkoutsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }

                                            finish();
                                        loader.dismiss();
                                        }
                                    });

                                    if (resultUri !=null){
                                        final StorageReference filepath = FirebaseStorage.getInstance().getReference()
                                                .child("workout images").child(currentUserId);
                                        Bitmap bitmap = null;

                                        try{
                                            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                        byte[] data = byteArrayOutputStream.toByteArray();
                                        UploadTask uploadTask = filepath.putBytes(data);

                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(WorkoutsActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                if (taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){
                                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String imageUrl = uri.toString();
                                                            Map newImageMap = new HashMap();
                                                            newImageMap.put("workoutpictureurl", imageUrl);

                                                            userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful()){
                                                                        Toast.makeText(WorkoutsActivity.this, "image url added to database successfully", Toast.LENGTH_SHORT).show();
                                                                    }else {
                                                                        Toast.makeText(WorkoutsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });

                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                        });

//                                    Intent intent = new Intent(TraineeRegistrationActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                        loader.dismiss();

                                    }



                                }
                            });
                        }

                    }
                });
            }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode ==1 && resultCode == RESULT_OK && data !=null){
                resultUri = data.getData();
                workout_image.setImageURI(resultUri);
            }
        }
    }


//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
