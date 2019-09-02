package com.ashstudios.safana.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ashstudios.safana.NewProjectSelectWorkerActivity;
import com.ashstudios.safana.R;

public class NewProjectActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        button = findViewById(R.id.btn_next);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generateEmployeeId = new Intent(NewProjectActivity.this, NewProjectSelectWorkerActivity.class);
                startActivity(generateEmployeeId);
                finish();
            }
        });
    }
}
