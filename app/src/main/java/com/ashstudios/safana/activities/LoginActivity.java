package com.ashstudios.safana.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ashstudios.safana.R;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText editText,editText1;
    public FirebaseFirestore db;
    private  AlertDialog dialog;
    Context context;
    String documentID;
    private Boolean isSwipe = false;
    private ConstraintLayout constraintLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize();
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width() - 20))) {
                        if(editText.getText().length() <= 0) {
                            editText.setError("Enter ID ");
                        }
                        else {
                            documentID = (editText.getText().toString().trim());
                            dialog.show();
                            //check if it is the first user or not
                            db.collection("Employees").document(documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if(Objects.requireNonNull(task.getResult()).exists()) {
                                            dialog.dismiss();
                                            if(task.getResult().getString("password").equals("")) {
                                                //new user activity
                                                Intent intent = new Intent(getBaseContext(),NewUserActivity.class);
                                                intent.putExtra("empid",documentID);
                                                finish();
                                                startActivity(intent);
                                            }
                                            else {
                                                //display the password field
                                                isSwipe = true;
                                                final AnimatorSet as = new AnimatorSet();
                                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(findViewById(R.id.et_emp_id), "translationX", -constraintLayout.getWidth());
                                                ObjectAnimator animator2 = ObjectAnimator.ofFloat(findViewById(R.id.et_pwd), "translationX", 0);
                                                animator1.setDuration(500);
                                                animator2.setDuration(500);
                                                as.play(animator1);
                                                as.play(animator2);
                                                as.start();
                                                editText1.requestFocus();
                                            }
                                        }
                                        else {
                                            dialog.dismiss();
                                            Toast.makeText(context, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        editText1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (editText1.getRight() - editText1.getCompoundDrawables()[2].getBounds().width() - 20)) && editText1.getText().length() != 10) {
                        if(editText1.getText().length() <= 0) {
                            editText1.setError("Enter password ");
                        }
                        else {
                            dialog.show();
                            db.collection("Employees").document(documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if( task.getResult().getString("password").equals(editText1.getText().toString().trim()))
                                        {
                                            SharedPref sharedPref = new SharedPref(context,true,documentID,task.getResult().getString("name"),task.getResult().getString("mail"));
                                            if(documentID.contains("SUP")){
                                                dialog.dismiss();
                                                Intent intent = new Intent(getBaseContext(),SupervisorDashboard.class);
                                                finish();
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                dialog.dismiss();
                                                Intent intent = new Intent(getBaseContext(),WorkerDashboardActivity.class);
                                                finish();
                                                startActivity(intent);
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    dialog.dismiss();
                                }
                            });
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!isSwipe)
            super.onBackPressed();
        else {
            isSwipe = false;
            final AnimatorSet as = new AnimatorSet();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(findViewById(R.id.et_pwd), "translationX", constraintLayout.getWidth());
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(findViewById(R.id.et_emp_id), "translationX", 0);
            animator1.setDuration(500);
            animator2.setDuration(500);
            as.play(animator1);
            as.play(animator2);
            as.start();
        }
    }

    private void initialize() {
        context = LoginActivity.this;
        editText = findViewById(R.id.et_emp_id);
        editText1 = findViewById(R.id.et_pwd);
        constraintLayout = findViewById(R.id.constraint_layout);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setPadding(10,30,10,30);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        dialog = alertDialog.create();
        dialog.setCancelable(false);
        dialog.setView(progressBar);
    }
}
