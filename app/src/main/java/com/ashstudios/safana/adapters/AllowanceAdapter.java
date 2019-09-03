package com.ashstudios.safana.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ashstudios.safana.R;
import com.ashstudios.safana.models.AllowanceModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class AllowanceAdapter extends RecyclerView.Adapter<AllowanceAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AllowanceModel> allowanceModelArrayList;

    public AllowanceAdapter(Context context, ArrayList<AllowanceModel> allowanceModelArrayList) {
        this.context = context;
        this.allowanceModelArrayList = allowanceModelArrayList;
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
        AllowanceModel allowanceModel = allowanceModelArrayList.get(position);
        //holder.imageView.setImageResource(Integer.parseInt(allowanceModel.getUrl()));
        holder.imageView.setPadding(10,10,10,10);
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(allowanceModel.getUrl()).centerInside().fit().into(holder.imageView);
        holder.name.setTypeface(holder.name.getTypeface(), Typeface.BOLD);
        holder.name.setText(allowanceModel.getName());
        holder.date.setText(allowanceModel.getDuration());
    }

    @Override
    public int getItemCount() {
        return allowanceModelArrayList.size();
    }

}
