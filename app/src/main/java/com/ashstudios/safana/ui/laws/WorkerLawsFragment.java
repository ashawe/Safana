package com.ashstudios.safana.ui.laws;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ashstudios.safana.R;

public class WorkerLawsFragment extends Fragment {

    private WorkerLawsViewModel workerLawsViewModel;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workerLawsViewModel =
                ViewModelProviders.of(this).get(WorkerLawsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_worker_laws, container, false);
        textView = root.findViewById(R.id.tv_laws);
        textView.setText(getResources().getString(R.string.worker_laws_rules));
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}