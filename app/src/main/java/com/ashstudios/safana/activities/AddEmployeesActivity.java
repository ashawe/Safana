package com.ashstudios.safana.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ashstudios.safana.R;

import java.util.ArrayList;

public class AddEmployeesActivity extends AppCompatActivity {

    Button mBtnGenerate;
    EditText mEtNumberOfEmployees;
    TextView mTvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);
        mBtnGenerate = findViewById(R.id.btn_generate);
        mEtNumberOfEmployees = findViewById(R.id.et_number_of_emp);
        mTvList = findViewById(R.id.tv_display_list);

        mBtnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mEtNumberOfEmployees.getText().toString().matches("") )
                    Toast.makeText(getBaseContext(), "Please fill number of employee ids to generate", Toast.LENGTH_LONG).show();
                else
                {
                    Toast.makeText(getBaseContext(), "Generating...", Toast.LENGTH_SHORT).show();
                    int num = Integer.parseInt(mEtNumberOfEmployees.getText().toString());
                    ArrayList<Integer> list = generateID(num);
                    showNewIds(list);
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

    private ArrayList<Integer> generateID(int num) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < num; i++ )
        {
            list.add(i);
        }
        return list;
    }

    private void showNewIds(ArrayList<Integer> list)
    {
        StringBuilder str = new StringBuilder();
        for( int i : list)
        {
            str.append(i);
            str.append("\n");
        }
        mTvList.setText(str.toString());
        mEtNumberOfEmployees.setText("");
    }
}
