package com.ashstudios.safana.ui.worker_details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.WorkerRVAdapter;

public class WorkerDetailsFragment extends Fragment {

    static private WorkerDetailsViewModel workerDetailsViewModel;
    static RecyclerView recyclerView;
    WorkerRVAdapter workerRVAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workerDetailsViewModel = ViewModelProviders.of(this).get(WorkerDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_worker_details, container, false);

        recyclerView = root.findViewById(R.id.rc_worker_details);
        workerRVAdapter = new WorkerRVAdapter(workerDetailsViewModel,getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(workerRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    public static void sort(Context mContext,Bundle b)
    {
        Toast.makeText( mContext, "sorting...", Toast.LENGTH_LONG).show();
        workerDetailsViewModel.sort(b);
        WorkerRVAdapter workerRVAdapter = new WorkerRVAdapter(workerDetailsViewModel,mContext);
        recyclerView.setAdapter(workerRVAdapter);
    }
}