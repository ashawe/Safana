package com.ashstudios.safana.ui.allowance_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ashstudios.safana.activities.CreateAllowanceActivity;
import com.ashstudios.safana.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllowanceManagementFragment extends Fragment {

    private AllowanceManagementViewModel allowanceManagementViewModel;
    private FloatingActionButton floatingActionButton;
    private AutoCompleteTextView autoCompleteTextView;
    private ChipGroup chipGroup;
    private ArrayList<String> chips;
    private Spinner spinner;
    Button mBtnGrant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allowanceManagementViewModel =
                ViewModelProviders.of(this).get(AllowanceManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_allowance_management, container, false);
        floatingActionButton = root.findViewById(R.id.fab);
        autoCompleteTextView = root.findViewById(R.id.et_emp_id_name);
        chipGroup = root.findViewById(R.id.cg_emp_names);
        spinner = root.findViewById(R.id.spinner_allowance_name);
        mBtnGrant = root.findViewById(R.id.btn_grant);
        chips = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), CreateAllowanceActivity.class);
                startActivity(intent);
            }
        });

        mBtnGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Granted Allowance!", Toast.LENGTH_SHORT).show();
                autoCompleteTextView.setText("");
                chipGroup.removeAllViews();
                chips.clear();
            }
        });

        String[] arraySpinner = new String[] {"-- Select Allowance --","Food Allowance", "Travel Allowance", "Laptop", "Accommodation"};
        String[] arr = new String[] {"Harsh Saglani", "Krunal Pande", "Rohit Suthar", "Manav Shah", "John Doe", "Robert Downey Jr.","Carry Minati","Tanmay Bhatt", "Bhuvan Bam", "Parth Nakil","Chaitanya Dhakre","David Levithan","Dan Brown","Bill gates","Anthony Horowitz"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , arr);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final Chip chip = new Chip(getActivity());
                chip.setText(arg0.getItemAtPosition(arg2).toString());
                chip.setId(arg2);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chipGroup.removeView(chip);
                        chips.remove(chip.getText());
                    }
                });
                chip.setCloseIconVisible(true);
                if(!chips.contains(arg0.getItemAtPosition(arg2).toString())) {
                    chipGroup.addView(chip);
                    chips.add(arg0.getItemAtPosition(arg2).toString());
                }
                autoCompleteTextView.setText("");
            }
        });


        return root;
    }
}