package com.ashstudios.safana.ui.projectstatus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ashstudios.safana.R;
import com.ashstudios.safana.ui.mytasks.MyTasksViewModel;

public class ProjectStatusFragment extends Fragment {

    private ProjectStatusViewModel projectStatusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectStatusViewModel =
                ViewModelProviders.of(this).get(ProjectStatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_status, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        projectStatusViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}