package com.ashstudios.safana.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ashstudios.safana.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText editText,editText1;
    public FirebaseFirestore db;
    private Boolean isPresent = false;
    Context context;
    String documentID;
    Intent intent;
    private Boolean isSwipe = false;
    private ConstraintLayout constraintLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
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
                            //check if it is the first user or not
                            db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        boolean userExists = false;
                                        for(DocumentSnapshot doc:task.getResult()) {
                                            if(doc.getString("empid").equals(editText.getText().toString().trim())) {
                                                if(doc.getString("password").equals(""))
                                                {
                                                    Intent intent = new Intent(getBaseContext(),NewUserActivity.class);
                                                    startActivity(intent);
                                                    userExists = true;
                                                }
                                                else
                                                {
                                                    documentID = doc.getId();
                                                    userExists = true;
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
                                        }
                                        if(!userExists) {
                                            Toast.makeText(context, "user doesn't exist", Toast.LENGTH_SHORT).show();
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
                            db.collection("users").document(documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if( task.getResult().getString("password").equals(editText1.getText().toString().trim()))
                                        {
                                            if(task.getResult().getBoolean("isSup")){
                                                Intent intent = new Intent(getBaseContext(),SupervisorDashboard.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Intent intent = new Intent(getBaseContext(),WorkerDashboardActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(context, "wrong password", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onBackPressed() {
        if(!isSwipe)
            super.onBackPressed();
        else {
            isSwipe = false;
            isPresent = false;
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
    }
}
