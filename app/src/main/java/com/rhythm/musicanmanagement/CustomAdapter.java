package com.rhythm.musicanmanagement;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList event_id,event_title,event_people,event_date,event_time,event_location,event_description;

    CustomAdapter(Activity activity,Context context,ArrayList event_id
            ,ArrayList event_title,ArrayList event_people,
                  ArrayList event_date,ArrayList event_time,
                  ArrayList event_location,ArrayList event_description){
        this.activity=activity;
        this.context=context;
        this.event_id=event_id;
        this.event_title=event_title;
        this.event_people=event_people;
        this.event_date=event_date;
        this.event_time=event_time;
        this.event_location=event_location;
        this.event_description=event_description;

    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.data_show,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.event_id_text.setText(String.valueOf(event_id.get(position)));
        holder.event_title_text.setText(String.valueOf(event_title.get(position)));
        holder.event_description_text.setText(String.valueOf(event_description.get(position)));
        holder.event_date_text.setText(String.valueOf(event_date.get(position)));
        holder.event_location_text.setText(String.valueOf(event_location.get(position)));
        try{
            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, update_event.class);
                i.putExtra("id",String.valueOf(event_id.get(position)));
                i.putExtra("title",String.valueOf(event_title.get(position)));
                i.putExtra("people",String.valueOf(event_people.get(position)));
                i.putExtra("date",String.valueOf(event_date.get(position)));
                i.putExtra("time",String.valueOf(event_time.get(position)));
                i.putExtra("location",String.valueOf(event_location.get(position)));
                i.putExtra("description",String.valueOf(event_description.get(position)));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivityForResult(i,1);

                }
            });
        }catch (Exception e){
            e.getMessage();

        }


    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_id_text,event_title_text,event_description_text,event_date_text,event_location_text;
        Button btn_edit,btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_id_text=itemView.findViewById(R.id.event_id_text);
            event_title_text=itemView.findViewById(R.id.event_title_text);
            event_description_text=itemView.findViewById(R.id.event_description_text);
            event_date_text=itemView.findViewById(R.id.event_date_text);
            event_location_text=itemView.findViewById(R.id.event_location_text);

            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_delete=itemView.findViewById(R.id.btn_delete);

        }
    }
}
