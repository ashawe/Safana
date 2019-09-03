package com.ashstudios.safana.ui.leave_management;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.models.LeaveModel;
import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.LeaveManagementRVAdapter;
import com.ashstudios.safana.others.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

public class LeaveManagementFragment extends Fragment {

    static  private LeaveManagementViewModel leaveManagementViewModel;
    static RecyclerView recyclerView;
    private Boolean isUndo = false;
    private LeaveManagementRVAdapter leaveManagementRVAdapter;
    private ConstraintLayout constraintLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaveManagementViewModel =
                ViewModelProviders.of(this).get(LeaveManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leave_management, container, false);

        constraintLayout = root.findViewById(R.id.cl_leave_management);
        recyclerView = root.findViewById(R.id.rc_worker_leave_requests);
        leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(leaveManagementRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        enableSwipeToCompleteAndUndo();

        return root;
    }

    private void enableSwipeToCompleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                isUndo = true;
                final int position = viewHolder.getAdapterPosition();
                final LeaveModel item = leaveManagementRVAdapter.getLeaveModels().get(position);

                leaveManagementRVAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Leave Request Approved", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isUndo) {
                            leaveManagementRVAdapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                            isUndo = false;
                        }
                    }
                });

                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


    public static void sort(Context mContext, Bundle b)
    {
        Toast.makeText( mContext, "sorting...", Toast.LENGTH_LONG).show();
        leaveManagementViewModel.sort(b);
        LeaveManagementRVAdapter leaveManagementRVAdapter = new LeaveManagementRVAdapter(leaveManagementViewModel,mContext);
        recyclerView.setAdapter(leaveManagementRVAdapter);
    }
}