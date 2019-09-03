package com.ashstudios.safana.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ashstudios.safana.models.LeaveModel;
import com.ashstudios.safana.R;
import com.ashstudios.safana.ui.leave_management.LeaveManagementViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaveManagementRVAdapter extends RecyclerView.Adapter<LeaveManagementRVAdapter.ViewHolder>{

    private ArrayList<LeaveModel> leaveModels;
    private Context mContext;

    public ArrayList<LeaveModel> getLeaveModels() {
        return leaveModels;
    }

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
            public void onClick(final View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Simple Alert");
//                builder.setMessage("We have a message");
//                builder.setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(v.getContext(),
//                                        "OK was clicked",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(v.getContext(),
//                                android.R.string.no, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setCancelable(false);
//                builder.show();

                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create(); //Read Update

                final View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.leave_management_dialog,null);

                alertDialog.setView(view1);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaveModels.size();
    }

    public void removeItem(int position) {
        leaveModels.remove(position);
        notifyDataSetChanged();
    }

    public void restoreItem(LeaveModel item, int position) {
        leaveModels.add(position,item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView circleImageView;
        TextView name,date,reason;
        ConstraintLayout ll_worker_item;
        TextDrawable textDrawable;
        ColorGenerator colorGenerator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            colorGenerator = ColorGenerator.MATERIAL;
            textDrawable = TextDrawable.builder().buildRect("H",colorGenerator.getRandomColor());
            name = itemView.findViewById(R.id.worker_name);
            date = itemView.findViewById(R.id.leave_date);
            reason = itemView.findViewById(R.id.reason);
            ll_worker_item = itemView.findViewById(R.id.ll_worker_leave_item);
        }
    }

}
