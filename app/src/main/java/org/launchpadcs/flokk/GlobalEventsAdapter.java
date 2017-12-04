package org.launchpadcs.flokk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ccirr on 10/19/2017.
 */

public class GlobalEventsAdapter extends RecyclerView.Adapter<GlobalEventsAdapter.MyViewHolder> {

    private static List<Event> eventsList;
    private Activity con;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, date, time, location;
        public ImageButton emailButton;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            location = (TextView) view.findViewById(R.id.location);
            cardView = (CardView) view.findViewById(R.id.cv);
            emailButton = (ImageButton) view.findViewById(R.id.emailButton);
        }
    }

    public GlobalEventsAdapter(List<Event> eventsList, Activity con) {
        this.eventsList = eventsList;
        this.con = con;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_events_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event event = eventsList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.location.setText(event.getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, EventInformation.class);
                String json = new Gson().toJson(event);
                intent.putExtra("jsonObject", json);
                con.startActivity(intent);

            }
        });
        holder.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{event.getEmail()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, event.getTitle());
                con.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

    }

    public int getItemCount() {
        return eventsList.size();
    }

    public static List<Event> getEventsList() {
        return eventsList;
    }


}
