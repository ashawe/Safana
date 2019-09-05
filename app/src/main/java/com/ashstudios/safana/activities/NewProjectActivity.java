package com.ashstudios.safana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ashstudios.safana.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class NewProjectActivity extends AppCompatActivity {

    Button button;
    FirebaseFirestore db;
    AlertDialog dialog;
    EditText etProjectName,etProjectBudget,etStartDate,etDueDate,etAdditionalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        button = findViewById(R.id.btn_next);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etProjectName = findViewById(R.id.project_name);
        etProjectBudget = findViewById(R.id.project_budget);
        etStartDate = findViewById(R.id.project_start_date);
        etDueDate = findViewById(R.id.project_due_date);
        etAdditionalDetails = findViewById(R.id.project_additional_details);


        // firestore
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // for loader
        ProgressBar progressBarLoading = new ProgressBar(NewProjectActivity.this);
        progressBarLoading.setPadding(10,30,10,30);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewProjectActivity.this);
        dialog = alertDialog.create();
        dialog.setCancelable(false);
        dialog.setView(progressBarLoading);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(etProjectName.getText().toString().equals("")) {
                    etProjectName.setError("Please fill project name");
                    flag = false;
                }
                if(etProjectBudget.getText().toString().equals("")) {
                    etProjectBudget.setError("Please fill project name");
                    flag = false;
                }
                if(etDueDate.getText().toString().equals("")){
                    etDueDate.setError("Please fill project name");
                    flag = false;
                }
                if(etStartDate.getText().toString().equals("")){
                    etStartDate.setError("Please fill project name");
                    flag = false;
                }

                if(flag)
                {
                    Intent generateEmployeeId = new Intent(NewProjectActivity.this, NewProjectSelectWorkerActivity.class);
                    generateEmployeeId.putExtra("name",etProjectName.getText().toString());
                    generateEmployeeId.putExtra("budget",etProjectBudget.getText().toString());
                    generateEmployeeId.putExtra("stDate",etStartDate.getText().toString());
                    generateEmployeeId.putExtra("duDate",etDueDate.getText().toString());
                    startActivity(generateEmployeeId);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
