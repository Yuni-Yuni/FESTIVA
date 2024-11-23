package com.example.festiva;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForGreetings extends RecyclerView.Adapter<AdapterForGreetings.MyViewHolder> {

    private Context context;
    private ArrayList<String> event_title, greeting_text;
    private ArrayList<Integer> event_greeting_id;
    Activity activity;

    AdapterForGreetings(Activity activity, Context context, ArrayList<String> event_title,
                        ArrayList<Integer> event_greeting_id, ArrayList<String> greeting_text){
        this.activity = activity;
        this.context = context;
        this.event_title = event_title;
        this.event_greeting_id = event_greeting_id;
        this.greeting_text = greeting_text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_for_greetings, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.event_title.setText(String.valueOf(event_title.get(position)));

        //holder.event_greeting_id.setText(event_greeting_id.get(position));
        holder.greeting_text.setText(String.valueOf(greeting_text.get(position)));
        holder.EventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GetGreetingActivity.class);
                intent.putExtra("event_title", String.valueOf(event_title.get(position)));
                intent.putExtra("greeting_text", String.valueOf(greeting_text.get(position)));

                activity.startActivityForResult(intent, 5);
            }
        });

    }

    @Override
    public int getItemCount() {
        return event_title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event_title, greeting_text;
        LinearLayout EventLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_title = itemView.findViewById(R.id.event_title);
            greeting_text = itemView.findViewById(R.id.greeting_text);
            EventLayout = itemView.findViewById(R.id.GreetingLayout);

        }
    }

    // Метод для обновления данных
    public void updateData(ArrayList<String> newEventTitle, ArrayList<Integer> newEventGreetingID, ArrayList<String> newGreetingText) {
        this.event_title = newEventTitle;
        this.event_greeting_id = newEventGreetingID;
        this.greeting_text = newGreetingText;
    }

    public void deleteData() {
        this.event_title.clear();
        this.event_greeting_id.clear();
        this.greeting_text.clear();
    }

}
