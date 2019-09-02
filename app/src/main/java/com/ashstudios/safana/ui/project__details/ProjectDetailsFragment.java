package com.ashstudios.safana.ui.project__details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ashstudios.safana.activities.NewProjectActivity;
import com.ashstudios.safana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProjectDetailsFragment extends Fragment {

    private ProjectDetailsViewModel projectDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectDetailsViewModel =
                ViewModelProviders.of(this).get(ProjectDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_details, container, false);
        ProgressBar progressBar = root.findViewById(R.id.progress_bar);
        FloatingActionButton fab = root.findViewById(R.id.fab);

        progressBar.setProgress(60);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generateEmployeeId = new Intent(getContext(), NewProjectActivity.class);
                startActivity(generateEmployeeId);
            }
        });
        return root;
    }
}