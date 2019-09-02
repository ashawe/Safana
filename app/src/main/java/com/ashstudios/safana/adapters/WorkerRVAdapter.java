package com.ashstudios.safana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashstudios.safana.R;
import com.ashstudios.safana.WorkerModel;
import com.ashstudios.safana.activities.LoginActivity;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerRVAdapter extends RecyclerView.Adapter<WorkerRVAdapter.ViewHolder> {

    private ArrayList<WorkerModel> workerModels;
    private Context mContext;

    public WorkerRVAdapter(WorkerDetailsViewModel workerDetailsViewModel, Context mContext) {
        this.workerModels = workerDetailsViewModel.getWorkerModels();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_list_item,parent,false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        WorkerModel workerModel = workerModels.get(position);
        holder.name.setText(workerModel.getName());
        holder.role.setText(workerModel.getRole());
        Picasso.get()
                .load(workerModel.getImgUrl())
                .noFade()
                .resizeDimen(R.dimen.profile_photo,R.dimen.profile_photo)
                .into(holder.circleImageView);
        holder.ll_worker_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return workerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView name,role;
        LinearLayout ll_worker_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.worker_name);
            role = itemView.findViewById(R.id.worker_role);
            ll_worker_item = itemView.findViewById(R.id.ll_worker_item);
        }
    }
}
