package com.ashstudios.safana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.LeaveModel;
import com.ashstudios.safana.R;
import com.ashstudios.safana.WorkerModel;
import com.ashstudios.safana.activities.WorkerProfileActivity;
import com.ashstudios.safana.ui.leave_management.LeaveManagementViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveManagementRVAdapter extends RecyclerView.Adapter<LeaveManagementRVAdapter.ViewHolder>{

    private ArrayList<LeaveModel> leaveModels;
    private Context mContext;

    public LeaveManagementRVAdapter(LeaveManagementViewModel leaveManagementViewModel, Context mContext) {
        this.leaveModels = leaveManagementViewModel.getLeaveModels();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_leave_list_item,parent,false);
        return (new LeaveManagementRVAdapter.ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaveModel leaveModel = leaveModels.get(position);
        holder.name.setText(leaveModel.getName());
        holder.date.setText(leaveModel.getDate());
        holder.reason.setText(leaveModel.getReason());
        Picasso.get()
                .load(leaveModel.getImgUrl())
                .noFade()
                .resizeDimen(R.dimen.profile_photo,R.dimen.profile_photo)
                .into(holder.circleImageView);
        holder.ll_worker_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkerProfileActivity.class);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return leaveModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView name,date,reason;
        ConstraintLayout ll_worker_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.worker_name);
            date = itemView.findViewById(R.id.leave_date);
            reason = itemView.findViewById(R.id.reason);
            ll_worker_item = itemView.findViewById(R.id.ll_worker_leave_item);
        }
    }
}
