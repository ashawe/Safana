package com.ashstudios.safana.ui.leave_management;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.LeaveManagementRVAdapter;
import com.ashstudios.safana.adapters.WorkerRVAdapter;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;

public class LeaveManagementFragment extends Fragment {

    static  private LeaveManagementViewModel leaveManagementViewModel;
    static RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaveManagementViewModel =
                ViewModelProviders.of(this).get(LeaveManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leave_management, container, false);

        recyclerView = root.findViewById(R.id.rc_worker_leave_requests);
        LeaveManagementRVAdapter leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(leaveManagementRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().findViewById(R.layout.fragment_bottom_sheet_sort);
        return root;
    }


    public static void sort(Context mContext, Bundle b)
    {
        Toast.makeText( mContext, "sorting...", Toast.LENGTH_LONG).show();
        leaveManagementViewModel.sort(b);
        LeaveManagementRVAdapter leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,mContext);
        recyclerView.setAdapter(leaveManagementRVAdapter);
    }
}