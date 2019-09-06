package com.ashstudios.safana.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.WorkerRVSelectAdapter;
import com.ashstudios.safana.models.WorkerModel;
import com.ashstudios.safana.others.CustomAlertDialog;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewProjectSelectWorkerActivity extends AppCompatActivity {

    private ArrayList<WorkerModel> workerModels;
    private ArrayList<String> alreadyPresentWorkers,notAssignedWorkers,unselectedWorkers;
    private FirebaseFirestore db;
    private SharedPref sharedPref;
    private WorkerDetailsViewModel workerDetailsViewModel;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private TextView tv_alert, title;
    private Bundle prevData;
    private WorkerRVSelectAdapter workerRVSelectAdapter;
    private boolean isEdit;
    private CustomAlertDialog customAlertDialog;
    private String project_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeProgressBarDialog();
        setContentView(R.layout.activity_new_project_select_worker);
        sharedPref = new SharedPref(getApplicationContext());
        workerDetailsViewModel = new WorkerDetailsViewModel();
        db = FirebaseFirestore.getInstance();
        isEdit = getIntent().getExtras().getBoolean("isEdit");
        title = findViewById(R.id.title_project_name);
        prevData = getIntent().getExtras();
        customAlertDialog = new CustomAlertDialog(NewProjectSelectWorkerActivity.this);
        workerModels = new ArrayList<>();
        alreadyPresentWorkers = new ArrayList<>();
        notAssignedWorkers = new ArrayList<>();
        unselectedWorkers = new ArrayList<>();
        //if project is in edit mode then retrive already added project employees also
        if(isEdit) {
            fetchProjectId();
        }
        else {
            initNoProjectWorkers();
        }
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
                submitData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitData() {
        final ArrayList<String> workerModelIds = getWorkerIds();
        int size = workerModelIds.size();
        if(workerModelIds.size() == 0)
        {
            Toast.makeText(this, "Please select at least one employee", Toast.LENGTH_SHORT).show();
            return;
        }
        customAlertDialog.setTextViewText("Saving Project");
        customAlertDialog.show();
        Map<String,Object> projDetails = new HashMap<>();
        projDetails.put("name", Objects.requireNonNull(prevData.getString("name")));
        projDetails.put("budget", Objects.requireNonNull(prevData.getString("budget")));
        projDetails.put("start_date", Objects.requireNonNull(prevData.getString("start_date")));
        projDetails.put("due_date", Objects.requireNonNull(prevData.getString("due_date")));
        projDetails.put("additional_details", Objects.requireNonNull(prevData.getString("additional_details")));
        projDetails.put("supervisor_id",sharedPref.getEMP_ID());
        projDetails.put("workers", workerModelIds );

        if(isEdit) {
            final Map<String,Object> data = new HashMap<>();
            data.put("project_id", FieldValue.delete());

            db.collection("Projects").document(project_id).set(projDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        tv_alert.setText("Adding Workers");
                        WriteBatch batch = db.batch();
                        for(String id : workerModelIds)
                        {
                            DocumentReference worker = db.collection("Employees").document(id);
                            worker.update("project_id",project_id);
                        }
                        if(isEdit) {
                            for(String id : unselectedWorkers) {
                                DocumentReference unselectedWorker = db.collection("Employees").document(id);
                                unselectedWorker.update(data);
                            }
                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    customAlertDialog.dismiss();
                                    Toast.makeText(NewProjectSelectWorkerActivity.this, "Project Created!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
        else {
            db.collection("Projects").add(projDetails).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful()) {
                        tv_alert.setText("Adding Workers");
                        WriteBatch batch = db.batch();
                        for(String id : workerModelIds)
                        {
                            DocumentReference worker = db.collection("Employees").document(id);
                            worker.update("project_id",task.getResult().getId());
                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    customAlertDialog.dismiss();
                                    Toast.makeText(NewProjectSelectWorkerActivity.this, "Project Created!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void fetchProjectId() {
        customAlertDialog.setTextViewText("Fetching Workers");
        customAlertDialog.show();
        //first fetch the project id from database
        db.collection("Projects").whereEqualTo("supervisor_id",sharedPref.getEMP_ID()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().size() == 1) {
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            project_id = documentSnapshot.getId();
                            retriveAlreadyPresentWorkers(documentSnapshot.getId());
                        }
                    }
                }
            }
        });
    }

    private void retriveAlreadyPresentWorkers(String project_id) {
        db.collection("Employees").whereEqualTo("project_id",project_id.trim()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult() != null) {
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            WorkerModel workerModel = new WorkerModel();
                            workerModel.setEmp_id(documentSnapshot.getId());
                            workerModel.setSelected(true);
                            alreadyPresentWorkers.add(documentSnapshot.getId());
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
                        initNoProjectWorkers();
                    }
                }
            }
        });
    }

    private void initNoProjectWorkers()
    {
        if(!customAlertDialog.isShowing()) {
            customAlertDialog.setTextViewText("Fetching Workers");
            customAlertDialog.show();
        }
        db.collection("Employees").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult() != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (!documentSnapshot.getString("password").equals(""))
                                if (!documentSnapshot.getData().containsKey("project_id")) {
                                    if (!documentSnapshot.getId().equals(sharedPref.getEMP_ID())) {
                                        Msg.log("emp found " + documentSnapshot.getString("name"));
                                        WorkerModel workerModel = new WorkerModel();
                                        workerModel.setEmp_id(documentSnapshot.getId());
                                        notAssignedWorkers.add(documentSnapshot.getId());
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
                        if( workerModels.size() > 0) {
                            RecyclerView recyclerView = findViewById(R.id.rc_worker_details);
                            workerRVSelectAdapter = new WorkerRVSelectAdapter(workerModels, NewProjectSelectWorkerActivity.this);

                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setAdapter(workerRVSelectAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(NewProjectSelectWorkerActivity.this));
                            customAlertDialog.dismiss();
                        }
                        else
                            title.setText("Each employee is working on a project");
                    }
                    else
                    {
                        title.setText("Each employee is working on a project");
                    }
                }
            }
        });
    }

    private ArrayList<String> getWorkerIds()
    {
        ArrayList<String> ids = new ArrayList<>();
        for(WorkerModel worker : workerModels){
            if (worker.isSelected())
                ids.add(worker.getEmp_id());
            else {
                //if the worker is already present in the project and now if it is unselected then update project id in the database
                if(alreadyPresentWorkers.contains(worker.getEmp_id())) {
                    unselectedWorkers.add(worker.getEmp_id());
                }
            }
        }
        return ids;
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
