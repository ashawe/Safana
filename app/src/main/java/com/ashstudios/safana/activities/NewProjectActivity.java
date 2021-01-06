package com.ashstudios.safana.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ashstudios.safana.R;
import com.ashstudios.safana.others.CustomAlertDialog;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class NewProjectActivity extends AppCompatActivity {

    private Button button;
    private FirebaseFirestore db;
    private AlertDialog dialog;
    private EditText etProjectName,etProjectBudget,etStartDate,etDueDate,etAdditionalDetails;
    private boolean isEdit = false;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        if(getIntent().getStringExtra("isEdit").equals("true"))
            isEdit = true;

        sharedPref = new SharedPref(NewProjectActivity.this);
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
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        db.setFirestoreSettings(settings);

        //if the activity is open for editing of the previous project then retrive the data
        if(isEdit)
            retriveProjectData();
        // for loader
        ProgressBar progressBarLoading = new ProgressBar(NewProjectActivity.this);
        progressBarLoading.setPadding(10,30,10,30);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewProjectActivity.this);
        dialog = alertDialog.create();
        dialog.setCancelable(false);
        dialog.setView(progressBarLoading);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            final int mYear = c.get(Calendar.YEAR);
            final int mMonth = c.get(Calendar.MONTH);
            final int mDay = c.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(final View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewProjectActivity.this, R.style.CustomDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view1, final int year, final int month, final int dayOfMonth) {
                        etStartDate.setText(dayOfMonth+ "/" + ((Integer) month+1) + "/" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        etDueDate.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            final int mYear = c.get(Calendar.YEAR);
            final int mMonth = c.get(Calendar.MONTH);
            final int mDay = c.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(final View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewProjectActivity.this, R.style.CustomDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view1, final int year, final int month, final int dayOfMonth) {
                        etDueDate.setText(dayOfMonth+ "/" + ((Integer) month+1) + "/" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(etProjectName.getText().toString().length() < 4){
                    etProjectName.setError("Project name must be more than 4 characters");
                    flag = false;
                }
                if(etProjectBudget.getText().toString().equals("")) {
                    etProjectBudget.setError("Please fill project budget");
                    flag = false;
                }
                if(TextUtils.isEmpty(etStartDate.getText().toString())){
                    etDueDate.setError("Please fill start date");
                    flag = false;
                }

                if(TextUtils.isEmpty(etDueDate.getText().toString())){
                    etStartDate.setError("Please fill due date");
                    flag = false;
                }

                if(flag)
                {
                    Intent generateEmployeeId = new Intent(NewProjectActivity.this, NewProjectSelectWorkerActivity.class);
                    generateEmployeeId.putExtra("isEdit",isEdit);
                    generateEmployeeId.putExtra("name",etProjectName.getText().toString());
                    generateEmployeeId.putExtra("budget",etProjectBudget.getText().toString());
                    generateEmployeeId.putExtra("start_date",etStartDate.getText().toString());
                    generateEmployeeId.putExtra("due_date",etDueDate.getText().toString());
                    generateEmployeeId.putExtra("additional_details",etAdditionalDetails.getText().toString());
                    startActivity(generateEmployeeId);
                    finish();
                }
            }
        });
    }

    private void retriveProjectData() {
        //retrive the project details
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(NewProjectActivity.this);
        customAlertDialog.setTextViewText("Retriving Data...");
        customAlertDialog.show();
        db.collection("Projects").whereEqualTo("supervisor_id",sharedPref.getEMP_ID()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())  {
                    if(task.getResult() != null && task.getResult().size() == 1) {
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            etProjectName.setText(documentSnapshot.getString("name"));
                            etProjectBudget.setText(documentSnapshot.getString("budget"));
                            etStartDate.setText(documentSnapshot.getString("start_date"));
                            etDueDate.setText(documentSnapshot.getString("due_date"));
                            etAdditionalDetails.setText(documentSnapshot.getString("additional_details"));
                            customAlertDialog.dismiss();
                        }
                    }
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
