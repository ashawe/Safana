package com.ashstudios.safana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ashstudios.safana.R;
import com.ashstudios.safana.activities.TaskDetailsActivity;
import com.ashstudios.safana.models.TaskModel;

import java.util.ArrayList;

public class SupervisorTaskAdapter extends RecyclerView.Adapter<SupervisorTaskAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TaskModel> taskModelArrayList;

    public SupervisorTaskAdapter(Context context, ArrayList<TaskModel> taskModelArrayList) {
        this.context = context;
        this.taskModelArrayList = taskModelArrayList;
    }

    public void restoreItem(TaskModel item, int position) {
        taskModelArrayList.add(position,item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        taskModelArrayList.remove(position);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_count);
            name = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, TaskDetailsActivity.class);
                    context.startActivity(i);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_task_model,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel taskModel = taskModelArrayList.get(position);
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        TextDrawable textDrawable = TextDrawable.builder().buildRect(String.valueOf(position+1),colorGenerator.getRandomColor());
        holder.imageView.setImageDrawable(textDrawable);
        holder.name.setText(taskModel.getName());
        holder.date.setText(taskModel.getDate());
    }

    @Override
    public int getItemCount() {
        return taskModelArrayList.size();
    }

    public ArrayList<TaskModel> getData() {
        return taskModelArrayList;
    }
}
