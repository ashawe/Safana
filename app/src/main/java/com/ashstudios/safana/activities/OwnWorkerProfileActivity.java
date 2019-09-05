package com.ashstudios.safana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ashstudios.safana.R;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class OwnWorkerProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvEmpId, tvName, tvRole, tvEmail, tvPhoneNumber, tvGender, tvBirthDate;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private TextView tv_alert;
    private SharedPref sharedPref;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_worker_profile);
        db = FirebaseFirestore.getInstance();
        initialize();
        RetriveEmployeeData();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getBaseContext(),EditProfileActivity.class);
                intent.putExtra("which","notfirst");
                startActivity(intent);
                finish();
                return false;
            }
        });
    }

    private void RetriveEmployeeData() {
        tv_alert.setText("Retriving Data...");
        alertDialog.show();
        tvEmpId.setText(sharedPref.getEMP_ID());
        tvName.setText(sharedPref.getNAME());
        tvEmail.setText(sharedPref.getEMAIL());
        db.collection("Employees").document(sharedPref.getEMP_ID().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                tvRole.setText(documentSnapshot.getString("role"));
                tvBirthDate.setText(documentSnapshot.getString("birth_date"));
                tvPhoneNumber.setText(documentSnapshot.getString("mobile"));
                tvGender.setText(documentSnapshot.getString("sex"));
                Picasso.get().load(documentSnapshot.getString("profile_image")).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(imageView);
                alertDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OwnWorkerProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
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

    private void initialize() {
        imageView = findViewById(R.id.profile_image);
        tvEmpId = findViewById(R.id.tv_emp_id);
        tvName = findViewById(R.id.emp_name);
        tvEmail = findViewById(R.id.tv_email);
        tvBirthDate = findViewById(R.id.tv_bdate);
        tvPhoneNumber = findViewById(R.id.tv_mob);
        tvGender = findViewById(R.id.tv_sex);
        tvRole = findViewById(R.id.emp_role);
        tvEmpId = findViewById(R.id.tv_emp_id);
        sharedPref = new SharedPref(OwnWorkerProfileActivity.this);
        initializeProgressBarDialog();
    }
    
    private void initializeProgressBarDialog() {
        View v = getLayoutInflater().inflate(R.layout.alert_progress,null);
        progressBar = v.findViewById(R.id.progressBar2);
        tv_alert = v.findViewById(R.id.alert_tv);
        ProgressBar progressBar = new ProgressBar(OwnWorkerProfileActivity.this);
        progressBar.setPadding(10,30,10,30);

        final AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(OwnWorkerProfileActivity.this);
        alertDialog = alertDialogbuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(v);
    }

}
