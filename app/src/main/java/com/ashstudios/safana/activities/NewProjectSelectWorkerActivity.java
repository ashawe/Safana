package com.ashstudios.safana.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.WorkerRVSelectAdapter;
import com.ashstudios.safana.models.WorkerModel;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NewProjectSelectWorkerActivity extends AppCompatActivity {

    private ArrayList<WorkerModel> workerModels;
    private FirebaseFirestore db;
    private SharedPref sharedPref;
    private WorkerDetailsViewModel workerDetailsViewModel;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private TextView tv_alert, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeProgressBarDialog();
        setContentView(R.layout.activity_new_project_select_worker);

        sharedPref = new SharedPref(getApplicationContext());
        workerDetailsViewModel = new WorkerDetailsViewModel();
        db = FirebaseFirestore.getInstance();
        title = findViewById(R.id.title_project_name);

        initNoProjectWorkers();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initNoProjectWorkers()
    {
        tv_alert.setText("Fetching Workers");
        alertDialog.show();
        workerModels = new ArrayList<>();
        db.collection("Employees").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Msg.log("success");
                    if(task.getResult() != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Msg.log("success123");
                            if (!documentSnapshot.getString("password").equals(""))
                                if (!documentSnapshot.getData().containsKey("project_id")) {
                                    if (!documentSnapshot.getId().equals(sharedPref.getEMP_ID())) {
                                        Msg.log("emp found " + documentSnapshot.getString("name"));
                                        WorkerModel workerModel = new WorkerModel();
                                        workerModel.setName(documentSnapshot.getString("name"));
                                        if (documentSnapshot.getData().containsKey("profile_image"))
                                            workerModel.setImgUrl(documentSnapshot.getString("profile_image"));
                                        else
                                            workerModel.setImgUrl("https://i.imgur.com/wnKtRoZ.png");
                                        if (documentSnapshot.getData().containsKey("role"))
                                            workerModel.setRole(documentSnapshot.getString("role"));
                                        else
                                            workerModel.setRole("NA");
                                        workerModels.add(workerModel);
                                    }
                                }
                        }
                        Msg.log("" + workerModels.size());
                        RecyclerView recyclerView = findViewById(R.id.rc_worker_details);
                        WorkerRVSelectAdapter workerRVSelectAdapter = new WorkerRVSelectAdapter(workerModels, NewProjectSelectWorkerActivity.this);

                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(workerRVSelectAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(NewProjectSelectWorkerActivity.this));
                        alertDialog.dismiss();
                    }
                    else
                    {
                        title.setText("Each employee is working on a project");
                    }
                }
            }
        });
    }

    private void initializeProgressBarDialog() {
        View v = getLayoutInflater().inflate(R.layout.alert_progress,null);
        progressBar = v.findViewById(R.id.progressBar2);
        tv_alert = v.findViewById(R.id.alert_tv);
        final AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(NewProjectSelectWorkerActivity.this);
        alertDialog = alertDialogbuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(v);
    }
}
