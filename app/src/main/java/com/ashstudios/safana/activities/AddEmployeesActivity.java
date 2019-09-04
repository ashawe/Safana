package com.ashstudios.safana.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ashstudios.safana.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddEmployeesActivity extends AppCompatActivity {

    Button mBtnGenerate;
    EditText mEtNumberOfEmployees;
    TextView mTvList,tv_alert;
    public FirebaseFirestore db;
    AlertDialog dialog;
    ArrayList<String> userNames;
    ArrayList<String> dataBaseUserNames;
    ProgressBar progressBar;
    Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);
        mBtnGenerate = findViewById(R.id.btn_generate);
        mEtNumberOfEmployees = findViewById(R.id.et_number_of_emp);
        mTvList = findViewById(R.id.tv_display_list);
        userNames = new ArrayList<>();
        dataBaseUserNames = new ArrayList<>();

        View v = getLayoutInflater().inflate(R.layout.alert_progress,null);
        progressBar = v.findViewById(R.id.progressBar2);
        tv_alert = v.findViewById(R.id.aleet_tv);
        ProgressBar progressBar = new ProgressBar(AddEmployeesActivity.this);
        progressBar.setPadding(10,30,10,30);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEmployeesActivity.this);
        dialog = alertDialog.create();
        dialog.setCancelable(false);
        dialog.setView(v);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        mBtnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mEtNumberOfEmployees.getText().toString().matches("") )
                    Toast.makeText(getBaseContext(), "Please fill number of employee ids to generate", Toast.LENGTH_LONG).show();
                else
                {
                    dataBaseUserNames.clear();
                    userNames.clear();
                    counter = Integer.parseInt(mEtNumberOfEmployees.getText().toString());
                    if(counter > 0 && counter < 11 )
                    {
//                        Toast.makeText(getBaseContext(), "Generating...", Toast.LENGTH_SHORT).show();
                        dialog.show();
                        generateID();
                        tv_alert.setText("Checking in Database");
                    }
                    else
                    {
                        mEtNumberOfEmployees.setError("Please select a number between 1 and 10 ");
                    }
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    private void generateID()
    {
        String empId = "EMP" + generateRandom();
        isUserInDb(empId);
    }

    private void isUserInDb(final String userId)
    {
        //Toast.makeText(AddEmployeesActivity.this, "userid:" + userId, Toast.LENGTH_SHORT).show();
        db.collection("Employees").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //Toast.makeText(AddEmployeesActivity.this, "here", Toast.LENGTH_SHORT).show();
                if(!task.getResult().exists())
                {
                    if( userNames.size() == counter) {
                        tv_alert.setText("Adding IDs to Database");
                    }
                    else
                    {
                        generateID();
                        userNames.add(userId);
                        addUserInDb(userId);
                    }
                }
                else
                    generateID();
            }
        });
    }

    private void addUserInDb(final String userId) {
        Map<String,String> map = new HashMap<>();
        map.put("password","");
        db.collection("Employees").document(userId).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    dataBaseUserNames.add(userId);
                    if( dataBaseUserNames.size() >= counter )
                    {
                        dialog.dismiss();
                        showNewIds();
                        Toast.makeText(AddEmployeesActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showNewIds()
    {
        StringBuilder str = new StringBuilder();
        for( String usrName : userNames ) {
            str.append(usrName);
            str.append("\n");
        }
        mTvList.setText(str.toString());
        mEtNumberOfEmployees.setText("");
    }

    private int generateRandom()
    {
        Random r = new Random();
        return r.nextInt( 9999 - 1000) + 1000;
    }
}
