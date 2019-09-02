package com.ashstudios.safana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashstudios.safana.adapters.WorkerRVAdapter;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;

public class NewProjectSelectWorkerActivity extends AppCompatActivity {

    private WorkerDetailsViewModel workerDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project_select_worker);

        workerDetailsViewModel = new WorkerDetailsViewModel();

        RecyclerView recyclerView = findViewById(R.id.rc_worker_details);
        WorkerRVSelectAdapter workerRVSelectAdapter = new WorkerRVSelectAdapter(workerDetailsViewModel,NewProjectSelectWorkerActivity.this);


        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(workerRVSelectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(NewProjectSelectWorkerActivity.this));

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
}
