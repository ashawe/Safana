package com.ashstudios.safana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ashstudios.safana.R;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {

    private Context context;
    private EditText mEtName, mEtEmail, mEtpassword;
    private Button mBtnSignUp;
    private FirebaseFirestore db;
    private String empId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        db = FirebaseFirestore.getInstance();
        empId = getIntent().getStringExtra("empid");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");
        setSupportActionBar(toolbar);

        context = NewUserActivity.this;
        mEtName = findViewById(R.id.et_name);
        mEtEmail = findViewById(R.id.et_email);
        mEtpassword = findViewById(R.id.et_password);
        mBtnSignUp = findViewById(R.id.btn_sign_up);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails(mEtName.getText().toString().trim(), mEtEmail.getText().toString().trim(), mEtpassword.getText().toString().trim());
            }
        });
    }

    private void updateUserDetails(final String name, final String email, String password) {
        Map<String,String> data = new HashMap<>();
        data.put("name",name);
        data.put("mail",email);
        data.put("password",password);
        db.collection("Employees").document(empId).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //if successfull login then initialize shared pref
                new SharedPref(context,true,empId,name,email);
                Intent intent = new Intent(context,EditProfileActivity.class);
                finish();
                Toast.makeText(context, "First complete your profile", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "There was an error while updating your profile information.", Toast.LENGTH_SHORT).show();
                Msg.log(e.getMessage());
            }
        });
    }
}
