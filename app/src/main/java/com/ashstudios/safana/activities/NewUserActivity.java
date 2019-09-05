package com.ashstudios.safana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private AlertDialog alertDialog;
    private TextView tv_alert;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        db = FirebaseFirestore.getInstance();
        initializeProgressBarDialog();
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
                if(validateFields())
                    updateUserDetails(mEtName.getText().toString().trim(), mEtEmail.getText().toString().trim(), mEtpassword.getText().toString().trim());
            }
        });
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean validateFields() {
        boolean isValid = true;
        if(mEtName.getText().length() <= 0) {
            mEtName.setError("Enter name");
            isValid = false;
        }
        else if(!isValidEmail(mEtEmail.getText().toString())) {
            mEtEmail.setError("Enter valid email");
            isValid = false;
        }
        else if(mEtpassword.getText().toString().length() <= 0) {
            mEtpassword.setError("Enter valid password");
            isValid = false;
        }
        else if(mEtpassword.getText().toString().length() < 8) {
            mEtpassword.setError("Password is too short");
            isValid = false;
        }
        return isValid;
    }


    private void updateUserDetails(final String name, final String email, String password) {
        tv_alert.setText("Updating profile...");
        alertDialog.show();
        Map<String,String> data = new HashMap<>();
        data.put("name",name);
        data.put("mail",email);
        data.put("password",password);
        db.collection("Employees").document(empId).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                alertDialog.dismiss();
                //if successfull login then initialize shared pref
                new SharedPref(context,true,empId,name,email);
                Intent intent = new Intent(context,EditProfileActivity.class);
                intent.putExtra("which","first");
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

    private void initializeProgressBarDialog() {
        View v = getLayoutInflater().inflate(R.layout.alert_progress,null);
        progressBar = v.findViewById(R.id.progressBar2);
        tv_alert = v.findViewById(R.id.alert_tv);
        final AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(NewUserActivity.this);
        alertDialog = alertDialogbuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(v);
    }
}
