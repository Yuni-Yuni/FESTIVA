package com.example.festiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> event_id, event_title, event_description;


    CustomAdapter(Context context, ArrayList<String> event_id, ArrayList<String> event_title, ArrayList<String> event_description){
        this.context = context;
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_description = event_description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.event_id.setText(String.valueOf(event_id.get(position)));
        holder.event_title.setText(String.valueOf(event_title.get(position)));
        holder.event_description.setText(String.valueOf(event_description.get(position)));

    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event_id, event_title, event_description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_id = itemView.findViewById(R.id.event_id_text);
            event_title = itemView.findViewById(R.id.event_title);
            event_description = itemView.findViewById(R.id.event_description);
        }
    }
}
